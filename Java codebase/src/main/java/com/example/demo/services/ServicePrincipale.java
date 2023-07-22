package com.example.demo.services;

import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.time.*;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import jakarta.transaction.Transactional;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;


@Service
public class ServicePrincipale {
	
	private final RepositoryMansione rMansione;
	private final RepositoryDipendente rDipendente;
	private final RepositoryCliente rCliente;
	private final RepositoryCategoria rCategoria;
	private final RepositoryProdotto rProdotto;
	private final RepositoryVendita rVendita;
	private final RepositoryVenditaDettagli rVenditaDettagli;
	private final ObjectMapper objectMapper;
	
	public ServicePrincipale(RepositoryMansione rMansione, RepositoryDipendente rDipendente, RepositoryCliente rCliente, RepositoryCategoria rCategoria, RepositoryProdotto rProdotto, RepositoryVendita rVendita, RepositoryVenditaDettagli rVenditaDettagli, ObjectMapper objectMapper) {
		this.rMansione = rMansione;
		this.rDipendente = rDipendente;
		this.rCliente = rCliente;
		this.rCategoria = rCategoria;
		this.rProdotto = rProdotto;
		this.rVendita = rVendita;
		this.rVenditaDettagli = rVenditaDettagli;
		this.objectMapper = objectMapper;
	}
	
