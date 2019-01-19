package br.slobra.aplicacao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.slobra.aplicacao.service.ObraService;
import br.slobra.aplicacao.web.rest.errors.BadRequestAlertException;
import br.slobra.aplicacao.web.rest.util.HeaderUtil;
import br.slobra.aplicacao.web.rest.util.PaginationUtil;
import br.slobra.aplicacao.service.dto.ObraDTO;
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
 * REST controller for managing Obra.
 */
@RestController
@RequestMapping("/api")
public class ObraResource {

    private final Logger log = LoggerFactory.getLogger(ObraResource.class);

    private static final String ENTITY_NAME = "obra";

    private final ObraService obraService;

    public ObraResource(ObraService obraService) {
        this.obraService = obraService;
    }

    /**
     * POST  /obras : Create a new obra.
     *
     * @param obraDTO the obraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new obraDTO, or with status 400 (Bad Request) if the obra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/obras")
    @Timed
    public ResponseEntity<ObraDTO> createObra(@Valid @RequestBody ObraDTO obraDTO) throws URISyntaxException {
        log.debug("REST request to save Obra : {}", obraDTO);
        if (obraDTO.getId() != null) {
            throw new BadRequestAlertException("A new obra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObraDTO result = obraService.save(obraDTO);
        return ResponseEntity.created(new URI("/api/obras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /obras : Updates an existing obra.
     *
     * @param obraDTO the obraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated obraDTO,
     * or with status 400 (Bad Request) if the obraDTO is not valid,
     * or with status 500 (Internal Server Error) if the obraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/obras")
    @Timed
    public ResponseEntity<ObraDTO> updateObra(@Valid @RequestBody ObraDTO obraDTO) throws URISyntaxException {
        log.debug("REST request to update Obra : {}", obraDTO);
        if (obraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObraDTO result = obraService.save(obraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, obraDTO.getId().toString()))
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
    public ResponseEntity<List<ObraDTO>> getAllObras(Pageable pageable) {
        log.debug("REST request to get a page of Obras");
        Page<ObraDTO> page = obraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/obras");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /obras/:id : get the "id" obra.
     *
     * @param id the id of the obraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the obraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/obras/{id}")
    @Timed
    public ResponseEntity<ObraDTO> getObra(@PathVariable Long id) {
        log.debug("REST request to get Obra : {}", id);
        Optional<ObraDTO> obraDTO = obraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(obraDTO);
    }

    /**
     * DELETE  /obras/:id : delete the "id" obra.
     *
     * @param id the id of the obraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/obras/{id}")
    @Timed
    public ResponseEntity<Void> deleteObra(@PathVariable Long id) {
        log.debug("REST request to delete Obra : {}", id);
        obraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
