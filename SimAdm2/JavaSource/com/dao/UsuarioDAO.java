package com.dao;
 
import java.util.HashMap;
import java.util.Map;
 
import com.model.Usuarios;
 
public class UsuarioDAO extends GenericDAO<Usuarios> {
 
	private static final long serialVersionUID = 1L;

	public UsuarioDAO() {
        super(Usuarios.class);
    }
 
    public Usuarios findUserByEmail(String email){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("email", email);     
 
        return super.findOneResult(Usuarios.FIND_BY_EMAIL, parameters);
    }
    
    public void delete(Usuarios user) {
    	super.delete(user.getId(), Usuarios.class);
}
}