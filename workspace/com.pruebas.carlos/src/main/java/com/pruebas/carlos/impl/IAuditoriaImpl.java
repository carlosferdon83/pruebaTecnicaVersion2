package com.pruebas.carlos.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.pruebas.carlos.entities.Auditoria;
import com.pruebas.carlos.interfaces.IAuditoria;

public class IAuditoriaImpl implements IAuditoria {
	
	private static final String PERSISTENCE_UNIT_NAME = "com.pruebas.carlos";;
    private static final EntityManager entityMgrObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    private static final EntityTransaction transactionObj = entityMgrObj.getTransaction();
    

	public IAuditoriaImpl(){		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Auditoria> listaAuditoria() {
		Query queryObj = entityMgrObj.createQuery("select n from Auditoria n order by n.idauditoria");
        List<Auditoria> lista = queryObj.getResultList();
        if (lista != null && lista.size() > 0) {
            return lista;
        } else {
            return null;
        }
	}


}

