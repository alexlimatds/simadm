package br.org.simadm2.simadmgui;
import java.awt.BorderLayout;

import javax.swing.JApplet;

import br.org.simadm2.simadmgui.component.EditorPanel;



public class ModeladorApplet extends JApplet {
	private EditorPanel panel;
	
	public void init() {
		panel = new EditorPanel();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel, BorderLayout.CENTER);
	}
}
