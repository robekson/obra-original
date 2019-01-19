package br.slobra.aplicacao.repository;

import br.slobra.aplicacao.domain.ResumoGasto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResumoGasto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumoGastoRepository extends JpaRepository<ResumoGasto, Long> {

}
