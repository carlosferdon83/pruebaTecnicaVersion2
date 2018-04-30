package com.pruebas.carlos.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import com.pruebas.carlos.entities.Cuenta;
import com.pruebas.carlos.interfaces.ICuentas;

public class ICuentasImpl implements ICuentas {

	private static final String PERSISTENCE_UNIT_NAME = "com.pruebas.carlos";;
    private static final EntityManager entityMgrObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    private static final EntityTransaction transactionObj = entityMgrObj.getTransaction();
    
    public ICuentasImpl(){    	
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Cuenta> listaCuentas() {
		Query queryObj = entityMgrObj.createQuery("select n from Cuenta n order by n.idcuentas");
        List<Cuenta> lista = queryObj.getResultList();
        if (lista != null && lista.size() > 0) {
            return lista;
        } else {
            return null;
        }
	}

	@Override
	public void edit(Cuenta cuenta) {
		if(!transactionObj.isActive()){
			transactionObj.begin();
		}
		entityMgrObj.remove(entityMgrObj.merge(cuenta));
		transactionObj.commit();		
	}

	@Override
	public void remove(Cuenta cuenta) {
		if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
        entityMgrObj.merge(cuenta);
        transactionObj.commit();
	}

	@Override
	public Cuenta find(Object id) {
        return entityMgrObj.find(Cuenta.class, id);
	}

	@Override
	public void create(Cuenta cuenta) {
		if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
		entityMgrObj.persist(cuenta);
		transactionObj.commit();
	}

	@Override
	public Cuenta obtenerSaldoCuenta(long id) {
		Cuenta cuenta = null;
        try{
            Query query = entityMgrObj.createQuery("select c from Cuenta c where c.idcuentas = ?1");
            query.setParameter(1,id);
            cuenta = (Cuenta) query.getSingleResult();
        }catch(Exception ex){
            System.out.println("Un error ha ocurrido "+ex.getMessage());
        }
        return cuenta;
	}


}
