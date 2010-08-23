require '_script'

on :button_click do |c|
  case c.packet.buttonId
  when 153
    c.player.get_walking_queue.set_running_toggled true
  when 152
    c.player.get_walking_queue.set_running_toggled false
  when 2458
    c.send_packet Packets::LogoutPacket.new
    persistor = Persist::Persistors.get(Model::PlayerBuilder.java_class)
    persistor.put(c.player)
  end
end
