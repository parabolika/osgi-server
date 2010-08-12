require '_script'

on :pre_player_tick do |params|
  params['player'].get_walking_queue.process_next_movement

  # Update the local player list
  other_players = params['player'].get_local_player_entries
  for other_player_entry in other_players
    other_player = other_player_entry.get_player
    if !Model::World.get_world.get_players.contains(other_player) ||
      !other_player.get_location.is_within_distance(params['player'].get_location)
      other_player_entry.set_status Model::LocalPlayerListEntry::LocalPlayerStatus::BEING_REMOVED
    else
      other_player_entry.set_status Model::LocalPlayerListEntry::LocalPlayerStatus::STEADY
    end
  end

  for other_player in Model::World.get_world.get_players
    next if params['player'].equals other_player
    next if params['player'].get_local_player_entries.contains other_player

    # TODO: This is just wrong.
    in_list = false
    for list_player in params['player'].get_local_player_entries
      if list_player.get_player.equals other_player
        in_list = true
        break
      end
    end

    if !in_list && other_player.get_location.is_within_distance(params['player'].get_location)
      params['player'].get_local_player_entries.add Model::LocalPlayerListEntry.new(other_player, Model::LocalPlayerListEntry::LocalPlayerStatus::BEING_ADDED)
    end
  end
end

on :player_tick do |params|
  player = params['player']

  # Manage the walking queue
  if player.is_map_region_changing
    region_packet = Packets::MapRegionPacket.new player.get_location.region_x, player.get_location.region_y
    params['client'].write params['packet_service'].build(region_packet)
    player.set_last_known_region player.get_location
  end

  # Send an update packet every tick
  update_packet = Packets::PlayerUpdatePacket.new player
  params['client'].write params['packet_service'].build(update_packet)

  # Manage and perform actions from the action queue
  action_queue = params['player'].get_action_queue
  if action = action_queue.peek
    context = action.get_context
    block = action.get_callable

    if player.get_location.is_within_interaction_distance(context['object_loc'])
      action_queue.poll
      block.call
    end
  end
end

on :post_player_tick do |params|
  player = params['player']

  for other_player_entry in player.get_local_player_entries
    if other_player_entry.get_status == Model::LocalPlayerListEntry::LocalPlayerStatus::BEING_REMOVED
      player.get_local_player_entries.remove other_player_entry
    end
  end

  player.get_update_flags.get_flags.clear
  player.set_teleporting false
  player.set_teleport_target nil
  player.set_map_region_changing false
  player.get_chat_queue.poll
end
