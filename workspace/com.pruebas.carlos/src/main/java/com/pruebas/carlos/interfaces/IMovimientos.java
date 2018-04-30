package com.pruebas.carlos.interfaces;

import java.util.List;

import com.pruebas.carlos.entities.Movimiento;

public interface IMovimientos {
	
	public List<Movimiento> listaMovimientos();
	public void edit(Movimiento movimiento);
	public void remove(Movimiento movimiento);
	public Movimiento find(Object id);
	public void create(Movimiento movimiento);

}
