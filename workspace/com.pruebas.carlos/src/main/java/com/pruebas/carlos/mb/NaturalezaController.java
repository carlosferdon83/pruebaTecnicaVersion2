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

import com.pruebas.carlos.entities.Naturaleza;
import com.pruebas.carlos.entities.Tiposdocumento;
import com.pruebas.carlos.factory.Factory;
import com.pruebas.carlos.interfaces.INaturaleza;
import com.pruebas.carlos.util.JsfUtil;
import com.pruebas.carlos.util.JsfUtil.PersistAction;

@ManagedBean(name = "naturalezaController")
@SessionScoped
public class NaturalezaController implements Serializable {
	
	
	private static final long serialVersionUID = -7891532595137553299L;
	private List<Naturaleza> items = null;
    private Naturaleza selected;
    private Factory factory;
    private INaturaleza naturaleza;

    @PostConstruct
    public void init() {
    	Factory factory = new Factory();
    	naturaleza = factory.obtenerNaturaleza();
    }

    public Naturaleza getSelected() {
        return selected;
    }

    public void setSelected(Naturaleza selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    
    public Naturaleza prepareCreate() {
        selected = new Naturaleza();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Registro Exitoso");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Actualizacion Exitosa");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Registro Eliminado");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Naturaleza> getItems() {
        if (items == null) {
            items = naturaleza.listaNaturaleza();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.DELETE) {
                    naturaleza.edit(selected);
                } 
                if (persistAction == PersistAction.UPDATE){
                    naturaleza.remove(selected);
                }
            	if (persistAction == PersistAction.CREATE){
            		naturaleza.create(selected);
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

    public List<Naturaleza> getItemsAvailableSelectMany() {
        return naturaleza.listaNaturaleza();
    }

    public List<Naturaleza> getItemsAvailableSelectOne() {
        return naturaleza.listaNaturaleza();
    }

    @FacesConverter(forClass = Naturaleza.class)
    public static class NaturalezaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NaturalezaController controller = (NaturalezaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "naturalezaController");
            return controller.naturaleza.find(getKey(value));
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
            if (object instanceof Naturaleza) {
                Naturaleza o = (Naturaleza) object;
                return getStringKey(o.getIdnaturaleza());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Naturaleza.class.getName()});
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

	public INaturaleza getNaturaleza() {
		return naturaleza;
	}

	public void setNaturaleza(INaturaleza naturaleza) {
		this.naturaleza = naturaleza;
	}
}
