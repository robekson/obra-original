package br.slobra.aplicacao.repository;

import br.slobra.aplicacao.domain.Periodo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Periodo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long> {

}
