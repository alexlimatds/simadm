package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class EstudoArea extends JFrame {
	
	private ArcoPanel panelArc;
	
	public EstudoArea() {
		super("Estudo da seta");
		setLayout(new BorderLayout());
		panelArc = new ArcoPanel();
		getContentPane().add(panelArc, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//pack();
		setResizable(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EstudoArea f = new EstudoArea();
		f.setSize(400, 400);
		f.setVisible(true);
	}
	
	private class ArcoPanel extends JComponent{
		
		public ArcoPanel() {
			super();
		}
		
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			
			//TODO:estudo area
			Arc2D arco = new Arc2D.Double();
			arco.setArc(10, 10, 100, 100, 0, 270, Arc2D.OPEN);
			g2.draw(arco);
			Rectangle2D rec = new Rectangle2D.Double(50, 50, 100, 100);
			g2.draw(rec);
			Area a1 = new Area(arco);
			a1.intersect(new Area(rec));
			//a1.transform(AffineTransform.getTranslateInstance(150, 100));
			g2.draw(a1);
			PathIterator path = a1.getPathIterator(null, 10);
			//PathIterator path = a1.getPathIterator(null);
			double[] pts = new double[6];
			int type;
			g2.setColor(Color.red);
			List pontos = new ArrayList();
			while(!path.isDone()){
				type = path.currentSegment(pts);
				System.out.println("P: " + pts[0] + ", " + pts[1]);
				g2.drawOval((int)pts[0] - 3, (int)pts[1] - 3, 6, 6);
				
				System.out.println("Inside arco: " + arco.contains(pts[0], pts[1]));
				System.out.println("Inside rec: " + rec.contains(pts[0], pts[1]));
				
				path.next();
			}
			
			g.dispose();
		}
	}
}
