package com.parabolika.server.net;

import java.util.UUID;

import com.parabolika.server.common.Packet;

/**
 * A representation of a single connection to the network service. Instances are
 * created by the underlying network service, and should not be created by the
 * user.
 */
public interface INetworkClient {

	/**
	 * Returns a unique identifier for this client. This identifier is not
	 * persistent across connections, so it should not be used as a key for
	 * storing player information. It should be used to match a
	 * <code>Player</code> instance to an <code>INetworkClient</code> instance.
	 * 
	 * @return the identifier.
	 */
	public UUID getUUID();

	/**
	 * Writes a <code>Packet</code> to this <code>INetworkClient</code>'s output
	 * stream. The <code>Packet</code> object is encoded to the proper format by
	 * the underlying services. Note that this method may or may not block,
	 * depending upon the underlying implementation details.
	 *
	 * @param packet the packet to be written
	 */
	public void write(Packet packet);

	/**
	 * Sets the incoming ISAAC cipher keys.  These keys are used as offsets for
	 * incoming packet IDs.  The keys are exchanged during the login process and
	 * remain null until the login process is complete.  Normally, these keys
	 * would be transmitted in an RSA encrypted block, but private servers
	 * generally disable the encryption.
	 */
	public void setIncomingCipherKeys(int[] keys);

	/**
	 * Sets the outgoing ISAAC cipher keys.
	 * @see #setIncomingCipherKeys(int[])
	 *
	 * @param keys the set of 4 keys to be passed to the ISAAC algorithm
	 */
	public void setOutgoingCipherKeys(int[] keys);
}
