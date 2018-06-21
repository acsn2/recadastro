package br.mppe.mp.recadastro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mppe.mp.recadastro.domain.CategoriaESocial;
import br.mppe.mp.recadastro.repository.CategoriaESocialRepository;
import br.mppe.mp.recadastro.repository.search.CategoriaESocialSearchRepository;
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
 * REST controller for managing CategoriaESocial.
 */
@RestController
@RequestMapping("/api")
public class CategoriaESocialResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaESocialResource.class);

    private static final String ENTITY_NAME = "categoriaESocial";

    private final CategoriaESocialRepository categoriaESocialRepository;

    private final CategoriaESocialSearchRepository categoriaESocialSearchRepository;

    public CategoriaESocialResource(CategoriaESocialRepository categoriaESocialRepository, CategoriaESocialSearchRepository categoriaESocialSearchRepository) {
        this.categoriaESocialRepository = categoriaESocialRepository;
        this.categoriaESocialSearchRepository = categoriaESocialSearchRepository;
    }

    /**
     * POST  /categoria-e-socials : Create a new categoriaESocial.
     *
     * @param categoriaESocial the categoriaESocial to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoriaESocial, or with status 400 (Bad Request) if the categoriaESocial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categoria-e-socials")
    @Timed
    public ResponseEntity<CategoriaESocial> createCategoriaESocial(@Valid @RequestBody CategoriaESocial categoriaESocial) throws URISyntaxException {
        log.debug("REST request to save CategoriaESocial : {}", categoriaESocial);
        if (categoriaESocial.getId() != null) {
            throw new BadRequestAlertException("A new categoriaESocial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaESocial result = categoriaESocialRepository.save(categoriaESocial);
        categoriaESocialSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/categoria-e-socials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categoria-e-socials : Updates an existing categoriaESocial.
     *
     * @param categoriaESocial the categoriaESocial to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoriaESocial,
     * or with status 400 (Bad Request) if the categoriaESocial is not valid,
     * or with status 500 (Internal Server Error) if the categoriaESocial couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categoria-e-socials")
    @Timed
    public ResponseEntity<CategoriaESocial> updateCategoriaESocial(@Valid @RequestBody CategoriaESocial categoriaESocial) throws URISyntaxException {
        log.debug("REST request to update CategoriaESocial : {}", categoriaESocial);
        if (categoriaESocial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriaESocial result = categoriaESocialRepository.save(categoriaESocial);
        categoriaESocialSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categoriaESocial.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categoria-e-socials : get all the categoriaESocials.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categoriaESocials in body
     */
    @GetMapping("/categoria-e-socials")
    @Timed
    public List<CategoriaESocial> getAllCategoriaESocials() {
        log.debug("REST request to get all CategoriaESocials");
        return categoriaESocialRepository.findAll();
    }

    /**
     * GET  /categoria-e-socials/:id : get the "id" categoriaESocial.
     *
     * @param id the id of the categoriaESocial to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoriaESocial, or with status 404 (Not Found)
     */
    @GetMapping("/categoria-e-socials/{id}")
    @Timed
    public ResponseEntity<CategoriaESocial> getCategoriaESocial(@PathVariable Long id) {
        log.debug("REST request to get CategoriaESocial : {}", id);
        Optional<CategoriaESocial> categoriaESocial = categoriaESocialRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(categoriaESocial);
    }

    /**
     * DELETE  /categoria-e-socials/:id : delete the "id" categoriaESocial.
     *
     * @param id the id of the categoriaESocial to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categoria-e-socials/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategoriaESocial(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaESocial : {}", id);

        categoriaESocialRepository.deleteById(id);
        categoriaESocialSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/categoria-e-socials?query=:query : search for the categoriaESocial corresponding
     * to the query.
     *
     * @param query the query of the categoriaESocial search
     * @return the result of the search
     */
    @GetMapping("/_search/categoria-e-socials")
    @Timed
    public List<CategoriaESocial> searchCategoriaESocials(@RequestParam String query) {
        log.debug("REST request to search CategoriaESocials for query {}", query);
        return StreamSupport
            .stream(categoriaESocialSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
