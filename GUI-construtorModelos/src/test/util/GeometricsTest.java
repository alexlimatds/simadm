package test.util;

import java.awt.Point;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import br.org.simadm2.simadmgui.util.Geometrics;

import junit.framework.TestCase;

public class GeometricsTest extends TestCase {
	
	public void testContemPontoParaArco(){
		Arc2D arco = new Arc2D.Double(-3, -3, 6, 6, 45, 125, Arc2D.OPEN);
		Point2D centro = new Point2D.Double(0, 0);
		double raio = 3;
		Point2D ponto = Geometrics.getPoint(35, centro, raio);
		boolean resultado = Geometrics.contemPonto(arco, ponto);
		assertEquals(false, resultado);
		ponto = Geometrics.getPoint(90, centro, raio);
		resultado = Geometrics.contemPonto(arco, ponto);
		assertEquals(true, resultado);
		
		arco.setAngleStart(90);
		arco.setAngleExtent(290);
		ponto = Geometrics.getPoint(45, centro, raio);
		resultado = Geometrics.contemPonto(arco, ponto);
		assertEquals(false, resultado);
		ponto = Geometrics.getPoint(100, centro, raio);
		resultado = Geometrics.contemPonto(arco, ponto);
		assertEquals(true, resultado);
		ponto = Geometrics.getPoint(15, centro, raio);
		resultado = Geometrics.contemPonto(arco, ponto);
		assertEquals(true, resultado);
		
		arco.setAngleStart(90);
		arco.setAngleExtent(-30);
		ponto = Geometrics.getPoint(100, centro, raio);
		resultado = Geometrics.contemPonto(arco, ponto);
		assertEquals(false, resultado);
		ponto = Geometrics.getPoint(15, centro, raio);
		resultado = Geometrics.contemPonto(arco, ponto);
		assertEquals(false, resultado);
		ponto = Geometrics.getPoint(75, centro, raio);
		resultado = Geometrics.contemPonto(arco, ponto);
		assertEquals(true, resultado);
		
		arco.setAngleStart(90);
		arco.setAngleExtent(-270);
		ponto = Geometrics.getPoint(100, centro, raio);
		resultado = Geometrics.contemPonto(arco, ponto);
		assertEquals(false, resultado);
		ponto = Geometrics.getPoint(45, centro, raio);
		resultado = Geometrics.contemPonto(arco, ponto);
		assertEquals(true, resultado);
		ponto = Geometrics.getPoint(300, centro, raio);
		resultado = Geometrics.contemPonto(arco, ponto);
		assertEquals(true, resultado);
	}
	
	public void testCalcularIntersecaoCirculoERetangulo(){
		Point centro = new Point(3, 3);
		double raio = 3;
		Rectangle2D rec = new Rectangle2D.Double(3, 3, 4 , 4);
		Point2D[] resultado = Geometrics.calcularIntersecao(centro, raio, rec);
		assertNotNull(resultado);
		Point esperado1 = new Point(6, 3);
		Point esperado2 = new Point(3, 6);
		assertTrue( esperado1.equals(resultado[0]) || esperado1.equals(resultado[1]) );
		assertTrue( esperado2.equals(resultado[0]) || esperado2.equals(resultado[1]) );
	}
	
