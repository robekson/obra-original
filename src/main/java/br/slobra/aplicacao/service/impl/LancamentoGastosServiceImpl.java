package br.slobra.aplicacao.service.impl;

import br.slobra.aplicacao.service.LancamentoGastosService;
import br.slobra.aplicacao.domain.LancamentoGastos;
import br.slobra.aplicacao.repository.LancamentoGastosRepository;
import br.slobra.aplicacao.service.dto.LancamentoGastosDTO;
import br.slobra.aplicacao.service.mapper.LancamentoGastosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing LancamentoGastos.
 */
@Service
@Transactional
public class LancamentoGastosServiceImpl implements LancamentoGastosService {

    private final Logger log = LoggerFactory.getLogger(LancamentoGastosServiceImpl.class);

    private final LancamentoGastosRepository lancamentoGastosRepository;

    private final LancamentoGastosMapper lancamentoGastosMapper;

    public LancamentoGastosServiceImpl(LancamentoGastosRepository lancamentoGastosRepository, LancamentoGastosMapper lancamentoGastosMapper) {
        this.lancamentoGastosRepository = lancamentoGastosRepository;
        this.lancamentoGastosMapper = lancamentoGastosMapper;
    }

    /**
     * Save a lancamentoGastos.
     *
     * @param lancamentoGastosDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LancamentoGastosDTO save(LancamentoGastosDTO lancamentoGastosDTO) {
        log.debug("Request to save LancamentoGastos : {}", lancamentoGastosDTO);

        LancamentoGastos lancamentoGastos = lancamentoGastosMapper.toEntity(lancamentoGastosDTO);
        lancamentoGastos = lancamentoGastosRepository.save(lancamentoGastos);
        return lancamentoGastosMapper.toDto(lancamentoGastos);
    }

    /**
     * Get all the lancamentoGastos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LancamentoGastosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LancamentoGastos");
        return lancamentoGastosRepository.findAll(pageable)
            .map(lancamentoGastosMapper::toDto);
    }


    /**
     * Get one lancamentoGastos by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LancamentoGastosDTO> findOne(Long id) {
        log.debug("Request to get LancamentoGastos : {}", id);
        return lancamentoGastosRepository.findById(id)
            .map(lancamentoGastosMapper::toDto);
    }

    /**
     * Delete the lancamentoGastos by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LancamentoGastos : {}", id);
        lancamentoGastosRepository.deleteById(id);
    }
}
