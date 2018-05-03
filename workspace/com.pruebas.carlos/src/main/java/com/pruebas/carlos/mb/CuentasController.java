package com.pruebas.carlos.mb;

import java.io.Serializable;
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
import com.pruebas.carlos.entities.Cuenta;
import com.pruebas.carlos.factory.Factory;
import com.pruebas.carlos.interfaces.ICuentas;
import com.pruebas.carlos.util.JsfUtil;
import com.pruebas.carlos.util.JsfUtil.PersistAction;

@ManagedBean(name = "cuentasController")
@SessionScoped
public class CuentasController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7034953990245654547L;
	private List<Cuenta> items = null;
    private Cuenta selected;
    private Factory factory;
    private ICuentas cuentas;

    @PostConstruct
    public void init() {
    	Factory factory = new Factory();
    	cuentas = factory.obtenerCuentas();
    }

    public Cuenta getSelected() {
        return selected;
    }

    public void setSelected(Cuenta selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Cuenta prepareCreate() {
        selected = new Cuenta();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Cuenta Registrada");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Cuenta Actualizada");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Cuenta Borrada");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Cuenta> getItems() {
        if (items == null) {
            items = cuentas.listaCuentas();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.DELETE) {
                    cuentas.edit(selected);
                }
                if (persistAction == PersistAction.UPDATE){
                    cuentas.remove(selected);
                }
                if (persistAction == PersistAction.CREATE){
                	cuentas.create(selected);
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

    public List<Cuenta> getItemsAvailableSelectMany() {
        return cuentas.listaCuentas();
    }

    public List<Cuenta> getItemsAvailableSelectOne() {
        return cuentas.listaCuentas();
    }

    @FacesConverter(forClass = Cuenta.class)
    public static class CuentasControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CuentasController controller = (CuentasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cuentasController");
            return controller.cuentas.find(getKey(value));
        }

        long getKey(String value) {
            long key;
            key = Long.parseLong(value);
            return key;
        }

        String getStringKey(long l) {
            StringBuilder sb = new StringBuilder();
            sb.append(l);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cuenta) {
                Cuenta o = (Cuenta) object;
                return getStringKey(o.getIdcuentas());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cuenta.class.getName()});
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

	public ICuentas getCuentas() {
		return cuentas;
	}

	public void setCuentas(ICuentas cuentas) {
		this.cuentas = cuentas;
	}


}
