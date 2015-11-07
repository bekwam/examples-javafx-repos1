package com.bekwam.examples.javafx.macmenu;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MacMainMenuHybrid extends JFrame {

	private JFXPanel createMainFXWindow() throws Exception {
		JFXPanel jfxPanel = new JFXPanel();  //  initializes the toolkit
		FXMLLoader fxmlLoader = new FXMLLoader( this.getClass().getResource("/macmenu-fxml/MacMenu.fxml") );
		fxmlLoader.load();
		Parent p = fxmlLoader.getRoot();
		Scene scene = new Scene(p);
		jfxPanel.setScene( scene );
		return jfxPanel;
	}
	
	private static final long serialVersionUID = 4110897631836483138L;

	public MacMainMenuHybrid() {
	
		this.setLayout( new FlowLayout(FlowLayout.CENTER) );
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem closeItem = new JMenuItem("Close");
		closeItem.addActionListener((evt) -> {
			Platform.exit();
			System.exit(0);
		});
		
		fileMenu.add( closeItem );
		
		menubar.add( fileMenu );
		menubar.add( editMenu );
		menubar.add( helpMenu );
		
		this.setJMenuBar(menubar);
		
		try {

			JFXPanel mainFXWindow = createMainFXWindow();		
		
			this.getContentPane().add( mainFXWindow );
		
		} catch(Exception exc) {
			exc.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String[] args) {
		
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		
		MacMainMenuHybrid app = new MacMainMenuHybrid();
		app.setBounds(10, 10, 1024, 768);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
