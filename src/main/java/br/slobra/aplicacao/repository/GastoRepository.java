package br.slobra.aplicacao.repository;

import br.slobra.aplicacao.domain.Gasto;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


/**
 * Spring Data  repository for the Gasto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

	@Query("SELECT g FROM Gasto g where g.obra.id = :id")
    List<Gasto> findByObra(@Param("id") Long idObra);

   /* @Query("SELECT g FROM Gasto t WHERE " +
        "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
        "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")

    @Query("SELECT g FROM Gasto g WHERE "
        List<Gasto> findBySearchTerm(@Param("searchTerm") String searchTerm,
                                Pageable pageRequest);*/

    @Query("SELECT g FROM Gasto g where year(g.mesAno) = ?1 and month(g.mesAno) = ?2")
    Page<Gasto> getByGastoYearAndMonth(int year, int month, Pageable pageRequest);


}
