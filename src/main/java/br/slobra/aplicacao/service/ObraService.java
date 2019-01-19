package br.slobra.aplicacao.service;

import br.slobra.aplicacao.service.dto.ObraDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Obra.
 */
public interface ObraService {

    /**
     * Save a obra.
     *
     * @param obraDTO the entity to save
     * @return the persisted entity
     */
    ObraDTO save(ObraDTO obraDTO);

    /**
     * Get all the obras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ObraDTO> findAll(Pageable pageable);


    /**
     * Get the "id" obra.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ObraDTO> findOne(Long id);

    /**
     * Delete the "id" obra.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
