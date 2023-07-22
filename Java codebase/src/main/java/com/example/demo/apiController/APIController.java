package com.example.demo.apiController;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.demo.entities.*;
import com.example.demo.services.*;

@RequestMapping("/api")
@RestController
public class APIController {
	
	private final ServicePrincipale sp;
	
	public APIController(ServicePrincipale sp) {
		this.sp = sp;
	}
	
	//MANSIONE
	
			@GetMapping("/mansione")
			public List<Mansione> getMansione(){
				return this.sp.mansioneListAll();
			}
			
			@GetMapping("/mansione/byId/{id}")
			public Mansione getMansioneById(@PathVariable Long id){
				return this.sp.mansioneListById(id);
			}
			
			@GetMapping("/mansione/search/byNome/{nome}")
			public List<Mansione> getMansioneSearchByNome(@PathVariable String nome){
				return this.sp.mansioneSearchByNome(nome);
			}
			
			@GetMapping("/mansione/search/byDescrizione/{descrizione}")
			public List<Mansione> getMansioneSearchByDescrizione(@PathVariable String descrizione){
				return this.sp.mansioneSearchByDescrizione(descrizione);
			}
			
			@PostMapping("/mansione")
			public ResponseEntity<String> postMansione(@RequestBody Mansione m){
				return this.sp.mansionePost(m);
			}
			
			@PutMapping("/mansione/{id}")
			public ResponseEntity<String> putMansione(@RequestBody Mansione m, @PathVariable Long id){
				return this.sp.mansionePut(m, id);
			}
			
			@PatchMapping("/mansione/{id}")
			public ResponseEntity<String> patchMansione(@RequestBody Mansione m, @PathVariable Long id){
				return this.sp.mansionePatch(m, id);
			}
			
			@DeleteMapping("/mansione/{id}")
			public void deleteMansione(@PathVariable Long id){
				this.sp.mansioneDelete(id);
			}
			
	//DIPENDENTE
			
			@GetMapping("/dipendente")
			public List<Dipendente> getDipendente(){
				return this.sp.dipendenteListAll();
			}
			
			@GetMapping("/dipendente/byId/{id}")
			public Dipendente getDipendenteById(@PathVariable Long id){
				return this.sp.dipendenteListById(id);
			}
			
			@GetMapping("/dipendente/byCodice_dipendente/{codice_dipendente}")
			public Dipendente getDipendenteByCodice_dipendente(@PathVariable String codice_dipendente) {
				return this.sp.dipendenteListByCodice_dipendente(codice_dipendente);
			}
			
			@GetMapping("/dipendente/search/byNome/{nome}")
			public List<Dipendente> getDipendenteSearchByNome(@PathVariable String nome){
				return this.sp.dipendenteSearchByNome(nome);
			}
			
			@GetMapping("/dipendente/search/byCognome/{cognome}")
			public List<Dipendente> getDipendenteSearchByCognome(@PathVariable String cognome){
				return this.sp.dipendenteSearchByCognome(cognome);
			}
			
			@GetMapping("/dipendente/search/byEmail/{email}")
			public List<Dipendente> getDipendenteSearchByEmail(@PathVariable String email){
				return this.sp.dipendenteSearchByEmail(email);
			}
			
			@GetMapping("/dipendente/search/byIdMansione/{id_mansione}")
			public List<Dipendente> getMansioneSearchByIdMansione(@PathVariable Long id_mansione){
				return this.sp.dipendenteSearchByIdMansione(id_mansione);
			}
			
			@PostMapping("/dipendente")
			public ResponseEntity<String> postDipendente(@RequestBody Dipendente d){
				return this.sp.dipendentePost(d);
			}
			
			@PutMapping("/dipendente/{id}")
			public ResponseEntity<String> putDipendente(@RequestBody Dipendente d, @PathVariable Long id){
				return this.sp.dipendentePut(d, id);
			}
			
			@PatchMapping("/dipendente/{id}")
			public ResponseEntity<String> patchDipendente(@RequestBody Dipendente d, @PathVariable Long id){
				return this.sp.dipendentePatch(d, id);
			}
			
			@DeleteMapping("/dipendente/{id}")
			public void deleteDipendente(@PathVariable Long id) {
				this.sp.dipendenteDelete(id);
			}
			
