package org.simadm2.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.simadm2.facade.UsuarioFacade;
import org.simadm2.model.TipoUsuario;
import org.simadm2.model.Usuarios;

@RequestScoped
@ManagedBean
public class LoginMB extends AbstractMB {
	@ManagedProperty(value = UsuarioMB.INJECTION_NAME)
	private UsuarioMB userMB;

	private String email;
	private String senha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String login() {
		UsuarioFacade userFacade = new UsuarioFacade();

		Usuarios user = userFacade.isValidLogin(email, senha);
		
		if(user != null){
			userMB.setUser(user);
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().setAttribute("user", user);
			
			if(user.isAdmin()){
				return "/pages/protected/admin/index.xhtml";
			}
			else if(user.isAluno()){
				return "/pages/protected/aluno/index.xhtml";
				
			}
			else if(user.isProfessor())
			{
				return "/pages/protected/professor/index.xhtml";
				
			}
			//return "/pages/protected/Inicio2.xhtml";
		}

		displayErrorMessageToUser("Email ou senha inválidos!");
		
		return null;
	}

	public void setUserMB(UsuarioMB userMB) {
		this.userMB = userMB;
	}	
}