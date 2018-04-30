package com.pruebas.carlos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TIPOSDOCUMENTO database table.
 * 
 */
@Entity
@Table(name="TIPOSDOCUMENTO")
@NamedQuery(name="Tiposdocumento.findAll", query="SELECT t FROM Tiposdocumento t")
public class Tiposdocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private long idtipodocumento;

	@Column(nullable=false, length=30)
	private String descripcion;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="tiposdocumento")
	private List<Cliente> clientes;

	public Tiposdocumento() {
	}

	public long getIdtipodocumento() {
		return this.idtipodocumento;
	}

	public void setIdtipodocumento(long idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente addCliente(Cliente cliente) {
		getClientes().add(cliente);
		cliente.setTiposdocumento(this);

		return cliente;
	}

	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setTiposdocumento(null);

		return cliente;
	}

}