package br.org.simadm2.simadmgui.util;

import java.awt.Point;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Classe que oferece métodos utilitários para cálculo geométrico.
 * @author Alexandre
 *
 */
public abstract class Geometrics {
	
	/*public static Point[] getCircleIntersectionPoints(Point centro1, double raio1, Point centro2, double raio2){
		double a = -2 * centro1.x;
		double b = -2 * centro1.y;
		double c = Math.pow(centro1.x, 2) * Math.pow(centro1.y, 2) - Math.pow(raio1, 2);
		double d = -2 * centro2.x;
		double e = -2 * centro2.y;
		double f = Math.pow(centro2.x, 2) * Math.pow(centro2.y, 2) - Math.pow(raio1, 2);
		double w = a - d;
		double z = b - e;
		double r = c - f;
		double[] ys = 
	}*/
	
	/**
	 * ATENÇÃO: o arco deve ser um arco de círculo (e não de elipse)
	 */
	public static Point2D[] calcularIntersecao(Arc2D arco, Rectangle2D rec){
		Point2D centro = new Point2D.Double(arco.getCenterX(), arco.getCenterY());
		Point2D[] pts = Geometrics.calcularIntersecao(centro, arco.getHeight() / 2, rec);
		if(pts != null){
			ArrayList lista = new ArrayList();
			for(int i = 0; i < pts.length; i++){
				if(contemPonto(arco, pts[i])){
					lista.add(pts[i]);
				}
			}
			if(lista.size() != 0){
				pts = new Point2D[lista.size()];
				return (Point2D[])lista.toArray(pts);
			}
		}
		return null;
	}
	
	/**
	 * Atenção: apenas para arcos de círculo.
	 * @param arco
	 * @param ponto
	 * @return
	 */
	public static boolean contemPonto(Arc2D arco, Point2D ponto){
		Point2D centro = new Point2D.Double(arco.getCenterX(), arco.getCenterY());
		double dist = ponto.distance(centro) - 0.00000001;
		if(dist > arco.getHeight() / 2){
			return false;
		}
		
		if(arco.getAngleExtent() >= 360 || arco.getAngleExtent() <= -360){
			return true;
		}
		else{
			double beta = arco.getAngleExtent() + arco.getAngleStart();
			double alfa = calcularAngulo(centro, ponto);
			if(arco.getAngleExtent() > 0){ //anti-horário
				if(beta > 360){
					if(alfa >= arco.getAngleStart() || alfa <= (beta - 360)){
						return true;
					}
					return false;
				}
				else{
					if(alfa >= arco.getAngleStart() && alfa <= beta){
						return true;
					}
					return false;
				}
			}
			else{//horário
				if(beta < 0){
					if(alfa <= arco.getAngleStart() || alfa >= (beta + 360)){
						return true;
					}
					return false;
				}
				else{
					if(alfa <= arco.getAngleStart() && alfa >= beta){
						return true;
					}
					return false;
				}
			}
		}
	}
	
