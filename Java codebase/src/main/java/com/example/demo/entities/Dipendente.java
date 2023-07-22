package com.example.demo.entities;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;

@Entity
@Table(name="dipendenti")
public class Dipendente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	@JsonProperty(value="id", required=false)
	private Long id;
	
	@Column(name="nome")
	@JsonProperty(value="nome", required=false)
	private String nome;
	
	@Column(name="cognome")
	@JsonProperty(value="cognome", required=false)
	private String cognome;
	
	@Column(name="email")
	@JsonProperty(value="email", required=false)
	private String email;
	
	@Column(name="codice_dipendente")
	@JsonProperty(value="codice_dipendente", required=false)
	private String codice_dipendente;
	
	@ManyToOne
	@JoinColumn(name="id_mansione")
	@JsonProperty(value="mansione", required=false)
	private Mansione mansione;
	
	public Dipendente() {};
	
	public Dipendente(String codice_dipendente) {
		this.codice_dipendente = codice_dipendente;
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//La mansione viene restituita all'esterno creando una defensive copy
	public Mansione getMansione() {
		if (this.mansione!=null) {
			Mansione m = new Mansione();
			m.setId(this.mansione.getId());
			m.setNome(this.mansione.getNome());
			m.setDescrizione(this.mansione.getDescrizione());
			return m;
		}
		else
			return null;
	}

	public void setMansione(Mansione mansione) {
		this.mansione = mansione;
	}

	public String getCodice_dipendente() {
		return codice_dipendente;
	}

	public void setCodice_dipendente(String codice_dipendente) {
		this.codice_dipendente = codice_dipendente;
	}
	
	//Questo metodo viene usato per sovrascrivere i campi dell'oggetto usando le informazioni di un secondo oggetto entrante
	//Id e codice_dipendente non vengono mai sovrascritti (e i campi corrispondenti dell'oggetto entrante vengono semplicemente ignorati)
	public void splice(Dipendente d) {
		String nuovoNome = d.getNome();
		String nuovoCognome = d.getCognome();
		String nuovaEmail = d.getEmail();
		Mansione nuovaMansione = d.getMansione();
		if (nuovoNome!=null)
			this.nome = nuovoNome;
		if (nuovoCognome!=null)
			this.cognome = nuovoCognome;
		if (nuovaEmail!=null)
			this.email = nuovaEmail;
		if (nuovaMansione!=null)
			this.mansione = nuovaMansione;
	}
	
	//Un oggetto di tipo dipendente ha senso solo se ha un nome, un cognome e una mansione
	@JsonIgnore
	public boolean isValid() {
		return this.nome!=null && this.cognome!=null && this.mansione!=null;
	}
	
	public Dipendente clone() {
		Dipendente d = new Dipendente();
		d.setCodice_dipendente(this.codice_dipendente);
		d.setCognome(this.cognome);
		d.setEmail(this.email);
		d.setId(this.id);
		d.setNome(this.nome);
		d.setMansione(this.mansione.clone());
		return d;
	}

}
