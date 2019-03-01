package com.rooomy;

import com.rooomy.gui.MainPage;

import javax.swing.*;

public class Application extends JFrame{

	private static final long serialVersionUID = 1L;

	public Application() {
		super("CLTFConverter");
		this.getContentPane().add(new MainPage());
		
		this.setSize(620,650);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);	
		this.setResizable(false);
	}
	
	public static void main(String[] args) {
		new Application();
	}
}
