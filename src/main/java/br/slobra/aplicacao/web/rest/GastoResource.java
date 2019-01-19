package br.slobra.aplicacao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.slobra.aplicacao.service.GastoService;
import br.slobra.aplicacao.web.rest.errors.BadRequestAlertException;
import br.slobra.aplicacao.web.rest.util.HeaderUtil;
import br.slobra.aplicacao.web.rest.util.PaginationUtil;
import br.slobra.aplicacao.service.dto.GastoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Gasto.
 */
@RestController
@RequestMapping("/api")
public class GastoResource {

    private final Logger log = LoggerFactory.getLogger(GastoResource.class);

    private static final String ENTITY_NAME = "gasto";

    private final GastoService gastoService;

    public GastoResource(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    /**
     * POST  /gastos : Create a new gasto.
     *
     * @param gastoDTO the gastoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gastoDTO, or with status 400 (Bad Request) if the gasto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gastos")
    @Timed
    public ResponseEntity<GastoDTO> createGasto(@Valid @RequestBody GastoDTO gastoDTO) throws URISyntaxException {
        log.debug("REST request to save Gasto : {}", gastoDTO);
        if (gastoDTO.getId() != null) {
            throw new BadRequestAlertException("A new gasto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GastoDTO result = gastoService.save(gastoDTO);
        return ResponseEntity.created(new URI("/api/gastos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gastos : Updates an existing gasto.
     *
     * @param gastoDTO the gastoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gastoDTO,
     * or with status 400 (Bad Request) if the gastoDTO is not valid,
     * or with status 500 (Internal Server Error) if the gastoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gastos")
    @Timed
    public ResponseEntity<GastoDTO> updateGasto(@Valid @RequestBody GastoDTO gastoDTO) throws URISyntaxException {
        log.debug("REST request to update Gasto : {}", gastoDTO);
        if (gastoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GastoDTO result = gastoService.save(gastoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gastoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gastos : get all the gastos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gastos in body
     */
    @GetMapping("/gastos")
    @Timed
    public ResponseEntity<List<GastoDTO>> getAllGastos(Pageable pageable) {
        log.debug("REST request to get a page of Gastos");
        Page<GastoDTO> page = gastoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gastos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /gastos/:id : get the "id" gasto.
     *
     * @param id the id of the gastoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gastoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/gastos/{id}")
    @Timed
    public ResponseEntity<GastoDTO> getGasto(@PathVariable Long id) {
        log.debug("REST request to get Gasto : {}", id);
        Optional<GastoDTO> gastoDTO = gastoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gastoDTO);
    }

    /**
     * DELETE  /gastos/:id : delete the "id" gasto.
     *
     * @param id the id of the gastoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gastos/{id}")
    @Timed
    public ResponseEntity<Void> deleteGasto(@PathVariable Long id) {
        log.debug("REST request to delete Gasto : {}", id);
        gastoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
