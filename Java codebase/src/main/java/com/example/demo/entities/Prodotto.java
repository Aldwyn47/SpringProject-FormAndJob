package com.example.demo.entities;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;

@Entity
@Table(name="prodotti")
public class Prodotto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	@JsonProperty(value="id", required=false)
	private Long id;
	
	@Column(name="nome")
	@JsonProperty(value="nome", required=false)
	private String nome;
	
	@Column(name="descrizione")
	@JsonProperty(value="descrizione", required=false)
	private String descrizione;
	
	@Column(name="costo")
	@JsonProperty(value="costo", required=false)
	private Long costo;
	
	@Column(name="codice_prodotto")
	@JsonProperty(value="codice_prodotto", required=false)
	private String codice_prodotto;
	
	@ManyToOne
	@JoinColumn(name="id_categoria")
	@JsonProperty(value="categoria", required=false)
	private Categoria categoria;
	
	public Prodotto() {}
	
	public Prodotto(Long id) {
		this.id = id;
	}
	
	public Prodotto(String codice_prodotto) {
		this.codice_prodotto = codice_prodotto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Long getCosto() {
		return costo;
	}

	public void setCosto(Long costo) {
		this.costo = costo;
	}

	public String getCodice_prodotto() {
		return codice_prodotto;
	}

	public void setCodice_prodotto(String codice_prodotto) {
		this.codice_prodotto = codice_prodotto;
	}

	//La categoria viene restituita all'esterno creando una defensive copy
	public Categoria getCategoria() {
		if (this.categoria==null)
			return null;
		else {
			Categoria c = new Categoria();
			c.setId(this.categoria.getId());
			c.setNome(this.categoria.getNome());
			c.setDescrizione(this.categoria.getDescrizione());
			return c;
		}
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	//Questo metodo viene usato per sovrascrivere i campi dell'oggetto usando le informazioni di un secondo oggetto entrante
	//Id e codice_prodotto non vengono mai sovrascritti (e i campi corrispondenti dell'oggetto entrante vengono semplicemente ignorati)
	public void splice(Prodotto p) {
		String nuovoNome = p.getNome();
		String nuovaDescrizione = p.getDescrizione();
		Categoria nuovaCategoria = p.getCategoria();
		Long nuovoCosto = p.getCosto();
		if (nuovoNome!=null)
			this.nome = nuovoNome;
		if (nuovaDescrizione!=null)
			this.descrizione = nuovaDescrizione;
		if (nuovaCategoria!=null)
			this.categoria = nuovaCategoria;
		if (nuovoCosto!=null && nuovoCosto>=0)
			this.costo = nuovoCosto;
	}
	
	//Un oggetto di tipo prodotto ha senso solo se ha un nome, una categoria e un costo (che deve essere non negativo)
	@JsonIgnore
	public boolean isValid() {
		return this.nome!=null && this.categoria!=null && this.costo!=null && this.costo>=0;
	}
	
	public Prodotto clone() {
		Prodotto p = new Prodotto();
		p.setCodice_prodotto(this.codice_prodotto);
		p.setCosto(this.costo);
		p.setDescrizione(this.descrizione);
		p.setId(this.id);
		p.setNome(this.nome);
		p.setCategoria(this.categoria.clone());
		return p;
	}
}
