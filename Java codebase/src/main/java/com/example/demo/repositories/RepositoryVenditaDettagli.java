package com.example.demo.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.*;

import com.example.demo.entities.*;

public interface RepositoryVenditaDettagli extends JpaRepository<VenditaDettagli, Long>{
	
	@Query("SELECT vd FROM VenditaDettagli vd WHERE vd.vendita.id = :id_vendita")
	public List<VenditaDettagli> findById_vendita(Long id_vendita);
	
	@Query("SELECT DISTINCT dv.prodotto.id as id, dv.prodotto.nome as nome, dv.prodotto.descrizione as descrizione, dv.prodotto.categoria as categoria, dv.prodotto.codice_prodotto as codice_prodotto, dv.prodotto.costo as costo FROM VenditaDettagli dv WHERE dv.vendita.cliente.id = :id")
	public List<Map<String,Object>> findProdottiById_cliente(Long id);
	
	@Modifying
	@Query("DELETE FROM VenditaDettagli vd WHERE vd.vendita.id = :id_vendita")
	public void deleteById_vendita(Long id_vendita);
}
