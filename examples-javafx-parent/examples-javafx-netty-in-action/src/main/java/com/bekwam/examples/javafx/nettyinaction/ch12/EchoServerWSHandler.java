package com.bekwam.examples.javafx.nettyinaction.ch12;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * Business logic for EchoServerWS process
 * 
 * Echo back input to source with a transformation (capitalization)
 * 
 * Based on "Netty in Action" example in Ch 12
 * 
 * @author carlwalker
 *
 */
public class EchoServerWSHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private Logger logger = LoggerFactory.getLogger( EchoServerWSHandler.class );

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {	
		String text = msg.text().toUpperCase();
		TextWebSocketFrame outFrame = new TextWebSocketFrame(true, 0, text);
		ctx.channel().writeAndFlush(outFrame);  // send back a transformed frame
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("error processing websocket frame", cause);
		ctx.close();
	}
}
