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

import com.pruebas.carlos.entities.Cuenta;
import com.pruebas.carlos.entities.Movimiento;
import com.pruebas.carlos.factory.Factory;
import com.pruebas.carlos.interfaces.ICuentas;
import com.pruebas.carlos.interfaces.IMovimientos;
import com.pruebas.carlos.util.JsfUtil;
import com.pruebas.carlos.util.JsfUtil.PersistAction;

@ManagedBean(name = "movimientosController")
@SessionScoped
public class MovimientosController {

	private List<Movimiento> items = null;
    private Movimiento selected;
    private Cuenta cuentas;
    private Factory factory, factoryCuenta;
    private IMovimientos movimientos;
    private ICuentas cuenta;

    @PostConstruct
    public void init(){
    	factory = new Factory();
    	factoryCuenta = new Factory();
    	movimientos = factory.obtenerMovimientos();
    	cuenta = factoryCuenta.obtenerCuentas();
    }
    

    public Movimiento getSelected() {
        return selected;
    }

    public void setSelected(Movimiento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }   

    public Movimiento prepareCreate() {
        selected = new Movimiento();
        initializeEmbeddableKey();
        return selected;
    }
    
    
    public boolean validation(){        
        Cuenta datoCuenta = cuenta.obtenerSaldoCuenta(selected.getCuenta().getIdcuentas());
        double saldo = datoCuenta.getSaldo().doubleValue();;
        double transaccion = selected.getValor().doubleValue();
        if(saldo < transaccion && selected.getNaturaleza().getIdnaturaleza()==2){        	
            return false;        
        }else{
        	return true;
        }       
        
    }

    public void create() {        
        if(validation()){
            persist(PersistAction.CREATE, "Movimiento Registrado");
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }            
       }else{
            JsfUtil.addErrorMessage("No tiene suficiente saldo para la transaccion");
        }    
        
    }

    public void update() {
        persist(PersistAction.UPDATE, "Movimiento Actualizado");
    }

    public void destroy() {
        persist(PersistAction.DELETE, "Movimiento Borrado");
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Movimiento> getItems() {
        if (items == null) {
            items = movimientos.listaMovimientos();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();            
            try {
                if (persistAction == PersistAction.DELETE) {
                    movimientos.edit(selected);
                }
                if (persistAction == PersistAction.UPDATE){
                    movimientos.remove(selected);
                }
            	if (persistAction == PersistAction.CREATE){
            		movimientos.create(selected);
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

    public List<Movimiento> getItemsAvailableSelectMany() {
        return movimientos.listaMovimientos();
    }

    public List<Movimiento> getItemsAvailableSelectOne() {
        return movimientos.listaMovimientos();
    }

    public Cuenta getCuentas() {
        return cuentas;
    }

    public void setCuentas(Cuenta cuentas) {
        this.cuentas = cuentas;
    }

    @FacesConverter(forClass = Movimiento.class)
    public static class MovimientosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovimientosController controller = (MovimientosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "movimientosController");
            return controller.movimientos.find(getKey(value));
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
            if (object instanceof Movimiento) {
                Movimiento o = (Movimiento) object;
                return getStringKey(o.getIdmovimientos());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Movimiento.class.getName()});
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


	public IMovimientos getMovimientos() {
		return movimientos;
	}


	public void setMovimientos(IMovimientos movimientos) {
		this.movimientos = movimientos;
	}


	public ICuentas getCuenta() {
		return cuenta;
	}


	public void setCuenta(ICuentas cuenta) {
		this.cuenta = cuenta;
	}


	public Factory getFactoryCuenta() {
		return factoryCuenta;
	}


	public void setFactoryCuenta(Factory factoryCuenta) {
		this.factoryCuenta = factoryCuenta;
	}
}
