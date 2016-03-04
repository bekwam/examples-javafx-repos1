package com.bekwam.examplees.javafx.nettyinaction.ch2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;

@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	private Logger logger = LoggerFactory.getLogger( EchoClientHandler.class );

	private final StringProperty receivingMessageModel;
	
	public EchoClientHandler(StringProperty receivingMessageModel) {
		this.receivingMessageModel = receivingMessageModel;
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext arg0, ByteBuf in) throws Exception {
		final String cm = in.toString(CharsetUtil.UTF_8);
		Platform.runLater( () -> receivingMessageModel.set(cm) );
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//ctx.writeAndFlush(Unpooled.copiedBuffer(receivingMessageModel.get(), CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error( "error in echo client", cause );
		ctx.close();
	}	
}
