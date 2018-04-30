package com.pruebas.carlos.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.pruebas.carlos.entities.Naturaleza;
import com.pruebas.carlos.interfaces.INaturaleza;

public class INaturalezaImpl implements INaturaleza {
	
	private static final String PERSISTENCE_UNIT_NAME = "com.pruebas.carlos";;
    private static final EntityManager entityMgrObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    private static final EntityTransaction transactionObj = entityMgrObj.getTransaction();
    
    public INaturalezaImpl(){    	
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Naturaleza> listaNaturaleza() {
		Query queryObj = entityMgrObj.createQuery("select n from Naturaleza n order by n.idnaturaleza");
        List<Naturaleza> lista = queryObj.getResultList();
        if (lista != null && lista.size() > 0) {
            return lista;
        } else {
            return null;
        }
	}

	@Override
	public void edit(Naturaleza naturaleza) {
		if(!transactionObj.isActive()){
			transactionObj.begin();
		}
		entityMgrObj.remove(entityMgrObj.merge(naturaleza));
		transactionObj.commit();		
	}

	@Override
	public void remove(Naturaleza naturaleza) {
		if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
        entityMgrObj.merge(naturaleza);
        transactionObj.commit();
	}

	@Override
	public Naturaleza find(Object id) {
        return entityMgrObj.find(Naturaleza.class, id);
	}

	@Override
	public void create(Naturaleza naturaleza) {
		if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
		entityMgrObj.persist(naturaleza);
		transactionObj.commit();
	}

}
