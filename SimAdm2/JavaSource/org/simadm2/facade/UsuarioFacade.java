package org.simadm2.facade;

import org.simadm2.dao.UsuarioDAO;
import org.simadm2.model.Usuarios;

public class UsuarioFacade {
	private UsuarioDAO userDAO = new UsuarioDAO();

	public Usuarios isValidLogin(String email, String password) {
		userDAO.beginTransaction();
		Usuarios user = userDAO.findUserByEmail(email);

		if (user == null || !user.getSenha().equals(password)) {
			return null;
		}

		return user;
	}
}