package com.parabolika.server.packet;

import com.parabolika.server.common.Packet;
import com.parabolika.server.packet.packets.IPacketRepresentation;

/**
 * A class for building and parsing <code>Packet</code> objects.
 */
public interface IPacketService {

	/**
	 * Parses a decoded <code>Packet</code> and returns the associated
	 * <code>IPacketRepresentation</code> object. If no parser for this packet
	 * is available, the method will return <code>null</code>.
	 *
	 * @param packet the packet to be parsed
	 * @return an <code>IPacketRepresentation</code> for the parsed packet
	 */
	public IPacketRepresentation parse(Packet packet);

	/**
	 * Builds a <code>Packet</code> from the specified
	 * <code>IPacketRepresenation</code>. Returns <code>null</code> if no
	 * builder is available for this packet. This method is responsible for
	 * building all of the required information for the returned
	 * <code>Packet</code> except for the written length, which will be computed
	 * by the <code>INetworkService</code>.
	 *
	 * @param packetRep the packet representation to be built from
	 * @return a <code>Packet</code> containing the constructed data
	 */
	public Packet build(IPacketRepresentation packetRep);
}
