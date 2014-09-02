package com.facade;

import java.io.Serializable;
import java.util.List;

import com.dao.UsuarioDAO;
import com.model.Usuarios;

public class UsuarioFacade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UsuarioDAO userDAO = new UsuarioDAO();

	public Usuarios isValidLogin(String email, String password) {
		userDAO.beginTransaction();
		Usuarios user = userDAO.findUserByEmail(email);

		if (user == null || !user.getSenha().equals(password)) {
			return null;
		}

		return user;
	}
	
	
	public void criarUsuario(Usuarios user) {
		userDAO.beginTransaction();
		userDAO.save(user);
		userDAO.commitAndCloseTransaction();
	}
	
	
	public void updateUsuario(Usuarios user) {
		userDAO.beginTransaction();
		Usuarios editUsuario = userDAO.find(user.getId());
		editUsuario.setNome(user.getNome());
		editUsuario.setEmail(user.getEmail());
		editUsuario.setSenha(user.getSenha());
		
		userDAO.commitAndCloseTransaction();
	}
	
	
	public void deleteUsuario(Usuarios user){
		userDAO.beginTransaction();
		Usuarios deleteUsuario = userDAO.findReferenceOnly(user.getId());
		userDAO.delete(deleteUsuario);
		userDAO.commitAndCloseTransaction();
		
	}
	
	
	public Usuarios findUsuario(int usuarioID) {
		userDAO.beginTransaction();
		Usuarios usuario = userDAO.find(usuarioID);
		userDAO.closeTransaction();
		return usuario;
	}
	
	public List<Usuarios> listAll() {
		userDAO.beginTransaction();
		List<Usuarios> result = userDAO.findAll();
		userDAO.closeTransaction();

		return result;
	}
	
}