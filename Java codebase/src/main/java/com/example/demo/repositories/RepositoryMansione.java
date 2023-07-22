package com.example.demo.repositories;

import org.springframework.data.jpa.repository.*;
import java.util.*;

import com.example.demo.entities.*;



public interface RepositoryMansione extends JpaRepository<Mansione, Long> {
	
	public Optional<Mansione> findById(Long id);
	
	@Query("SELECT m FROM Mansione m WHERE m.nome LIKE :nome%")
	public List<Mansione> searchByNome(String nome);
	
	@Query("SELECT m FROM Mansione m WHERE m.descrizione LIKE %:descrizione%")
	public List<Mansione> searchByDescrizione(String descrizione);

	public void deleteById(Long l);

}
