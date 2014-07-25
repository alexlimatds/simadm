package org.simadm2.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Usuarios")
@NamedQuery(name = "Usuarios.findUserByEmail", query = "select u from Usuarios u where u.email = :email")
public class Usuarios implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_EMAIL = "Usuarios.findUserByEmail";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(unique = true)
	private String email;
	private String senha;
	private String nome;
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoUsuario getTipo() {
		return tipoUsuario;
	}

	public void setTipo(TipoUsuario role) {
		this.tipoUsuario = role;
	}

	public boolean isAdmin() {
		return TipoUsuario.ADMIN.equals(tipoUsuario);
	}

	public boolean isAluno() {
		return TipoUsuario.ALUNO.equals(tipoUsuario);
	}
	
	public boolean isProfessor() {
		return TipoUsuario.PROFESSOR.equals(tipoUsuario);
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Usuarios) {
			Usuarios user = (Usuarios) obj;
			return user.getId() == id;
		}

		return false;
	}
}