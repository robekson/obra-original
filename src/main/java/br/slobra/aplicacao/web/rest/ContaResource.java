package br.slobra.aplicacao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.slobra.aplicacao.service.ContaService;
import br.slobra.aplicacao.web.rest.errors.BadRequestAlertException;
import br.slobra.aplicacao.web.rest.util.HeaderUtil;
import br.slobra.aplicacao.service.dto.ContaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Conta.
 */
@RestController
@RequestMapping("/api")
public class ContaResource {

    private final Logger log = LoggerFactory.getLogger(ContaResource.class);

    private static final String ENTITY_NAME = "conta";

    private final ContaService contaService;

    public ContaResource(ContaService contaService) {
        this.contaService = contaService;
    }

    /**
     * POST  /contas : Create a new conta.
     *
     * @param contaDTO the contaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contaDTO, or with status 400 (Bad Request) if the conta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contas")
    @Timed
    public ResponseEntity<ContaDTO> createConta(@Valid @RequestBody ContaDTO contaDTO) throws URISyntaxException {
        log.debug("REST request to save Conta : {}", contaDTO);
        if (contaDTO.getId() != null) {
            throw new BadRequestAlertException("A new conta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContaDTO result = contaService.save(contaDTO);
        return ResponseEntity.created(new URI("/api/contas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contas : Updates an existing conta.
     *
     * @param contaDTO the contaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contaDTO,
     * or with status 400 (Bad Request) if the contaDTO is not valid,
     * or with status 500 (Internal Server Error) if the contaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contas")
    @Timed
    public ResponseEntity<ContaDTO> updateConta(@Valid @RequestBody ContaDTO contaDTO) throws URISyntaxException {
        log.debug("REST request to update Conta : {}", contaDTO);
        if (contaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContaDTO result = contaService.save(contaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contas : get all the contas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contas in body
     */
    @GetMapping("/contas")
    @Timed
    public List<ContaDTO> getAllContas() {
        log.debug("REST request to get all Contas");
        return contaService.findAll();
    }

    /**
     * GET  /contas/:id : get the "id" conta.
     *
     * @param id the id of the contaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contas/{id}")
    @Timed
    public ResponseEntity<ContaDTO> getConta(@PathVariable Long id) {
        log.debug("REST request to get Conta : {}", id);
        Optional<ContaDTO> contaDTO = contaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contaDTO);
    }

    /**
     * DELETE  /contas/:id : delete the "id" conta.
     *
     * @param id the id of the contaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contas/{id}")
    @Timed
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        log.debug("REST request to delete Conta : {}", id);
        contaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
