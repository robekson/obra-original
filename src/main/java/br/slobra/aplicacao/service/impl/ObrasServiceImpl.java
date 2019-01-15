package br.slobra.aplicacao.service.impl;

import br.slobra.aplicacao.service.ObrasService;
import br.slobra.aplicacao.domain.Obras;
import br.slobra.aplicacao.repository.ObrasRepository;
import br.slobra.aplicacao.service.dto.ObrasDTO;
import br.slobra.aplicacao.service.mapper.ObrasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Obras.
 */
@Service
@Transactional
public class ObrasServiceImpl implements ObrasService {

    private final Logger log = LoggerFactory.getLogger(ObrasServiceImpl.class);

    private final ObrasRepository obrasRepository;

    private final ObrasMapper obrasMapper;

    public ObrasServiceImpl(ObrasRepository obrasRepository, ObrasMapper obrasMapper) {
        this.obrasRepository = obrasRepository;
        this.obrasMapper = obrasMapper;
    }

    /**
     * Save a obras.
     *
     * @param obrasDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ObrasDTO save(ObrasDTO obrasDTO) {
        log.debug("Request to save Obras : {}", obrasDTO);

        Obras obras = obrasMapper.toEntity(obrasDTO);
        obras = obrasRepository.save(obras);
        return obrasMapper.toDto(obras);
    }

    /**
     * Get all the obras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ObrasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Obras");
        return obrasRepository.findAll(pageable)
            .map(obrasMapper::toDto);
    }


    /**
     * Get one obras by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ObrasDTO> findOne(Long id) {
        log.debug("Request to get Obras : {}", id);
        return obrasRepository.findById(id)
            .map(obrasMapper::toDto);
    }

    /**
     * Delete the obras by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Obras : {}", id);
        obrasRepository.deleteById(id);
    }
}
