package br.slobra.aplicacao.service;

import br.slobra.aplicacao.service.dto.PeriodoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Periodo.
 */
public interface PeriodoService {

    /**
     * Save a periodo.
     *
     * @param periodoDTO the entity to save
     * @return the persisted entity
     */
    PeriodoDTO save(PeriodoDTO periodoDTO);

    /**
     * Get all the periodos.
     *
     * @return the list of entities
     */
    List<PeriodoDTO> findAll();


    /**
     * Get the "id" periodo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PeriodoDTO> findOne(Long id);

    /**
     * Delete the "id" periodo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
