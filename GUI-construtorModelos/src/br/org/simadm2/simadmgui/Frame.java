package br.org.simadm2.simadmgui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import br.org.simadm2.simadmgui.component.EditorPanel;


/*
 * Created on 18/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Servidor
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Frame extends JFrame{
	
	private EditorPanel panel;
	
	public Frame(){
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel = new EditorPanel();
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel, BorderLayout.CENTER);
		
	}
	
	public static void main(String[] args) {
		Frame f = new Frame();
		f.setSize(500, 500);
		f.setLocation(200, 100);
		f.setVisible(true);
	}
}
