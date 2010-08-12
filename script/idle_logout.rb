require '_script'

on :idle_logout do |params|
  params['client'].write params['packet_service'].build(Packets::LogoutPacket.new)
end
