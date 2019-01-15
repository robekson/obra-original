package br.slobra.aplicacao.service;

import br.slobra.aplicacao.service.dto.LancamentoGastosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing LancamentoGastos.
 */
public interface LancamentoGastosService {

    /**
     * Save a lancamentoGastos.
     *
     * @param lancamentoGastosDTO the entity to save
     * @return the persisted entity
     */
    LancamentoGastosDTO save(LancamentoGastosDTO lancamentoGastosDTO);

    /**
     * Get all the lancamentoGastos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LancamentoGastosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" lancamentoGastos.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LancamentoGastosDTO> findOne(Long id);

    /**
     * Delete the "id" lancamentoGastos.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
