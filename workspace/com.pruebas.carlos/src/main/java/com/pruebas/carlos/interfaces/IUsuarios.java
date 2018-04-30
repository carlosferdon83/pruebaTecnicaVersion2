package com.pruebas.carlos.interfaces;

import java.util.List;
import com.pruebas.carlos.entities.Usuario;

public interface IUsuarios {
	
	public List<Usuario> listaUsuarios();
	public void edit(Usuario usuario);
	public void remove(Usuario usuario);
	public Usuario find(Object id);
	public void create(Usuario usuario);
	public Usuario validarUsuario(String usuario, String clave);

}
