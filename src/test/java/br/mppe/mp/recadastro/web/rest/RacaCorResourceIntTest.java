package br.mppe.mp.recadastro.web.rest;

import br.mppe.mp.recadastro.RecadastroApp;

import br.mppe.mp.recadastro.domain.RacaCor;
import br.mppe.mp.recadastro.repository.RacaCorRepository;
import br.mppe.mp.recadastro.repository.search.RacaCorSearchRepository;
import br.mppe.mp.recadastro.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static br.mppe.mp.recadastro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RacaCorResource REST controller.
 *
 * @see RacaCorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecadastroApp.class)
public class RacaCorResourceIntTest {

    private static final Integer DEFAULT_COD_RACA_COR = 1;
    private static final Integer UPDATED_COD_RACA_COR = 2;

    private static final String DEFAULT_DESC_RACA_COR = "AAAAAAAAAA";
    private static final String UPDATED_DESC_RACA_COR = "BBBBBBBBBB";

    @Autowired
    private RacaCorRepository racaCorRepository;


    /**
     * This repository is mocked in the br.mppe.mp.recadastro.repository.search test package.
     *
     * @see br.mppe.mp.recadastro.repository.search.RacaCorSearchRepositoryMockConfiguration
     */
    @Autowired
    private RacaCorSearchRepository mockRacaCorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRacaCorMockMvc;

