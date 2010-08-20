class Context
  def send_packet(packet)
    client.write packet_service.build(packet)
  end

  def send_message(message)
    send_packet Packets::MessagePacket.new(message)
  end
end
