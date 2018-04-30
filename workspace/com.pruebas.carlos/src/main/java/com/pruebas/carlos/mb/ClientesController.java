package com.pruebas.carlos.mb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.pruebas.carlos.entities.Cliente;
import com.pruebas.carlos.factory.Factory;
import com.pruebas.carlos.interfaces.IClientes;
import com.pruebas.carlos.util.JsfUtil;
import com.pruebas.carlos.util.JsfUtil.PersistAction;

@ManagedBean(name = "clientesController")
@SessionScoped
public class ClientesController {

	private List<Cliente> items = null;
    private Cliente selected;
    private Factory factory;
    private IClientes clientes;

    @PostConstruct
    public void init(){
    	factory = new Factory();
    	clientes = factory.obtenerClientes();
    }

    public Cliente getSelected() {
        return selected;
    }

    public void setSelected(Cliente selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Cliente prepareCreate() {
        selected = new Cliente();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Cliente Registrado");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Cliente actualizado");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Cliente Borrado");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Cliente> getItems() {
        if (items == null) {
            items = clientes.listaClientes();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.DELETE) {
                    clientes.edit(selected);
                } 
                if (persistAction == PersistAction.UPDATE){
                    clientes.remove(selected);
                }
                if (persistAction == PersistAction.CREATE){
                	clientes.create(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (Exception ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, "Error de persistencia");
                }
            } 
        }
    }

    public List<Cliente> getItemsAvailableSelectMany() {
        return clientes.listaClientes();
    }

    public List<Cliente> getItemsAvailableSelectOne() {
        return clientes.listaClientes();
    }

    @FacesConverter(forClass = Cliente.class)
    public static class ClientesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClientesController controller = (ClientesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "clientesController");
            return controller.clientes.find(getKey(value));
        }

        long getKey(String value) {
            long key;
            key = Long.parseLong(value);
            return key;
        }

        String getStringKey(long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cliente) {
                Cliente o = (Cliente) object;
                return getStringKey(o.getIdcliente());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cliente.class.getName()});
                return null;
            }
        }

    }

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}

	public IClientes getClientes() {
		return clientes;
	}

	public void setClientes(IClientes clientes) {
		this.clientes = clientes;
	}
}
