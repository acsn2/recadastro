package br.mppe.mp.recadastro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mppe.mp.recadastro.domain.ParenteServidor;
import br.mppe.mp.recadastro.repository.ParenteServidorRepository;
import br.mppe.mp.recadastro.repository.search.ParenteServidorSearchRepository;
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
 * REST controller for managing ParenteServidor.
 */
@RestController
@RequestMapping("/api")
public class ParenteServidorResource {

    private final Logger log = LoggerFactory.getLogger(ParenteServidorResource.class);

    private static final String ENTITY_NAME = "parenteServidor";

    private final ParenteServidorRepository parenteServidorRepository;

    private final ParenteServidorSearchRepository parenteServidorSearchRepository;

    public ParenteServidorResource(ParenteServidorRepository parenteServidorRepository, ParenteServidorSearchRepository parenteServidorSearchRepository) {
        this.parenteServidorRepository = parenteServidorRepository;
        this.parenteServidorSearchRepository = parenteServidorSearchRepository;
    }

    /**
     * POST  /parente-servidors : Create a new parenteServidor.
     *
     * @param parenteServidor the parenteServidor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parenteServidor, or with status 400 (Bad Request) if the parenteServidor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parente-servidors")
    @Timed
    public ResponseEntity<ParenteServidor> createParenteServidor(@Valid @RequestBody ParenteServidor parenteServidor) throws URISyntaxException {
        log.debug("REST request to save ParenteServidor : {}", parenteServidor);
        if (parenteServidor.getId() != null) {
            throw new BadRequestAlertException("A new parenteServidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParenteServidor result = parenteServidorRepository.save(parenteServidor);
        parenteServidorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/parente-servidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parente-servidors : Updates an existing parenteServidor.
     *
     * @param parenteServidor the parenteServidor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parenteServidor,
     * or with status 400 (Bad Request) if the parenteServidor is not valid,
     * or with status 500 (Internal Server Error) if the parenteServidor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parente-servidors")
    @Timed
    public ResponseEntity<ParenteServidor> updateParenteServidor(@Valid @RequestBody ParenteServidor parenteServidor) throws URISyntaxException {
        log.debug("REST request to update ParenteServidor : {}", parenteServidor);
        if (parenteServidor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParenteServidor result = parenteServidorRepository.save(parenteServidor);
        parenteServidorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parenteServidor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parente-servidors : get all the parenteServidors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of parenteServidors in body
     */
    @GetMapping("/parente-servidors")
    @Timed
    public List<ParenteServidor> getAllParenteServidors() {
        log.debug("REST request to get all ParenteServidors");
        return parenteServidorRepository.findAll();
    }

    /**
     * GET  /parente-servidors/:id : get the "id" parenteServidor.
     *
     * @param id the id of the parenteServidor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parenteServidor, or with status 404 (Not Found)
     */
    @GetMapping("/parente-servidors/{id}")
    @Timed
    public ResponseEntity<ParenteServidor> getParenteServidor(@PathVariable Long id) {
        log.debug("REST request to get ParenteServidor : {}", id);
        Optional<ParenteServidor> parenteServidor = parenteServidorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parenteServidor);
    }

    /**
     * DELETE  /parente-servidors/:id : delete the "id" parenteServidor.
     *
     * @param id the id of the parenteServidor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parente-servidors/{id}")
    @Timed
    public ResponseEntity<Void> deleteParenteServidor(@PathVariable Long id) {
        log.debug("REST request to delete ParenteServidor : {}", id);

        parenteServidorRepository.deleteById(id);
        parenteServidorSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/parente-servidors?query=:query : search for the parenteServidor corresponding
     * to the query.
     *
     * @param query the query of the parenteServidor search
     * @return the result of the search
     */
    @GetMapping("/_search/parente-servidors")
    @Timed
    public List<ParenteServidor> searchParenteServidors(@RequestParam String query) {
        log.debug("REST request to search ParenteServidors for query {}", query);
        return StreamSupport
            .stream(parenteServidorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
