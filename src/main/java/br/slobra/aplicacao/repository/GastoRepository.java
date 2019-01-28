package br.slobra.aplicacao.repository;

import br.slobra.aplicacao.domain.Gasto;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data  repository for the Gasto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
	
	@Query("SELECT g FROM Gasto g where g.obra.id = :id") 
	List<Gasto> findByObra(@Param("id") Long idObra);

}
