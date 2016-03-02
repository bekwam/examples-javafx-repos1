package com.bekwam.examplees.javafx.nettyinaction.ch2;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

	private final int port;
	
	public EchoServer(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) throws Exception {
		if( args.length != 1 ) {
			System.err.println("usage: java EchoServer port");
			System.exit(1);
		}
		
		int port = Integer.parseInt(args[0]);
		new EchoServer(port).start();
	}
	
	public void start() throws Exception {
		
		final EchoServerHandler echoServerHandler = new EchoServerHandler();
		
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b
				.group(group)
				.channel(NioServerSocketChannel.class)
				.localAddress(new InetSocketAddress(port))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast( echoServerHandler );
					}					
				});
				
			// connect
			
			ChannelFuture f = b.bind().sync();
			
			// close
			
			f.channel().closeFuture().sync();
			
		} finally {
			group.shutdownGracefully().sync();
		}
	}
}
