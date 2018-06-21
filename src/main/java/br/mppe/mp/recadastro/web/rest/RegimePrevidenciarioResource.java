package br.mppe.mp.recadastro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mppe.mp.recadastro.domain.RegimePrevidenciario;
import br.mppe.mp.recadastro.repository.RegimePrevidenciarioRepository;
import br.mppe.mp.recadastro.repository.search.RegimePrevidenciarioSearchRepository;
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
 * REST controller for managing RegimePrevidenciario.
 */
@RestController
@RequestMapping("/api")
public class RegimePrevidenciarioResource {

    private final Logger log = LoggerFactory.getLogger(RegimePrevidenciarioResource.class);

    private static final String ENTITY_NAME = "regimePrevidenciario";

    private final RegimePrevidenciarioRepository regimePrevidenciarioRepository;

    private final RegimePrevidenciarioSearchRepository regimePrevidenciarioSearchRepository;

    public RegimePrevidenciarioResource(RegimePrevidenciarioRepository regimePrevidenciarioRepository, RegimePrevidenciarioSearchRepository regimePrevidenciarioSearchRepository) {
        this.regimePrevidenciarioRepository = regimePrevidenciarioRepository;
        this.regimePrevidenciarioSearchRepository = regimePrevidenciarioSearchRepository;
    }

    /**
     * POST  /regime-previdenciarios : Create a new regimePrevidenciario.
     *
     * @param regimePrevidenciario the regimePrevidenciario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regimePrevidenciario, or with status 400 (Bad Request) if the regimePrevidenciario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regime-previdenciarios")
    @Timed
    public ResponseEntity<RegimePrevidenciario> createRegimePrevidenciario(@Valid @RequestBody RegimePrevidenciario regimePrevidenciario) throws URISyntaxException {
        log.debug("REST request to save RegimePrevidenciario : {}", regimePrevidenciario);
        if (regimePrevidenciario.getId() != null) {
            throw new BadRequestAlertException("A new regimePrevidenciario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegimePrevidenciario result = regimePrevidenciarioRepository.save(regimePrevidenciario);
        regimePrevidenciarioSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/regime-previdenciarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regime-previdenciarios : Updates an existing regimePrevidenciario.
     *
     * @param regimePrevidenciario the regimePrevidenciario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regimePrevidenciario,
     * or with status 400 (Bad Request) if the regimePrevidenciario is not valid,
     * or with status 500 (Internal Server Error) if the regimePrevidenciario couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regime-previdenciarios")
    @Timed
    public ResponseEntity<RegimePrevidenciario> updateRegimePrevidenciario(@Valid @RequestBody RegimePrevidenciario regimePrevidenciario) throws URISyntaxException {
        log.debug("REST request to update RegimePrevidenciario : {}", regimePrevidenciario);
        if (regimePrevidenciario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegimePrevidenciario result = regimePrevidenciarioRepository.save(regimePrevidenciario);
        regimePrevidenciarioSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, regimePrevidenciario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regime-previdenciarios : get all the regimePrevidenciarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of regimePrevidenciarios in body
     */
    @GetMapping("/regime-previdenciarios")
    @Timed
    public List<RegimePrevidenciario> getAllRegimePrevidenciarios() {
        log.debug("REST request to get all RegimePrevidenciarios");
        return regimePrevidenciarioRepository.findAll();
    }

    /**
     * GET  /regime-previdenciarios/:id : get the "id" regimePrevidenciario.
     *
     * @param id the id of the regimePrevidenciario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regimePrevidenciario, or with status 404 (Not Found)
     */
    @GetMapping("/regime-previdenciarios/{id}")
    @Timed
    public ResponseEntity<RegimePrevidenciario> getRegimePrevidenciario(@PathVariable Long id) {
        log.debug("REST request to get RegimePrevidenciario : {}", id);
        Optional<RegimePrevidenciario> regimePrevidenciario = regimePrevidenciarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(regimePrevidenciario);
    }

    /**
     * DELETE  /regime-previdenciarios/:id : delete the "id" regimePrevidenciario.
     *
     * @param id the id of the regimePrevidenciario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regime-previdenciarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegimePrevidenciario(@PathVariable Long id) {
        log.debug("REST request to delete RegimePrevidenciario : {}", id);

        regimePrevidenciarioRepository.deleteById(id);
        regimePrevidenciarioSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/regime-previdenciarios?query=:query : search for the regimePrevidenciario corresponding
     * to the query.
     *
     * @param query the query of the regimePrevidenciario search
     * @return the result of the search
     */
    @GetMapping("/_search/regime-previdenciarios")
    @Timed
    public List<RegimePrevidenciario> searchRegimePrevidenciarios(@RequestParam String query) {
        log.debug("REST request to search RegimePrevidenciarios for query {}", query);
        return StreamSupport
            .stream(regimePrevidenciarioSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
