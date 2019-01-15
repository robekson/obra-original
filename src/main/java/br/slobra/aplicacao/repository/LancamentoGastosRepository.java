package br.slobra.aplicacao.repository;

import br.slobra.aplicacao.domain.LancamentoGastos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LancamentoGastos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LancamentoGastosRepository extends JpaRepository<LancamentoGastos, Long> {

}
