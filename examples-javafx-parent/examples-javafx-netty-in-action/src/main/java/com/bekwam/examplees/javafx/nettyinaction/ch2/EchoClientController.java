package com.bekwam.examplees.javafx.nettyinaction.ch2;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EchoClientController {

	private Logger logger = LoggerFactory.getLogger( EchoClient.class );

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
	
	private BooleanProperty connected = new SimpleBooleanProperty(false);
	private StringProperty currentMessageModel = new SimpleStringProperty("");
	
	@FXML
	public void initialize() {
		tfReceive.textProperty().bind( this.currentMessageModel );
		btnConnect.disableProperty().bind( connected );
		tfHost.disableProperty().bind( connected );
		tfPort.disableProperty().bind( connected );
		tfSend.disableProperty().bind( connected );
		btnDisconnect.disableProperty().bind( connected.not() );
		btnSend.disableProperty().bind( connected.not() );

		tfSend.textProperty().bind(currentMessageModel);
	}

	@FXML
	public void send() {
		
	}
	
	@FXML
	public void connect() {
		
		String host = tfHost.getText();
		int port = Integer.parseInt(tfPort.getText());

		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
		
			Bootstrap b = new Bootstrap();
			b
				.group(group)
				.channel(NioSocketChannel.class)
				.remoteAddress( new InetSocketAddress(host, port) )
				.handler( new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new EchoClientHandler(currentMessageModel));
					}
				});
			
			ChannelFuture f = b.connect();
			
			f.addListener((arg0) -> {
				Platform.runLater(() -> connected.set(true) );
			});
			
			f.sync();
			
			ChannelFuture cf = f.channel().closeFuture();
			
			cf.addListener((arg0) -> {
				Platform.runLater(() -> connected.set(false) );
			});
			
			cf.sync();
			
		} catch(Exception exc) {
			
			logger.error( "client error", exc );
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Client");
			alert.setHeaderText( exc.getClass().getName() );
			alert.setContentText( exc.getMessage() );
			alert.showAndWait();
			
			connected.set(false);

		} finally {
			try {
				group.shutdownGracefully().sync();
			} catch(Exception exc) {
				logger.error( "shutdown error", exc );
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Shutdown");
				alert.setHeaderText( exc.getClass().getName() );
				alert.setContentText( exc.getMessage() );
				alert.showAndWait();
				
				connected.set(false);
			}
		}
	}
	
	@FXML
	public void disconnect() {
		
	}
}
