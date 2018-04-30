package com.pruebas.carlos.interfaces;

import java.util.List;
import com.pruebas.carlos.entities.Naturaleza;

public interface INaturaleza {
	
	public List<Naturaleza> listaNaturaleza();
	public void edit(Naturaleza naturaleza);
	public void remove(Naturaleza naturaleza);
	public Naturaleza find(Object id);
	public void create(Naturaleza naturaleza);
	
}
