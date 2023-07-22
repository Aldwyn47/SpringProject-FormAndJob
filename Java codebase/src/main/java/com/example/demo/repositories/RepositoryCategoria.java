package com.example.demo.repositories;

import org.springframework.data.jpa.repository.*;
import java.util.*;

import com.example.demo.entities.*;

public interface RepositoryCategoria extends JpaRepository<Categoria, Long> {
	
	public Optional<Categoria> findById(Long id);
	
	@Query("SELECT c FROM Categoria c WHERE c.nome LIKE :nome%")
	public List<Categoria> searchByNome(String nome);
	
	@Query("SELECT c FROM Categoria c WHERE c.descrizione LIKE %:descrizione%")
	public List<Categoria> searchByDescrizione(String descrizione);

	public void deleteById(Long l);

}