    private RacaCor racaCor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RacaCorResource racaCorResource = new RacaCorResource(racaCorRepository, mockRacaCorSearchRepository);
        this.restRacaCorMockMvc = MockMvcBuilders.standaloneSetup(racaCorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RacaCor createEntity(EntityManager em) {
        RacaCor racaCor = new RacaCor()
            .codRacaCor(DEFAULT_COD_RACA_COR)
            .descRacaCor(DEFAULT_DESC_RACA_COR);
        return racaCor;
    }

    @Before
    public void initTest() {
        racaCor = createEntity(em);
    }

    @Test
    @Transactional
    public void createRacaCor() throws Exception {
        int databaseSizeBeforeCreate = racaCorRepository.findAll().size();

        // Create the RacaCor
        restRacaCorMockMvc.perform(post("/api/raca-cors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racaCor)))
            .andExpect(status().isCreated());

        // Validate the RacaCor in the database
        List<RacaCor> racaCorList = racaCorRepository.findAll();
        assertThat(racaCorList).hasSize(databaseSizeBeforeCreate + 1);
        RacaCor testRacaCor = racaCorList.get(racaCorList.size() - 1);
        assertThat(testRacaCor.getCodRacaCor()).isEqualTo(DEFAULT_COD_RACA_COR);
        assertThat(testRacaCor.getDescRacaCor()).isEqualTo(DEFAULT_DESC_RACA_COR);

        // Validate the RacaCor in Elasticsearch
        verify(mockRacaCorSearchRepository, times(1)).save(testRacaCor);
    }

    @Test
    @Transactional
    public void createRacaCorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = racaCorRepository.findAll().size();

        // Create the RacaCor with an existing ID
        racaCor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRacaCorMockMvc.perform(post("/api/raca-cors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racaCor)))
            .andExpect(status().isBadRequest());

        // Validate the RacaCor in the database
        List<RacaCor> racaCorList = racaCorRepository.findAll();
        assertThat(racaCorList).hasSize(databaseSizeBeforeCreate);

        // Validate the RacaCor in Elasticsearch
        verify(mockRacaCorSearchRepository, times(0)).save(racaCor);
    }

    @Test
    @Transactional
    public void checkCodRacaCorIsRequired() throws Exception {
        int databaseSizeBeforeTest = racaCorRepository.findAll().size();
        // set the field null
        racaCor.setCodRacaCor(null);

        // Create the RacaCor, which fails.

        restRacaCorMockMvc.perform(post("/api/raca-cors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racaCor)))
            .andExpect(status().isBadRequest());

        List<RacaCor> racaCorList = racaCorRepository.findAll();
        assertThat(racaCorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRacaCors() throws Exception {
        // Initialize the database
        racaCorRepository.saveAndFlush(racaCor);

        // Get all the racaCorList
        restRacaCorMockMvc.perform(get("/api/raca-cors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(racaCor.getId().intValue())))
            .andExpect(jsonPath("$.[*].codRacaCor").value(hasItem(DEFAULT_COD_RACA_COR)))
            .andExpect(jsonPath("$.[*].descRacaCor").value(hasItem(DEFAULT_DESC_RACA_COR.toString())));
    }
    

    @Test
    @Transactional
    public void getRacaCor() throws Exception {
        // Initialize the database
        racaCorRepository.saveAndFlush(racaCor);

        // Get the racaCor
        restRacaCorMockMvc.perform(get("/api/raca-cors/{id}", racaCor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(racaCor.getId().intValue()))
            .andExpect(jsonPath("$.codRacaCor").value(DEFAULT_COD_RACA_COR))
            .andExpect(jsonPath("$.descRacaCor").value(DEFAULT_DESC_RACA_COR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRacaCor() throws Exception {
        // Get the racaCor
        restRacaCorMockMvc.perform(get("/api/raca-cors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRacaCor() throws Exception {
        // Initialize the database
        racaCorRepository.saveAndFlush(racaCor);

        int databaseSizeBeforeUpdate = racaCorRepository.findAll().size();

        // Update the racaCor
        RacaCor updatedRacaCor = racaCorRepository.findById(racaCor.getId()).get();
        // Disconnect from session so that the updates on updatedRacaCor are not directly saved in db
        em.detach(updatedRacaCor);
        updatedRacaCor
            .codRacaCor(UPDATED_COD_RACA_COR)
            .descRacaCor(UPDATED_DESC_RACA_COR);

        restRacaCorMockMvc.perform(put("/api/raca-cors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRacaCor)))
            .andExpect(status().isOk());

        // Validate the RacaCor in the database
        List<RacaCor> racaCorList = racaCorRepository.findAll();
        assertThat(racaCorList).hasSize(databaseSizeBeforeUpdate);
        RacaCor testRacaCor = racaCorList.get(racaCorList.size() - 1);
        assertThat(testRacaCor.getCodRacaCor()).isEqualTo(UPDATED_COD_RACA_COR);
        assertThat(testRacaCor.getDescRacaCor()).isEqualTo(UPDATED_DESC_RACA_COR);

        // Validate the RacaCor in Elasticsearch
        verify(mockRacaCorSearchRepository, times(1)).save(testRacaCor);
    }

    @Test
    @Transactional
    public void updateNonExistingRacaCor() throws Exception {
        int databaseSizeBeforeUpdate = racaCorRepository.findAll().size();

        // Create the RacaCor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRacaCorMockMvc.perform(put("/api/raca-cors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racaCor)))
            .andExpect(status().isBadRequest());

        // Validate the RacaCor in the database
        List<RacaCor> racaCorList = racaCorRepository.findAll();
        assertThat(racaCorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RacaCor in Elasticsearch
        verify(mockRacaCorSearchRepository, times(0)).save(racaCor);
    }

    @Test
    @Transactional
    public void deleteRacaCor() throws Exception {
        // Initialize the database
        racaCorRepository.saveAndFlush(racaCor);

        int databaseSizeBeforeDelete = racaCorRepository.findAll().size();

        // Get the racaCor
        restRacaCorMockMvc.perform(delete("/api/raca-cors/{id}", racaCor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RacaCor> racaCorList = racaCorRepository.findAll();
        assertThat(racaCorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RacaCor in Elasticsearch
        verify(mockRacaCorSearchRepository, times(1)).deleteById(racaCor.getId());
    }

    @Test
    @Transactional
    public void searchRacaCor() throws Exception {
        // Initialize the database
        racaCorRepository.saveAndFlush(racaCor);
        when(mockRacaCorSearchRepository.search(queryStringQuery("id:" + racaCor.getId())))
            .thenReturn(Collections.singletonList(racaCor));
        // Search the racaCor
        restRacaCorMockMvc.perform(get("/api/_search/raca-cors?query=id:" + racaCor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(racaCor.getId().intValue())))
            .andExpect(jsonPath("$.[*].codRacaCor").value(hasItem(DEFAULT_COD_RACA_COR)))
            .andExpect(jsonPath("$.[*].descRacaCor").value(hasItem(DEFAULT_DESC_RACA_COR.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RacaCor.class);
        RacaCor racaCor1 = new RacaCor();
        racaCor1.setId(1L);
        RacaCor racaCor2 = new RacaCor();
        racaCor2.setId(racaCor1.getId());
        assertThat(racaCor1).isEqualTo(racaCor2);
        racaCor2.setId(2L);
        assertThat(racaCor1).isNotEqualTo(racaCor2);
        racaCor1.setId(null);
        assertThat(racaCor1).isNotEqualTo(racaCor2);
    }
}
