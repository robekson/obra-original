package br.slobra.aplicacao.service.impl;

import br.slobra.aplicacao.service.GastoService;
import br.slobra.aplicacao.domain.Gasto;
import br.slobra.aplicacao.repository.GastoRepository;
import br.slobra.aplicacao.service.dto.GastoDTO;
import br.slobra.aplicacao.service.mapper.GastoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Date;

/**
 * Service Implementation for managing Gasto.
 */
@Service
@Transactional
public class GastoServiceImpl implements GastoService {

    private final Logger log = LoggerFactory.getLogger(GastoServiceImpl.class);

    private final GastoRepository gastoRepository;

    private final GastoMapper gastoMapper;

    public GastoServiceImpl(GastoRepository gastoRepository, GastoMapper gastoMapper) {
        this.gastoRepository = gastoRepository;
        this.gastoMapper = gastoMapper;
    }

    /**
     * Save a gasto.
     *
     * @param gastoDTO the entity to save
     * @return the persisted entity
     */
    @Override    
    @CachePut(value= GastoRepository.GASTO_CACHE)
    public GastoDTO save(GastoDTO gastoDTO) {
        log.debug("Request to save Gasto : {}", gastoDTO);

        Gasto gasto = gastoMapper.toEntity(gastoDTO);
        gasto = gastoRepository.save(gasto);
        return gastoMapper.toDto(gasto);
    }
   /**
     * Get all the gastos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Cacheable(cacheNames = GastoRepository.GASTO_CACHE)
    @CacheEvict(value = GastoRepository.GASTO_CACHE, allEntries = true)
    @Override
    @Transactional(readOnly = true)
    public Page<GastoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Gastos");
        return gastoRepository.findAll(pageable)
            .map(gastoMapper::toDto);
    }

    /*@Override
    @Transactional(readOnly = true)
    public Page<GastoDTO> findByAnoMesPage(int ano,int mes,Long idObra, Pageable pageable) {
        log.debug("Request to get Gastos mes ano");
            return gastoRepository.getByGastoYearAndMonthPage(ano,mes,idObra,pageable).map(gastoMapper::toDto);
    }*/


    public Page<GastoDTO> findByAnoMesPage(int ano,int mes,Long idObra,Pageable pageable) {
        log.debug("Request to get Gastos mes ano");
        return gastoRepository.getByGastoYearAndMonthOrderByNameDesc(ano,mes,idObra,pageable).map(gastoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GastoDTO> findByAnoMes(int ano,int mes,Long idObra) {
        log.debug("Request to get Gastos mes ano");
        List<Gasto> lista = gastoRepository.getByGastoYearAndMonth(ano,mes,idObra);
        return lista.stream().map(gastoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GastoDTO> findResumoTotalInterval(Date dataInicial,Date dataFinal,Long idObra) {
        log.debug("Request to get Gastos mes ano");
        LocalDate dateInicial = dataInicial.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateFinal = dataFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dateInicial=dateInicial.plusMonths(-1);
        dateFinal=dateFinal.plusMonths(1);
        List<Gasto> lista = gastoRepository.getByGastoResumoTotalInterval(dateInicial,dateFinal,idObra);
        return lista.stream().map(gastoMapper::toDto).collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<GastoDTO> findByObra(Long idObra) {
        log.debug("Request to get all Gastos");
        List<Gasto> lista = gastoRepository.findByObra(idObra);
        return lista.stream().map(gastoMapper::toDto).collect(Collectors.toList());
    }


    /**
     * Get one gasto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GastoDTO> findOne(Long id) {
        log.debug("Request to get Gasto : {}", id);
        return gastoRepository.findById(id)
            .map(gastoMapper::toDto);
    }

    /**
     * Delete the gasto by id.
     *
     * @param id the id of the entity
     */
    @Override
    @CacheEvict(value = GastoRepository.GASTO_CACHE, allEntries = true)
    public void delete(Long id) {
        log.debug("Request to delete Gasto : {}", id);
        gastoRepository.deleteById(id);
    }
}
