require '_script'

on :chat do |params|
  packet = params['packet']

  message = Model::ChatMessage.new packet.message, packet.effects, packet.color
  params['player'].get_chat_queue.add message
  params['player'].get_update_flags.get_flags.add Model::UpdateFlagSet::UpdateType::CHAT
end
