package br.mppe.mp.recadastro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mppe.mp.recadastro.domain.Escolaridade;
import br.mppe.mp.recadastro.repository.EscolaridadeRepository;
import br.mppe.mp.recadastro.repository.search.EscolaridadeSearchRepository;
import br.mppe.mp.recadastro.web.rest.errors.BadRequestAlertException;
import br.mppe.mp.recadastro.web.rest.util.HeaderUtil;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Escolaridade.
 */
@RestController
@RequestMapping("/api")
public class EscolaridadeResource {

    private final Logger log = LoggerFactory.getLogger(EscolaridadeResource.class);

    private static final String ENTITY_NAME = "escolaridade";

    private final EscolaridadeRepository escolaridadeRepository;

    private final EscolaridadeSearchRepository escolaridadeSearchRepository;

    public EscolaridadeResource(EscolaridadeRepository escolaridadeRepository, EscolaridadeSearchRepository escolaridadeSearchRepository) {
        this.escolaridadeRepository = escolaridadeRepository;
        this.escolaridadeSearchRepository = escolaridadeSearchRepository;
    }

    /**
     * POST  /escolaridades : Create a new escolaridade.
     *
     * @param escolaridade the escolaridade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new escolaridade, or with status 400 (Bad Request) if the escolaridade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/escolaridades")
    @Timed
    public ResponseEntity<Escolaridade> createEscolaridade(@Valid @RequestBody Escolaridade escolaridade) throws URISyntaxException {
        log.debug("REST request to save Escolaridade : {}", escolaridade);
        if (escolaridade.getId() != null) {
            throw new BadRequestAlertException("A new escolaridade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Escolaridade result = escolaridadeRepository.save(escolaridade);
        escolaridadeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/escolaridades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /escolaridades : Updates an existing escolaridade.
     *
     * @param escolaridade the escolaridade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated escolaridade,
     * or with status 400 (Bad Request) if the escolaridade is not valid,
     * or with status 500 (Internal Server Error) if the escolaridade couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/escolaridades")
    @Timed
    public ResponseEntity<Escolaridade> updateEscolaridade(@Valid @RequestBody Escolaridade escolaridade) throws URISyntaxException {
        log.debug("REST request to update Escolaridade : {}", escolaridade);
        if (escolaridade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Escolaridade result = escolaridadeRepository.save(escolaridade);
        escolaridadeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, escolaridade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /escolaridades : get all the escolaridades.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of escolaridades in body
     */
    @GetMapping("/escolaridades")
    @Timed
    public List<Escolaridade> getAllEscolaridades() {
        log.debug("REST request to get all Escolaridades");
        return escolaridadeRepository.findAll();
    }

    /**
     * GET  /escolaridades/:id : get the "id" escolaridade.
     *
     * @param id the id of the escolaridade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the escolaridade, or with status 404 (Not Found)
     */
    @GetMapping("/escolaridades/{id}")
    @Timed
    public ResponseEntity<Escolaridade> getEscolaridade(@PathVariable Long id) {
        log.debug("REST request to get Escolaridade : {}", id);
        Optional<Escolaridade> escolaridade = escolaridadeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(escolaridade);
    }

    /**
     * DELETE  /escolaridades/:id : delete the "id" escolaridade.
     *
     * @param id the id of the escolaridade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/escolaridades/{id}")
    @Timed
    public ResponseEntity<Void> deleteEscolaridade(@PathVariable Long id) {
        log.debug("REST request to delete Escolaridade : {}", id);

        escolaridadeRepository.deleteById(id);
        escolaridadeSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/escolaridades?query=:query : search for the escolaridade corresponding
     * to the query.
     *
     * @param query the query of the escolaridade search
     * @return the result of the search
     */
    @GetMapping("/_search/escolaridades")
    @Timed
    public List<Escolaridade> searchEscolaridades(@RequestParam String query) {
        log.debug("REST request to search Escolaridades for query {}", query);
        return StreamSupport
            .stream(escolaridadeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
