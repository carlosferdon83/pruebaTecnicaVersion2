package com.pruebas.carlos.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.pruebas.carlos.entities.Cliente;
import com.pruebas.carlos.interfaces.IClientes;

public class IClientesImpl implements IClientes, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5689747170509117033L;
	private static final String PERSISTENCE_UNIT_NAME = "com.pruebas.carlos";;
    private static final EntityManager entityMgrObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    private static final EntityTransaction transactionObj = entityMgrObj.getTransaction();
    
    public IClientesImpl(){    	
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> listaClientes() {
		Query queryObj = entityMgrObj.createQuery("select n from Cliente n order by n.idcliente");
        List<Cliente> lista = queryObj.getResultList();
        if (lista != null && lista.size() > 0) {
            return lista;
        } else {
            return null;
        }
	}

	@Override
	public void edit(Cliente cliente) {
		if(!transactionObj.isActive()){
			transactionObj.begin();
		}
		entityMgrObj.remove(entityMgrObj.merge(cliente));
		transactionObj.commit();		
	}

	@Override
	public void remove(Cliente cliente) {
		if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
        entityMgrObj.merge(cliente);
        transactionObj.commit();
	}

	@Override
	public Cliente find(Object id) {
        return entityMgrObj.find(Cliente.class, id);
	}

	@Override
	public void create(Cliente cliente) {
		if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
		entityMgrObj.persist(cliente);
		transactionObj.commit();
	}


}
