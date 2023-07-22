package com.example.demo.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.*;

import com.example.demo.entities.*;



public interface RepositoryProdotto extends JpaRepository<Prodotto,Long>{
	
	public Optional<Prodotto> findById(Long id);
	
	@Query("SELECT p FROM Prodotto p WHERE p.codice_prodotto=:codice_prodotto")
	public Optional<Prodotto> findByCodice_prodotto(String codice_prodotto);
	
	@Query("SELECT p FROM Prodotto p WHERE p.nome LIKE :nome%")
	public List<Prodotto> searchByNome(String nome);
	
	@Query("SELECT p FROM Prodotto p WHERE p.descrizione LIKE %:descrizione%")
	public List<Prodotto> searchByDescrizione(String descrizione);
	
	@Query("SELECT p FROM Prodotto p WHERE p.categoria.id=:id_categoria")
	public List<Prodotto> searchById_categoria(Long id_categoria);
	
	@Query("SELECT p FROM Prodotto p WHERE p.costo>=:minCost AND p.costo<=:maxCost")
	public List<Prodotto> searchByCosto(Long minCost, Long maxCost);
	
	@Query("SELECT new com.example.demo.entities.Prodotto(p.codice_prodotto) FROM Prodotto p WHERE p.codice_prodotto LIKE :codice_prodotto_parziale% ORDER BY p.codice_prodotto ASC")
	public List<Prodotto> searchCodiciOmonimi(String codice_prodotto_parziale);

	public void deleteById(Long id);

}
