require '_script'

on :idle_logout do |c|
  c.send_packet(Packets::LogoutPacket.new)
end
