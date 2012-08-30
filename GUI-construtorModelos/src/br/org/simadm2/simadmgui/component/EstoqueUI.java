package br.org.simadm2.simadmgui.component;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import br.org.simadm2.simadmgui.util.Geometrics;


/**
 * Representação visual de um estoque.
 * @author Alexandre Lima
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EstoqueUI extends ModelComponentUI {
	
	private static final int SHAPE_WIDTH = 30;
	private static final int SHAPE_HEIGTH = 30;
	
	/**
	 * 
	 */
	public EstoqueUI(String name) {
		super(name);
	}
	
	/**
	 * Retorna um retângulo indicando as dimensões da forma (shape) 
	 * deste componente. ATENÇÃO: o retângulo retornado não possui 
	 * informações acerca da localização da forma.
	 */
	protected Rectangle getShapeDimensions() {
		return new Rectangle(SHAPE_WIDTH, SHAPE_HEIGTH);
	}
	
	/**
	 * Cria e retorna o shape deste componente.
	 */
	protected Shape criarShape(int x, int y) {
		return new Rectangle(x, y, SHAPE_WIDTH, SHAPE_HEIGTH);
	}
	
	/**
	 * Recalcula e atualiza a posição do conector recebido como parâmetro.
	 * @param	p	O ponto que será utilizado para calcular a posição do conector. Basicamente 
	 * 				o ponto clicado pelo usuário (que não necessariamente é o ponto do conector).
	 * @param	con	O conector cuja posição será atualizada.
	 */
	public void atualizarPosicaoDoConector(Point2D p, ConectorUI con){
		Point2D conectorPoint = calcularPontoDoConector(p);
		con.setLocation((int)conectorPoint.getX(), (int)conectorPoint.getY());
	}
	
	/**
	 * Calcula o ponto do conector, a partir do ponto clicado pelo usuário. 
	 * @param p	O ponto clicado pelo usuário
	 * @return
	 */
	protected Point2D calcularPontoDoConector(Point2D p){
		Rectangle rec = componentShape.getBounds();
		Point centro = new Point((int)rec.getCenterX(), (int)rec.getCenterY());
		double alfa = Geometrics.calcularAngulo(centro, p);
		Line2D linha = Geometrics.criarLinha(centro.x, centro.y, alfa, rec.getWidth());
		Point2D p2 = Geometrics.getLineIntersectionPoint(linha.getP2(), rec); //ponto de interseção entre a linha que liga pBase ao centro do retângulo
		return new Point((int)p2.getX(), (int)p2.getY());
	}
}
