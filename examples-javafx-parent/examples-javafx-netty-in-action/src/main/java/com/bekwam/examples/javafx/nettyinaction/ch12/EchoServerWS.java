package com.bekwam.examples.javafx.nettyinaction.ch12;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.ImmediateEventExecutor;

public class EchoServerWS {

	private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);	
	private final EventLoopGroup group = new NioEventLoopGroup();
	private Channel channel;

	public ChannelFuture start(InetSocketAddress address) {
		
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap
			.group(group)
			.channel(NioServerSocketChannel.class)
			.childHandler(createInitializer(channelGroup));
		
		ChannelFuture future = bootstrap.bind(address);
		future.syncUninterruptibly();
		channel = future.channel();

		return future;
	}
	
	protected ChannelInitializer<Channel> createInitializer(ChannelGroup channelGroup) {
	
		return new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast(new HttpServerCodec() );
				p.addLast(new ChunkedWriteHandler());
				p.addLast(new HttpObjectAggregator(64 * 1024));
				p.addLast(new HttpRequestHandler("/ws"));
				p.addLast(new WebSocketServerProtocolHandler("/ws"));
				p.addLast(new TextWebSocketFrameHandler(channelGroup));
			}
			
		};
	}
	
	public void destroy() {
		if( channel != null ) {
			channel.close();
		}
		channelGroup.close();
		group.shutdownGracefully();
	}
	
	public static void main(String[] args) throws Exception {
		if( args.length != 1 ) {
			System.err.println("usage: java EchoServerWS port");
			System.exit(1);
		}
		
		int port = Integer.parseInt(args[0]);
		EchoServerWS endpoint = new EchoServerWS();
		ChannelFuture future = endpoint.start(new InetSocketAddress(port));
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				endpoint.destroy();
			}			
		});
		future.channel().closeFuture().syncUninterruptibly();
	}
}
