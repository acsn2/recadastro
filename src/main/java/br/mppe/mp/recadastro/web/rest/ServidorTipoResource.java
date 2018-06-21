package br.mppe.mp.recadastro.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.mppe.mp.recadastro.domain.ServidorTipo;
import br.mppe.mp.recadastro.repository.ServidorTipoRepository;
import br.mppe.mp.recadastro.repository.search.ServidorTipoSearchRepository;
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
 * REST controller for managing ServidorTipo.
 */
@RestController
@RequestMapping("/api")
public class ServidorTipoResource {

    private final Logger log = LoggerFactory.getLogger(ServidorTipoResource.class);

    private static final String ENTITY_NAME = "servidorTipo";

    private final ServidorTipoRepository servidorTipoRepository;

    private final ServidorTipoSearchRepository servidorTipoSearchRepository;

    public ServidorTipoResource(ServidorTipoRepository servidorTipoRepository, ServidorTipoSearchRepository servidorTipoSearchRepository) {
        this.servidorTipoRepository = servidorTipoRepository;
        this.servidorTipoSearchRepository = servidorTipoSearchRepository;
    }

    /**
     * POST  /servidor-tipos : Create a new servidorTipo.
     *
     * @param servidorTipo the servidorTipo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new servidorTipo, or with status 400 (Bad Request) if the servidorTipo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/servidor-tipos")
    @Timed
    public ResponseEntity<ServidorTipo> createServidorTipo(@Valid @RequestBody ServidorTipo servidorTipo) throws URISyntaxException {
        log.debug("REST request to save ServidorTipo : {}", servidorTipo);
        if (servidorTipo.getId() != null) {
            throw new BadRequestAlertException("A new servidorTipo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServidorTipo result = servidorTipoRepository.save(servidorTipo);
        servidorTipoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/servidor-tipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servidor-tipos : Updates an existing servidorTipo.
     *
     * @param servidorTipo the servidorTipo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated servidorTipo,
     * or with status 400 (Bad Request) if the servidorTipo is not valid,
     * or with status 500 (Internal Server Error) if the servidorTipo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/servidor-tipos")
    @Timed
    public ResponseEntity<ServidorTipo> updateServidorTipo(@Valid @RequestBody ServidorTipo servidorTipo) throws URISyntaxException {
        log.debug("REST request to update ServidorTipo : {}", servidorTipo);
        if (servidorTipo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServidorTipo result = servidorTipoRepository.save(servidorTipo);
        servidorTipoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, servidorTipo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servidor-tipos : get all the servidorTipos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of servidorTipos in body
     */
    @GetMapping("/servidor-tipos")
    @Timed
    public List<ServidorTipo> getAllServidorTipos() {
        log.debug("REST request to get all ServidorTipos");
        return servidorTipoRepository.findAll();
    }

    /**
     * GET  /servidor-tipos/:id : get the "id" servidorTipo.
     *
     * @param id the id of the servidorTipo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the servidorTipo, or with status 404 (Not Found)
     */
    @GetMapping("/servidor-tipos/{id}")
    @Timed
    public ResponseEntity<ServidorTipo> getServidorTipo(@PathVariable Long id) {
        log.debug("REST request to get ServidorTipo : {}", id);
        Optional<ServidorTipo> servidorTipo = servidorTipoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(servidorTipo);
    }

    /**
     * DELETE  /servidor-tipos/:id : delete the "id" servidorTipo.
     *
     * @param id the id of the servidorTipo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/servidor-tipos/{id}")
    @Timed
    public ResponseEntity<Void> deleteServidorTipo(@PathVariable Long id) {
        log.debug("REST request to delete ServidorTipo : {}", id);

        servidorTipoRepository.deleteById(id);
        servidorTipoSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/servidor-tipos?query=:query : search for the servidorTipo corresponding
     * to the query.
     *
     * @param query the query of the servidorTipo search
     * @return the result of the search
     */
    @GetMapping("/_search/servidor-tipos")
    @Timed
    public List<ServidorTipo> searchServidorTipos(@RequestParam String query) {
        log.debug("REST request to search ServidorTipos for query {}", query);
        return StreamSupport
            .stream(servidorTipoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
