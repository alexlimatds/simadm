package br.org.simadm2.simadmgui.component;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import br.org.simadm2.simadmgui.util.Geometrics;


public class FluxoUI extends ModelComponentUI {
	
	private static final int SHAPE_WIDTH = 20;
	private static final int SHAPE_HEIGHT = 20;
	
	public FluxoUI(String name) {
		super(name);
	}

	protected Shape criarShape(int x, int y) {
		int[] xPoints = {x, x + SHAPE_WIDTH, x, x + SHAPE_WIDTH};
		int[] yPoints = {y, y, y + SHAPE_HEIGHT, y + SHAPE_HEIGHT};
		return new Polygon(xPoints, yPoints, xPoints.length);
	}

	protected Rectangle getShapeDimensions() {
		return new Rectangle(SHAPE_WIDTH, SHAPE_HEIGHT);
	}
	
	public void atualizarPosicaoDoConector(Point2D p, ConectorUI con){
		Point2D conectorPoint = calcularPontoDoConector(p);
		con.setLocation((int)conectorPoint.getX(), (int)conectorPoint.getY());
	}

	public Point2D calcularPontoDoConector(Point2D p) {
		//está igual ao do estoque
		Rectangle rec = componentShape.getBounds();
		Point centro = new Point((int)rec.getCenterX(), (int)rec.getCenterY());
		double alfa = Geometrics.calcularAngulo(centro, p);
		Line2D linha = Geometrics.criarLinha(centro.x, centro.y, alfa, rec.getWidth());
		Point2D p2 = Geometrics.getLineIntersectionPoint(linha.getP2(), rec);
		return new Point((int)p2.getX(), (int)p2.getY());
	}
}
