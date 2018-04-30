package com.pruebas.carlos.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.pruebas.carlos.entities.Movimiento;
import com.pruebas.carlos.interfaces.IMovimientos;

public class IMovimientosImpl implements IMovimientos {
	
	private static final String PERSISTENCE_UNIT_NAME = "com.pruebas.carlos";;
    private static final EntityManager entityMgrObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    private static final EntityTransaction transactionObj = entityMgrObj.getTransaction();
    
    public IMovimientosImpl(){    	
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Movimiento> listaMovimientos() {
		Query queryObj = entityMgrObj.createQuery("select n from Movimiento n order by n.idmovimientos");
        List<Movimiento> lista = queryObj.getResultList();
        if (lista != null && lista.size() > 0) {
            return lista;
        } else {
            return null;
        }
	}

	@Override
	public void edit(Movimiento movimiento) {
		if(!transactionObj.isActive()){
			transactionObj.begin();
		}
		entityMgrObj.remove(entityMgrObj.merge(movimiento));
		transactionObj.commit();		
	}

	@Override
	public void remove(Movimiento movimiento) {
		if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
        entityMgrObj.merge(movimiento);
        transactionObj.commit();
	}

	@Override
	public Movimiento find(Object id) {
        return entityMgrObj.find(Movimiento.class, id);
	}

	@Override
	public void create(Movimiento movimiento) {
		if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
		entityMgrObj.persist(movimiento);
		transactionObj.commit();
	}


}
