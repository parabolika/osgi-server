require '_script'

SIDEBARS = { 1 => 3917, 2 => 638, 3 => 3213, 4 => 1644, 5 => 5608, 6 => 1151,
  8 => 5065, 9 => 5715, 10 => 2449, 11 => 4445, 12 => 147, 13 => 2699, 0 => 2423 }

on :login do |c|
  c.send_packet Packets::LoginResponsePacket.new 2, 2, 0

  persistor = Persist::Persistors.get(Model::PlayerBuilder.java_class)
  builder = Model::PlayerBuilder.new.
        with_uuid(c.client.get_uuid).
        with_index(Model::World.get_world.get_next_available_index).
        with_details(c.packet.username, c.packet.password, 0)
  builder = persistor.get(builder, c.packet.username)
  player = builder.build

  Model::World.get_world.add_player player

  session_keys = c.packet.sessionKeys
  c.client.set_incoming_cipher_keys session_keys
  session_keys = session_keys.map { |key| key + 50 }
  c.client.set_outgoing_cipher_keys session_keys

  (0..20).each { |skill| c.send_packet(Packets::SetSkillPacket.new(skill, 1, 99)) }
  SIDEBARS.each do |id, interface|
    c.send_packet Packets::SetInterfacePacket.new(id, interface)
  end

  c.send_packet Packets::PlayerDetailsPacket.new(player.get_index, true)

  player.get_update_flags.get_flags.add Model::UpdateFlagSet::UpdateType::APPEARANCE
  player.set_map_region_changing true

  c.send_packet Packets::MessagePacket.new("Welcome to Runescape.")
end
