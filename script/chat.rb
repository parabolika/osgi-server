require '_script'

on :chat do |c|
  message = Model::ChatMessage.new c.packet.message, c.packet.effects, c.packet.color
  c.player.get_chat_queue.add message
  c.player.get_update_flags.get_flags.add Model::UpdateFlagSet::UpdateType::CHAT
end
