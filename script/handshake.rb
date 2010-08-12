require '_script'

BIG_NUM = 999999999999999999

on :handshake do |params|
  params['client'].write params['packet_service'].build(Packets::HandshakeResponsePacket.new 0, (rand * BIG_NUM).to_i)
end
