package com.pruebas.carlos.factory;

import com.pruebas.carlos.impl.IAuditoriaImpl;
import com.pruebas.carlos.impl.IClientesImpl;
import com.pruebas.carlos.impl.ICuentasImpl;
import com.pruebas.carlos.impl.IMovimientosImpl;
import com.pruebas.carlos.impl.INaturalezaImpl;
import com.pruebas.carlos.impl.ITiposDocumentoImpl;
import com.pruebas.carlos.impl.IUsuariosImpl;
import com.pruebas.carlos.interfaces.IAuditoria;
import com.pruebas.carlos.interfaces.IClientes;
import com.pruebas.carlos.interfaces.ICuentas;
import com.pruebas.carlos.interfaces.IMovimientos;
import com.pruebas.carlos.interfaces.INaturaleza;
import com.pruebas.carlos.interfaces.ITiposDocumento;
import com.pruebas.carlos.interfaces.IUsuarios;

public class Factory {
	
	public ITiposDocumento obtenerInstancia(){        
        return new ITiposDocumentoImpl();       
    }
	
	public INaturaleza obtenerNaturaleza(){
		return new INaturalezaImpl();
	}
	
	public IUsuarios obtenerUsuarios(){
		return new IUsuariosImpl();
	}
	
	public ICuentas obtenerCuentas(){
		return new ICuentasImpl();
	}
	
	public IMovimientos obtenerMovimientos(){
		return new IMovimientosImpl();
	}
	
	public IClientes obtenerClientes(){
		return new IClientesImpl();
	}
	
	public IAuditoria obtenerAuditoria(){
		return new IAuditoriaImpl();
	}

}
