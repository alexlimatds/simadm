package br.org.simadm2.simadmgui.component;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

/*
 * Created on 18/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * Componente que agrupa o EditorCanvas e os componentes de interação com o usuário.
 * 
 * @author Alexandre
 */
public class EditorPanel extends JPanel{
	
	private JLabel label; //para exibição de mensagens
	private EditorCanvas canvas;
	private JButton btEstoque, btVariavel, btFluxo, btCanal, btConstante;
	private JToolBar toolBar;
	private JPopupMenu popUpCanvas, popUpComponent;
	
	public EditorPanel(){
		super();
		
		label = new JLabel("");
		label.setVisible(true);
		
		canvas = new EditorCanvas();
		
		criarBotoes();
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(btEstoque);
		toolBar.add(btVariavel);
		toolBar.add(btConstante);
		toolBar.add(btFluxo);
		toolBar.add(btCanal);
		
		setLayout(new BorderLayout());
		add(toolBar, BorderLayout.NORTH);
		add(label, BorderLayout.SOUTH);
		add(canvas, BorderLayout.CENTER);
		
	}
	
	private void criarBotoes(){
		btEstoque = new JButton(Icones.ICON_ESTOQUE);
		btEstoque.setToolTipText("Estoque");
		btEstoque.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				canvas.ativarModoDeAdicaoDeEstoque();
			}
		});
		
		btVariavel = new JButton(Icones.ICON_VARIAVEL);
		btVariavel.setToolTipText("Variável Auxiliar");
		btVariavel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				canvas.ativarModoDeAdicaoDeVariavel();
			}
		});
		
		btFluxo = new JButton(Icones.ICON_FLUXO);
		btFluxo.setToolTipText("Fluxo");
		btFluxo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				canvas.ativarModoDeAdicaoDeFluxo();
			}
		});
		
		btCanal = new JButton(Icones.ICON_CANAL);
		btCanal.setToolTipText("Canal");
		btCanal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				canvas.ativarModoDeAdicaoDeCanal();
			}
		});
		
		btConstante = new JButton(Icones.ICON_CONSTANTE);
		btConstante.setToolTipText("Constante");
		btConstante.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				canvas.ativarModoDeAdicaoDeConstante();
			}
		});
	}
	
	private void criarMenusPopUp(){
		//popUpCanvas
		/*popUpCanvas = new JPopupMenu();
		JMenuItem item = new JMenuItem(ADD_ESTOQUE);
		item.addActionListener(this);
		item.setActionCommand(ADD_ESTOQUE);
		popUpCanvas.add(item);
		item = new JMenuItem(ADD_FLUXO);
		item.setActionCommand(ADD_FLUXO);
		item.addActionListener(this);
		popUpCanvas.add(item);
		item = new JMenuItem(ADD_VARIAVEL);
		item.addActionListener(this);
		item.setActionCommand(ADD_VARIAVEL);
		popUpCanvas.add(item);
		//popUpComponent
		popUpComponent = new JPopupMenu();
		item = new JMenuItem(LINK);
		item.addActionListener(this);
		item.setActionCommand(LINK);
		popUpComponent.add(item);
		item = new JMenuItem(EDIT);
		item.addActionListener(this);
		item.setActionCommand(EDIT);
		popUpComponent.add(item);
		item = new JMenuItem(REMOVE);
		item.addActionListener(this);
		item.setActionCommand(REMOVE);
		popUpComponent.add(item);*/
	}
	
	/*private void verificarPopup(MouseEvent event){
		if(event.isPopupTrigger()){
			if(event.getSource() instanceof EditorCanvas){
				popUpCanvas.show(event.getComponent(), event.getX(), event.getY());
			}
			else{
				popUpComponent.show(event.getComponent(), event.getX(), event.getY());
			}
			mouseX = event.getX();
			mouseY = event.getY();
		}
	}*/
}