	/**
	 * Calcula os pontos de interseção entre uma linha e um retângulo.
	 * @param	centro	O centro da circunferência.
	 * @param	raio	O raio da circunferência.
	 * @param	rec		O retângulo em questão.
	 * @return	Um contendo array contendo o(s) ponto(s) de interseção. Caso 
	 * 			não exista ponto de interseção, retorna <code>null</code>.
	 */
	public static Point2D[] calcularIntersecao(Point2D centro, double raio, Rectangle2D rec){
		//criando a reta superior do retângulo
		double x = rec.getX();
		double y = rec.getY();
		double w = rec.getWidth();
		double h = rec.getHeight();
		Line2D recLine = new Line2D.Double(x, y, x + w, y);
		ArrayList pontos = new ArrayList();
		Point2D[] p = calcularIntersecao(centro, raio, recLine);
		if( p != null ){
			for(int i = 0; i < p.length; i++){
				pontos.add(p[i]);
			}
		}
		//criando a reta inferior do retângulo
		recLine.setLine(x, y + h, x + w, y + h);
		p = calcularIntersecao(centro, raio, recLine);
		if( p != null ){
			for(int i = 0; i < p.length; i++){
				pontos.add(p[i]);
			}
		}
		//criando a reta direita do retângulo
		recLine.setLine(x + w, y, x + w, y + h);
		p = calcularIntersecao(centro, raio, recLine);
		if( p != null ){
			for(int i = 0; i < p.length; i++){
				pontos.add(p[i]);
			}
		}
		//criando a reta esquerda do retângulo
		recLine.setLine(x, y, x, y + h);
		p = calcularIntersecao(centro, raio, recLine);
		if( p != null ){
			for(int i = 0; i < p.length; i++){
				pontos.add(p[i]);
			}
		}
		if(pontos.size() != 0){
			p = new Point2D[pontos.size()];
			return (Point2D[])pontos.toArray(p);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Calcula os pontos de interseção entre uma linha e uma circunferência.
	 * @param	centro	O centro da circunferência.
	 * @param	raio	O raio da circunferência.
	 * @param	linha	A linha em questão.
	 * @return	Um array de tamanho 2 ou 1 contendo o(s) ponto(s) de interseção. Caso 
	 * 			não exista ponto de interseção, retorna <code>null</code>.
	 */
	public static Point2D[] calcularIntersecao(Point2D centro, double raio, Line2D linha){
		//calcula os coeficientes da reta
		double[] cofs = Geometrics.getLineCoeficients(linha);
		double a = cofs[0];
		double b = cofs[1];
		double c = cofs[2];
		
		//calcula coeficientes da circunferência
		double d = centro.getX() * -2;
		double e = centro.getY() * -2;
		double f = centro.getX() * centro.getX() + centro.getY() * centro.getY() - raio * raio;
		
		//calcula coeficientes da equação do 2º grau que determina as coordenadas y dos pontos de interseção
		double cofa = b * b + a * a;
		double cofb = 2 * b * c - a * d * b + a * a * e;
		double cofc = c * c - a * d * c + a * a * f;
		//encontra as raízes da equação do 2º grau
		double[] ys = getRaizesQuadraticas(cofa, cofb, cofc);
		if(ys == null){ //não possui raízes reais
			return null;
		}
		
		//calcula as coordenadas x dos pontos de interseção
		double[] xs = new double[2];
		if(a != 0){
			xs[0] = (-b * ys[0] - c) / a;
			xs[1] = (-b * ys[1] - c) / a;
		}
		else{//ys[0] == ys[1]
			xs = getRaizesQuadraticas(1, d, (ys[0] * ys[0] + e * ys[0] + f));
		}
		if(xs == null){//sem raízes reais
			return null;
		}
		
		//determina quais pontos pertencem à linha e os retorna
		if(contemPonto(linha, xs[0], ys[0])){
			if(contemPonto(linha, xs[1], ys[1])){
				if(xs[0] == xs[1] && ys[0] == ys[1]){ //veririca se os pontos são iguais
					return new Point2D[]{new Point2D.Double(xs[0], ys[0])};
				}
				return new Point2D[]{new Point2D.Double(xs[0], ys[0]), 
								   new Point2D.Double(xs[1], ys[1])};
			}
			else{
				return new Point2D[]{new Point2D.Double(xs[0], ys[0])};
			}
		}
		if(contemPonto(linha, xs[1], ys[1])){
			return new Point2D[]{new Point2D.Double(xs[1], ys[1])};
		}
		else{
			return null;
		}
	}
	
	/**
	 * Indica se um ponto pertence a uma determinada linha. O cálculo é feito encontrando-se a 
	 * equação geral da reta à qual a linha pertence, e substituindo as coordenadas do ponto na 
	 * equação. Caso o valor resultante esteja entre -0.00009 e 0.000009, verifica se o ponto 
	 * está nos limites da linha. 
	 * @param linha	A linha em questão.
	 * @param x		Coordenada x do ponto em questão.
	 * @param y		Coordenada y do ponto em questão.
	 * @return		<code>true</code> caso a linha possua o ponto.
	 */
	public static boolean contemPonto(Line2D linha, double x, double y){
		double[] cofs = getLineCoeficients(linha);
		double res = cofs[0] * x + cofs[1] * y + cofs[2];
		if(res > -0.000009 && res < 0.000009){ //verifica se o ponto está NA RETA
			//verifica se o ponto está na linha
			if( ( (x >= linha.getX1() && x <= linha.getX2()) || (x >= linha.getX2() && x <= linha.getX1()) ) &&
				( (y >= linha.getY1() && y <= linha.getY2()) || (y >= linha.getY2() && y <= linha.getY1()) ) ){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	/**
	 * Calcula as raízes de uma equação do segundo grau. Considere uma equação da 
	 * forma <code>ax² + bx + c = 0</code>. 
	 * @param	a	O coeficiente que acompanha x²
	 * @param	b	O coeficiente que acompanha x
	 * @param	c	O termo independente 
	 * @return	um array de tamanho 2 contendo as raízes. No caso em que a equação possui 
	 * 			duas raízes iguais, o array será de tamanho 2 contendo duas raízes iguais. 
	 * 			Caso a equação não possua raízes reais, retorna <code>null</code>.
	 */
	public static double[] getRaizesQuadraticas(double a, double b, double c){
		if(a == 0){
			throw new IllegalArgumentException("A não pode ser negativo.");
		}
		double delta = b * b - 4 * a * c;
		if(delta < 0){
			return null;
		}
		double[] raizes = new double[2];
		raizes[0] = (-b + Math.sqrt(delta)) / (2 * a);
		raizes[1] = (-b - Math.sqrt(delta)) / (2 * a);
		return raizes;
	}
	
	/**
	 * Retorna um ponto localizado em uma circunferência, a partir do centro 
	 * e do raio da circunferência e do ângulo (em graus) em que o ponto se encontra. O 
	 * ângulo de zero graus aponta para a posição de 3 horas, e os ângulos são 
	 * contados no sentido anti-horário, o que siginifica que um ângualo de 90 graus 
	 * aponta para a posição 12 horas.
	 * @param alfa	O ângulo em que o ponto de localiza.
	 * @param centro	O centro da circunferência a qual o ponto pertence
	 * @param raio		O raio da circunferência
	 * @return	O ponto desejado.
	 */
	public static Point2D getPoint(double alfa, Point2D centro, double raio){
		double beta = 360 - alfa;
		double dx = Math.cos(Math.toRadians(beta)) * raio;
		double dy = Math.sin(Math.toRadians(beta)) * raio;
		return new Point2D.Double((centro.getX() + dx), (centro.getY() + dy));
	}
	
	/**
	 * Cria uma linha a partir de um ponto, um ângulo e um comprimento.
	 * @param x	Coordenada X do ponto de início da linha
	 * @param y	Coordenada Y do ponto de início da linha
	 * @param angulo	Ângulo entre a linha e uma reta horizontal que passa sobre o ponto de início da linha. 
	 * 					Desta forma, o ângulo de zero graus encontra-se na posição de três horas. O ângulo é 
	 * 					medido no sentido anti-horário.
	 * @param comprimento	Comprimento da linha
	 * @return	A linha criada.
	 */
	public static Line2D criarLinha(int x, int y, double angulo, double comprimento){
		double anguloRad = Math.toRadians(angulo);
		int p2x = (int)(x  + Math.cos(anguloRad) * comprimento);
		int p2y = (int)(y  - Math.sin(anguloRad) * comprimento);
		return new Line2D.Double(x, y, p2x, p2y);
	}
	
	/**
	 * Calcula o ângulo de um ponto, em graus, em relação a um centro. 
	 * O ângulo de zero graus localiza-se na posição de 3 horas do relógio. 
	 * Além disso, o ângulo é calculado no sentido horário.
	 * @param centro	O centro
	 * @param ponto		O ponto que se deseja obter o ângulo
	 * @return	O ângulo em graus
	 */
	public static double calcularAngulo(Point2D centro, Point2D ponto){
		if(ponto.getY() == centro.getY() && ponto.getX() > centro.getX() || centro.equals(ponto)){
			return 0;
		}
		else if(ponto.getX() == centro.getX() && ponto.getY() < centro.getY()){
			return 90;
		}
		else if(ponto.getY() == centro.getY() && ponto.getX() < centro.getX()){
			return 180;
		}
		else if(ponto.getX() == centro.getX() && ponto.getY() > centro.getY()){
			return 270;
		}
		else{
			double raio = centro.distance(ponto);
			double alfa = Math.asin( Math.abs(ponto.getY() - centro.getY()) / raio );
			//converte alfa para graus
			alfa = Math.toDegrees(alfa); // 0 < alfa < 90
			double beta = 0;
			//determinando o quadrante do ponto
			if(ponto.getX() > centro.getX() && ponto.getY() < centro.getY()){ //1º quadrante
				beta = alfa;
			}
			else if(ponto.getX() < centro.getX() && ponto.getY() < centro.getY()){ //2º quadrante
				beta = 180 - alfa;
			}
			else if(ponto.getX() < centro.getX() && ponto.getY() > centro.getY()){ //3º quadrante
				beta = 180 + alfa;
			}
			else if(ponto.getX() > centro.getX() && ponto.getY() > centro.getY()){ //4º quadrante
				beta = 360 - alfa;
			}
			
			return beta;
		}
	}
	
	/**
	 * Calcula o centro de uma circuferência que contém três pontos 
	 * específicos (p1, p2 e p3).
	 * @param p1
	 * @param p2
	 * @param p3
	 * @return	O centro da circuferência, ou <code>null</code> caso p1, p2 e p3 
	 * 			sejam colineares (pertençam à mesma reta).
	 */
	public static Point2D getCenterPoint(Point2D p1, Point2D p2, Point2D p3){
		/*
		 * cálculo do determinante com as coordenadas de p1, p2 e p3.
		 * |x1 y1 1|
		 * |x2 y2 1| = detBase
		 * |x3 y3 1|
		 */
		double detBase = getDeterminante(p1.getX(), p1.getY(), 1, p2.getX(), p2.getY(), 1, p3.getX(), p3.getY(), 1);
		//verifica se os pontos são colineares
		if(detBase == 0){
			return null;
		}
		//variáveis para simplificar as equações
		double v1 = (p1.getX() * p1.getX() + p1.getY() * p1.getY()) * -1;
		double v2 = (p2.getX() * p2.getX() + p2.getY() * p2.getY()) * -1;
		double v3 = (p3.getX() * p3.getX() + p3.getY() * p3.getY()) * -1;
		
		/* cálculo do determinante que resulta o A da equação geral da circunferência
		 * |v1 y1 1|
		 * |v2 y2 1| = A
		 * |v3 y3 1|
		 */
		double a = getDeterminante(v1, p1.getY(), 1, v2, p2.getY(), 1, v3, p3.getY(), 1) / detBase;
		
		/* cálculo do determinante que resulta o B da equação geral da circunferência
		 * |x1 v1 1|
		 * |x2 v2 1| = B
		 * |x3 v3 1|
		 */
		double b = getDeterminante(p1.getX(), v1, 1, p2.getX(), v2, 1, p3.getX(), v3, 1) / detBase;
		return new Point2D.Double((a / -2), (b / -2));
	}
	
	/**
	 * Calcula o determinante de uma matriz de terceira ordem.
	 * @param a11	Elemento da matriz na linha 1 e coluna 1
	 * @param a12	Elemento da matriz na linha 1 e coluna 2
	 * @param a13	Elemento da matriz na linha 1 e coluna 3
	 * @param a21	Elemento da matriz na linha 2 e coluna 1
	 * @param a22	Elemento da matriz na linha 2 e coluna 2
	 * @param a23	Elemento da matriz na linha 2 e coluna 3
	 * @param a31	Elemento da matriz na linha 3 e coluna 1
	 * @param a32	Elemento da matriz na linha 3 e coluna 2
	 * @param a33	Elemento da matriz na linha 3 e coluna 3
	 * @return	O determinante da matriz;
	 */
	public static double getDeterminante(double a11, double a12, double a13, double a21, double a22, 
									  double a23, double a31, double a32, double a33){
		return a11 * a22 * a33 + a21 * a32 * a13 + a12 * a23 * a31 - 
			   a31 * a22 * a13 - a12 * a21 * a33 - a23 * a32 * a11;
	}
	
	/**
	 * Retorna o ponto de interseção entre uma linha e um retângulo, 
	 * sendo que a linha liga um ponto ao centro do retângulo.
	 * @param p		O ponto.
	 * @param rec	O retângulo. 
	 * @return	O ponto de interseção. Caso p se encontre dentro do retângulo, retorna p.
	 */
	public static Point2D getLineIntersectionPoint(Point2D p, Rectangle2D rec){
		if(!rec.contains(p)){
			//linha que vai do ponto ao centro do retângulo
			Line2D line = new Line2D.Double(p.getX(), p.getY(), rec.getCenterX(), rec.getCenterY()); 
			//criando a reta superior do retângulo
			double x = rec.getX();
			double y = rec.getY();
			double w = rec.getWidth();
			double h = rec.getHeight();
			Line2D recLine = new Line2D.Double(x, y, x + w, y);
			if(recLine.intersectsLine(line)){
				return getLinesIntersection(line, recLine);
			}
			//criando a reta inferior do retângulo
			recLine.setLine(x, y + h, x + w, y + h);
			if(recLine.intersectsLine(line)){
				return getLinesIntersection(line, recLine);
			}
			//criando a reta direita do retângulo
			recLine.setLine(x + w, y, x + w, y + h);
			if(recLine.intersectsLine(line)){
				return getLinesIntersection(line, recLine);
			}
			//criando a reta esquerda do retângulo
			recLine.setLine(x, y, x, y + h);
			return getLinesIntersection(line, recLine);
		}
		else{
			return p;
		}
	}
	
	
	/**
	 * Retorna o ponto de interseção entre duas linhas.
	 * @param line1	
	 * @param line2
	 * @return	O ponto de interseção. Retorna <code>null</code> caso 
	 * 			não haja ponto de interseção entre as duas linhas.
	 */
	public static Point2D getLinesIntersection(Line2D line1, Line2D line2){
		if(line1.intersectsLine(line2)){
			//o ponto de interseção pode ser calculado resolvendo um sistema 
			//de equações envolvendo as equações gerais das retas
			double a1, a2, b1, b2, c1, c2;
			double[] cofs1 = getLineCoeficients(line1);
			double[] cofs2 = getLineCoeficients(line2);
			if(cofs1[0] != 0){ //para evitar uma divisão por zero
				a1 = cofs1[0]; b1 = cofs1[1]; c1 = cofs1[2];
				a2 = cofs2[0]; b2 = cofs2[1]; c2 = cofs2[2];
			}
			else{
				a1 = cofs2[0]; b1 = cofs2[1]; c1 = cofs2[2];
				a2 = cofs1[0]; b2 = cofs1[1]; c2 = cofs1[2];
			}
			double y = (a2 * c1 - a1 * c2)/(-a2 * b1 + a1 * b2);
			double x = (-b1 * y - c1)/a1;
			return new Point2D.Double(x, y);
		}
		return null;
	}
	
	/**
	 * Retorna os coeficientes a, b e c da equação geral da reta de uma linha.
	 * @param line	A linha que se deseja obter os coeficientes.
	 * @return	Um array de tamanho 3 contendo os coeficientes. O coeficientes 
	 * 			a, b e c são armazenados respectivamente nos índices 0, 1 e 2 do array.
	 */
	private static double[] getLineCoeficients(Line2D line){
		double[] cofs = new double[3];
		//para entender o cálculo dos coeficientes, pesquise, em um livro de 
		//matemática (geometria analítica) como determinar a equação geral 
		//da reta a partir de dois pontos
		cofs[0] = line.getY1() - line.getY2();
		cofs[1] = line.getX2() - line.getX1();
		cofs[2] = line.getX1() * line.getY2() - line.getX2() * line.getY1();
		
		return cofs;
	}
}
