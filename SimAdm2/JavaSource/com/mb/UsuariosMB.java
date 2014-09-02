package com.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.facade.UsuarioFacade;
import com.model.Usuarios;

@ViewScoped
@ManagedBean(name="UsuariosMB")
public class UsuariosMB extends AbstractMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuarios usuario;
	private List<Usuarios> usuarios;
	private UsuarioFacade usuarioFacade;
	private Usuarios usuarioSelecionado;
	
	
	public void criarUsuario() {
		try {
			getUsuarioFacade().criarUsuario(usuario);
			//closeDialog();
			displayInfoMessageToUser("Usuário Criado com Sucesso!!");
			ListaUsuarios();
			resetUsuario();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Erro ao Criar Usuário.");
			e.printStackTrace();
		}
	}
	
	public void updateUsuario() {
		try {
			getUsuarioFacade().updateUsuario(usuario);
			//closeDialog();
			displayInfoMessageToUser("usuario atualizado com Sucesso!");
			ListaUsuarios();
			resetUsuario();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Erro ao Editar Usuário.");
			e.printStackTrace();
		}
	}
	
	public void deleteUsuario() {
		try {
			getUsuarioFacade().deleteUsuario(usuario);
			//closeDialog();
			displayInfoMessageToUser("Usuario deletado com Sucesso!");
			ListaUsuarios();
			resetUsuario();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Erro ao Deletar Usuário.");
			e.printStackTrace();
		}
	}

	
	private void ListaUsuarios() {
		usuarios = getUsuarioFacade().listAll();
	}

	public void resetUsuario() {
		usuario = new Usuarios();
	}
	
	
	public UsuarioFacade getUsuarioFacade() {
		if (usuarioFacade == null) {
			usuarioFacade = new UsuarioFacade();
		}

		return usuarioFacade;
	}
	
	public List<Usuarios> getAllusers() {
		if (usuarios == null) {
			ListaUsuarios();
		}

		return usuarios;
	}
	
	public Usuarios getUsuario() {
		if (usuario == null) {
			usuario = new Usuarios();
		}

		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	
	
	public void setusuarioSelecionado(Usuarios usuario) {
		usuarioSelecionado = getUsuarioFacade().findUsuario(usuario.getId());
	}

	public Usuarios getusuarioSelecionado() {
		if (usuarioSelecionado == null) {
			usuarioSelecionado = new Usuarios();
		}

		return usuarioSelecionado;
	}
	
}
