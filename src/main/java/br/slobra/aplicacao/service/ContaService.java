package br.slobra.aplicacao.service;

import br.slobra.aplicacao.service.dto.ContaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Conta.
 */
public interface ContaService {

    /**
     * Save a conta.
     *
     * @param contaDTO the entity to save
     * @return the persisted entity
     */
    ContaDTO save(ContaDTO contaDTO);

    /**
     * Get all the contas.
     *
     * @return the list of entities
     */
    List<ContaDTO> findAll();


    /**
     * Get the "id" conta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ContaDTO> findOne(Long id);

    /**
     * Delete the "id" conta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
