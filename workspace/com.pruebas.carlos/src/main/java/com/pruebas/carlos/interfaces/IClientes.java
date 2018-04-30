package com.pruebas.carlos.interfaces;

import java.util.List;
import com.pruebas.carlos.entities.Cliente;

public interface IClientes {
	
	public List<Cliente> listaClientes();
	public void edit(Cliente cliente);
	public void remove(Cliente cliente);
	public Cliente find(Object id);
	public void create(Cliente cliente);
	
}
