package br.mppe.mp.recadastro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mppe.mp.recadastro.domain.AnelViario;
import br.mppe.mp.recadastro.repository.AnelViarioRepository;
import br.mppe.mp.recadastro.repository.search.AnelViarioSearchRepository;
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
 * REST controller for managing AnelViario.
 */
@RestController
@RequestMapping("/api")
public class AnelViarioResource {

    private final Logger log = LoggerFactory.getLogger(AnelViarioResource.class);

    private static final String ENTITY_NAME = "anelViario";

    private final AnelViarioRepository anelViarioRepository;

    private final AnelViarioSearchRepository anelViarioSearchRepository;

    public AnelViarioResource(AnelViarioRepository anelViarioRepository, AnelViarioSearchRepository anelViarioSearchRepository) {
        this.anelViarioRepository = anelViarioRepository;
        this.anelViarioSearchRepository = anelViarioSearchRepository;
    }

    /**
     * POST  /anel-viarios : Create a new anelViario.
     *
     * @param anelViario the anelViario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new anelViario, or with status 400 (Bad Request) if the anelViario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/anel-viarios")
    @Timed
    public ResponseEntity<AnelViario> createAnelViario(@Valid @RequestBody AnelViario anelViario) throws URISyntaxException {
        log.debug("REST request to save AnelViario : {}", anelViario);
        if (anelViario.getId() != null) {
            throw new BadRequestAlertException("A new anelViario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnelViario result = anelViarioRepository.save(anelViario);
        anelViarioSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/anel-viarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /anel-viarios : Updates an existing anelViario.
     *
     * @param anelViario the anelViario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated anelViario,
     * or with status 400 (Bad Request) if the anelViario is not valid,
     * or with status 500 (Internal Server Error) if the anelViario couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/anel-viarios")
    @Timed
    public ResponseEntity<AnelViario> updateAnelViario(@Valid @RequestBody AnelViario anelViario) throws URISyntaxException {
        log.debug("REST request to update AnelViario : {}", anelViario);
        if (anelViario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnelViario result = anelViarioRepository.save(anelViario);
        anelViarioSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, anelViario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /anel-viarios : get all the anelViarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of anelViarios in body
     */
    @GetMapping("/anel-viarios")
    @Timed
    public List<AnelViario> getAllAnelViarios() {
        log.debug("REST request to get all AnelViarios");
        return anelViarioRepository.findAll();
    }

    /**
     * GET  /anel-viarios/:id : get the "id" anelViario.
     *
     * @param id the id of the anelViario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the anelViario, or with status 404 (Not Found)
     */
    @GetMapping("/anel-viarios/{id}")
    @Timed
    public ResponseEntity<AnelViario> getAnelViario(@PathVariable Long id) {
        log.debug("REST request to get AnelViario : {}", id);
        Optional<AnelViario> anelViario = anelViarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(anelViario);
    }

    /**
     * DELETE  /anel-viarios/:id : delete the "id" anelViario.
     *
     * @param id the id of the anelViario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/anel-viarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnelViario(@PathVariable Long id) {
        log.debug("REST request to delete AnelViario : {}", id);

        anelViarioRepository.deleteById(id);
        anelViarioSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/anel-viarios?query=:query : search for the anelViario corresponding
     * to the query.
     *
     * @param query the query of the anelViario search
     * @return the result of the search
     */
    @GetMapping("/_search/anel-viarios")
    @Timed
    public List<AnelViario> searchAnelViarios(@RequestParam String query) {
        log.debug("REST request to search AnelViarios for query {}", query);
        return StreamSupport
            .stream(anelViarioSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
