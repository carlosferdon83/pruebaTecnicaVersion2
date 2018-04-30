package com.pruebas.carlos.interfaces;

import java.util.List;

import com.pruebas.carlos.entities.Tiposdocumento;

public interface ITiposDocumento {
	public List<Tiposdocumento> listaTiposDocumento();
    public void edit(Tiposdocumento tipo);
    public void remove(Tiposdocumento tipo);
    public Tiposdocumento find(Object id);
    public void create(Tiposdocumento tipo);
}
