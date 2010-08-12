require '_script'

on :button_click do |params|
  case params['packet'].buttonId
  when 153
    params['player'].get_walking_queue.set_running_toggled true
  when 152
    params['player'].get_walking_queue.set_running_toggled false
  when 2458
    params['client'].write params['packet_service'].build(Packets::LogoutPacket.new)
  end
end