	public void testCalcularIntersecaoCirculoELinha(){
		Line2D linha = new Line2D.Double(0, 0, 6, 6);
		Point centro = new Point(2, 2);
		double raio = 2;
		Point2D esperado1 = new Point.Double(3.41, 3.41);
		Point2D esperado2 = new Point.Double(0.58, 0.58);
		Point2D[] resultado = Geometrics.calcularIntersecao(centro, raio, linha);
		assertNotNull(resultado);
		assertTrue( (esperado1.getX() - resultado[0].getX() < 0.01) || (esperado1.getX() - resultado[1].getX() < 0.01) );
		assertTrue( (esperado1.getY() - resultado[0].getY() < 0.01) || (esperado1.getY() - resultado[1].getY() < 0.01) );
		
		assertTrue( (esperado2.getX() - resultado[0].getX() < 0.01) || (esperado2.getX() - resultado[1].getX() < 0.01) );
		assertTrue( (esperado2.getY() - resultado[0].getY() < 0.01) || (esperado2.getY() - resultado[1].getY() < 0.01) );
		
		//no próximo teste a linha é modificada para conter apenas um ponto de interseção
		linha.setLine(0, 0, 2, 2);
		resultado = Geometrics.calcularIntersecao(centro, raio, linha);
		assertNotNull(resultado);
		assertEquals(1, resultado.length);
		assertTrue( (esperado2.getX() - resultado[0].getX() < 0.01) );
		assertTrue( (esperado2.getY() - resultado[0].getY() < 0.01) );
		
		//no próximo teste a linha é modificada para não conter pontos de interseção (mas a equação da reta continua igual)
		linha.setLine(-5, -5, 0, 0);
		resultado = Geometrics.calcularIntersecao(centro, raio, linha);
		assertNull(resultado);
		
		//no próximo teste a linha é modificada para não conter pontos de interseção (a equação da reta não é mais a mesma)
		linha.setLine(-10, 10, 10, -10);
		resultado = Geometrics.calcularIntersecao(centro, raio, linha);
		assertNull(resultado);
		
		//linha vertical
		linha.setLine(2, -1, 2, 2);
		resultado = Geometrics.calcularIntersecao(centro, raio, linha);
		esperado1 = new Point(2, 0);
		assertEquals(esperado1, resultado[0]);
		
		//no próximo teste a linha deve conter dois pontos de interseção
		linha = new Line2D.Double(-3, -4, 3, 2);
		centro = new Point(1, -1);
		raio = Math.sqrt(5);
		esperado1 = new Point(2, 1);
		esperado2 = new Point(-1, -2);
		resultado = Geometrics.calcularIntersecao(centro, raio, linha);
		assertNotNull(resultado);
		assertTrue( (esperado1.getX() - resultado[0].getX() < 0.01) || (esperado1.getX() - resultado[1].getX() < 0.01) );
		assertTrue( (esperado1.getY() - resultado[0].getY() < 0.01) || (esperado1.getY() - resultado[1].getY() < 0.01) );
		assertTrue( (esperado2.getX() - resultado[0].getX() < 0.01) || (esperado2.getX() - resultado[1].getX() < 0.01) );
		assertTrue( (esperado2.getY() - resultado[0].getY() < 0.01) || (esperado2.getY() - resultado[1].getY() < 0.01) );
	}
	
	public void testCalcularIntersecaoArcoERetangulo(){
		Arc2D arco = new Arc2D.Double(-3, -3, 6, 6, 90, 270, Arc2D.OPEN);
		Rectangle2D rec1 = new Rectangle2D.Double(2, 1, 2, 1);
		Point2D[] resultado = Geometrics.calcularIntersecao(arco, rec1);
		assertNotNull(resultado);
		assertEquals(2, resultado.length);
		Point2D esperado1 = new Point2D.Double(Math.sqrt(8), 1);
		Point2D esperado2 = new Point2D.Double(Math.sqrt(5), 2);
		assertTrue( (esperado1.getX() - resultado[0].getX() < 0.01) || (esperado1.getX() - resultado[1].getX() < 0.01) );
		assertTrue( (esperado1.getY() - resultado[0].getY() < 0.01) || (esperado1.getY() - resultado[1].getY() < 0.01) );
		assertTrue( (esperado2.getX() - resultado[0].getX() < 0.01) || (esperado2.getX() - resultado[1].getX() < 0.01) );
		assertTrue( (esperado2.getY() - resultado[0].getY() < 0.01) || (esperado2.getY() - resultado[1].getY() < 0.01) );
		
		Rectangle2D rec2 = new Rectangle2D.Double(0, -5, 3, 3);
		resultado = Geometrics.calcularIntersecao(arco, rec2);
		assertNotNull(resultado);
		assertEquals(1, resultado.length);
		esperado1 = new Point2D.Double(0, -3);
		assertEquals(esperado1.getX(), resultado[0].getX(), 0.01);
		assertEquals(esperado1.getY(), resultado[0].getY(), 0.01);
	}
	
	public void testContemPonto(){
		Line2D line = new Line2D.Double(1, 10, 5, 1);
		assertTrue(Geometrics.contemPonto(line, 1, 10));
		assertTrue(Geometrics.contemPonto(line, 5, 1));
		assertTrue(Geometrics.contemPonto(line, 29.0/9, 5));
		assertFalse(Geometrics.contemPonto(line, 6, -5/4));
		assertFalse(Geometrics.contemPonto(line, -10, 139.0/4));
	}
	
