package br.mppe.mp.recadastro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mppe.mp.recadastro.domain.RacaCor;
import br.mppe.mp.recadastro.repository.RacaCorRepository;
import br.mppe.mp.recadastro.repository.search.RacaCorSearchRepository;
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
 * REST controller for managing RacaCor.
 */
@RestController
@RequestMapping("/api")
public class RacaCorResource {

    private final Logger log = LoggerFactory.getLogger(RacaCorResource.class);

    private static final String ENTITY_NAME = "racaCor";

    private final RacaCorRepository racaCorRepository;

    private final RacaCorSearchRepository racaCorSearchRepository;

    public RacaCorResource(RacaCorRepository racaCorRepository, RacaCorSearchRepository racaCorSearchRepository) {
        this.racaCorRepository = racaCorRepository;
        this.racaCorSearchRepository = racaCorSearchRepository;
    }

    /**
     * POST  /raca-cors : Create a new racaCor.
     *
     * @param racaCor the racaCor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new racaCor, or with status 400 (Bad Request) if the racaCor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/raca-cors")
    @Timed
    public ResponseEntity<RacaCor> createRacaCor(@Valid @RequestBody RacaCor racaCor) throws URISyntaxException {
        log.debug("REST request to save RacaCor : {}", racaCor);
        if (racaCor.getId() != null) {
            throw new BadRequestAlertException("A new racaCor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RacaCor result = racaCorRepository.save(racaCor);
        racaCorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/raca-cors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /raca-cors : Updates an existing racaCor.
     *
     * @param racaCor the racaCor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated racaCor,
     * or with status 400 (Bad Request) if the racaCor is not valid,
     * or with status 500 (Internal Server Error) if the racaCor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/raca-cors")
    @Timed
    public ResponseEntity<RacaCor> updateRacaCor(@Valid @RequestBody RacaCor racaCor) throws URISyntaxException {
        log.debug("REST request to update RacaCor : {}", racaCor);
        if (racaCor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RacaCor result = racaCorRepository.save(racaCor);
        racaCorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, racaCor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /raca-cors : get all the racaCors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of racaCors in body
     */
    @GetMapping("/raca-cors")
    @Timed
    public List<RacaCor> getAllRacaCors() {
        log.debug("REST request to get all RacaCors");
        return racaCorRepository.findAll();
    }

    /**
     * GET  /raca-cors/:id : get the "id" racaCor.
     *
     * @param id the id of the racaCor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the racaCor, or with status 404 (Not Found)
     */
    @GetMapping("/raca-cors/{id}")
    @Timed
    public ResponseEntity<RacaCor> getRacaCor(@PathVariable Long id) {
        log.debug("REST request to get RacaCor : {}", id);
        Optional<RacaCor> racaCor = racaCorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(racaCor);
    }

    /**
     * DELETE  /raca-cors/:id : delete the "id" racaCor.
     *
     * @param id the id of the racaCor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/raca-cors/{id}")
    @Timed
    public ResponseEntity<Void> deleteRacaCor(@PathVariable Long id) {
        log.debug("REST request to delete RacaCor : {}", id);

        racaCorRepository.deleteById(id);
        racaCorSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/raca-cors?query=:query : search for the racaCor corresponding
     * to the query.
     *
     * @param query the query of the racaCor search
     * @return the result of the search
     */
    @GetMapping("/_search/raca-cors")
    @Timed
    public List<RacaCor> searchRacaCors(@RequestParam String query) {
        log.debug("REST request to search RacaCors for query {}", query);
        return StreamSupport
            .stream(racaCorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
