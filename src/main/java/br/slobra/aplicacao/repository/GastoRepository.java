package br.slobra.aplicacao.repository;

import br.slobra.aplicacao.domain.Gasto;

import java.time.LocalDate;
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


    //@Query("SELECT g FROM Gasto g where year(g.mesAno) = :ano and month(g.mesAno) = :mes and g.obra.id = :id")
    //Page<Gasto> getByGastoYearAndMonthPage(@Param("ano")int year, @Param("mes")int month, @Param("id")Long idObra, Pageable pageRequest);

    @Query("SELECT g FROM Gasto g where year(g.mesAno) = :ano and month(g.mesAno) = :mes and g.obra.id = :id ")
    Page<Gasto> getByGastoYearAndMonthOrderByNameDesc(@Param("ano")int year, @Param("mes")int month, @Param("id")Long idObra, Pageable pageRequest);

    @Query("SELECT g FROM Gasto g where year(g.mesAno) = ?1 and month(g.mesAno) = ?2 and g.obra.id = ?3")
    List<Gasto> getByGastoYearAndMonth(int year, int month, Long idObra);


    @Query("SELECT g FROM Gasto g where g.mesAno BETWEEN :startDate AND :endDate and g.obra.id = :id ")
    List<Gasto> getByGastoResumoTotalInterval(@Param("startDate")LocalDate dataInicial, @Param("endDate")LocalDate dataFinal,@Param("id")Long idObra);


}
