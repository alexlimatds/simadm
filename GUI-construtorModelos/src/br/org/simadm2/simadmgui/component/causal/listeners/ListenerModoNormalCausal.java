package br.org.simadm2.simadmgui.component.causal.listeners;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import br.org.simadm2.simadmgui.component.ComponenteSelecionavel;
import br.org.simadm2.simadmgui.component.ConectorUI;
import br.org.simadm2.simadmgui.component.ModelComponentUI;
import br.org.simadm2.simadmgui.component.causal.CausalCanvas;


public class ListenerModoNormalCausal extends MouseInputAdapter{
	
	private int baseX = -1;
	private int baseY = -1;
	private CausalCanvas canvas;
	 
	public ListenerModoNormalCausal(CausalCanvas c) {
		this.canvas = c;
	}
	
	
	public void mouseDragged(MouseEvent event) {
		if(event.getComponent() instanceof ModelComponentUI){
			arrastarModelComponente(event);
		}
		else if(event.getComponent() instanceof ConectorUI){
			arrastarConector(event);
		}
	}
	
	public void mousePressed(MouseEvent event) {
		//utilizados pelo código de arrasto de componentes
		baseX = event.getX();
		baseY = event.getY();
		
		//controla a seleção do componente
		controlarSelecao(event);
	}
	
	public void mouseClicked(MouseEvent event) {
		controlarSelecao(event);
		//verifica se foi utilizado o botão direito do mouse
		if((event.getModifiers() & MouseEvent.BUTTON2_MASK) == MouseEvent.BUTTON2_MASK){
			
		}
	}
	
	public void mouseReleased(MouseEvent event) {			
		//utilizados pelo código de arrasto de componentes
		baseX = -1;
		baseY = -1;
	}
	
	/**
	 * Realiza o controle do componente atualmente selecionado.
	 * @param event
	 */
	private void controlarSelecao(MouseEvent event){
		if( event.getComponent() instanceof ComponenteSelecionavel ){
			ComponenteSelecionavel comp = (ComponenteSelecionavel)event.getComponent();
			canvas.selecionarComponente(comp);
		}
		else{
			canvas.descelecionarComponente();
		}
	}
	
	/**
	 * Método responsável por tratar o arrasto de um componente de modelo.
	 * @param event
	 */
	public void arrastarModelComponente(MouseEvent event){
		Component source = event.getComponent();
		
		//verifica se foi utilizado o botão esquerdo do mouse
		if((event.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK){
			if(baseX != -1 && baseY != -1){
				//calcula as novas coordenadas do componente
				int x = source.getX() + event.getX() - baseX;
				int y = source.getY() + event.getY() - baseY;
				//evita que o componente seja arrastado para fora da janela
				Container parent = source.getParent();
				x = Math.max(x, 0);
				x = Math.min(x, parent.getWidth() - source.getWidth());
				y = Math.max(y, 0);
				y = Math.min(y, parent.getHeight() - source.getHeight());
					
				source.setLocation(x, y); //atualiza a localização do componente
				parent.repaint(); //repinta a tela que contém o componente
			}
		}
	}
	
	/**
	 * Método responsável por tratar o arrasto de um conector. 
	 * @param event
	 */
	private void arrastarConector(MouseEvent event){
		ConectorUI source = (ConectorUI)event.getComponent();
		//verifica se foi utilizado o botão esquerdo do mouse
		if((event.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK){
			if(baseX != -1 && baseY != -1){
				//calcula as novas coordenadas do conector
				int x = source.getX() + event.getX() - baseX;
				int y = source.getY() + event.getY() - baseY;
				
				ModelComponentUI parent = (ModelComponentUI)source.getParent();
				parent.atualizarPosicaoDoConector(new Point(x, y), source); //atualiza a localização do componente
				parent.repaint(); //repinta a tela que contém o componente
			}
		}
	}
}
