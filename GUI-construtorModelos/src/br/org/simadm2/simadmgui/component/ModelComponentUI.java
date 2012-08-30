package br.org.simadm2.simadmgui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Representação visual genérica de um componente de modelo.
 * @author Alexandre Lima
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class ModelComponentUI extends ComponenteSelecionavel {
	
	public static final Color COLOR = Color.BLUE;
	
	private String name;
	protected LabelUI label;
	protected List conectores = new ArrayList();
	protected Shape componentShape;
	private int xShape, yShape;
	
	public ModelComponentUI(String name){
		this.name = name;
		//a ordem dos métodos a seguir deve ser respeitada
		label = new LabelUI(name, this);
		add(label);
		calcularTamanho();
		componentShape = criarShape(xShape, yShape);
		posicionarLabel();
	}
	
	/**
	 * Cria a forma (shape) deste componente em uma posição específica.
	 * @param x	coordenada x da forma
	 * @param y	coordenada x da forma
	 * @return
	 */
	protected abstract Shape criarShape(int x, int y);
	
	/**
	 * Retorna as dimensões da forma (shape) deste componente, sem 
	 * incluir o texto (label)
	 * @return
	 */
	protected abstract Rectangle getShapeDimensions();
	
	/*public Rectangle getShapeBounds(){
		return componentShape.getBounds();
	}*/
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getMinimunSize()
	 */
	public Dimension getMinimumSize() {
		return getPreferredSize();
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
		return getSize();
	}
	
	public boolean contains(int x, int y) {
		if(label.contains(x, y)){
			return true;
		}
		if(componentShape.getBounds().contains(x, y)){
			return true;
		}
		return false;
	}
	
	/**
	 * Remove um componente da lista de componentes e repinta este componente.
	 * @param	O componente a ser removido.
	 */
	public void remove(Component comp) {
		super.remove(comp);
		repaint();
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		if(isSelecionado()){
			g2.setColor(COLOR);
		}
		else{
			g2.setColor(Color.WHITE);
		}
		g2.fill(componentShape);
		g2.setColor(COLOR);
		g2.draw(componentShape);
		
		//desenha os componentes filhos, neste caso o label e os conectores
		paintChildren(g2);
		
		g2.dispose();
	}
	
	//determina o tamanho deste componente e a posição da forma (shape)
	private void calcularTamanho(){
		int sobra = 3; //sobra de cada lado
		Rectangle rec = getShapeDimensions();
		if(label.getWidth() >= rec.getWidth()){
			setSize(label.getWidth() + sobra * 2, rec.height + label.getHeight() + sobra * 2);
			xShape = (int)label.getBounds().getCenterX() - rec.width / 2 + sobra;
			yShape = sobra;
		}
		else{
			setSize(rec.width + sobra * 2, rec.height + label.getHeight() + sobra * 2);
			xShape = sobra;
			yShape = sobra;
		}
	}
	
	//posiciona o label
	private void posicionarLabel(){
		Rectangle rec = getShapeDimensions();
		int xLabel = 1;
		if(label.getWidth() < rec.getWidth()){
			xLabel = (int)rec.getCenterX() - label.getWidth() / 2;
		}
		label.setLocation(xLabel, rec.height);
	}
	
	/**
	 * Cria e adiciona um conector a este componente. A localização do conector será calculada 
	 * pelo método <code>calcularPontoDoConector(Point)</code>.
	 * 
	 * @param	p	O ponto que será utilizado para calcular a posição do conector. Basicamente 
	 * 				trata-se do ponto em que o usuário clicou o componente.
	 * @return	O conector criado. Caso <code>p</code> não esteja na área da forma do componente, 
	 * 			retorna <code>null</code>.
	 */
	public ConectorUI adicionarConector(Point2D p){
		if(componentShape.contains(p)){
			Point2D conectorPoint = calcularPontoDoConector(p);
			ConectorUI con = new ConectorUI((int)conectorPoint.getX(), (int)conectorPoint.getY());
			add(con);
			
			MouseListener[] mls = getMouseListeners();
			for(int i = 0; i < mls.length; i++){
				con.addMouseListener(mls[i]);
			}
			MouseMotionListener[] mmls = getMouseMotionListeners();
			for(int i = 0; i < mmls.length; i++){
				con.addMouseMotionListener(mmls[i]);
			}
			
			repaint();
			return con;
		}
		return null;
	}
	
	/**
	 * Retorna a localização do centro do shape em relação ao parent deste componente.
	 * @return
	 */
	public Point getShapeCenterLocation(){
		Rectangle rec = componentShape.getBounds();
		return new Point((int)rec.getCenterX() + getX(), (int)rec.getCenterY() + getY());
	}
	
	/**
	 * Retorna um retângulo que contém o shape deste componente. Note que as coordenadas 
	 * de localização do retângulo retornado referem-se ao parent deste componente.
	 * @return
	 */
	public Rectangle2D getShapeBounds(){
		Rectangle rec = componentShape.getBounds();
		rec.setRect(rec.getX() + getX(), rec.getY() + getY(), rec.width, rec.height);
		return rec;
	}
	
	/**
	 * Calcula o ponto do conector, a partir do ponto clicado pelo usuário. 
	 * @param p
	 * @return
	 */
	protected abstract Point2D calcularPontoDoConector(Point2D p);
	
	/**
	 * Recalcula e atualiza a posição do conector recebido como parâmetro.
	 * @param p
	 * @param con
	 */
	public abstract void atualizarPosicaoDoConector(Point2D p, ConectorUI con);

	public Shape getComponentShape() {
		return componentShape;
	}
}
