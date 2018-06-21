package br.mppe.mp.recadastro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mppe.mp.recadastro.domain.Orgao;
import br.mppe.mp.recadastro.repository.OrgaoRepository;
import br.mppe.mp.recadastro.repository.search.OrgaoSearchRepository;
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
 * REST controller for managing Orgao.
 */
@RestController
@RequestMapping("/api")
public class OrgaoResource {

    private final Logger log = LoggerFactory.getLogger(OrgaoResource.class);

    private static final String ENTITY_NAME = "orgao";

    private final OrgaoRepository orgaoRepository;

    private final OrgaoSearchRepository orgaoSearchRepository;

    public OrgaoResource(OrgaoRepository orgaoRepository, OrgaoSearchRepository orgaoSearchRepository) {
        this.orgaoRepository = orgaoRepository;
        this.orgaoSearchRepository = orgaoSearchRepository;
    }

    /**
     * POST  /orgaos : Create a new orgao.
     *
     * @param orgao the orgao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orgao, or with status 400 (Bad Request) if the orgao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orgaos")
    @Timed
    public ResponseEntity<Orgao> createOrgao(@Valid @RequestBody Orgao orgao) throws URISyntaxException {
        log.debug("REST request to save Orgao : {}", orgao);
        if (orgao.getId() != null) {
            throw new BadRequestAlertException("A new orgao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Orgao result = orgaoRepository.save(orgao);
        orgaoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/orgaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orgaos : Updates an existing orgao.
     *
     * @param orgao the orgao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orgao,
     * or with status 400 (Bad Request) if the orgao is not valid,
     * or with status 500 (Internal Server Error) if the orgao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orgaos")
    @Timed
    public ResponseEntity<Orgao> updateOrgao(@Valid @RequestBody Orgao orgao) throws URISyntaxException {
        log.debug("REST request to update Orgao : {}", orgao);
        if (orgao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Orgao result = orgaoRepository.save(orgao);
        orgaoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orgao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orgaos : get all the orgaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orgaos in body
     */
    @GetMapping("/orgaos")
    @Timed
    public List<Orgao> getAllOrgaos() {
        log.debug("REST request to get all Orgaos");
        return orgaoRepository.findAll();
    }

    /**
     * GET  /orgaos/:id : get the "id" orgao.
     *
     * @param id the id of the orgao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orgao, or with status 404 (Not Found)
     */
    @GetMapping("/orgaos/{id}")
    @Timed
    public ResponseEntity<Orgao> getOrgao(@PathVariable Long id) {
        log.debug("REST request to get Orgao : {}", id);
        Optional<Orgao> orgao = orgaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orgao);
    }

    /**
     * DELETE  /orgaos/:id : delete the "id" orgao.
     *
     * @param id the id of the orgao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orgaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrgao(@PathVariable Long id) {
        log.debug("REST request to delete Orgao : {}", id);

        orgaoRepository.deleteById(id);
        orgaoSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/orgaos?query=:query : search for the orgao corresponding
     * to the query.
     *
     * @param query the query of the orgao search
     * @return the result of the search
     */
    @GetMapping("/_search/orgaos")
    @Timed
    public List<Orgao> searchOrgaos(@RequestParam String query) {
        log.debug("REST request to search Orgaos for query {}", query);
        return StreamSupport
            .stream(orgaoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
