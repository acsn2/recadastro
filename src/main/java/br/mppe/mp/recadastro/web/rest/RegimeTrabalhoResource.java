package br.mppe.mp.recadastro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mppe.mp.recadastro.domain.RegimeTrabalho;
import br.mppe.mp.recadastro.repository.RegimeTrabalhoRepository;
import br.mppe.mp.recadastro.repository.search.RegimeTrabalhoSearchRepository;
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
 * REST controller for managing RegimeTrabalho.
 */
@RestController
@RequestMapping("/api")
public class RegimeTrabalhoResource {

    private final Logger log = LoggerFactory.getLogger(RegimeTrabalhoResource.class);

    private static final String ENTITY_NAME = "regimeTrabalho";

    private final RegimeTrabalhoRepository regimeTrabalhoRepository;

    private final RegimeTrabalhoSearchRepository regimeTrabalhoSearchRepository;

    public RegimeTrabalhoResource(RegimeTrabalhoRepository regimeTrabalhoRepository, RegimeTrabalhoSearchRepository regimeTrabalhoSearchRepository) {
        this.regimeTrabalhoRepository = regimeTrabalhoRepository;
        this.regimeTrabalhoSearchRepository = regimeTrabalhoSearchRepository;
    }

    /**
     * POST  /regime-trabalhos : Create a new regimeTrabalho.
     *
     * @param regimeTrabalho the regimeTrabalho to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regimeTrabalho, or with status 400 (Bad Request) if the regimeTrabalho has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regime-trabalhos")
    @Timed
    public ResponseEntity<RegimeTrabalho> createRegimeTrabalho(@Valid @RequestBody RegimeTrabalho regimeTrabalho) throws URISyntaxException {
        log.debug("REST request to save RegimeTrabalho : {}", regimeTrabalho);
        if (regimeTrabalho.getId() != null) {
            throw new BadRequestAlertException("A new regimeTrabalho cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegimeTrabalho result = regimeTrabalhoRepository.save(regimeTrabalho);
        regimeTrabalhoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/regime-trabalhos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regime-trabalhos : Updates an existing regimeTrabalho.
     *
     * @param regimeTrabalho the regimeTrabalho to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regimeTrabalho,
     * or with status 400 (Bad Request) if the regimeTrabalho is not valid,
     * or with status 500 (Internal Server Error) if the regimeTrabalho couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regime-trabalhos")
    @Timed
    public ResponseEntity<RegimeTrabalho> updateRegimeTrabalho(@Valid @RequestBody RegimeTrabalho regimeTrabalho) throws URISyntaxException {
        log.debug("REST request to update RegimeTrabalho : {}", regimeTrabalho);
        if (regimeTrabalho.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegimeTrabalho result = regimeTrabalhoRepository.save(regimeTrabalho);
        regimeTrabalhoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, regimeTrabalho.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regime-trabalhos : get all the regimeTrabalhos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of regimeTrabalhos in body
     */
    @GetMapping("/regime-trabalhos")
    @Timed
    public List<RegimeTrabalho> getAllRegimeTrabalhos() {
        log.debug("REST request to get all RegimeTrabalhos");
        return regimeTrabalhoRepository.findAll();
    }

    /**
     * GET  /regime-trabalhos/:id : get the "id" regimeTrabalho.
     *
     * @param id the id of the regimeTrabalho to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regimeTrabalho, or with status 404 (Not Found)
     */
    @GetMapping("/regime-trabalhos/{id}")
    @Timed
    public ResponseEntity<RegimeTrabalho> getRegimeTrabalho(@PathVariable Long id) {
        log.debug("REST request to get RegimeTrabalho : {}", id);
        Optional<RegimeTrabalho> regimeTrabalho = regimeTrabalhoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(regimeTrabalho);
    }

    /**
     * DELETE  /regime-trabalhos/:id : delete the "id" regimeTrabalho.
     *
     * @param id the id of the regimeTrabalho to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regime-trabalhos/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegimeTrabalho(@PathVariable Long id) {
        log.debug("REST request to delete RegimeTrabalho : {}", id);

        regimeTrabalhoRepository.deleteById(id);
        regimeTrabalhoSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/regime-trabalhos?query=:query : search for the regimeTrabalho corresponding
     * to the query.
     *
     * @param query the query of the regimeTrabalho search
     * @return the result of the search
     */
    @GetMapping("/_search/regime-trabalhos")
    @Timed
    public List<RegimeTrabalho> searchRegimeTrabalhos(@RequestParam String query) {
        log.debug("REST request to search RegimeTrabalhos for query {}", query);
        return StreamSupport
            .stream(regimeTrabalhoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
