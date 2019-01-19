package br.slobra.aplicacao.repository;

import br.slobra.aplicacao.domain.Gasto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Gasto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

}
