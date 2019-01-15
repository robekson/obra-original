package br.slobra.aplicacao.service;

import br.slobra.aplicacao.service.dto.ObrasDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Obras.
 */
public interface ObrasService {

    /**
     * Save a obras.
     *
     * @param obrasDTO the entity to save
     * @return the persisted entity
     */
    ObrasDTO save(ObrasDTO obrasDTO);

    /**
     * Get all the obras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ObrasDTO> findAll(Pageable pageable);


    /**
     * Get the "id" obras.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ObrasDTO> findOne(Long id);

    /**
     * Delete the "id" obras.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
