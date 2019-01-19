package br.slobra.aplicacao.repository;

import br.slobra.aplicacao.domain.Obra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Obra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {

}
