package com.pruebas.carlos.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import com.pruebas.carlos.entities.Usuario;
import com.pruebas.carlos.interfaces.IUsuarios;

public class IUsuariosImpl implements IUsuarios {
	
	private static final String PERSISTENCE_UNIT_NAME = "com.pruebas.carlos";;
    private static final EntityManager entityMgrObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    private static final EntityTransaction transactionObj = entityMgrObj.getTransaction();
    
    public IUsuariosImpl(){    	
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listaUsuarios() {
		Query queryObj = entityMgrObj.createQuery("SELECT t FROM Tiposdocumento t");
        List<Usuario> lista = queryObj.getResultList();
        if (lista != null && lista.size() > 0) {
            return lista;
        } else {
            return null;
        }
	}
	
	@Override
	public Usuario validarUsuario(String usuario, String clave){
        Usuario usuarios = null;
        try{
            Query query = entityMgrObj.createQuery("select u from Usuario u where u.login= :usuario and u.pass = :clave");
            query.setParameter("usuario", usuario);
            query.setParameter("clave", clave);
            usuarios = (Usuario) query.getSingleResult();
        }catch(Exception ex){
            System.out.println("Un error ha ocurrido "+ex.getMessage());
        }
        return usuarios;
    }
    

	@Override
	public void edit(Usuario usuario) {
		if(!transactionObj.isActive()){
			transactionObj.begin();
		}
		entityMgrObj.remove(entityMgrObj.merge(usuario));
		transactionObj.commit();		
	}

	@Override
	public void remove(Usuario usuario) {
		if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
        entityMgrObj.merge(usuario);
        transactionObj.commit();		
	}

	@Override
	public Usuario find(Object id) {		
		return entityMgrObj.find(Usuario.class, id);
	}

	@Override
	public void create(Usuario usuario) {
		if (!transactionObj.isActive()) {
            transactionObj.begin();
        }
		entityMgrObj.persist(usuario);
		transactionObj.commit();		
	}

}
