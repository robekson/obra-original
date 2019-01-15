package br.slobra.aplicacao.repository;

import br.slobra.aplicacao.domain.Obras;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Obras entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObrasRepository extends JpaRepository<Obras, Long> {

}
