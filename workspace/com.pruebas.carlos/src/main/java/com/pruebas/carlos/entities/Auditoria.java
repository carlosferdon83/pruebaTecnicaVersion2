package com.pruebas.carlos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the AUDITORIA database table.
 * 
 */
@Entity
@Table(name="AUDITORIA")
@NamedQuery(name="Auditoria.findAll", query="SELECT a FROM Auditoria a")
public class Auditoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private long idauditoria;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechamovimiento;

	@Column(nullable=false)
	private BigDecimal idcuentas;

	@Column(nullable=false)
	private BigDecimal idmovimientos;

	@Column(nullable=false)
	private BigDecimal idnaturaleza;

	@Column(nullable=false)
	private BigDecimal valor;

	public Auditoria() {
	}

	public long getIdauditoria() {
		return this.idauditoria;
	}

	public void setIdauditoria(long idauditoria) {
		this.idauditoria = idauditoria;
	}

	public Date getFechamovimiento() {
		return this.fechamovimiento;
	}

	public void setFechamovimiento(Date fechamovimiento) {
		this.fechamovimiento = fechamovimiento;
	}

	public BigDecimal getIdcuentas() {
		return this.idcuentas;
	}

	public void setIdcuentas(BigDecimal idcuentas) {
		this.idcuentas = idcuentas;
	}

	public BigDecimal getIdmovimientos() {
		return this.idmovimientos;
	}

	public void setIdmovimientos(BigDecimal idmovimientos) {
		this.idmovimientos = idmovimientos;
	}

	public BigDecimal getIdnaturaleza() {
		return this.idnaturaleza;
	}

	public void setIdnaturaleza(BigDecimal idnaturaleza) {
		this.idnaturaleza = idnaturaleza;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}