package com.pruebas.carlos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the MOVIMIENTOS database table.
 * 
 */
@Entity
@Table(name="MOVIMIENTOS")
@NamedQuery(name="Movimiento.findAll", query="SELECT m FROM Movimiento m")
public class Movimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_MOVIMIENTOS" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_MOVIMIENTOS")
	@Column(unique=true, nullable=false)
	private long idmovimientos;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechamovimiento;

	@Column(nullable=false)
	private BigDecimal valor;

	//bi-directional many-to-one association to Cuenta
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IDCUENTAS", nullable=false)
	private Cuenta cuenta;

	//bi-directional many-to-one association to Naturaleza
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IDNATURALEZA", nullable=false)
	private Naturaleza naturaleza;

	public Movimiento() {
	}

	public long getIdmovimientos() {
		return this.idmovimientos;
	}

	public void setIdmovimientos(long idmovimientos) {
		this.idmovimientos = idmovimientos;
	}

	public Date getFechamovimiento() {
		return this.fechamovimiento;
	}

	public void setFechamovimiento(Date fechamovimiento) {
		this.fechamovimiento = fechamovimiento;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Cuenta getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Naturaleza getNaturaleza() {
		return this.naturaleza;
	}

	public void setNaturaleza(Naturaleza naturaleza) {
		this.naturaleza = naturaleza;
	}

}