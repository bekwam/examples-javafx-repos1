package com.bekwam.examples.javafx.nettyinaction.ch12;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private final String wsURI;
	private File indexHTML;
	
	public HttpRequestHandler(String wsURI) throws URISyntaxException {
		this.wsURI = wsURI;		
		String path = null;
		URL url = HttpRequestHandler.class.getResource("/index.html");
		path = url.getPath();
		indexHTML = new File(path);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		
		if( wsURI.equalsIgnoreCase(request.getUri()) ) {
			
			ctx.fireChannelRead(request.retain());
		
		} else {
			
			if( HttpHeaders.is100ContinueExpected(request) ) {
				send100Continue(ctx);
			}
			
			try (
				RandomAccessFile rFile = new RandomAccessFile(indexHTML, "r")
			) {
				HttpResponse response = new DefaultHttpResponse( request.getProtocolVersion(), HttpResponseStatus.OK );
				response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
				boolean keepAlive = HttpHeaders.isKeepAlive(request);
				if( keepAlive ) {
					response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, rFile.length());
					response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
				}
				
				ctx.write(response);
				
				if( ctx.pipeline().get(SslHandler.class) == null ) {
					ctx.write(new DefaultFileRegion(rFile.getChannel(), 0, rFile.length()));
				} else {
					ctx.write(new ChunkedNioFile(rFile.getChannel()));
				}
				
				ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
				
				if( !keepAlive ) {
					future.addListener(ChannelFutureListener.CLOSE);
				}
			}
		}
	}
	
	private void send100Continue(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		ctx.writeAndFlush( response );
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
