package br.slobra.aplicacao.service.impl;

import br.slobra.aplicacao.service.ResumoGastoService;
import br.slobra.aplicacao.domain.ResumoGasto;
import br.slobra.aplicacao.repository.ResumoGastoRepository;
import br.slobra.aplicacao.service.dto.ResumoGastoDTO;
import br.slobra.aplicacao.service.mapper.ResumoGastoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ResumoGasto.
 */
@Service
@Transactional
public class ResumoGastoServiceImpl implements ResumoGastoService {

    private final Logger log = LoggerFactory.getLogger(ResumoGastoServiceImpl.class);

    private final ResumoGastoRepository resumoGastoRepository;

    private final ResumoGastoMapper resumoGastoMapper;

    public ResumoGastoServiceImpl(ResumoGastoRepository resumoGastoRepository, ResumoGastoMapper resumoGastoMapper) {
        this.resumoGastoRepository = resumoGastoRepository;
        this.resumoGastoMapper = resumoGastoMapper;
    }

    /**
     * Save a resumoGasto.
     *
     * @param resumoGastoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ResumoGastoDTO save(ResumoGastoDTO resumoGastoDTO) {
        log.debug("Request to save ResumoGasto : {}", resumoGastoDTO);

        ResumoGasto resumoGasto = resumoGastoMapper.toEntity(resumoGastoDTO);
        resumoGasto = resumoGastoRepository.save(resumoGasto);
        return resumoGastoMapper.toDto(resumoGasto);
    }

    /**
     * Get all the resumoGastos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResumoGastoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResumoGastos");
        return resumoGastoRepository.findAll(pageable)
            .map(resumoGastoMapper::toDto);
    }


    /**
     * Get one resumoGasto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResumoGastoDTO> findOne(Long id) {
        log.debug("Request to get ResumoGasto : {}", id);
        return resumoGastoRepository.findById(id)
            .map(resumoGastoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResumoGastoDTO findByObra(Long idObra) {
        ResumoGasto resumoGasto = resumoGastoRepository.findByObra(idObra);
        return resumoGastoMapper.toDto(resumoGasto);
    }

    /**
     * Delete the resumoGasto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResumoGasto : {}", id);
        resumoGastoRepository.deleteById(id);
    }
}
