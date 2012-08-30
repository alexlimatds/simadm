package br.org.simadm2.simadmgui.component;

import javax.swing.ImageIcon;

/**
 * Classe responsável por criar e disponibilizar os ícones da aplicação.
 * @author Alexandre
 *
 */
public abstract class Icones {
	
	/**
	 * Ícone de estoque.
	 */
	public static final ImageIcon ICON_ESTOQUE = new ImageIcon(Icones.class.getResource("/br/org/simadm2/simadmgui/component/icons/estoque.png"));
	/**
	 * Ícone de variável auxiliar.
	 */
	public static final ImageIcon ICON_VARIAVEL = new ImageIcon(Icones.class.getResource("/br/org/simadm2/simadmgui/component/icons/variavel.png"));
	/**
	 * Ícone de fluxo.
	 */
	public static final ImageIcon ICON_FLUXO = new ImageIcon(Icones.class.getResource("/br/org/simadm2/simadmgui/component/icons/fluxo.png"));
	/**
	 * Ícone de canal.
	 */
	public static final ImageIcon ICON_CANAL = new ImageIcon(Icones.class.getResource("/br/org/simadm2/simadmgui/component/icons/canal.png"));
	/**
	 * Ícone de constante.
	 */
	public static final ImageIcon ICON_CONSTANTE = new ImageIcon(Icones.class.getResource("/br/org/simadm2/simadmgui/component/icons/constante.png"));
	
}