	public ResponseEntity<String> utilityRestituisciOkEAllegaJson(Object inserito){
		String responseBody = "";
		try {
			responseBody = objectMapper.writeValueAsString(inserito);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(responseBody,HttpStatus.OK);
	}
	
	//MANSIONE
	
			public List<Mansione> mansioneListAll(){
				return this.rMansione.findAll();
			}
			
			public Mansione mansioneListById(Long id){
				Optional<Mansione> op = this.rMansione.findById(id);
				if (op.isPresent())
					return op.get();
				else
					return null;
			}
			
			public List<Mansione> mansioneSearchByNome(String nome){
				return this.rMansione.searchByNome(nome);
			}
			
			public List<Mansione> mansioneSearchByDescrizione(String descrizione){
				return this.rMansione.searchByDescrizione(descrizione);
			}
			
			@Transactional
			public ResponseEntity<String> mansionePost(Mansione m) {
				if (!m.isValid()) //Se l'oggetto in arrivo è un'istanza non valida, errore
					return new ResponseEntity<>("Istanza non valida",HttpStatus.BAD_REQUEST);
				m.setId(null); //Un eventuale id passato dall'esterno deve venir annullato, onde evitare di sovrascrivere un record già esistente con il saveAndFlush che segue
				Mansione inserito = this.rMansione.save(m);
				return utilityRestituisciOkEAllegaJson(inserito);
			}
			
			@Transactional
			public ResponseEntity<String> mansionePut(Mansione m, Long id) {
				if (this.mansioneListById(id)==null) //Se non esiste un record associato all'id in arrivo, errore
					return new ResponseEntity<>("Non esiste un record associato all'id inviato", HttpStatus.BAD_REQUEST);
				if	(!m.isValid()) //Se l'istanza inviata non è valida, errore
					return new ResponseEntity<>("L'istanza inviata non è valida",HttpStatus.BAD_REQUEST);
				m.setId(id);
				Mansione inserito = this.rMansione.save(m);
				return utilityRestituisciOkEAllegaJson(inserito);	
			}
			
			@Transactional
			public ResponseEntity<String> mansionePatch(Mansione m, Long id) {
				Mansione vecchiDati = this.mansioneListById(id); 
				if (vecchiDati==null) //Se non esiste un record associato all'id inviato, errore
					return new ResponseEntity<>("Non esiste un record associato all'id inviato",HttpStatus.BAD_REQUEST);
				vecchiDati.splice(m); //I campi dell'oggetto inviato vengono copiati nell'oggetto già esistente
				Mansione inserito = this.rMansione.save(vecchiDati); //L'oggetto già esistente viene reinserito nel database
				return utilityRestituisciOkEAllegaJson(inserito);	
			}
			
			@Transactional
			public void mansioneDelete(Long id){
				this.rMansione.deleteById(id);
			}
			
	//DIPENDENTE
			
			public List<Dipendente> dipendenteListAll(){
				return this.rDipendente.findAll();
			}
			
			public Dipendente dipendenteListById(Long id){
				Optional<Dipendente> op = this.rDipendente.findById(id);
				if (op.isPresent())
					return op.get();
				else
					return null;
			}
			
			public Dipendente dipendenteListByCodice_dipendente(String codice_dipendente){
				Optional<Dipendente> op = this.rDipendente.findByCodice_dipendente(codice_dipendente);
				if (op.isPresent())
					return op.get();
				else
					return null;
			}
			
			public List<Dipendente> dipendenteSearchByNome(String nome){
				return this.rDipendente.searchByNome(nome);
			}
			
			public List<Dipendente> dipendenteSearchByCognome(String cognome){
				return this.rDipendente.searchByCognome(cognome);
			}
			
			public List<Dipendente> dipendenteSearchByEmail(String email){
				return this.rDipendente.searchByEmail(email);
			}
			
			public List<Dipendente> dipendenteSearchByIdMansione(Long id_mansione){
				return this.rDipendente.searchByIdMansione(id_mansione);
			}
			
			@Transactional
			public ResponseEntity<String> dipendentePost(Dipendente d) {
				if (!d.isValid()) //Se l'istanza in arrivo non è valida, errore
					return new ResponseEntity<>("l'istanza di dipendente inviata non è valida",HttpStatus.BAD_REQUEST);
				d.setId(null); //L'eventuale id dell'oggetto entrante deve essere annullato per evitare di sovrascrivere una riga già esistente con saveAndFlush
				Integer hash = d.hashCode(); //Dall'oggetto entrante viene ricavato un hash code per inizializzare il codice identificativo
				String hash12 = "D00"+hash.toString(); //L'hash viene convertito a stringa e completato con un prefisso che identifica i dipendenti
				if (hash12.length()>=12) //La lunghezza dell'hash viene uniformata a 12 caratteri (eliminando i successivi se presenti o aggiungendo delle 'X' se è troppo corto)
					hash12 = hash12.substring(0,12);
				else {
					while (hash12.length()<12)
						hash12 = hash12 + "X";
				}
				List<Dipendente> omonimi = this.rDipendente.searchCodiciOmonimi(hash12); //Viene controllata la presenza di eventuali record con lo stesso codice identificativo
				Integer last = 1; //L'ultima cifra dell'hash è un contatore incrementale che viene usato per distinguere eventuali omonimi
				for (Dipendente om : omonimi) {
					String cd = om.getCodice_dipendente();
					int next = Integer.parseInt(cd.substring(12,cd.length()));
					if (next-last>0)
						break;
					last = last + 1;
				}
				d.setCodice_dipendente(hash12 + last); 
				Dipendente inserito = this.rDipendente.save(d); 
				return utilityRestituisciOkEAllegaJson(inserito);
			}
			
			@Transactional
			public ResponseEntity<String> dipendentePut(Dipendente d, Long id) {
				Dipendente vecchiDati = this.dipendenteListById(id);
				if (vecchiDati==null) //Se non esiste un record corrispondente all'id specificato, errore 
					return new ResponseEntity<>("Non esiste un record corrispondente all'id specificato",HttpStatus.BAD_REQUEST);
				if(!d.isValid()) //Se l'istanza inviata non è valida, errore
					return new ResponseEntity<>("l'istanza inviata non è valida",HttpStatus.BAD_REQUEST);
				d.setId(id);
				d.setCodice_dipendente(vecchiDati.getCodice_dipendente());
				Dipendente inserito = this.rDipendente.save(d);
				return utilityRestituisciOkEAllegaJson(inserito);
			}

			
			@Transactional
			public ResponseEntity<String> dipendentePatch(Dipendente d, Long id) {
				Dipendente vecchiDati = this.dipendenteListById(id);
				if (vecchiDati==null) //Se non esiste un record corrispondente all'id specificato, errore
					return new ResponseEntity<>("Non esiste un record corrispondente all'id specificato",HttpStatus.BAD_REQUEST);
				vecchiDati.splice(d); //I campi dell'oggetto inviato vengono copiati nel record esistente
				Dipendente inserito = this.rDipendente.save(vecchiDati); //Il record viene reinserito nel database
				return utilityRestituisciOkEAllegaJson(inserito);
			}
			
			@Transactional
			public void dipendenteDelete(Long id) {
				this.rDipendente.deleteById(id);
			}
			
	//CLIENTE
			
			public List<Cliente> clienteListAll(){
				return this.rCliente.findAll();
			}
			
			public Cliente clienteListById(Long id){
				Optional<Cliente> op = this.rCliente.findById(id);
				if (op.isPresent())
					return op.get();
				else
					return null;
			}
			
			public Cliente clienteListByCodice_cliente(String codice_cliente){
				Optional<Cliente> op = this.rCliente.findByCodice_cliente(codice_cliente);
				if (op.isPresent())
					return op.get();
				else
					return null;
			}
			
			public List<Cliente> clienteSearchByNome(String nome){
				return this.rCliente.searchByNome(nome);
			}
			
			public List<Cliente> clienteSearchByCognome(String cognome){
				return this.rCliente.searchByCognome(cognome);
			}
			
			public List<Cliente> clienteSearchByEmail(String email){
				return this.rCliente.searchByEmail(email);
			}
			
			@Transactional
			public ResponseEntity<String> clientePost(Cliente c) {
				if (!c.isValid()) //Se l'istanza in arrivo non è valida, errore
					return new ResponseEntity<>("Istanza di cliente non valida",HttpStatus.BAD_REQUEST);
				c.setId(null); //L'eventuale id dell'oggetto entrante deve essere annullato per evitare di sovrascrivere una riga già esistente con saveAndFlush
				Integer hash = c.hashCode(); //Dall'oggetto entrante viene ricavato un hash code per inizializzare il codice identificativo
				String hash12 = "C00" + hash.toString(); //L'hash viene convertito a stringa e completato con un prefisso che identifica i clienti
				if (hash12.length()>=12) //La lunghezza dell'hash viene uniformata a 12 caratteri (eliminando i successivi se presenti o aggiungendo delle 'X' se è troppo corto)
					hash12 = hash12.substring(0,12);
				else {
					while (hash12.length()<12)
						hash12 = hash12 + "X";
				}
				List<Cliente> omonimi = this.rCliente.searchCodiciOmonimi(hash12); //Viene controllata la presenza di eventuali record con lo stesso codice identificativo
				Integer last = 1;
				for (Cliente om : omonimi) {
					String cd = om.getCodice_cliente();
					int next = Integer.parseInt(cd.substring(12,cd.length()));
					if (next-last>0)
						break;
					last = last + 1;
				}
				c.setCodice_cliente(hash12 + last); //L'ultima cifra dell'hash è un contatore incrementale che viene usato per distinguere eventuali omonimi
				Cliente inserito = this.rCliente.save(c);
				return utilityRestituisciOkEAllegaJson(inserito);
			}
			
			@Transactional
			public ResponseEntity<String> clientePut(Cliente c, Long id) {
				Cliente vecchiDati = this.clienteListById(id);
				if (vecchiDati==null) //Se l'id fornito non corrisponde a un record esistente, errore
					return new ResponseEntity<>("L'id fornito non corrisponde a un record esistente",HttpStatus.BAD_REQUEST);
				if (!c.isValid()) //Se l'istanza di cliente fornita non è valida, errore
					return new ResponseEntity<>("L'istanza di cliente fornita non è valida",HttpStatus.BAD_REQUEST);
					//Se l'id fornito corrisponde a un record esistente e l'istanza in arrivo è valida, si lavora
				c.setId(id);
				c.setCodice_cliente(vecchiDati.getCodice_cliente());
				Cliente inserito = this.rCliente.save(c);
				return utilityRestituisciOkEAllegaJson(inserito);
			}
			
			@Transactional
			public ResponseEntity<String> clientePatch(Cliente c, Long id) {
				Cliente vecchiDati = this.clienteListById(id);
				if (vecchiDati==null) //Se l'id fornito non corrisponde a un record esistente, errore
					return new ResponseEntity<>("Istanza di cliente non valida",HttpStatus.BAD_REQUEST);
				vecchiDati.splice(c); //I campi dell'oggetto in arrivo vengono copiati nel record esistente
				Cliente inserito = this.rCliente.save(vecchiDati); //Il record esistente viene reinserito nel database
				return utilityRestituisciOkEAllegaJson(inserito);
			}
			
			@Transactional
			public void clienteDelete(Long id) {
				this.rCliente.deleteById(id);
			}
			
	//CATEGORIA
			
			public List<Categoria> categoriaListAll(){
				return this.rCategoria.findAll();
			}
			
			public Categoria categoriaListById(Long id){
				Optional<Categoria> op = this.rCategoria.findById(id);
				if (op.isPresent())
					return op.get();
				else
					return null;
			}
			
			public List<Categoria> categoriaSearchByNome(String nome){
				return this.rCategoria.searchByNome(nome);
			}
			
			public List<Categoria> categoriaSearchByDescrizione(String descrizione){
				return this.rCategoria.searchByDescrizione(descrizione);
			}
			
			@Transactional
			public ResponseEntity<String> categoriaPost(Categoria c) {
				if (!c.isValid()) //Se l'istanza in arrivo non è valida, errore
					return new ResponseEntity<>("Istanza di categoria non valida",HttpStatus.BAD_REQUEST); 
				c.setId(null); //Un eventuale id passato dall'esterno deve essere reso null per evitare di sovrascrivere un eventuale record già esistente
				Categoria inserito = this.rCategoria.save(c);
				return utilityRestituisciOkEAllegaJson(inserito);
			}

			
			@Transactional
			public ResponseEntity<String> categoriaPut(Categoria c, Long id) {
				if (this.categoriaListById(id)==null) //Se l'id fornito non corrisponde a un record esistente, errore
					return new ResponseEntity<>("L'id fornito non corrisponde a un record esistente",HttpStatus.BAD_REQUEST);
				if (!c.isValid()) // Se l'istanza di Categoria in arrivo non è valida, errore
					return new ResponseEntity<>("Istanza di categoria non valida",HttpStatus.BAD_REQUEST);
				c.setId(id);
				Categoria inserito = this.rCategoria.save(c);
				return utilityRestituisciOkEAllegaJson(inserito);
			}
			
			@Transactional
			public ResponseEntity<String> categoriaPatch(Categoria c, Long id) {
				Categoria vecchiDati = this.categoriaListById(id);
				if (vecchiDati==null) //Se non esiste un record corrispondente all'id passato, errore
					return new ResponseEntity<>("L'id fornito non corrisponde a un record esistente",HttpStatus.BAD_REQUEST);
				vecchiDati.splice(c); //I campi dell'oggetto in arrivo vengono copiati nel record esistente
				Categoria inserito = this.rCategoria.save(vecchiDati);
				return utilityRestituisciOkEAllegaJson(inserito);
			}
			
			@Transactional
			public void categoriaDelete(Long id){
				this.rCategoria.deleteById(id);
			}

	//PRODOTTO
			
			public List<Prodotto> prodottoListAll(){
				return this.rProdotto.findAll();
			}
			
			public Prodotto prodottoListById(Long id){
				Optional<Prodotto> op = this.rProdotto.findById(id);
				if (op.isPresent())
					return op.get();
				else
					return null;
			}
			
			public Prodotto prodottoListByCodice_prodotto(String codice_prodotto) {
				Optional<Prodotto> op = this.rProdotto.findByCodice_prodotto(codice_prodotto);
				if (op.isPresent())
					return op.get();
				else
					return null;
			}
			
			public List<Map<String,Object>> prodottoListByAcquirente(Long id){
				return this.rVenditaDettagli.findProdottiById_cliente(id);
			}
			
			public List<Prodotto> prodottoSearchByNome(String nome){
				return this.rProdotto.searchByNome(nome);
			}
			
			public List<Prodotto> prodottoSearchByDescrizione(String descrizione){
				return this.rProdotto.searchByDescrizione(descrizione);
			}
			
			public List<Prodotto> prodottoSearchById_categoria(Long id_categoria){
				return this.rProdotto.searchById_categoria(id_categoria);
			}
			
			public List<Prodotto> prodottoSearchByCosto(Long minCost, Long maxCost){
				return this.rProdotto.searchByCosto(minCost, maxCost);
			}
			
			@Transactional
			public ResponseEntity<String> prodottoPost(Prodotto p) {
				if (!p.isValid()) 
					return new ResponseEntity<>("Istanza di prodotto non valida",HttpStatus.BAD_REQUEST);
				p.setId(null); //L'eventuale id dell'oggetto entrante deve essere annullato per evitare di sovrascrivere una riga già esistente con saveAndFlush
				Integer hash = p.hashCode(); //Dall'oggetto entrante viene ricavato un hash code per inizializzare il codice identificativo
				String hash12 = "P00" + hash.toString(); //L'hash viene convertito a stringa e completato con un prefisso che identifica i prodotti
				if (hash12.length()>=12) //La lunghezza dell'hash viene uniformata a 12 caratteri (eliminando i successivi se presenti o aggiungendo delle 'X' se è troppo corto)
					hash12 = hash12.substring(0,12);
				else {
					while (hash12.length()<12)
						hash12 = hash12 + "X";
				}
				List<Prodotto> omonimi = this.rProdotto.searchCodiciOmonimi(hash12); //Viene controllata la presenza di eventuali record con lo stesso codice identificativo
				Integer last = 1;
				for (Prodotto om : omonimi) {
					String cd = om.getCodice_prodotto();
					int next = Integer.parseInt(cd.substring(12,cd.length()));
					if (next-last>0)
						break;
					last = last + 1;
				}
				p.setCodice_prodotto(hash12 + last); //L'ultima cifra dell'hash è un contatore incrementale che viene usato per distinguere eventuali omonimi
				Prodotto inserito = this.rProdotto.save(p);
				return utilityRestituisciOkEAllegaJson(inserito);
			}
			
			@Transactional
			public ResponseEntity<String> prodottoPut(Prodotto p, Long id) {
				Prodotto vecchiDati = this.prodottoListById(id);
				if (vecchiDati==null) //Se l'id fornito non corrisponde a un record esistente, errore
					return new ResponseEntity<>("L'id fornito non corrisponde a un record esistente",HttpStatus.BAD_REQUEST);
				if(!p.isValid()) //Se l'istanza fornita non è valida, errore
					return new ResponseEntity<>("Istanza di prodotto non valida",HttpStatus.BAD_REQUEST);
				p.setId(id);
				p.setCodice_prodotto(vecchiDati.getCodice_prodotto());
				Prodotto inserito = this.rProdotto.save(p);
				return utilityRestituisciOkEAllegaJson(inserito);
			}
			
			@Transactional
			public ResponseEntity<String> prodottoPatch(Prodotto p, Long id) {
				Prodotto vecchiDati = this.prodottoListById(id);
				if (vecchiDati==null) //Se l'id fornito non corrisponde a un record esistente, errore
					return new ResponseEntity<>("L'id fornito non corrisponde a un record esistente",HttpStatus.BAD_REQUEST);
				vecchiDati.splice(p); //I campi dell'oggetto in arrivo vengono copiati nel record esistente
				Prodotto inserito = this.rProdotto.save(vecchiDati); //Il record viene rimesso nel database
				return utilityRestituisciOkEAllegaJson(inserito);
			}
			
			@Transactional
			public void prodottoDeleteById(Long id) {
				this.rProdotto.deleteById(id);
			}
			
	//VENDITA
			
			public List<Vendita> venditaListAll(){
				return this.rVendita.findAll();
			}
			
			public Vendita venditaListById(Long id){
				Optional<Vendita> op = this.rVendita.findById(id);
				if (op.isPresent())
					return op.get();
				else
					return null;
			}
			
			public Vendita venditaListByCodice_vendita(String codice_vendita){
				Optional<Vendita> op = this.rVendita.findByCodice_vendita(codice_vendita);
				if (op.isPresent())
					return op.get();
				else
					return null;
			}
			
			public List<Vendita> venditaListById_cliente(Long id){
				return this.rVendita.findById_cliente(id);
			}
			
			public ResponseEntity<Object> venditaListByData(Timestamp lowerBound, Timestamp upperBound){
				List<Vendita> res = this.rVendita.findByData(lowerBound, upperBound);
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
			
			public List<Vendita> venditaListById_clienteAndPagato(Long id, Boolean pagato){
				return this.rVendita.findById_clienteAndPagato(id, pagato);
			}
			
			public List<Vendita> venditaListById_prodotto(Long id){
				return this.rVendita.findById_prodotto(id);
			}
			
			@Transactional
			public ResponseEntity<String> venditaPost(Vendita v) {
				if (!v.isValid()) //Se l'istanza di vendita non è valida, errore di bad request
					return new ResponseEntity<>("Istanza di vendita non valida",HttpStatus.BAD_REQUEST);
				for (VenditaDettagli vd : v.getDettagliVendita())
					if (!vd.isValid()) //Se anche solo uno dei dettagliVendita allegati non è valido, errore di bad request
						return new ResponseEntity<>("Istanza di venditaDettagli non valida",HttpStatus.BAD_REQUEST);
				v.setId(null); //L'eventuale id dell'oggetto entrante deve essere annullato per evitare di sovrascrivere una riga già esistente con saveAndFlush
				Integer hash = v.hashCode(); //Dall'oggetto entrante viene ricavato un hash code per inizializzare il codice identificativo
				String hash12 = "V00" + hash.toString(); //L'hash viene convertito a stringa e completato con un prefisso che identifica le vendite
				if (hash12.length()>=12) //La lunghezza dell'hash viene uniformata a 12 caratteri (eliminando i successivi se presenti o aggiungendo delle 'X' se è troppo corto)
					hash12 = hash12.substring(0,12);
				else {
					while (hash12.length()<12)
						hash12 = hash12 + "X";
				}
				List<Vendita> omonimi = this.rVendita.searchCodiciOmonimi(hash12); //Viene controllata la presenza di eventuali record con lo stesso codice identificativo
				Integer last = 1;
				for (Vendita om : omonimi) {
					String cd = om.getCodice_vendita();
					int next = Integer.parseInt(cd.substring(12,cd.length()));
					if (next-last>0)
						break;
					last = last + 1;
				}
				v.setCodice_vendita(hash12 + last); //L'ultima cifra dell'hash è un contatore incrementale che viene usato per distinguere eventuali omonimi
				v.setData(Timestamp.from(Instant.now().atZone(ZoneId.of("Europe/Rome")).toInstant()));
				Vendita inserito = this.rVendita.save(v);
				List<VenditaDettagli> inseriti = new ArrayList<>();
				for (VenditaDettagli vd : v.getDettagliVendita()) {
					vd.setVendita(inserito);
					inseriti.add(this.rVenditaDettagli.save(vd));
				}
				inserito.setDettagliVendita(inseriti);
				return utilityRestituisciOkEAllegaJson(inserito);
			}

			@Transactional
			public ResponseEntity<String> venditaPut(Vendita v, Long id) {
				if (!v.isValid()) //Se l'istanza di vendita non è valida, errore di bad request
					return new ResponseEntity<>("Istanza di vendita non valida",HttpStatus.BAD_REQUEST);
				for (VenditaDettagli vd : v.getDettagliVendita())
					if (!vd.isValid()) //Se anche solo uno dei dettagliVendita allegati non è valido, errore di bad request
						return new ResponseEntity<>("Istanza di venditaDettagli non valida",HttpStatus.BAD_REQUEST);
				Vendita vecchiDati = this.venditaListById(id);
				if (vecchiDati==null) //Se l'id fornito non corrisponde a un record esistente, errore
					return new ResponseEntity<>("L'id fornito non corrisponde a un record esistente",HttpStatus.BAD_REQUEST);
				v.setId(id);
				v.setCodice_vendita(vecchiDati.getCodice_vendita());
				Vendita inserito = this.rVendita.save(v);
				this.rVenditaDettagli.deleteById_vendita(id); //I vecchi record vengono cancellati affinché li si possa sostituire con quelli nuovi
				List<VenditaDettagli> inseriti = new ArrayList<>();
				for (VenditaDettagli vd : v.getDettagliVendita()) {
					vd.setVendita(inserito);
					inseriti.add(this.rVenditaDettagli.save(vd));
				}
				inserito.setDettagliVendita(inseriti);
				return utilityRestituisciOkEAllegaJson(inserito);
			}
				
			
			@Transactional
			public ResponseEntity<String> venditaPatch(Vendita v, Long id) {
				Vendita vecchiDati = this.venditaListById(id);
				if (vecchiDati==null) //Se l'id fornito non corrisponde a un record esistente, errore
					return new ResponseEntity<>("L'id fornito non corrisponde a un record esistente",HttpStatus.BAD_REQUEST);
				if (v.getDettagliVendita()!=null)
					for (VenditaDettagli vd : v.getDettagliVendita())
						if (!vd.isValid()) //Se anche solo uno degli eventuali dettagliVendita allegati non è valido, errore di bad request
							return new ResponseEntity<>("Istanza di venditaDettagli non valida",HttpStatus.BAD_REQUEST);
				vecchiDati.splice(v); //I singoli campi dell'oggetto entrante vengono copiati selettivamente nel record esistente
				vecchiDati.setDettagliVendita(null);
				Vendita inserito = this.rVendita.save(vecchiDati);
				if (v.getDettagliVendita()!=null) { //Se l'oggetto entrante include anche nuovi dettagli vendita, questi vengono sostituiti ai vecchi
					this.rVenditaDettagli.deleteById_vendita(id); //I vecchi record vengono cancellati affinché li si possa sostituire con quelli nuovi
					List<VenditaDettagli> inseriti = new ArrayList<>();
					for (VenditaDettagli vd : v.getDettagliVendita()) {
						vd.setVendita(inserito);
						inseriti.add(this.rVenditaDettagli.save(vd));
					}
					inserito.setDettagliVendita(inseriti);
				}
				return utilityRestituisciOkEAllegaJson(inserito);
			}
				
			
			@Transactional
			public void venditaDeleteById(Long id) {
				this.rVendita.deleteById(id);
			}
			
}
