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
import com.pruebas.carlos.entities.Auditoria;
import com.pruebas.carlos.factory.Factory;
import com.pruebas.carlos.interfaces.IAuditoria;
import com.pruebas.carlos.util.JsfUtil;
import com.pruebas.carlos.util.JsfUtil.PersistAction;

@ManagedBean(name = "auditoriaController")
@SessionScoped
public class AuditoriaController {
	
	private List<Auditoria> items = null;
    private Auditoria selected;
    private Factory factory;
    private IAuditoria auditoria;

    @PostConstruct
    public void init() {
    	factory = new Factory();
    	auditoria = factory.obtenerAuditoria();
    }

    public Auditoria getSelected() {
        return selected;
    }

    public void setSelected(Auditoria selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Auditoria prepareCreate() {
        selected = new Auditoria();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, "Auditoria Registrada");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Auditoria Actualizada");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Auditoria Borrada");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Auditoria> getItems() {
        if (items == null) {
            items = auditoria.listaAuditoria();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                /*if (persistAction == PersistAction.DELETE) {
                    auditoria.edit(selected);
                }
                if (persistAction == PersistAction.UPDATE){
                    .remove(selected);
                }
                if (persistAction == PersistAction.CREATE){
                	
                }*/
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

    public List<Auditoria> getItemsAvailableSelectMany() {
        return auditoria.listaAuditoria();
    }

    public List<Auditoria> getItemsAvailableSelectOne() {
        return auditoria.listaAuditoria();
    }

    @FacesConverter(forClass = Auditoria.class)
    public static class AuditoriaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AuditoriaController controller = (AuditoriaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "auditoriaController");
            return null;//controller.auditoria.find(getKey(value));
        }

        java.math.BigDecimal getKey(String value) {
            java.math.BigDecimal key;
            key = new java.math.BigDecimal(value);
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
            if (object instanceof Auditoria) {
                Auditoria o = (Auditoria) object;
                return getStringKey(o.getIdauditoria());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Auditoria.class.getName()});
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

	public IAuditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(IAuditoria auditoria) {
		this.auditoria = auditoria;
	}


}
