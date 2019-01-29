package br.slobra.aplicacao.repository;

import br.slobra.aplicacao.domain.ResumoGasto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



/**
 * Spring Data  repository for the ResumoGasto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResumoGastoRepository extends JpaRepository<ResumoGasto, Long> {

    @Query("SELECT r FROM ResumoGasto r where r.idObra = :id")
    ResumoGasto findByObra(@Param("id") Long idObra);

}
