package br.slobra.aplicacao.service.impl;

import br.slobra.aplicacao.service.ContaService;
import br.slobra.aplicacao.domain.Conta;
import br.slobra.aplicacao.repository.ContaRepository;
import br.slobra.aplicacao.service.dto.ContaDTO;
import br.slobra.aplicacao.service.mapper.ContaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Conta.
 */
@Service
@Transactional
public class ContaServiceImpl implements ContaService {

    private final Logger log = LoggerFactory.getLogger(ContaServiceImpl.class);

    private final ContaRepository contaRepository;

    private final ContaMapper contaMapper;

    public ContaServiceImpl(ContaRepository contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    /**
     * Save a conta.
     *
     * @param contaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContaDTO save(ContaDTO contaDTO) {
        log.debug("Request to save Conta : {}", contaDTO);

        Conta conta = contaMapper.toEntity(contaDTO);
        conta = contaRepository.save(conta);
        return contaMapper.toDto(conta);
    }

    /**
     * Get all the contas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContaDTO> findAll() {
        log.debug("Request to get all Contas");
        return contaRepository.findAll().stream()
            .map(contaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one conta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContaDTO> findOne(Long id) {
        log.debug("Request to get Conta : {}", id);
        return contaRepository.findById(id)
            .map(contaMapper::toDto);
    }

    /**
     * Delete the conta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Conta : {}", id);
        contaRepository.deleteById(id);
    }
}
