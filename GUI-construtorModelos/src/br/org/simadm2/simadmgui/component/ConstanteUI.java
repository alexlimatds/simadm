package br.org.simadm2.simadmgui.component;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;

import br.org.simadm2.simadmgui.util.Geometrics;


public class ConstanteUI extends ModelComponentUI {
	
	private static final int SHAPE_WIDTH = 30;
	private static final int SHAPE_HEIGTH = 30;
	
	public ConstanteUI(String name) {
		super(name);
	}

	protected Shape criarShape(int x, int y) {
		int[] xPoints = {SHAPE_WIDTH/2 + x, SHAPE_WIDTH + x, SHAPE_WIDTH/2 + x, x};
		int[] yPoints = {y, SHAPE_HEIGTH/2 + y, SHAPE_HEIGTH + y, SHAPE_HEIGTH/2 + y};
		
		return new Polygon(xPoints, yPoints, xPoints.length);
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
