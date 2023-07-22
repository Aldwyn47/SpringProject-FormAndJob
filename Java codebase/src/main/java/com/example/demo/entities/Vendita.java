package com.example.demo.entities;

import java.sql.*;
import java.util.*;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

@Entity
@Table(name="vendite")
public class Vendita {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	@JsonProperty(value="id", required=false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_cliente")
	@JsonProperty(value="cliente", required=false)
	private Cliente cliente;
	
	@Column(name="codice_vendita")
	@JsonProperty(value="codice_vendita")
	private String codice_vendita;

	@Column(name="data")
	@JsonProperty(value="data", required=false)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "Europe/Rome")
	private Timestamp data;
	
	@Column(name="pagato")
	@JsonProperty(value="pagato", required=false)
	private Boolean pagato;
	
	@OneToMany(mappedBy="vendita")
	@JsonProperty(value="dettagliVendita", required=false)
	private List<VenditaDettagli> dettagliVendita;
	
	public Vendita() {}
	
	public Vendita(String codice_vendita) {
		this.codice_vendita = codice_vendita;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		if (this.cliente==null)
			return null;
		else
			return this.cliente.clone();
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Timestamp getData() {
		if (data==null)
			return null;
		else {
			Timestamp d = (Timestamp) data.clone();
			return d;
		}
	}

	public void setData(Timestamp data) {
		this.data = data;
	}
	
	public Boolean getPagato() {
		return pagato;
	}

	public void setPagato(Boolean pagato) {
		this.pagato = pagato;
	}
	
	public String getCodice_vendita() {
		return codice_vendita;
	}

	public void setCodice_vendita(String codice_vendita) {
		this.codice_vendita = codice_vendita;
	}
	
	public List<VenditaDettagli> getDettagliVendita(){
		if (this.dettagliVendita==null)
			return null;
		else {
			List<VenditaDettagli> copy = new ArrayList<>();
			for (VenditaDettagli vd : this.dettagliVendita)
				copy.add(vd);
			return copy;
		}
	}
	
	public void setDettagliVendita(List<VenditaDettagli> l) {
		this.dettagliVendita = l;
	}
	
	public void splice(Vendita v) {
		Cliente nuovoCliente = v.getCliente();
		Timestamp nuovaData = v.getData();
		Boolean nuovoPagato = v.getPagato();
		if (nuovoCliente!=null)
			this.cliente = nuovoCliente;
		if (nuovaData!=null)
			this.data = nuovaData;
		if (nuovoPagato!=null)
			this.pagato = nuovoPagato;
	}
	
	public Vendita clone() {
		Vendita v = new Vendita();
		v.setCodice_vendita(this.codice_vendita);
		v.setId(this.id);
		v.setPagato(this.pagato);
		v.setCliente(this.cliente.clone());
		if (this.data!=null)
			v.setData((Timestamp)this.data.clone());
		return v;
	}
	
	@JsonIgnore
	public boolean isValid() {
		return this.pagato!=null && this.cliente!=null && this.dettagliVendita!=null;
	}

}
