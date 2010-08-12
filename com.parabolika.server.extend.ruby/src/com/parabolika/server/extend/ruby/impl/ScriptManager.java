package com.parabolika.server.extend.ruby.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import org.jruby.RubyInstanceConfig.CompileMode;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

import com.parabolika.server.common.GenericServiceTracker;
import com.parabolika.server.packet.IPacketService;

public class ScriptManager {
	private final String scriptBasePath;
	private final ScriptingContainer container;
	private final GenericServiceTracker<IPacketService> packetServiceTracker;

	public ScriptManager(String scriptStartingPoint,
			GenericServiceTracker<IPacketService> packetServiceTracker) {
		this.scriptBasePath = scriptStartingPoint;
		this.packetServiceTracker = packetServiceTracker;

		/*
		 * The SINGLETHREADED context makes sure that the container and
		 * associated scripts are reloaded fully each time the service is
		 * refreshed or updated.
		 */
		container = new ScriptingContainer(LocalContextScope.SINGLETHREAD);
		container.setLoadPaths(Arrays.asList(scriptStartingPoint));
		container.setCompileMode(CompileMode.JIT);
	}

	public void callScripts(String eventName, Map<String, Object> params) {
		params.put("packet_service", packetServiceTracker.getService());
		container.callMethod(null, "fire_event", eventName, params);
	}

	public void start() {
		container.runScriptlet(PathType.ABSOLUTE, scriptBasePath
				+ File.separator + "_script.rb");
	}

	public void stop() {
		container.clear();
	}
}
