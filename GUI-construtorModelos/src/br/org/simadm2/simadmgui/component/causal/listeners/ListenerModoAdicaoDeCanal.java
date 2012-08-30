package br.org.simadm2.simadmgui.component.causal.listeners;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import br.org.simadm2.simadmgui.component.CanalUI;
import br.org.simadm2.simadmgui.component.EditorCanvas;
import br.org.simadm2.simadmgui.component.ConectorUI;
import br.org.simadm2.simadmgui.component.ModelComponentUI;
import br.org.simadm2.simadmgui.component.causal.CausalCanvas;


public class ListenerModoAdicaoDeCanal extends MouseInputAdapter{
	
	private CausalCanvas canvas;
	private ConectorUI conectorOrigem;
	private CanalUI canalTemporario;
	private Component entered;
	 
	public ListenerModoAdicaoDeCanal(CausalCanvas c) {
		this.canvas = c;
	}
	
	
	public void mouseDragged(MouseEvent event) {
		if(conectorOrigem != null){
			if(event.getComponent() instanceof ModelComponentUI){
				ModelComponentUI comp = (ModelComponentUI)event.getComponent();
				
				if( comp.getComponentShape().contains(event.getPoint()) ){ //ponteiro sobre o componente de origem
					comp.atualizarPosicaoDoConector(event.getPoint(), conectorOrigem);
				}
				else{//ponteiro fora do componente de origem
					if(canalTemporario == null){
						canalTemporario = new CanalUI(conectorOrigem);
					}
					Point eventPoint = event.getPoint();
					canalTemporario.setTempPoint( new Point((int)(eventPoint.getX() + comp.getX()), (int)(eventPoint.getY() + comp.getY())) );
				}
			}
		}
	}
	
	public void mousePressed(MouseEvent event) {
		if(event.getComponent() instanceof ModelComponentUI){
			ModelComponentUI comp = (ModelComponentUI)event.getComponent();
			conectorOrigem = comp.adicionarConector(event.getPoint());
			conectorOrigem.addMouseListener(this);
		}
	}
	
	public void mouseReleased(MouseEvent event) {			
		/*if(event.getComponent() instanceof ModelComponentUI){
			Component origem = event.getComponent();
			//obtendo o ponto, em relação a canvas, no qual o botão foi solto
			Point p = new Point(event.getPoint().x + origem.getX(), event.getPoint().y + origem.getY());
			Component compAlvo = canvas.getComponentAt( p );
			if( compAlvo instanceof ModelComponentUI && compAlvo != origem ){
				canalTemporario.setDestino((ModelComponentUI)compAlvo);
			}
			else{
				conectorOrigem.getParent().remove( conectorOrigem );
				canvas.remove(canalTemporario);
			}
		}
		conectorOrigem = null;
		canalTemporario = null;*/
		
		if(event.getComponent() instanceof ModelComponentUI){
			Component origem = event.getComponent();
			Component compAlvo = entered;
			if( compAlvo instanceof ModelComponentUI && compAlvo != origem ){
				canalTemporario.setDestino((ModelComponentUI)compAlvo);
			}
			else{
				conectorOrigem.getParent().remove( conectorOrigem );
				canvas.remove(canalTemporario);
			}
		}
		conectorOrigem = null;
		canalTemporario = null;
	}
	
	public void mouseEntered(MouseEvent event) {
		if(event.getComponent() instanceof ModelComponentUI){
			entered = event.getComponent();
		}
	}
	
	public void mouseExited(MouseEvent event) {
		entered = null;
	}
}
