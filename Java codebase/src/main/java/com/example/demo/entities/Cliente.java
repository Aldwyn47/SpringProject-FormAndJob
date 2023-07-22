package com.example.demo.entities;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;

@Entity
@Table(name="clienti")
public class Cliente {
	
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
	
	@Column(name="codice_cliente")
	@JsonProperty(value="codice_cliente", required=false)
	private String codice_cliente;
	
	public Cliente() {};
	
	public Cliente(Long id) {
		this.id = id;
	}
	
	public Cliente(String codice_cliente) {
		this.codice_cliente = codice_cliente;
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

	public String getCodice_cliente() {
		return codice_cliente;
	}

	public void setCodice_cliente(String codice_dipendente) {
		this.codice_cliente = codice_dipendente;
	}
	
	//Questo metodo viene usato per sovrascrivere i campi dell'oggetto usando le informazioni di un secondo oggetto entrante
	//Id e codice_cliente non vengono mai sovrascritti (e i campi corrispondenti dell'oggetto entrante vengono semplicemente ignorati)
	public void splice(Cliente d) {
		String nuovoNome = d.getNome();
		String nuovoCognome = d.getCognome();
		String nuovaEmail = d.getEmail();
		if (nuovoNome!=null)
			this.nome = nuovoNome;
		if (nuovoCognome!=null)
			this.cognome = nuovoCognome;
		if (nuovaEmail!=null)
			this.email = nuovaEmail;
	}
	
	//Un oggetto di tipo cliente ha senso se ha un nome e un cognome
	@JsonIgnore
	public boolean isValid() {
		return this.nome!=null && this.cognome!=null;
	}
	
	public Cliente clone() {
		Cliente c = new Cliente();
		c.setNome(this.nome);
		c.setCognome(this.cognome);
		c.setEmail(this.email);
		c.setCodice_cliente(this.codice_cliente);
		c.setId(this.id);
		return c;
	}

}