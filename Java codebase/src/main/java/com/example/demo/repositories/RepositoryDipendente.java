package com.example.demo.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.*;

import com.example.demo.entities.*;



public interface RepositoryDipendente extends JpaRepository<Dipendente, Long> {
	
	public Optional<Dipendente> findById(Long id);
	
	@Query("SELECT d FROM Dipendente d WHERE d.codice_dipendente=:codice_dipendente")
	public Optional<Dipendente> findByCodice_dipendente(String codice_dipendente);
	
	@Query("SELECT d FROM Dipendente d WHERE d.nome LIKE :nome%")
	public List<Dipendente> searchByNome(String nome);
	
	@Query("SELECT d FROM Dipendente d WHERE d.cognome LIKE :cognome%")
	public List<Dipendente> searchByCognome(String cognome);
	
	@Query("SELECT d FROM Dipendente d WHERE d.email LIKE :email%")
	public List<Dipendente> searchByEmail(String email);
	
	@Query("SELECT d FROM Dipendente d WHERE d.mansione.id=:id")
	public List<Dipendente> searchByIdMansione(Long id);
	
	@Query("SELECT new com.example.demo.entities.Dipendente(d.codice_dipendente) FROM Dipendente d WHERE d.codice_dipendente LIKE :codice_dipendente_parziale% ORDER BY d.codice_dipendente ASC")
	public List<Dipendente> searchCodiciOmonimi(String codice_dipendente_parziale);
	
	public void deleteById(Long id);

}