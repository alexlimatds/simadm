package br.org.simadm2.simadmgui.geometric;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D.Double;

public class LineArrow implements Shape{
	
	private static final int WIDTH = 10;
	private static final int HEIGTH = 6;
	
	private Point2D p1, p2;
	private Line2D line = new Line2D.Double();
	private Polygon arrow = new Polygon();

	public LineArrow(double x1, double y1, double x2, double y2) {
		p1 = new Point2D.Double(x1, y1);
		p2 = new Point2D.Double(x1, y1);
		update(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2));
	}

	public LineArrow(Point2D p1, Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
		update(p1, p2);
	}
	
	private void update(Point2D p1, Point2D p2){
		line.setLine(p1, p2);
		
	}
	
	public void setLine(Point2D p1, Point2D p2){
		update(p1, p2);
	}

	public Rectangle getBounds() {
		int x = (int)Math.min(p1.getX(), p2.getX());
		int y = (int)Math.min(p1.getY(), p2.getY());
		int w = (int)Math.abs(p1.getX() - p2.getX());
		int h = (int)Math.abs(p1.getY() - p2.getY());
		return new Rectangle(x, y, w, h);
	}

	public Rectangle2D getBounds2D() {
		return getBounds();
	}

	public boolean contains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean contains(Point2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean contains(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean contains(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
