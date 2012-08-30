/*
 * Created on 20/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.org.simadm2.simadmgui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * @author Servidor
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LabelUI extends JComponent {
	
	public static final Font FONT = new Font("Arial", Font.BOLD, 11);
	public static final Color COLOR = ModelComponentUI.COLOR;
	
	private String text;
	private final ModelComponentUI dono;
	
	public LabelUI(String text, ModelComponentUI parent){
		super();
		if(parent == null){
			throw new IllegalArgumentException("parent não pode ser nulo");
		}
		this.dono = parent;
		setText(text);
		setVisible(true);
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
		FontMetrics fm = getFontMetrics(FONT);
		setSize(fm.stringWidth(text) + 2, fm.getHeight() + 2);
		setLocation();
	}
	
	//centraliza o label em relação ao dono
	private void setLocation(){
		//setLocation(, parent.getHeight() + )
	}
	
	/**
	 * @see javax.swing.JComponent.contais(int, int)
	 */
	public boolean contains(int x, int y) {
		return getBounds().contains(x, y);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g) {
		g.setFont(FONT);
		g.setColor(COLOR);
		g.drawString(text, 1, getHeight() - 1);
		
		g.dispose();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		return getSize();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getMaximumSize()
	 */
	public Dimension getMaximumSize() {
		return getSize();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getMinimumSize()
	 */
	public Dimension getMinimumSize() {
		return getSize();
	}
}
