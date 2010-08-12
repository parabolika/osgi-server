package com.parabolika.server.net;

import java.util.List;

/**
 * A fully self-contained server which will accept new connections, parse and
 * encode messages, and fire events as they occur.
 * <p>
 * The following events should be produced:
 * <ul>
 * <li>com/parabolika/server/net/CHANNEL_OPEN</li>
 * <li>com/parabolika/server/net/PACKET_ARRIVE</li>
 * <li>com/parabolika/server/net/CHANNEL_CLOSE</li>
 * </ul>
 * <p>
 * Every event should contain a property <code>client</code> referencing the
 * <code>INetworkClient</code> associated with the event. In addition to this,
 * the <code>PACKET_ARRIVE</code> event should also contain a property
 * <code>packet</code> referencing a <code>Packet</code> instance.
 * <p>
 * Note that an <code>INetworkService</code> implementation should output no
 * logging information above the <code>DEBUG</code> level, barring an error. The
 * only side-effects it should produce are events to be managed by the OSGi
 * container.
 */
public interface INetworkService {

	/**
	 * Returns a <code>List</code> of connected clients being managed by this
	 * <code>INetworkService</code>.  Client should be automatically registered
	 * and unregistered upon connection and disconnecting by the implementation.
	 *
	 * @return the list of current managed clients
	 */
	public List<INetworkClient> getClients();
}
