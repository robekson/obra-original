package br.slobra.aplicacao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.slobra.aplicacao.service.ResumoGastoService;
import br.slobra.aplicacao.web.rest.errors.BadRequestAlertException;
import br.slobra.aplicacao.web.rest.util.HeaderUtil;
import br.slobra.aplicacao.web.rest.util.PaginationUtil;
import br.slobra.aplicacao.service.dto.ResumoGastoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ResumoGasto.
 */
@RestController
@RequestMapping("/api")
public class ResumoGastoResource {

    private final Logger log = LoggerFactory.getLogger(ResumoGastoResource.class);

    private static final String ENTITY_NAME = "resumoGasto";

    private final ResumoGastoService resumoGastoService;

    public ResumoGastoResource(ResumoGastoService resumoGastoService) {
        this.resumoGastoService = resumoGastoService;
    }

    /**
     * POST  /resumo-gastos : Create a new resumoGasto.
     *
     * @param resumoGastoDTO the resumoGastoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resumoGastoDTO, or with status 400 (Bad Request) if the resumoGasto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resumo-gastos")
    @Timed
    public ResponseEntity<ResumoGastoDTO> createResumoGasto(@RequestBody ResumoGastoDTO resumoGastoDTO) throws URISyntaxException {
        log.debug("REST request to save ResumoGasto : {}", resumoGastoDTO);
        if (resumoGastoDTO.getId() != null) {
            throw new BadRequestAlertException("A new resumoGasto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResumoGastoDTO result = resumoGastoService.save(resumoGastoDTO);
        return ResponseEntity.created(new URI("/api/resumo-gastos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resumo-gastos : Updates an existing resumoGasto.
     *
     * @param resumoGastoDTO the resumoGastoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resumoGastoDTO,
     * or with status 400 (Bad Request) if the resumoGastoDTO is not valid,
     * or with status 500 (Internal Server Error) if the resumoGastoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resumo-gastos")
    @Timed
    public ResponseEntity<ResumoGastoDTO> updateResumoGasto(@RequestBody ResumoGastoDTO resumoGastoDTO) throws URISyntaxException {
        log.debug("REST request to update ResumoGasto : {}", resumoGastoDTO);
        if (resumoGastoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResumoGastoDTO result = resumoGastoService.save(resumoGastoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resumoGastoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resumo-gastos : get all the resumoGastos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of resumoGastos in body
     */
    @GetMapping("/resumo-gastos")
    @Timed
    public ResponseEntity<List<ResumoGastoDTO>> getAllResumoGastos(Pageable pageable) {
        log.debug("REST request to get a page of ResumoGastos");
        Page<ResumoGastoDTO> page = resumoGastoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resumo-gastos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /resumo-gastos/:id : get the "id" resumoGasto.
     *
     * @param id the id of the resumoGastoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resumoGastoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/resumo-gastos/{id}")
    @Timed
    public ResponseEntity<ResumoGastoDTO> getResumoGasto(@PathVariable Long id) {
        log.debug("REST request to get ResumoGasto : {}", id);
        Optional<ResumoGastoDTO> resumoGastoDTO = resumoGastoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resumoGastoDTO);
    }

    /**
     * DELETE  /resumo-gastos/:id : delete the "id" resumoGasto.
     *
     * @param id the id of the resumoGastoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resumo-gastos/{id}")
    @Timed
    public ResponseEntity<Void> deleteResumoGasto(@PathVariable Long id) {
        log.debug("REST request to delete ResumoGasto : {}", id);
        resumoGastoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
