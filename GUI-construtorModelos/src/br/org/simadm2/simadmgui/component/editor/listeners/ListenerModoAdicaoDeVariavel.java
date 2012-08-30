package br.org.simadm2.simadmgui.component.editor.listeners;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import br.org.simadm2.simadmgui.component.EditorCanvas;


public class ListenerModoAdicaoDeVariavel extends MouseInputAdapter{
	
	private EditorCanvas canvas;
	
	public ListenerModoAdicaoDeVariavel(EditorCanvas c) {
		canvas = c;
	}
	
	public void mouseClicked(MouseEvent event) {
		canvas.addVariavel(event.getX(), event.getY());
	}
}
