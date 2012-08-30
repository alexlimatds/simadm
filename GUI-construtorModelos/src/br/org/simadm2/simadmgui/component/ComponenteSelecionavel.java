package br.org.simadm2.simadmgui.component;

import javax.swing.JComponent;

public abstract class ComponenteSelecionavel extends JComponent {
	private boolean selecionado;

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
		repaint();
	}
}
