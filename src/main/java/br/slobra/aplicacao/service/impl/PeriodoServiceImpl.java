package br.slobra.aplicacao.service.impl;

import br.slobra.aplicacao.service.PeriodoService;
import br.slobra.aplicacao.domain.Periodo;
import br.slobra.aplicacao.repository.PeriodoRepository;
import br.slobra.aplicacao.service.dto.PeriodoDTO;
import br.slobra.aplicacao.service.mapper.PeriodoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Periodo.
 */
@Service
@Transactional
public class PeriodoServiceImpl implements PeriodoService {

    private final Logger log = LoggerFactory.getLogger(PeriodoServiceImpl.class);

    private final PeriodoRepository periodoRepository;

    private final PeriodoMapper periodoMapper;

    public PeriodoServiceImpl(PeriodoRepository periodoRepository, PeriodoMapper periodoMapper) {
        this.periodoRepository = periodoRepository;
        this.periodoMapper = periodoMapper;
    }

    /**
     * Save a periodo.
     *
     * @param periodoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PeriodoDTO save(PeriodoDTO periodoDTO) {
        log.debug("Request to save Periodo : {}", periodoDTO);

        Periodo periodo = periodoMapper.toEntity(periodoDTO);
        periodo = periodoRepository.save(periodo);
        return periodoMapper.toDto(periodo);
    }

    /**
     * Get all the periodos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PeriodoDTO> findAll() {
        log.debug("Request to get all Periodos");
        return periodoRepository.findAll().stream()
            .map(periodoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one periodo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PeriodoDTO> findOne(Long id) {
        log.debug("Request to get Periodo : {}", id);
        return periodoRepository.findById(id)
            .map(periodoMapper::toDto);
    }

    /**
     * Delete the periodo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Periodo : {}", id);
        periodoRepository.deleteById(id);
    }
}
