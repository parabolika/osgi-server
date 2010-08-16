OSGi-Server
===========
A Runescape private server implementation written on top of OSGi in Java.  Provides a very decoupled system, making it easy to reimplement a module differently.

Networking
----------
The base distribution provides two networking implementations: Netty and MINA.  The Netty implementation is preferred, as the MINA implementation isn't yet completed.

Some base classes are provided for convenience to new networking services in `com.parabolika.server.net.base`.

Scripting
---------
By default, a Ruby scripting environment is available, but it is quite simple to add different languages by intercepting events and handling them accordingly.

The plugin API is the preferred way to add functionality, beyond core needs to get the server running.  By default, walking, logging in, chatting, and many other things are implemented in Ruby instead of Java.

### Ruby
The Ruby plugin system runs on JRuby and uses a simple DSL to listen for events.  For instance, you can just place the following in the scripts/ directory and it will automatically be loaded:

    on :login do |params|
      puts "Someone logged in with #{params}"
    end

