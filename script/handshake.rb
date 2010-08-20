require '_script'

BIG_NUM = 999999999999999999

on :handshake do |c|
  c.send_packet(Packets::HandshakeResponsePacket.new 0, (rand * BIG_NUM).to_i)
end
