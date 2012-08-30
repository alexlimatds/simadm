package br.org.simadm2.simadmgui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import br.org.simadm2.simadmgui.component.editor.listeners.ListenerModoAdicaoDeCanal;
import br.org.simadm2.simadmgui.component.editor.listeners.ListenerModoAdicaoDeEstoque;
import br.org.simadm2.simadmgui.component.editor.listeners.ListenerModoAdicaoDeFluxo;
import br.org.simadm2.simadmgui.component.editor.listeners.ListenerModoAdicaoDeVariavel;
import br.org.simadm2.simadmgui.component.editor.listeners.ListenerModoNormal;

/**
 * Este componente é a tela na qual os componente de modelos são utilizados no 
 * editor de modelos.
 * 
 * @author Alexandre Lima
 */
public class EditorCanvas extends JPanel{
	
	public static final String ADD_ESTOQUE = "Adicionar Estoque";
	public static final String ADD_FLUXO = "Adicionar Fluxo";
	public static final String ADD_VARIAVEL = "Adicionar Variável";
	public static final String REMOVE = "Excluir";
	public static final String LINK = "Ligar";
	public static final String EDIT = "Editar";
	
	private static final int MODO_NORMAL = 0;
	private static final int MODO_ADICAO_DE_CANAL = 1;
	private static final int MODO_ADICAO_DE_ESTOQUE = 2;
	private static final int MODO_ADICAO_DE_FLUXO = 3;
	private static final int MODO_ADICAO_DE_VARIAVEL = 4;
	
	private CanvasMouseListener listener = new CanvasMouseListener();
	private MouseInputAdapter listenerModoNormal = new ListenerModoNormal(this);
	private MouseInputAdapter listenerModoAddEstoque = new ListenerModoAdicaoDeEstoque(this);
	private MouseInputAdapter listenerModoAddVariavel = new ListenerModoAdicaoDeVariavel(this);
	private MouseInputAdapter listenerModoAddFluxo = new ListenerModoAdicaoDeFluxo(this);
	private MouseInputAdapter listenerModoAddCanal = new ListenerModoAdicaoDeCanal(this);
	private ComponenteSelecionavel selecionado;
	private int contador = 1;
	private int modoOperacao = MODO_NORMAL;
	
	/**
	 * 
	 */
	public EditorCanvas() {
		super();
		setBackground(Color.WHITE);
		setLayout(null);
		this.addMouseListener(listener);
	}
	
	/**
	 * Adiciona um componente à esta tela. Também adiciona esta tela à lista de 
	 * mouse listeners e mouse motion listeners do componente recebido como 
	 * parâmetro.
	 * 
	 * @param c	O componente a ser inserido
	 * @param x	a coordenada x na qual o componente será inserido.
	 * @param y	a coordenada y na qual o componente será inserido.
	 */
	private void add(ModelComponentUI c, int x, int y){
		c.addMouseListener(listener);
		c.addMouseMotionListener(listener);
		c.setLocation(x, y);
		add(c);
		repaint();
	}
	
	public Component add(Component arg0) {
		Component c = super.add(arg0);
		repaint();
		return c;
	}
	
	public void remove(Component comp) {
		super.remove(comp);
		repaint();
	}
	
	public void addEstoque(int x, int y){
		add(new EstoqueUI("componente " + contador), x, y);
		contador++;
		ativarModoNormal();
	}
	
	public void addFluxo(int x, int y){
		add(new FluxoUI("componente " + contador), x, y);
		contador++;
		ativarModoNormal();
	}
	
	public void addVariavel(int x, int y){
		add(new VariavelUI("componente " + contador), x, y);
		contador++;
		ativarModoNormal();
	}
	
	/**
	 * Indica que a tela deve operar no modo de operação para a adição de canal.
	 */
	public void ativarModoDeAdicaoDeCanal(){
		modoOperacao = MODO_ADICAO_DE_CANAL;
		descelecionarComponente();
		setCursor(Cursores.CANAL);
	}
	
	public void ativarModoNormal(){
		modoOperacao = MODO_NORMAL;
		descelecionarComponente();
		setCursor(Cursor.getDefaultCursor());
	}
	
	public void ativarModoDeAdicaoDeEstoque(){
		modoOperacao = MODO_ADICAO_DE_ESTOQUE;
		descelecionarComponente();
		setCursor(Cursores.ESTOQUE);
	}
	
	public void ativarModoDeAdicaoDeFluxo(){
		modoOperacao = MODO_ADICAO_DE_FLUXO;
		descelecionarComponente();
		setCursor(Cursores.FLUXO);
	}
	
	public void ativarModoDeAdicaoDeVariavel(){
		modoOperacao = MODO_ADICAO_DE_VARIAVEL;
		descelecionarComponente();
		setCursor(Cursores.VARIAVEL);
	}
	
	/**
	 * Desceleciona o componente atualmente selecionado.
	 */
	public void descelecionarComponente(){
		if(selecionado != null){
			selecionado.setSelecionado(false);
			selecionado = null;
		}
	}
	
	/**
	 * Faz com que um componente fique selecionado.
	 * @param comp	O componente a ser selecionado.
	 */
	public void selecionarComponente(ComponenteSelecionavel comp){
		if(comp != selecionado){
			descelecionarComponente();
			comp.setSelecionado(true);
			selecionado = comp;
		}
	}
	
	
	
	/**
	 * Trata os eventos de mouse que ocorrem sobre a tela do modelo.
	 * @author Alexandre
	 */
	class CanvasMouseListener extends MouseInputAdapter{
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
		 */
		public void mouseDragged(MouseEvent event) {
			if(modoOperacao == MODO_NORMAL){
				listenerModoNormal.mouseDragged(event);
			}
			else if(modoOperacao == MODO_ADICAO_DE_CANAL){
				listenerModoAddCanal.mouseDragged(event);
			}
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed(MouseEvent event) {
			if(modoOperacao == MODO_NORMAL){
				listenerModoNormal.mousePressed(event);
			}
			else if(modoOperacao == MODO_ADICAO_DE_CANAL){
				listenerModoAddCanal.mousePressed(event);
			}
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseCliked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent event) {
			if(modoOperacao == MODO_NORMAL){
				listenerModoNormal.mouseClicked(event);
			}
			else if(modoOperacao == MODO_ADICAO_DE_CANAL){
				ativarModoNormal();
			}
			else if(modoOperacao == MODO_ADICAO_DE_ESTOQUE){
				listenerModoAddEstoque.mouseClicked(event);
			}
			else if(modoOperacao == MODO_ADICAO_DE_VARIAVEL){
				listenerModoAddVariavel.mouseClicked(event);
			}
			else if(modoOperacao == MODO_ADICAO_DE_FLUXO){
				listenerModoAddFluxo.mouseClicked(event);
			}
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased(MouseEvent event) {
			if(modoOperacao == MODO_NORMAL){			
				listenerModoNormal.mouseReleased(event);
			}
			else if(modoOperacao == MODO_ADICAO_DE_CANAL){
				listenerModoAddCanal.mouseReleased(event);
				ativarModoNormal();
			}
		}
		
		public void mouseEntered(MouseEvent event) {
			if(modoOperacao == MODO_ADICAO_DE_CANAL){
				listenerModoAddCanal.mouseEntered(event);
			}
		}
		
		public void mouseExited(MouseEvent event) {
			if(modoOperacao == MODO_ADICAO_DE_CANAL){
				listenerModoAddCanal.mouseExited(event);
			}
		}
	}
}
