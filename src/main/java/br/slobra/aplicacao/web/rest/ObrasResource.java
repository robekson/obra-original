package br.slobra.aplicacao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.slobra.aplicacao.service.ObrasService;
import br.slobra.aplicacao.web.rest.errors.BadRequestAlertException;
import br.slobra.aplicacao.web.rest.util.HeaderUtil;
import br.slobra.aplicacao.web.rest.util.PaginationUtil;
import br.slobra.aplicacao.service.dto.ObrasDTO;
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
 * REST controller for managing Obras.
 */
@RestController
@RequestMapping("/api")
public class ObrasResource {

    private final Logger log = LoggerFactory.getLogger(ObrasResource.class);

    private static final String ENTITY_NAME = "obras";

    private final ObrasService obrasService;

    public ObrasResource(ObrasService obrasService) {
        this.obrasService = obrasService;
    }

    /**
     * POST  /obras : Create a new obras.
     *
     * @param obrasDTO the obrasDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new obrasDTO, or with status 400 (Bad Request) if the obras has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/obras")
    @Timed
    public ResponseEntity<ObrasDTO> createObras(@RequestBody ObrasDTO obrasDTO) throws URISyntaxException {
        log.debug("REST request to save Obras : {}", obrasDTO);
        if (obrasDTO.getId() != null) {
            throw new BadRequestAlertException("A new obras cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObrasDTO result = obrasService.save(obrasDTO);
        return ResponseEntity.created(new URI("/api/obras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /obras : Updates an existing obras.
     *
     * @param obrasDTO the obrasDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated obrasDTO,
     * or with status 400 (Bad Request) if the obrasDTO is not valid,
     * or with status 500 (Internal Server Error) if the obrasDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/obras")
    @Timed
    public ResponseEntity<ObrasDTO> updateObras(@RequestBody ObrasDTO obrasDTO) throws URISyntaxException {
        log.debug("REST request to update Obras : {}", obrasDTO);
        if (obrasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObrasDTO result = obrasService.save(obrasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, obrasDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /obras : get all the obras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of obras in body
     */
    @GetMapping("/obras")
    @Timed
    public ResponseEntity<List<ObrasDTO>> getAllObras(Pageable pageable) {
        log.debug("REST request to get a page of Obras");
        Page<ObrasDTO> page = obrasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/obras");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /obras/:id : get the "id" obras.
     *
     * @param id the id of the obrasDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the obrasDTO, or with status 404 (Not Found)
     */
    @GetMapping("/obras/{id}")
    @Timed
    public ResponseEntity<ObrasDTO> getObras(@PathVariable Long id) {
        log.debug("REST request to get Obras : {}", id);
        Optional<ObrasDTO> obrasDTO = obrasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(obrasDTO);
    }

    /**
     * DELETE  /obras/:id : delete the "id" obras.
     *
     * @param id the id of the obrasDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/obras/{id}")
    @Timed
    public ResponseEntity<Void> deleteObras(@PathVariable Long id) {
        log.debug("REST request to delete Obras : {}", id);
        obrasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
