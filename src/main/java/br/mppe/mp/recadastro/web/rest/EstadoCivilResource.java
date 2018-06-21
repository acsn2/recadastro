package br.mppe.mp.recadastro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mppe.mp.recadastro.domain.EstadoCivil;
import br.mppe.mp.recadastro.repository.EstadoCivilRepository;
import br.mppe.mp.recadastro.repository.search.EstadoCivilSearchRepository;
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
 * REST controller for managing EstadoCivil.
 */
@RestController
@RequestMapping("/api")
public class EstadoCivilResource {

    private final Logger log = LoggerFactory.getLogger(EstadoCivilResource.class);

    private static final String ENTITY_NAME = "estadoCivil";

    private final EstadoCivilRepository estadoCivilRepository;

    private final EstadoCivilSearchRepository estadoCivilSearchRepository;

    public EstadoCivilResource(EstadoCivilRepository estadoCivilRepository, EstadoCivilSearchRepository estadoCivilSearchRepository) {
        this.estadoCivilRepository = estadoCivilRepository;
        this.estadoCivilSearchRepository = estadoCivilSearchRepository;
    }

    /**
     * POST  /estado-civils : Create a new estadoCivil.
     *
     * @param estadoCivil the estadoCivil to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estadoCivil, or with status 400 (Bad Request) if the estadoCivil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estado-civils")
    @Timed
    public ResponseEntity<EstadoCivil> createEstadoCivil(@Valid @RequestBody EstadoCivil estadoCivil) throws URISyntaxException {
        log.debug("REST request to save EstadoCivil : {}", estadoCivil);
        if (estadoCivil.getId() != null) {
            throw new BadRequestAlertException("A new estadoCivil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoCivil result = estadoCivilRepository.save(estadoCivil);
        estadoCivilSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/estado-civils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estado-civils : Updates an existing estadoCivil.
     *
     * @param estadoCivil the estadoCivil to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estadoCivil,
     * or with status 400 (Bad Request) if the estadoCivil is not valid,
     * or with status 500 (Internal Server Error) if the estadoCivil couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estado-civils")
    @Timed
    public ResponseEntity<EstadoCivil> updateEstadoCivil(@Valid @RequestBody EstadoCivil estadoCivil) throws URISyntaxException {
        log.debug("REST request to update EstadoCivil : {}", estadoCivil);
        if (estadoCivil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoCivil result = estadoCivilRepository.save(estadoCivil);
        estadoCivilSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estadoCivil.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estado-civils : get all the estadoCivils.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of estadoCivils in body
     */
    @GetMapping("/estado-civils")
    @Timed
    public List<EstadoCivil> getAllEstadoCivils() {
        log.debug("REST request to get all EstadoCivils");
        return estadoCivilRepository.findAll();
    }

    /**
     * GET  /estado-civils/:id : get the "id" estadoCivil.
     *
     * @param id the id of the estadoCivil to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estadoCivil, or with status 404 (Not Found)
     */
    @GetMapping("/estado-civils/{id}")
    @Timed
    public ResponseEntity<EstadoCivil> getEstadoCivil(@PathVariable Long id) {
        log.debug("REST request to get EstadoCivil : {}", id);
        Optional<EstadoCivil> estadoCivil = estadoCivilRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estadoCivil);
    }

    /**
     * DELETE  /estado-civils/:id : delete the "id" estadoCivil.
     *
     * @param id the id of the estadoCivil to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estado-civils/{id}")
    @Timed
    public ResponseEntity<Void> deleteEstadoCivil(@PathVariable Long id) {
        log.debug("REST request to delete EstadoCivil : {}", id);

        estadoCivilRepository.deleteById(id);
        estadoCivilSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/estado-civils?query=:query : search for the estadoCivil corresponding
     * to the query.
     *
     * @param query the query of the estadoCivil search
     * @return the result of the search
     */
    @GetMapping("/_search/estado-civils")
    @Timed
    public List<EstadoCivil> searchEstadoCivils(@RequestParam String query) {
        log.debug("REST request to search EstadoCivils for query {}", query);
        return StreamSupport
            .stream(estadoCivilSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
