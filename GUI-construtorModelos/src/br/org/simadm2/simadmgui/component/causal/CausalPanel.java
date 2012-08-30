package br.org.simadm2.simadmgui.component.causal;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import br.org.simadm2.simadmgui.component.Icones;

/*
 * Created on 18/04/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * Este é o painel do editor de modelos causais.
 * 
 * @author Servidor
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CausalPanel extends JPanel{
	
	private JLabel label;
	private CausalCanvas canvas;
	private JButton btVariavel, btCanal;
	private JToolBar toolBar;
	private JPopupMenu popUpCanvas, popUpComponent;
	
	public CausalPanel(){
		super();
		
		label = new JLabel("");
		label.setVisible(true);
		
		canvas = new CausalCanvas();
		
		criarBotoes();
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(btVariavel);
		toolBar.add(btCanal);
		
		setLayout(new BorderLayout());
		add(toolBar, BorderLayout.NORTH);
		add(label, BorderLayout.SOUTH);
		add(canvas, BorderLayout.CENTER);
		
	}
	
	private void criarBotoes(){
		
		btVariavel = new JButton(Icones.ICON_VARIAVEL);
		btVariavel.setToolTipText("Variável Auxiliar");
		btVariavel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				canvas.ativarModoDeAdicaoDeVariavel();
			}
		});
		
		btCanal = new JButton(Icones.ICON_CANAL);
		btCanal.setToolTipText("Canal");
		btCanal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				canvas.ativarModoDeAdicaoDeCanal();
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
