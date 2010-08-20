require 'java'

module Packets
  include_package 'com.parabolika.server.packet.packets'
end

module Model
  include_package 'com.parabolika.server.model'
end

@handlers = {}

class Context
  attr_reader :player, :client, :packet, :packet_service

  def initialize(player, client, packet, packet_service)
    @player, @client, @packet, @packet_service = player, client, packet, packet_service
  end
end

def on(event_name, &block)
  (@handlers[event_name] ||= []) << block
end

def fire_event(event_name, params)
  context = Context.new *(params.values)
  (@handlers[event_name.to_sym] ||= []).each { |h| h.call(context) }
end

def load_handlers
  scripts_dir = File.expand_path(File.dirname(__FILE__))
  Dir.foreach(scripts_dir) do |file|
    if file =~ /\.rb$/ && file != File.basename(__FILE__)
      require file
    end
  end
end

load_handlers