	//CLIENTE
			
			@GetMapping("/cliente")
			public List<Cliente> getCliente(){
				return this.sp.clienteListAll();
			}
			
			@GetMapping("/cliente/byId/{id}")
			public Cliente getClienteById(@PathVariable Long id){
				return this.sp.clienteListById(id);
			}
			
			@GetMapping("/cliente/byCodice_cliente/{codice_cliente}")
			public Cliente getClienteByCodice_cliente(@PathVariable String codice_cliente) {
				return this.sp.clienteListByCodice_cliente(codice_cliente);
			}
			
			@GetMapping("/cliente/search/byNome/{nome}")
			public List<Cliente> getClienteSearchByNome(@PathVariable String nome){
				return this.sp.clienteSearchByNome(nome);
			}
			
			@GetMapping("/cliente/search/byCognome/{cognome}")
			public List<Cliente> getClienteSearchByCognome(@PathVariable String cognome){
				return this.sp.clienteSearchByCognome(cognome);
			}
			
			@GetMapping("/cliente/search/byEmail/{email}")
			public List<Cliente> getClienteSearchByEmail(@PathVariable String email){
				return this.sp.clienteSearchByEmail(email);
			}
			
			@PostMapping("/cliente")
			public ResponseEntity<String> postCliente(@RequestBody Cliente c){
				return this.sp.clientePost(c);
			}
			
			@PutMapping("/cliente/{id}")
			public ResponseEntity<String> putCliente(@RequestBody Cliente c, @PathVariable Long id){
				return this.sp.clientePut(c, id);
			}
			
			@PatchMapping("/cliente/{id}")
			public ResponseEntity<String> patchCliente(@RequestBody Cliente c, @PathVariable Long id){
				return this.sp.clientePatch(c, id);
			}
			
			@DeleteMapping("/cliente/{id}")
			public void deleteCliente(@PathVariable Long id) {
				this.sp.clienteDelete(id);
			}
			
	//CATEGORIA
			
			@GetMapping("/categoria")
			public List<Categoria> getCategoria(){
				return this.sp.categoriaListAll();
			}
			
			@GetMapping("/categoria/byId/{id}")
			public Categoria getCategoriaById(@PathVariable Long id){
				return this.sp.categoriaListById(id);
			}
			
			@GetMapping("/categoria/search/byNome/{nome}")
			public List<Categoria> getCategoriaSearchByNome(@PathVariable String nome){
				return this.sp.categoriaSearchByNome(nome);
			}
			
			@GetMapping("/categoria/search/byDescrizione/{descrizione}")
			public List<Categoria> getCategoriaSearchByDescrizione(@PathVariable String descrizione){
				return this.sp.categoriaSearchByDescrizione(descrizione);
			}
			
			@PostMapping("/categoria")
			public ResponseEntity<String> postCategoria(@RequestBody Categoria c){
				return this.sp.categoriaPost(c);
			}
			
			@PutMapping("/categoria/{id}")
			public ResponseEntity<String> putCategoria(@RequestBody Categoria c, @PathVariable Long id){
				return this.sp.categoriaPut(c, id);
			}
			
			@PatchMapping("/categoria/{id}")
			public ResponseEntity<String> patchCategoria(@RequestBody Categoria c, @PathVariable Long id){
				return this.sp.categoriaPatch(c, id);
			}
			
			@DeleteMapping("/categoria/{id}")
			public void deleteCategoria(@PathVariable Long id){
				this.sp.categoriaDelete(id);
			}
			
	//PRODOTTO
			
			@GetMapping("/prodotto")
			public List<Prodotto> getProdotto(){
				return this.sp.prodottoListAll();
			}
			
			@GetMapping("/prodotto/byId/{id}")
			public Prodotto getProdottoById(@PathVariable Long id){
				return this.sp.prodottoListById(id);
			}
			
			@GetMapping("/prodotto/byCodice_prodotto/{codice_prodotto}")
			public Prodotto getProdottoByCodice_prodotto(@PathVariable String codice_prodotto){
				return this.sp.prodottoListByCodice_prodotto(codice_prodotto);
			}
			
