package com.bekwam.examples.javafx.nettyinaction.ch12;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.util.CharsetUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * JavaFX Controller interacting with a WebSocket server
 * 
 * Uses Netty for WebSocket protocol
 * 
 * Based on WebSocketClient example in Netty project
 * 
 * @author carlwalker
 *
 */
public class EchoClientControllerWS {

	private Logger logger = LoggerFactory.getLogger( EchoClientWS.class );

	@FXML
	TextField tfSend;
	
	@FXML
	TextField tfReceive;
	
	@FXML
	TextField tfHost;
	
	@FXML
	TextField tfPort;
	
	@FXML
	Button btnConnect;
	
	@FXML
	Button btnSend;
	
	@FXML
	Button btnDisconnect;
	
	@FXML
	HBox hboxStatus;
	
	@FXML
	ProgressIndicator piStatus;
	
	@FXML
	Label lblStatus;
	
	private BooleanProperty connected = new SimpleBooleanProperty(false);
	private StringProperty receivingMessageModel = new SimpleStringProperty("");
	private Channel channel;
	private EventLoopGroup group;
	
	@FXML
	public void initialize() {
		
		hboxStatus.setVisible(false);
		
		btnConnect.disableProperty().bind( connected );
		tfHost.disableProperty().bind( connected );
		tfPort.disableProperty().bind( connected );
		tfSend.disableProperty().bind( connected.not() );
		btnDisconnect.disableProperty().bind( connected.not() );
		btnSend.disableProperty().bind( connected.not() );

		tfReceive.textProperty().bind(receivingMessageModel);
	}

	@FXML
	public void send() {
		if( logger.isDebugEnabled() ) {
			logger.debug("[SEND]");
		}
		
		if( !connected.get() ) {
			if( logger.isWarnEnabled() ) {
				logger.warn("client not connected; skipping write");
			}
			return;
		}
		
		final String toSend = tfSend.getText();
		
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				
				TextWebSocketFrame frame = new TextWebSocketFrame(true, 0, Unpooled.copiedBuffer(toSend, CharsetUtil.UTF_8));
				ChannelFuture f = channel.writeAndFlush( frame);
				f.sync();

				return null;
			}
			
			@Override
			protected void failed() {
				
				Throwable exc = getException();
				logger.error( "client send error", exc );
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Client");
				alert.setHeaderText( exc.getClass().getName() );
				alert.setContentText( exc.getMessage() );
				alert.showAndWait();
				
				connected.set(false);
			}

		};
		
		hboxStatus.visibleProperty().bind( task.runningProperty() );
		lblStatus.textProperty().bind( task.messageProperty() );
		piStatus.progressProperty().bind(task.progressProperty());
		
		new Thread(task).start();
	}
	
	@FXML
	public void connect() throws URISyntaxException {
		
		if( connected.get() ) {
			if( logger.isWarnEnabled() ) {
				logger.warn("client already connected; skipping connect");
			}
			return;  // already connected; should be prevented with disabled
		}
		
		String host = tfHost.getText();
		int port = Integer.parseInt(tfPort.getText());

		group = new NioEventLoopGroup();
		
		final WebSocketClientProtocolHandler handler =
				  new WebSocketClientProtocolHandler(
						  WebSocketClientHandshakerFactory.newHandshaker(
								  new URI("ws://" + host + "/ws"), WebSocketVersion.V13, null, false, new DefaultHttpHeaders()));
		  
		Task<Channel> task = new Task<Channel>() {

			@Override
			protected Channel call() throws Exception {
				
				updateMessage("Bootstrapping");
				updateProgress(0.1d, 1.0d);
				
				Bootstrap b = new Bootstrap();
				b
					.group(group)
					.channel(NioSocketChannel.class)
					.remoteAddress( new InetSocketAddress(host, port) )
					.handler( new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							p.addLast(new HttpClientCodec());
							p.addLast(new HttpObjectAggregator(8192));
							p.addLast(handler);
							p.addLast(new EchoClientHandlerWS(receivingMessageModel));
						}
					});
				
				updateMessage("Connecting");
				updateProgress(0.2d, 1.0d);

				ChannelFuture f = b.connect();				
				f.sync();
				Channel chn = f.channel();

				if( logger.isDebugEnabled() ) {
					logger.debug("[CONNECT] channel active=" + chn.isActive() + ", open=" + chn.isOpen() + ", register=" + chn.isRegistered() + ", writeable=" + chn.isWritable());
				}

				return chn;
			}

			@Override
			protected void succeeded() {
				
				channel = getValue();
				connected.set(true);
			}

			@Override
			protected void failed() {
				
				Throwable exc = getException();
				logger.error( "client connect error", exc );
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Client");
				alert.setHeaderText( exc.getClass().getName() );
				alert.setContentText( exc.getMessage() );
				alert.showAndWait();
				
				connected.set(false);
			}
		};
		
		hboxStatus.visibleProperty().bind( task.runningProperty() );
		lblStatus.textProperty().bind( task.messageProperty() );
		piStatus.progressProperty().bind(task.progressProperty());
		
		new Thread(task).start();
	}
	
	@FXML
	public void disconnect() {
		
		if( !connected.get() ) {
			if( logger.isWarnEnabled() ) {
				logger.warn("client not connected; skipping disconnect");
			}
			return;
		}
		
		if( logger.isDebugEnabled() ) {
			logger.debug("[DISCONNECT]");
		}
		
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				
				updateMessage("Disconnecting");
				updateProgress(0.1d, 1.0d);
				
				channel.close().sync();					

				updateMessage("Closing group");
				updateProgress(0.5d, 1.0d);
				group.shutdownGracefully().sync();

				return null;
			}

			@Override
			protected void succeeded() {
				
				connected.set(false);
			}

			@Override
			protected void failed() {
				
				connected.set(false);

				Throwable t = getException();
				logger.error( "client disconnect error", t );
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Client");
				alert.setHeaderText( t.getClass().getName() );
				alert.setContentText( t.getMessage() );
				alert.showAndWait();

			}
			
		};
		
		hboxStatus.visibleProperty().bind( task.runningProperty() );
		lblStatus.textProperty().bind( task.messageProperty() );
		piStatus.progressProperty().bind(task.progressProperty());

		new Thread(task).start();
	}
}

