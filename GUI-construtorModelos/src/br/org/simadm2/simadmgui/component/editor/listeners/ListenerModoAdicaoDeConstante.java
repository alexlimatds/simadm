package br.org.simadm2.simadmgui.component.editor.listeners;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import br.org.simadm2.simadmgui.component.EditorCanvas;


public class ListenerModoAdicaoDeConstante extends MouseInputAdapter{
	
	private EditorCanvas canvas;
	
	public ListenerModoAdicaoDeConstante(EditorCanvas c) {
		canvas = c;
	}
	
	public void mouseClicked(MouseEvent event) {
		canvas.addConstante(event.getX(), event.getY());
	}
}
