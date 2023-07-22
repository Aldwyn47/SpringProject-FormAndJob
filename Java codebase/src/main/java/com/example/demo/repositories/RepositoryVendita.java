package com.example.demo.repositories;

import java.sql.Timestamp;
import java.util.*;
import org.springframework.data.jpa.repository.*;

import com.example.demo.entities.*;

public interface RepositoryVendita extends JpaRepository<Vendita,Long>{
	
	public Optional<Vendita> findById(Long id);
	
	@Query("SELECT v FROM Vendita v WHERE v.codice_vendita = :codice_vendita")
	public Optional<Vendita> findByCodice_vendita(String codice_vendita);
	
	@Query("SELECT v FROM Vendita v WHERE v.cliente.id = :id")
	public List<Vendita> findById_cliente(Long id);
	
	@Query("SELECT v FROM Vendita v WHERE v.data >= :lowerBound AND v.data <=:upperBound")
	public List<Vendita> findByData(Timestamp lowerBound, Timestamp upperBound);
	
	@Query("SELECT v FROM Vendita v WHERE v.cliente.id = :id AND pagato = :pagato")
	public List<Vendita> findById_clienteAndPagato(Long id, Boolean pagato);
	
	@Query("SELECT v FROM Vendita v JOIN v.dettagliVendita d WHERE d.prodotto.id = :id")
	public List<Vendita> findById_prodotto(Long id);
	
	@Query("SELECT new com.example.demo.entities.Vendita(v.codice_vendita) FROM Vendita v WHERE v.codice_vendita LIKE :codice_vendita_parziale% ORDER BY v.codice_vendita ASC")
	public List<Vendita> searchCodiciOmonimi(String codice_vendita_parziale);
	
	public void deleteById(Long id);

}
