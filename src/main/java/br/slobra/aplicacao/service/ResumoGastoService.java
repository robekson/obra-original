package br.slobra.aplicacao.service;

import br.slobra.aplicacao.service.dto.ResumoGastoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ResumoGasto.
 */
public interface ResumoGastoService {

    /**
     * Save a resumoGasto.
     *
     * @param resumoGastoDTO the entity to save
     * @return the persisted entity
     */
    ResumoGastoDTO save(ResumoGastoDTO resumoGastoDTO);

    /**
     * Get all the resumoGastos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResumoGastoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" resumoGasto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ResumoGastoDTO> findOne(Long id);

    /**
     * Delete the "id" resumoGasto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    ResumoGastoDTO findByObra(Long idObra);
}
