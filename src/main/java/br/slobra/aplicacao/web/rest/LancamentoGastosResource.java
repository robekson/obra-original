package br.slobra.aplicacao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.slobra.aplicacao.service.LancamentoGastosService;
import br.slobra.aplicacao.web.rest.errors.BadRequestAlertException;
import br.slobra.aplicacao.web.rest.util.HeaderUtil;
import br.slobra.aplicacao.web.rest.util.PaginationUtil;
import br.slobra.aplicacao.service.dto.LancamentoGastosDTO;
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
 * REST controller for managing LancamentoGastos.
 */
@RestController
@RequestMapping("/api")
public class LancamentoGastosResource {

    private final Logger log = LoggerFactory.getLogger(LancamentoGastosResource.class);

    private static final String ENTITY_NAME = "lancamentoGastos";

    private final LancamentoGastosService lancamentoGastosService;

    public LancamentoGastosResource(LancamentoGastosService lancamentoGastosService) {
        this.lancamentoGastosService = lancamentoGastosService;
    }

    /**
     * POST  /lancamento-gastos : Create a new lancamentoGastos.
     *
     * @param lancamentoGastosDTO the lancamentoGastosDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lancamentoGastosDTO, or with status 400 (Bad Request) if the lancamentoGastos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lancamento-gastos")
    @Timed
    public ResponseEntity<LancamentoGastosDTO> createLancamentoGastos(@RequestBody LancamentoGastosDTO lancamentoGastosDTO) throws URISyntaxException {
        log.debug("REST request to save LancamentoGastos : {}", lancamentoGastosDTO);
        if (lancamentoGastosDTO.getId() != null) {
            throw new BadRequestAlertException("A new lancamentoGastos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LancamentoGastosDTO result = lancamentoGastosService.save(lancamentoGastosDTO);
        return ResponseEntity.created(new URI("/api/lancamento-gastos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lancamento-gastos : Updates an existing lancamentoGastos.
     *
     * @param lancamentoGastosDTO the lancamentoGastosDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lancamentoGastosDTO,
     * or with status 400 (Bad Request) if the lancamentoGastosDTO is not valid,
     * or with status 500 (Internal Server Error) if the lancamentoGastosDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lancamento-gastos")
    @Timed
    public ResponseEntity<LancamentoGastosDTO> updateLancamentoGastos(@RequestBody LancamentoGastosDTO lancamentoGastosDTO) throws URISyntaxException {
        log.debug("REST request to update LancamentoGastos : {}", lancamentoGastosDTO);
        if (lancamentoGastosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LancamentoGastosDTO result = lancamentoGastosService.save(lancamentoGastosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lancamentoGastosDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lancamento-gastos : get all the lancamentoGastos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lancamentoGastos in body
     */
    @GetMapping("/lancamento-gastos")
    @Timed
    public ResponseEntity<List<LancamentoGastosDTO>> getAllLancamentoGastos(Pageable pageable) {
        log.debug("REST request to get a page of LancamentoGastos");
        Page<LancamentoGastosDTO> page = lancamentoGastosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lancamento-gastos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /lancamento-gastos/:id : get the "id" lancamentoGastos.
     *
     * @param id the id of the lancamentoGastosDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lancamentoGastosDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lancamento-gastos/{id}")
    @Timed
    public ResponseEntity<LancamentoGastosDTO> getLancamentoGastos(@PathVariable Long id) {
        log.debug("REST request to get LancamentoGastos : {}", id);
        Optional<LancamentoGastosDTO> lancamentoGastosDTO = lancamentoGastosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lancamentoGastosDTO);
    }

    /**
     * DELETE  /lancamento-gastos/:id : delete the "id" lancamentoGastos.
     *
     * @param id the id of the lancamentoGastosDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lancamento-gastos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLancamentoGastos(@PathVariable Long id) {
        log.debug("REST request to delete LancamentoGastos : {}", id);
        lancamentoGastosService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
