package com.parabolika.server.packet.translate.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import com.parabolika.server.packet.packets.IPacketRepresentation;
import com.parabolika.server.packet.translate.IPacketBuilder;
import com.parabolika.server.packet.translate.IPacketParser;
import com.parabolika.server.packet.translate.annotate.BuildsPacket;
import com.parabolika.server.packet.translate.annotate.ParsesPackets;

public class PacketTranslationServiceLoader {
	private static final Map<Class<? extends IPacketRepresentation>, IPacketBuilder> BUILDERS = loadBuilders();
	private static final Map<Integer, IPacketParser> PARSERS = loadParsers();

	private static Map<Class<? extends IPacketRepresentation>, IPacketBuilder> loadBuilders() {
		Map<Class<? extends IPacketRepresentation>, IPacketBuilder> map =
			new HashMap<Class<? extends IPacketRepresentation>, IPacketBuilder>();

		Iterator<IPacketBuilder> builders = ServiceLoader.load(IPacketBuilder.class).iterator();
		while(builders.hasNext()) {
			IPacketBuilder builder = builders.next();
			Class<? extends IPacketRepresentation> builds = builder.getClass().getAnnotation(BuildsPacket.class).value();
			map.put(builds, builder);
		}
		return Collections.unmodifiableMap(map);
	}

	private static Map<Integer, IPacketParser> loadParsers() {
		Map<Integer, IPacketParser> map = new HashMap<Integer, IPacketParser>();

		Iterator<IPacketParser> parsers = ServiceLoader.load(IPacketParser.class).iterator();
		while(parsers.hasNext()) {
			IPacketParser parser = parsers.next();
			for(int packetId : parser.getClass().getAnnotation(ParsesPackets.class).value()) {
				map.put(packetId, parser);
			}
		}
		return Collections.unmodifiableMap(map);
	}

	public static Map<Class<? extends IPacketRepresentation>, IPacketBuilder> getBuilders() {
		return BUILDERS;
	}

	public static Map<Integer, IPacketParser> getParsers() {
		return PARSERS;
	}
}
