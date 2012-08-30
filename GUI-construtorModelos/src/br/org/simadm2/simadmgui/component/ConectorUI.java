package br.org.simadm2.simadmgui.component;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.event.MouseInputAdapter;

public class ConectorUI extends ComponenteSelecionavel{
	
	private static final int WIDTH = 7;
	private static final int HEIGHT = 7;
	private static final MouseInputAdapter mouseAdapter = new ConectorMouseAdapter();
	
	private final Shape shape;
	private CanalUI conexao;
	private boolean mouseOver;
	
	public ConectorUI(int x, int y){
		super();
		shape = new Ellipse2D.Double(0, 0, WIDTH - 2, HEIGHT - 2);
		setLocation(x, y);
		setSize(WIDTH, HEIGHT);
		addMouseMotionListener(mouseAdapter);
		addMouseListener(mouseAdapter);
	}
	
	public CanalUI getConexao() {
		return conexao;
	}

	public void setConexao(CanalUI conexao) {
		this.conexao = conexao;
	}
	
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	public void setLocation(int x, int y) {
		super.setLocation(x - WIDTH / 2, y - HEIGHT / 2);
	}
	
	public void setLocation(Point p) {
		this.setLocation(p.x, p.y);
	}
	
	public void setMouseOver(boolean b){
		mouseOver = b;
	}
	
	public boolean isMouseOver(){
		return mouseOver;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getMaximumSize()
	 */
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		if(isSelecionado()){
			g2.setColor(ModelComponentUI.COLOR);
		}
		else{
			g2.setColor(Color.WHITE);
		}
		if(isMouseOver()){
			g2.setColor(Color.RED);
		}
		
		g2.fill(shape);
		g2.setColor(ModelComponentUI.COLOR);
		g2.draw(shape);
		
		g2.dispose();
	}

	/**
	 * Retorna a localização deste conector em relação ao <code>Container</code> do 
	 * parent deste conector.
	 * 
	 * @return  Um ponto indicando a localização. Caso o conector não possua parent, 
	 * 			retorna <code>null</code>.
	 */
	public Point getLocationOnParentContainer(){
		Container c = getParent();
		if(c == null){
			return null;
		}
		return new Point(c.getX() + getX(), c.getY() + getY());
	}
	
	/**
	 * Retorna a localização do centro deste conector em relação ao <code>Container</code> do 
	 * parent deste conector.
	 * 
	 * @return  Um ponto indicando a localização. Caso o conector não possua parent, 
	 * 			retorna <code>null</code>.
	 */
	public Point getCenterLocationOnParentContainer(){
		Container c = getParent();
		if(c == null){
			return null;
		}
		return new Point(c.getX() + getX() + getWidth() / 2, c.getY() + getY() + getHeight() / 2);
	}
}

class ConectorMouseAdapter extends MouseInputAdapter{
	public void mouseEntered(MouseEvent event) {
		if(event.getComponent() instanceof ConectorUI){
			ConectorUI con = (ConectorUI)event.getComponent();
			con.setMouseOver(true);
			con.repaint();
		}
	}
	
	public void mouseExited(MouseEvent event) {
		if(event.getComponent() instanceof ConectorUI){
			ConectorUI con = (ConectorUI)event.getComponent();
			con.setMouseOver(false);
			con.repaint();
		}
	}
}