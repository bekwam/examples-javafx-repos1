package com.bekwam.examples.javafx.nettyinaction.ch12;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;

@Sharable
public class EchoClientHandlerWS extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private Logger logger = LoggerFactory.getLogger( EchoClientHandlerWS.class );

	private final StringProperty receivingMessageModel;
	
	public EchoClientHandlerWS(StringProperty receivingMessageModel) {
		this.receivingMessageModel = receivingMessageModel;
	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		if( logger.isDebugEnabled() ) {
			logger.debug("[CHANNEL ACTIVE]");
		}
	}


	@Override
	protected void channelRead0(ChannelHandlerContext arg0, TextWebSocketFrame in) throws Exception {
		
		if( logger.isDebugEnabled() ) {
			logger.debug("[CHANNEL READ0] channel active=" + arg0.channel().isActive() + ", open=" + arg0.channel().isOpen());
		}
		
		final String cm = in.text();
		Platform.runLater( () -> receivingMessageModel.set(cm) );
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error( "error in echo client", cause );
		ctx.close();
	}	
}
