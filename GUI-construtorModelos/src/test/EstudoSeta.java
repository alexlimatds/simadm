package test;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.org.simadm2.simadmgui.geometric.Arrow;

public class EstudoSeta extends JFrame {
	
	private ArcoPanel panelArc;
	private JLabel lb1, lb2, lb3;
	private JTextField txt1, txt2, txt3;
	private JButton bt;
	
	public EstudoSeta() {
		super("Estudo da seta");
		setLayout(new BorderLayout());
		panelArc = new ArcoPanel();
		getContentPane().add(panelArc, BorderLayout.CENTER);
		
		lb1 = new JLabel("X");
		lb2 = new JLabel("Y");
		lb3 = new JLabel("ALFA");
		txt1 = new JTextField(3);
		txt2 = new JTextField(3);
		txt3 = new JTextField(3);
		bt = new JButton("Draw");
		bt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				panelArc.draw(Integer.parseInt(txt1.getText()), 
							  Integer.parseInt(txt2.getText()), 
							  Double.parseDouble(txt3.getText()));
			}
		});
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(lb1);
		southPanel.add(txt1);
		southPanel.add(lb2);
		southPanel.add(txt2);
		southPanel.add(lb3);
		southPanel.add(txt3);
		southPanel.add(bt);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EstudoSeta f = new EstudoSeta();
		f.setVisible(true);
	}
	
	private class ArcoPanel extends JComponent{
		
		private final int PANEL_SIZE = 300;
		private Shape seta = Arrow.getSeta(-1, -1, 0);
		
		public ArcoPanel() {
			super();
			setSize(PANEL_SIZE, PANEL_SIZE);
		}
		
		protected void paintComponent(Graphics g) {
			//desenha eixos
			g.drawLine(100, 0, 100, 200);
			g.drawLine(0, 100, 200, 100);
			
			//desenha seta
			Graphics2D g2 = (Graphics2D)g;
			g2.draw(seta);
			
			g.dispose();
		}
		
		public void draw(int x, int y, double alfa){
			seta = Arrow.getSeta(x, y, alfa);
			repaint();
		}
		
		public Dimension getPreferredSize() {
			return new Dimension(PANEL_SIZE, PANEL_SIZE);
		}
		
		public Dimension getMinimumSize() {
			return new Dimension(PANEL_SIZE, PANEL_SIZE);
		}
		
		public Dimension getMaximumSize() {
			return new Dimension(PANEL_SIZE, PANEL_SIZE);
		}
	}
}
