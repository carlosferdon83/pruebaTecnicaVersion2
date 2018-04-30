package com.pruebas.carlos.interfaces;

import java.util.List;
import com.pruebas.carlos.entities.Cuenta;

public interface ICuentas {
	
	public List<Cuenta> listaCuentas();
	public void edit(Cuenta cuenta);
	public void remove(Cuenta cuenta);
	public Cuenta find(Object id);
	public void create(Cuenta cuenta);
	public Cuenta obtenerSaldoCuenta(long id);

}
