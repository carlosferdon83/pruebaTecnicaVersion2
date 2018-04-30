package com.pruebas.carlos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the NATURALEZA database table.
 * 
 */
@Entity
@Table(name="NATURALEZA")
@NamedQuery(name="Naturaleza.findAll", query="SELECT n FROM Naturaleza n")
public class Naturaleza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private long idnaturaleza;

	@Column(nullable=false, length=20)
	private String descripcion;

	//bi-directional many-to-one association to Movimiento
	@OneToMany(mappedBy="naturaleza")
	private List<Movimiento> movimientos;

	public Naturaleza() {
	}

	public long getIdnaturaleza() {
		return this.idnaturaleza;
	}

	public void setIdnaturaleza(long idnaturaleza) {
		this.idnaturaleza = idnaturaleza;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Movimiento> getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public Movimiento addMovimiento(Movimiento movimiento) {
		getMovimientos().add(movimiento);
		movimiento.setNaturaleza(this);

		return movimiento;
	}

	public Movimiento removeMovimiento(Movimiento movimiento) {
		getMovimientos().remove(movimiento);
		movimiento.setNaturaleza(null);

		return movimiento;
	}

}