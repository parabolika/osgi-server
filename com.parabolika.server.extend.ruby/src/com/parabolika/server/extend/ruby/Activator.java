package com.parabolika.server.extend.ruby;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parabolika.server.common.GenericServiceTracker;
import com.parabolika.server.extend.ruby.impl.GameEventProxy;
import com.parabolika.server.extend.ruby.impl.ScriptManager;
import com.parabolika.server.packet.IPacketService;

/*
 * JRuby bundle must set `DynamicImport-Package: *' to import anonymous
 * classes within ruby scripts.  Make sure to do this every time the JRuby
 * bundle is updated.  Would like to find a way around this, as it violates
 * some OSGi principles.
 */
public class Activator implements BundleActivator {
	private static final Logger logger = LoggerFactory.getLogger(Activator.class);
	// XXX: Figure out how to get this from the OSGi container
	private static final String SCRIPTS_DIR = System.getProperty("server.extend.ruby.scripts_path");

	private GenericServiceTracker<IPacketService> packetServiceTracker = null;
	private ScriptManager scriptManager = null;
	private ServiceRegistration gameEventProxyReg = null;

	@Override
	public void start(BundleContext context) throws Exception {
		if(SCRIPTS_DIR == null) {
			throw new NullPointerException("Please define server.extend.ruby.scripts_path");
		}

		packetServiceTracker = new GenericServiceTracker<IPacketService>(context,  IPacketService.class.getName(), null);
		packetServiceTracker.open();

		scriptManager = new ScriptManager(SCRIPTS_DIR, packetServiceTracker);
		scriptManager.start();

		Dictionary<String, Object> proxyParams = new Hashtable<String, Object>();
		proxyParams.put(EventConstants.EVENT_TOPIC, "com/parabolika/server/event/GAME_EVENT");
		gameEventProxyReg = context.registerService(EventHandler.class.getName(), new GameEventProxy(scriptManager), proxyParams);

		logger.info("Ruby extension service started");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		gameEventProxyReg.unregister();
		scriptManager.stop();
		packetServiceTracker.close();
	}
}
