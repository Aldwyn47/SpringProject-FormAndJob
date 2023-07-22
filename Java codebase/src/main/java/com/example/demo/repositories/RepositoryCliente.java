package com.example.demo.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.*;

import com.example.demo.entities.*;

public interface RepositoryCliente extends JpaRepository<Cliente,Long> {
	
	public Optional<Cliente> findById(Long id);
	
	@Query("SELECT c FROM Cliente c WHERE c.codice_cliente=:codice_cliente")
	public Optional<Cliente> findByCodice_cliente(String codice_cliente);
	
	@Query("SELECT c FROM Cliente c WHERE c.nome LIKE :nome%")
	public List<Cliente> searchByNome(String nome);
	
	@Query("SELECT c FROM Cliente c WHERE c.cognome LIKE :cognome%")
	public List<Cliente> searchByCognome(String cognome);
	
	@Query("SELECT c FROM Cliente c WHERE c.email LIKE :email%")
	public List<Cliente> searchByEmail(String email);
	
	@Query("SELECT new com.example.demo.entities.Cliente(c.codice_cliente) FROM Cliente c WHERE c.codice_cliente LIKE :codice_cliente_parziale% ORDER BY c.codice_cliente ASC")
	public List<Cliente> searchCodiciOmonimi(String codice_cliente_parziale);
	
	public void deleteById(Long id);

}
