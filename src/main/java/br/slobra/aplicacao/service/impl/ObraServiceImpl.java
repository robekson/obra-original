package br.slobra.aplicacao.service.impl;

import br.slobra.aplicacao.service.ObraService;
import br.slobra.aplicacao.domain.Obra;
import br.slobra.aplicacao.repository.ObraRepository;
import br.slobra.aplicacao.service.dto.ObraDTO;
import br.slobra.aplicacao.service.mapper.ObraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Obra.
 */
@Service
@Transactional
public class ObraServiceImpl implements ObraService {

    private final Logger log = LoggerFactory.getLogger(ObraServiceImpl.class);

    private final ObraRepository obraRepository;

    private final ObraMapper obraMapper;

    public ObraServiceImpl(ObraRepository obraRepository, ObraMapper obraMapper) {
        this.obraRepository = obraRepository;
        this.obraMapper = obraMapper;
    }

    /**
     * Save a obra.
     *
     * @param obraDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ObraDTO save(ObraDTO obraDTO) {
        log.debug("Request to save Obra : {}", obraDTO);

        Obra obra = obraMapper.toEntity(obraDTO);
        obra = obraRepository.save(obra);
        return obraMapper.toDto(obra);
    }

    /**
     * Get all the obras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ObraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Obras");
        return obraRepository.findAll(pageable)
            .map(obraMapper::toDto);
    }


    /**
     * Get one obra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ObraDTO> findOne(Long id) {
        log.debug("Request to get Obra : {}", id);
        return obraRepository.findById(id)
            .map(obraMapper::toDto);
    }

    /**
     * Delete the obra by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Obra : {}", id);
        obraRepository.deleteById(id);
    }
}