	public void testGetPoint(){
		Point2D esperado = new Point2D.Double(10.071, - 2.071);
		Point2D centro = new Point2D.Double(3, 5);
		double raio = 10;
		Point2D resultado = Geometrics.getPoint(45, centro, raio);
		assertEquals(esperado.getX(), resultado.getX(), 0.01);
		assertEquals(esperado.getY(), resultado.getY(), 0.01);
		
		esperado = new Point2D.Double(-5.66, 0);
		resultado = Geometrics.getPoint(150, centro, raio);
		assertEquals(esperado.getX(), resultado.getX(), 0.01);
		assertEquals(esperado.getY(), resultado.getY(), 0.01);
		
		esperado = new Point2D.Double(-5.66, 10);
		resultado = Geometrics.getPoint(210, centro, raio);
		assertEquals(esperado.getX(), resultado.getX(), 0.01);
		assertEquals(esperado.getY(), resultado.getY(), 0.01);
		
		esperado = new Point2D.Double(8, 13.66);
		resultado = Geometrics.getPoint(300, centro, raio);
		assertEquals(esperado.getX(), resultado.getX(), 0.01);
		assertEquals(esperado.getY(), resultado.getY(), 0.01);
	}
	
	/*
	 * Test method for 'br.org.simadm2.simadmgui.Geometrics.getLinesIntersection(Line2D, Line2D)'
	 */
	public void testGetLinesIntersection() {
		Line2D r = new Line2D.Double(0, 0, 8, 8);
		Line2D s = new Line2D.Double(0, 8, 8, 0);
		Point2D p = Geometrics.getLinesIntersection(r, s);
		Point2D pEsperado = new Point2D.Double(4, 4);
		assertEquals(pEsperado, p);
		//teste 2
		r.setLine(1, 0, 3, 1);
		s.setLine(4, 0, 6, 1);
		p = Geometrics.getLinesIntersection(r, s);
		pEsperado = null;
		assertEquals(pEsperado, p);
		//teste 3
		s.setLine(0, 4, 4, 0);
		p = Geometrics.getLinesIntersection(r, s);
		pEsperado = new Point2D.Double(3, 1);
		assertEquals(pEsperado, p);
	}
	
	public void testgetCenterPoint(){
		Point p1 = new Point(0, 1);
		Point p2 = new Point(1, 0);
		Point p3 = new Point(2, 1);
		Point2D resultado = Geometrics.getCenterPoint(p1, p2, p3);
		Point esperado = new Point(1, 1);
		assertEquals(esperado, resultado);
		
		p1.setLocation(1, 0);
		p2.setLocation(-2, 0);
		p3.setLocation(3, 0);
		resultado = Geometrics.getCenterPoint(p1, p2, p3);
		assertNull(resultado);
		
		p1.setLocation(3, 1);
		p2.setLocation(-2, 6);
		p3.setLocation(-2, -4);
		esperado.setLocation(-2, 1);
		resultado = Geometrics.getCenterPoint(p1, p2, p3);
		assertEquals(esperado, resultado);
	}
	
	public void testGetDeterminante(){
		double esperado = -27;
		double resultado = Geometrics.getDeterminante(-1, 2, 3, 0, 1, 4, -2, -3, 5);
		assertEquals(esperado, resultado, 0);
		esperado = -74;
		resultado = Geometrics.getDeterminante(2, -1, 3, 0, 4, 5, 6, -2, 1);
		assertEquals(esperado, resultado, 0);
	}
	
	public void testCalcularAngulo(){
		Point p = new Point(15, 8);
		Point c = new Point(5, 8); //centro
		double esperado = 0;
		double resultado = Geometrics.calcularAngulo(c, p);
		assertEquals(esperado, resultado, 0);
		
		p.setLocation(5, -2);
		esperado = 90;
		resultado = Geometrics.calcularAngulo(c, p);
		assertEquals(esperado, resultado, 0);
		
		p.setLocation(-5, 8);
		esperado = 180;
		resultado = Geometrics.calcularAngulo(c, p);
		assertEquals(esperado, resultado, 0);
		
		p.setLocation(5, 18);
		esperado = 270;
		resultado = Geometrics.calcularAngulo(c, p);
		assertEquals(esperado, resultado, 0);
		
		c.setLocation(0, 0);
		p.setLocation(2, -2);
		esperado = 45;
		resultado = Geometrics.calcularAngulo(c, p);
		assertEquals(esperado, resultado, 0.5);
		
		c.setLocation(10, 10);
		p.setLocation(5, 8);
		esperado = 158.18;
		resultado = Geometrics.calcularAngulo(c, p);
		assertEquals(esperado, resultado, 0.1);
		
		p.setLocation(13, 9);
		esperado = 18.45;
		resultado = Geometrics.calcularAngulo(c, p);
		assertEquals(esperado, resultado, 0.1);
		
		p.setLocation(5, 12);
		esperado = 201.72;
		resultado = Geometrics.calcularAngulo(c, p);
		assertEquals(esperado, resultado, 0.1);
		
		p.setLocation(13, 14);
		esperado = 306.87;
		resultado = Geometrics.calcularAngulo(c, p);
		assertEquals(esperado, resultado, 0.1);
	}
}
