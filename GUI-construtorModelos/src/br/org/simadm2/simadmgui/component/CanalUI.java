package br.org.simadm2.simadmgui.component;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import br.org.simadm2.simadmgui.geometric.Arrow;
import br.org.simadm2.simadmgui.util.Geometrics;


public class CanalUI extends JComponent {
	
	private ConectorUI origem;
	private ModelComponentUI destino;
	private Point tempPoint;
	private Arc2D arco;
	
	public CanalUI(ConectorUI origem){
		super();
		if(origem == null){
			throw new IllegalArgumentException("Origem não pode ser nulo");
		}
		this.origem = origem;
		origem.getParent().getParent().add(this);
		tempPoint = origem.getLocation();
		arco = new Arc2D.Double(Arc2D.OPEN);
		calcularMudancas();
	}
	
	public void setDestino(ModelComponentUI destino) {
		this.destino = destino;
		repaint();
	}

	public Point getTempPoint() {
		return tempPoint;
	}

	public void setTempPoint(Point tempPoint) {
		this.tempPoint = tempPoint;
		repaint();
	}

	public ConectorUI getOrigem() {
		return origem;
	}

	public ModelComponentUI getDestino() {
		return destino;
	}
	
	public Dimension getPreferredSize(){
		calcularMudancas();
		return getSize();
	}
	
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
	
	protected void paintComponent(Graphics g) {
		calcularMudancas();
		Point p1 = new Point(); //conector
		Point p2 = new Point(); //destino
		Point p3 = new Point(); //centro da origem
		getPoints(p1, p2, p3); // os pontos são relativos ao parent deste canal
		Point2D centro = Geometrics.getCenterPoint(p1, p2, p3);
		
		//as linhas abaixo são somente para fins de debug
		/*g.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
		  g.drawLine(getWidth()/2, 1, getWidth()/2, getHeight() - 1);
		  g.drawLine(1, getHeight()/2, getWidth()- 1 , getHeight()/2);
		  g.drawArc(1, 1, getWidth() - 2, getHeight() - 2, 0, 360);*/
		//fim de linhas de debug
		Graphics2D g2 = (Graphics2D)g;
		g.setColor(ModelComponentUI.COLOR);
		
		if(centro == null){ //p1, p2 e p3 são colineares
			Line2D linha;
			if(destino != null){
				Rectangle2D rec = destino.getShapeBounds();
				Point2D pi = Geometrics.getLineIntersectionPoint(p1, rec);
				double angulo = Geometrics.calcularAngulo(p1, pi);
				g2.fill( Arrow.getSeta((int)pi.getX() - getX(), (int)pi.getY() - getY(), angulo) );
				linha = new Line2D.Double(p1.x - getX(), p1.y - getY(), pi.getX() - getX(), pi.getY() - getY());
			}
			else{
				linha = new Line2D.Double(p1.x - getX(), p1.y - getY(), p2.x - getX(), p2.y - getY());
			}
			g2.draw(linha);
		}
		else{
			double alfa = Geometrics.calcularAngulo(centro, p3);
			double beta = Geometrics.calcularAngulo(centro, p1);
			double gama = Geometrics.calcularAngulo(centro, p2);
			double delta = 0;
			double deltaSeta = 90;
			//verifica o sentido do arco
			if( (alfa > beta && !(beta <= 90 && alfa >= 270)) ||
				(alfa < beta && (alfa <= 90 && beta >= 270))	){//horário
				if(gama > beta){
					delta = (360 - gama + beta) * -1;
				}
				else{
					delta = (beta - gama) * -1;
				}
				deltaSeta = -90;
			}
			else{//anti-horário
				if(gama > beta){
					delta = (gama - beta);
				}
				else{
					delta = (360 - beta + gama);
				}
			}
			
			if(destino != null){
				//configura o arco no sistema de coordenadas do parent deste componente
				arco.setArc(1 + getX(), 1 + getY(), getWidth() - 2, getHeight() - 2, beta, delta, Arc2D.OPEN);
				Point2D[] pt = Geometrics.calcularIntersecao(arco, destino.getShapeBounds());
				if(pt != null){
					//calcula a posição da seta e a desenha
					double angulo = Geometrics.calcularAngulo(centro, pt[0]);
					g2.fill( Arrow.getSeta((int)pt[0].getX() - getX(), (int)pt[0].getY() - getY(), angulo + deltaSeta) );
					//recalcula a extensão do arco
					if(deltaSeta > 0){//anti-horário
						int extra = 0;
						if(gama < angulo){
							extra = 360;
						}
						delta = delta - (gama + extra - angulo);
					}
					else{//horário
						int extra = 0;
						if(gama > angulo){
							extra = 360;
						}
						delta = delta + (angulo + extra - gama);
					}
				}
			}
			//põe o arco no sistema de coordenadas deste componente
			arco.setArc(1, 1, getWidth() - 2, getHeight() - 2, beta, delta, Arc2D.OPEN);
		}
		g2.draw(arco);
		g.dispose();
	}
	
	/**
	 * Recalcula, com base nos componentes de origem e destino, o tamanho 
	 * e a localização deste componente.
	 */
	private void calcularMudancas(){
		Point2D p1 = new Point2D.Double();
		Point2D p2 = new Point2D.Double();
		Point2D p3 = new Point2D.Double();
		getPoints(p1, p2, p3);
		Point2D centro = Geometrics.getCenterPoint(p1, p2, p3);
		
		if(centro == null){
			int w = (int)Math.abs(p2.getX() - p1.getX()) + 2;
			if( w < 3){
				w = 3;
			}
			int h = (int)Math.abs(p2.getY() - p1.getY()) + 2;
			if( h < 3){
				h = 3;
			}
			setSize(w, h);
			int x = (int)Math.min(p2.getX(), p1.getX()) - 1;
			int y = (int)Math.min(p2.getY(), p1.getY()) - 1;
			setLocation(x, y);
		}
		else{
			double raio = Point.distance(centro.getX(), centro.getY(), p1.getX(), p1.getY());
			setLocation((int)(centro.getX() - raio - 1), (int)(centro.getY() - raio - 1));
			setSize((int)(2 * raio + 2), (int)(2 * raio + 2));
		}
	}
	
	/**
	 * Encontra os pontos utilizados para calcular o centro da circuferência 
	 * que liga o parent do conecotr, o conector e o destino. Os pontos são todos 
	 * relativos ao parent deste canal.
	 * @param p1	Ponto que receberá as coordenadas do conector
	 * @param p2	Ponto que receberá as coordenadas do destino
	 * @param p3	Ponto que receberá as coordenadas do parent do conector
	 */
	private void getPoints(Point2D p1, Point2D p2, Point2D p3){ 
		p1.setLocation( origem.getCenterLocationOnParentContainer() );
		if(destino != null){
			p2.setLocation( destino.getShapeCenterLocation() );
		}
		else{
			p2.setLocation( tempPoint );
		}
		p3.setLocation( ((ModelComponentUI)origem.getParent()).getShapeCenterLocation() );
	}
}
