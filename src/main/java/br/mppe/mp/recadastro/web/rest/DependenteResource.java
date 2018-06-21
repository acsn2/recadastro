package br.mppe.mp.recadastro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mppe.mp.recadastro.domain.Dependente;
import br.mppe.mp.recadastro.repository.DependenteRepository;
import br.mppe.mp.recadastro.repository.search.DependenteSearchRepository;
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
 * REST controller for managing Dependente.
 */
@RestController
@RequestMapping("/api")
public class DependenteResource {

    private final Logger log = LoggerFactory.getLogger(DependenteResource.class);

    private static final String ENTITY_NAME = "dependente";

    private final DependenteRepository dependenteRepository;

    private final DependenteSearchRepository dependenteSearchRepository;

    public DependenteResource(DependenteRepository dependenteRepository, DependenteSearchRepository dependenteSearchRepository) {
        this.dependenteRepository = dependenteRepository;
        this.dependenteSearchRepository = dependenteSearchRepository;
    }

    /**
     * POST  /dependentes : Create a new dependente.
     *
     * @param dependente the dependente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dependente, or with status 400 (Bad Request) if the dependente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dependentes")
    @Timed
    public ResponseEntity<Dependente> createDependente(@Valid @RequestBody Dependente dependente) throws URISyntaxException {
        log.debug("REST request to save Dependente : {}", dependente);
        if (dependente.getId() != null) {
            throw new BadRequestAlertException("A new dependente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dependente result = dependenteRepository.save(dependente);
        dependenteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dependentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dependentes : Updates an existing dependente.
     *
     * @param dependente the dependente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dependente,
     * or with status 400 (Bad Request) if the dependente is not valid,
     * or with status 500 (Internal Server Error) if the dependente couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dependentes")
    @Timed
    public ResponseEntity<Dependente> updateDependente(@Valid @RequestBody Dependente dependente) throws URISyntaxException {
        log.debug("REST request to update Dependente : {}", dependente);
        if (dependente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dependente result = dependenteRepository.save(dependente);
        dependenteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dependente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dependentes : get all the dependentes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dependentes in body
     */
    @GetMapping("/dependentes")
    @Timed
    public List<Dependente> getAllDependentes() {
        log.debug("REST request to get all Dependentes");
        return dependenteRepository.findAll();
    }

    /**
     * GET  /dependentes/:id : get the "id" dependente.
     *
     * @param id the id of the dependente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dependente, or with status 404 (Not Found)
     */
    @GetMapping("/dependentes/{id}")
    @Timed
    public ResponseEntity<Dependente> getDependente(@PathVariable Long id) {
        log.debug("REST request to get Dependente : {}", id);
        Optional<Dependente> dependente = dependenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dependente);
    }

    /**
     * DELETE  /dependentes/:id : delete the "id" dependente.
     *
     * @param id the id of the dependente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dependentes/{id}")
    @Timed
    public ResponseEntity<Void> deleteDependente(@PathVariable Long id) {
        log.debug("REST request to delete Dependente : {}", id);

        dependenteRepository.deleteById(id);
        dependenteSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dependentes?query=:query : search for the dependente corresponding
     * to the query.
     *
     * @param query the query of the dependente search
     * @return the result of the search
     */
    @GetMapping("/_search/dependentes")
    @Timed
    public List<Dependente> searchDependentes(@RequestParam String query) {
        log.debug("REST request to search Dependentes for query {}", query);
        return StreamSupport
            .stream(dependenteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
