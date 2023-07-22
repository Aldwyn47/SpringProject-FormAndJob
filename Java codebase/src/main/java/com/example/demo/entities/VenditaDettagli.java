package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name="dettaglivendite")
public class VenditaDettagli {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	@JsonProperty(value="id", required=false)
	@JsonIgnore
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_vendita")
	@JsonProperty(value="vendita", required=false)
	@JsonIgnore
	private Vendita vendita;
	
	@ManyToOne
	@JoinColumn(name="id_prodotto")
	@JsonProperty(value="prodotto", required=false)
	private Prodotto prodotto;
	
	@Column(name="quantita")
	@JsonProperty(value="quantita", required=false)
	private Integer quantita;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}
	
	public Vendita getVendita() {
		if (vendita==null)
				return null;
		else {
			Vendita v = new Vendita();
			v.setCliente(this.vendita.getCliente());
			v.setData(this.vendita.getData());
			v.setCodice_vendita(this.vendita.getCodice_vendita());
			v.setId(this.vendita.getId());
			v.setPagato(this.vendita.getPagato());
			return v;
		}
	}

	public void setVendita(Vendita vendita) {
		this.vendita = vendita;
	}

	public Prodotto getProdotto() {
		if (this.prodotto==null)
			return null;
		else {
			Prodotto p = new Prodotto();
			p.setCategoria(this.prodotto.getCategoria());
			p.setCodice_prodotto(this.prodotto.getCodice_prodotto());
			p.setCosto(this.prodotto.getCosto());
			p.setDescrizione(this.prodotto.getDescrizione());
			p.setId(this.prodotto.getId());
			p.setNome(this.prodotto.getNome());
			return p;
		}
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}
	
	@JsonIgnore
	public boolean isValid() {
		return this.prodotto!=null && this.quantita!=null && this.quantita>=0;
	}

}
