require '_script'

# TODO: Refactor this.
on :object_action do |params|
  packet = params['packet']
  object_loc = Model::Location.create(packet.objectX, packet.objectY, params['player'].get_location.get_z)
  params.put('object_loc', object_loc)

  block = case packet.objectId
  when 299
    lambda do
      params['client'].write params['packet_service'].build(Packets::MessagePacket.new "There's nothing in there!")
    end
  when 823
    lambda do
      params['client'].write params['packet_service'].build(Packets::MessagePacket.new "You fail to hit the dummy!")
    end
  when 2213
    lambda do
      params['client'].write params['packet_service'].build(Packets::MessagePacket.new "Cool, have some money.")
    end
  end
  params['player'].get_action_queue.add Model::ActionFuture.new(params, block)
end
