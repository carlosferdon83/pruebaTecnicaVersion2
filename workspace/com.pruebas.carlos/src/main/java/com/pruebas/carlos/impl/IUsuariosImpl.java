package com.pruebas.carlos.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import com.pruebas.carlos.entities.Usuario;
import com.pruebas.carlos.interfaces.IUsuarios;

public class IUsuariosImpl implements IUsuarios {
	
	static String persistence; 
    static EntityManager entityMgrObj; 
    static EntityManagerFactory emf;
    static EntityTransaction transactionObj; 
    
    public IUsuariosImpl(){   
    	persistence = "com.pruebas.carlos";
    	emf = Persistence.createEntityManagerFactory(persistence);
    	entityMgrObj = emf.createEntityManager();
    	transactionObj = entityMgrObj.getTransaction();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listaUsuarios() {
		Query queryObj = entityMgrObj.createQuery("SELECT t FROM Usuario t");
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
