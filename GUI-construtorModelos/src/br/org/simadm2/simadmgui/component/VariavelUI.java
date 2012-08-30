package br.org.simadm2.simadmgui.component;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import br.org.simadm2.simadmgui.util.Geometrics;


public class VariavelUI extends ModelComponentUI {
	
	private static final int SHAPE_WIDTH = 25;
	private static final int SHAPE_HEIGTH = 25;
	
	public VariavelUI(String name) {
		super(name);
	}

	protected Shape criarShape(int x, int y) {
		return new Ellipse2D.Double(x, y, SHAPE_WIDTH, SHAPE_HEIGTH);
	}

	protected Rectangle getShapeDimensions() {
		return new Rectangle(SHAPE_WIDTH, SHAPE_HEIGTH);
	}
	
	public void atualizarPosicaoDoConector(Point2D p, ConectorUI con){
		Point2D conectorPoint = calcularPontoDoConector(p);
		con.setLocation((int)conectorPoint.getX(), (int)conectorPoint.getY());
	}

	public Point2D calcularPontoDoConector(Point2D p) {
		Rectangle rec = componentShape.getBounds();
		Point centro = new Point((int)rec.getCenterX(), (int)rec.getCenterY());
		double alfa = Geometrics.calcularAngulo(centro, p);
		return Geometrics.getPoint(alfa, centro, rec.getWidth() / 2); 
	}
}
