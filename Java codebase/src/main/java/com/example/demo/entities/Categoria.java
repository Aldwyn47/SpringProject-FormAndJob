package com.example.demo.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

@Entity
@Table(name="categorie")
public class Categoria {
	
	@Column(name="nome")
	@JsonProperty(value="nome", required=false)
	private String nome;
	
	@Column(name="descrizione")
	@JsonProperty(value="descrizione", required=false)
	private String descrizione;
	
	@Column(name="id")
	@JsonProperty(value="id", required=false)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	public Categoria() {}
	
	public Categoria(Long id) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	//Questo metodo viene usato per sovrascrivere i campi dell'oggetto usando le informazioni di un secondo oggetto entrante
	//L'Id non viene mai sovrascritto (e il campo corrispondente dell'oggetto entrante viene semplicemente ignorato)
	public void splice(Categoria nuoviDati) {
		String nuovoNome = nuoviDati.getNome();
		String nuovaDescrizione = nuoviDati.getDescrizione();
		if (nuovoNome!=null)
			this.nome = nuovoNome;
		if (nuovaDescrizione!=null)
			this.descrizione = nuovaDescrizione;
	}
	
	//Un oggetto di tipo categoria ha senso solo se ha un nome
	@JsonIgnore
	public boolean isValid() {
		return this.nome!=null;
	}
	
	public Categoria clone() {
		Categoria c = new Categoria();
		c.setDescrizione(this.descrizione);
		c.setId(this.id);
		c.setNome(this.nome);
		return c;
	}

}