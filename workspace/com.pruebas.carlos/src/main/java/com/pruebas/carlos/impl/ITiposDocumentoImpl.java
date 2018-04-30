package com.pruebas.carlos.impl;

import com.pruebas.carlos.entities.Tiposdocumento;
import com.pruebas.carlos.interfaces.ITiposDocumento;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ITiposDocumentoImpl implements ITiposDocumento {
	private static final String PERSISTENCE_UNIT_NAME = "com.pruebas.carlos";;
    private static final EntityManager entityMgrObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    private static final EntityTransaction transactionObj = entityMgrObj.getTransaction();
    
    public ITiposDocumentoImpl(){       
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Tiposdocumento> listaTiposDocumento() {
        Query queryObj = entityMgrObj.createQuery("SELECT t FROM Tiposdocumento t");
        List<Tiposdocumento> lista = queryObj.getResultList();
        if (lista != null && lista.size() > 0) {
            return lista;
        } else {
            return null;
        }
    }
    
    @Override
    public void remove(Tiposdocumento tipo) {
        if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
        entityMgrObj.remove(entityMgrObj.merge(tipo));
        transactionObj.commit();
    }

    @Override
    public void edit(Tiposdocumento tipo) {
        if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
        entityMgrObj.merge(tipo);
        transactionObj.commit();
    }
    
    @Override
    public Tiposdocumento find(Object id) {
        return entityMgrObj.find(Tiposdocumento.class, id);
    }

	@Override
	public void create(Tiposdocumento tipo) {
		if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
		entityMgrObj.persist(tipo);
		transactionObj.commit();
		
	}
    
}
