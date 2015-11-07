package com.bekwam.examples.javafx.macmenu;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.EmptyBorder;

public class MacMenuSwing extends JFrame {

	private static final long serialVersionUID = 4110897631836483138L;

	public MacMenuSwing() {
	
		this.setLayout( new FlowLayout(FlowLayout.CENTER) );
		
		JMenuBar menubar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem closeItem = new JMenuItem("Close");
		closeItem.addActionListener((evt) -> {
			System.exit(0);
		});
		
		fileMenu.add( closeItem );
		
		menubar.add( fileMenu );
		menubar.add( editMenu );
		menubar.add( helpMenu );
		
		JLabel label = new JLabel("Hello, World! (From Swing)");
		label.setBorder( new EmptyBorder(10, 10, 10, 10));
		
		this.setJMenuBar(menubar);
		
		this.getContentPane().add( label );
	}
	
	public static void main(String[] args) {
		
		System.setProperty("apple.laf.useScreenMenuBar", "false");
		
		MacMenuSwing app = new MacMenuSwing();
		app.setBounds(10, 10, 1024, 768);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
