package br.slobra.aplicacao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.slobra.aplicacao.service.PeriodoService;
import br.slobra.aplicacao.web.rest.errors.BadRequestAlertException;
import br.slobra.aplicacao.web.rest.util.HeaderUtil;
import br.slobra.aplicacao.service.dto.PeriodoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Periodo.
 */
@RestController
@RequestMapping("/api")
public class PeriodoResource {

    private final Logger log = LoggerFactory.getLogger(PeriodoResource.class);

    private static final String ENTITY_NAME = "periodo";

    private final PeriodoService periodoService;

    public PeriodoResource(PeriodoService periodoService) {
        this.periodoService = periodoService;
    }

    /**
     * POST  /periodos : Create a new periodo.
     *
     * @param periodoDTO the periodoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodoDTO, or with status 400 (Bad Request) if the periodo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/periodos")
    @Timed
    public ResponseEntity<PeriodoDTO> createPeriodo(@RequestBody PeriodoDTO periodoDTO) throws URISyntaxException {
        log.debug("REST request to save Periodo : {}", periodoDTO);
        if (periodoDTO.getId() != null) {
            throw new BadRequestAlertException("A new periodo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodoDTO result = periodoService.save(periodoDTO);
        return ResponseEntity.created(new URI("/api/periodos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /periodos : Updates an existing periodo.
     *
     * @param periodoDTO the periodoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodoDTO,
     * or with status 400 (Bad Request) if the periodoDTO is not valid,
     * or with status 500 (Internal Server Error) if the periodoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/periodos")
    @Timed
    public ResponseEntity<PeriodoDTO> updatePeriodo(@RequestBody PeriodoDTO periodoDTO) throws URISyntaxException {
        log.debug("REST request to update Periodo : {}", periodoDTO);
        if (periodoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodoDTO result = periodoService.save(periodoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /periodos : get all the periodos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of periodos in body
     */
    @GetMapping("/periodos")
    @Timed
    public List<PeriodoDTO> getAllPeriodos() {
        log.debug("REST request to get all Periodos");
        return periodoService.findAll();
    }

    /**
     * GET  /periodos/:id : get the "id" periodo.
     *
     * @param id the id of the periodoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/periodos/{id}")
    @Timed
    public ResponseEntity<PeriodoDTO> getPeriodo(@PathVariable Long id) {
        log.debug("REST request to get Periodo : {}", id);
        Optional<PeriodoDTO> periodoDTO = periodoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodoDTO);
    }

    /**
     * DELETE  /periodos/:id : delete the "id" periodo.
     *
     * @param id the id of the periodoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/periodos/{id}")
    @Timed
    public ResponseEntity<Void> deletePeriodo(@PathVariable Long id) {
        log.debug("REST request to delete Periodo : {}", id);
        periodoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
