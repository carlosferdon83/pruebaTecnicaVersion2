package com.pruebas.carlos.mb;
import com.pruebas.carlos.factory.Factory;
import com.pruebas.carlos.entities.Tiposdocumento;
import com.pruebas.carlos.interfaces.ITiposDocumento;
import com.pruebas.carlos.util.JsfUtil;
import com.pruebas.carlos.util.JsfUtil.PersistAction;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@ManagedBean(name = "tiposDocumentoController")
@ViewScoped
public class TiposdocumentoController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1358935663600656241L;
	private List<Tiposdocumento> items = null;
    private Tiposdocumento selected;
    private Factory factory;
    private ITiposDocumento tipoDocumento;

    @PostConstruct
    public void init() {
        factory = new Factory();
        tipoDocumento = factory.obtenerInstancia();
    }

    public Tiposdocumento getSelected() {
        return selected;
    }

    public void setSelected(Tiposdocumento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Tiposdocumento prepareCreate() {
        selected = new Tiposdocumento();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {       
        //ResourceBundle.getBundle("/Bundle").getString("TiposdocumentoCreated")
        persist(PersistAction.CREATE, "Tipo Documento Registrado");
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, "Tipo Documento Actualizado");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Tipo Documento Eliminado");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Tiposdocumento> getItems() {        
        if (items == null) {
            items = tipoDocumento.listaTiposDocumento();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    tipoDocumento.edit(selected);
                } else {
                    tipoDocumento.remove(selected);
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

    public List<Tiposdocumento> getItemsAvailableSelectMany() {
        return tipoDocumento.listaTiposDocumento();
    }

    public List<Tiposdocumento> getItemsAvailableSelectOne() {
        return tipoDocumento.listaTiposDocumento();
    }

    public Factory getFactory() {
        return factory;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public ITiposDocumento getTipoDcumento() {
        return tipoDocumento;
    }

    public void setTipoDcumento(ITiposDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @FacesConverter(forClass = Tiposdocumento.class)
    public class TiposdocumentoControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TiposdocumentoController controller = (TiposdocumentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tiposDocumentoController");
            return tipoDocumento.find(getKey(value));
        }

        java.math.BigDecimal getKey(String value) {
            java.math.BigDecimal key;
            key = new java.math.BigDecimal(value);
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
            if (object instanceof Tiposdocumento) {
                Tiposdocumento o = (Tiposdocumento) object;
                return getStringKey(o.getIdtipodocumento());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Tiposdocumento.class.getName()});
                return null;
            }
        }

    }



}