			@GetMapping("/prodotto/byAcquirente/{id}")
			public List<Map<String,Object>> getProdottoByAcquirente(@PathVariable Long id){
				return this.sp.prodottoListByAcquirente(id);
			}
			
			@GetMapping("/prodotto/search/byNome/{nome}")
			public List<Prodotto> getProdottoSearchByNome(@PathVariable String nome){
				return this.sp.prodottoSearchByNome(nome);
			}
			
			@GetMapping("/prodotto/search/byDescrizione/{descrizione}")
			public List<Prodotto> getProdottoSearchByDescrizione(@PathVariable String descrizione){
				return this.sp.prodottoSearchByDescrizione(descrizione);
			}
			
			@GetMapping("/prodotto/search/byCosto/{minCost}/{maxCost}")
			public List<Prodotto> getProdottoSearchByCosto(@PathVariable Long minCost, @PathVariable Long maxCost){
				return this.sp.prodottoSearchByCosto(minCost, maxCost);
			}
			
			@PostMapping("/prodotto")
			public ResponseEntity<String> postProdotto(@RequestBody Prodotto p){
				return this.sp.prodottoPost(p);
			}
			
			@PutMapping("/prodotto/{id}")
			public ResponseEntity<String> putProdotto(@RequestBody Prodotto p, @PathVariable Long id){
				return this.sp.prodottoPut(p, id);
			}
			
			@PatchMapping("/prodotto/{id}")
			public ResponseEntity<String> patchProdotto(@RequestBody Prodotto p, @PathVariable Long id){
				return this.sp.prodottoPatch(p, id);
			}
			
			@DeleteMapping("/prodotto/{id}")
			public void deleteProdotto(@PathVariable Long id){
				this.sp.prodottoDeleteById(id);
			}
			
	//VENDITA
			
			@GetMapping("/vendita")
			public List<Vendita> getVendita(){
				return this.sp.venditaListAll();
			}
			
			@GetMapping("/vendita/byId/{id}")
			public Vendita getVenditaById(@PathVariable Long id){
				return this.sp.venditaListById(id);
			}
			
			@GetMapping("/vendita/byCodiceVendita/{codice_vendita}")
			public Vendita getVenditaById(@PathVariable String codice_vendita){
				return this.sp.venditaListByCodice_vendita(codice_vendita);
			}
			
			@GetMapping("/vendita/byIdCliente/{id}")
			public List<Vendita> getVenditaById_cliente(@PathVariable Long id){
				return this.sp.venditaListById_cliente(id);
			}
			
			@GetMapping("/vendita/byIdClienteAndPagato/{id}/{pagato}")
			public List<Vendita> getVenditaById_clienteAndPagato(@PathVariable Long id, @PathVariable Boolean pagato){
				return this.sp.venditaListById_clienteAndPagato(id, pagato);
			}
			
			@GetMapping("/vendita/byIdProdotto/{id}")
			public List<Vendita> getVenditaByCodice_prodotto(@PathVariable Long id){
				return this.sp.venditaListById_prodotto(id);
			}
			
			@GetMapping("/vendita/byData/{t1}/{t2}")
			public ResponseEntity<Object> getVenditaByData(@PathVariable String t1, @PathVariable String t2){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				try {
					Timestamp lowerBound = new Timestamp(dateFormat.parse(t1).getTime());
					Timestamp upperBound = new Timestamp(dateFormat.parse(t2).getTime());
					return this.sp.venditaListByData(lowerBound, upperBound);
				}
				catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
				}
			}
			
			@PostMapping("/vendita")
			public ResponseEntity<String> postVendita(@RequestBody Vendita v){
				return this.sp.venditaPost(v);
			}
			
			@PatchMapping("/vendita/{id}")
			public ResponseEntity<String> patchVendita(@RequestBody Vendita v, @PathVariable Long id){
				return this.sp.venditaPatch(v, id);
			}
			
			@PutMapping("/vendita/{id}")
			public ResponseEntity<String> putVendita(@RequestBody Vendita v, @PathVariable Long id){
				return this.sp.venditaPut(v, id);
			}
			
			@DeleteMapping("/vendita/{id}")
			public void deleteVendita(@PathVariable Long id){
				this.sp.venditaDeleteById(id);
			}

}
