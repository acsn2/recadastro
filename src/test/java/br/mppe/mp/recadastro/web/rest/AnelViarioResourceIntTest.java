package br.mppe.mp.recadastro.web.rest;

import br.mppe.mp.recadastro.RecadastroApp;

import br.mppe.mp.recadastro.domain.AnelViario;
import br.mppe.mp.recadastro.repository.AnelViarioRepository;
import br.mppe.mp.recadastro.repository.search.AnelViarioSearchRepository;
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
 * Test class for the AnelViarioResource REST controller.
 *
 * @see AnelViarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecadastroApp.class)
public class AnelViarioResourceIntTest {

    private static final Integer DEFAULT_COD_ANEL = 1;
    private static final Integer UPDATED_COD_ANEL = 2;

    private static final String DEFAULT_DESC_ANEL = "AAAAAAAAAA";
    private static final String UPDATED_DESC_ANEL = "BBBBBBBBBB";

    @Autowired
    private AnelViarioRepository anelViarioRepository;


    /**
     * This repository is mocked in the br.mppe.mp.recadastro.repository.search test package.
     *
     * @see br.mppe.mp.recadastro.repository.search.AnelViarioSearchRepositoryMockConfiguration
     */
    @Autowired
    private AnelViarioSearchRepository mockAnelViarioSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnelViarioMockMvc;

    private AnelViario anelViario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnelViarioResource anelViarioResource = new AnelViarioResource(anelViarioRepository, mockAnelViarioSearchRepository);
        this.restAnelViarioMockMvc = MockMvcBuilders.standaloneSetup(anelViarioResource)
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
    public static AnelViario createEntity(EntityManager em) {
        AnelViario anelViario = new AnelViario()
            .codAnel(DEFAULT_COD_ANEL)
            .descAnel(DEFAULT_DESC_ANEL);
        return anelViario;
    }

    @Before
    public void initTest() {
        anelViario = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnelViario() throws Exception {
        int databaseSizeBeforeCreate = anelViarioRepository.findAll().size();

        // Create the AnelViario
        restAnelViarioMockMvc.perform(post("/api/anel-viarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anelViario)))
            .andExpect(status().isCreated());

        // Validate the AnelViario in the database
        List<AnelViario> anelViarioList = anelViarioRepository.findAll();
        assertThat(anelViarioList).hasSize(databaseSizeBeforeCreate + 1);
        AnelViario testAnelViario = anelViarioList.get(anelViarioList.size() - 1);
        assertThat(testAnelViario.getCodAnel()).isEqualTo(DEFAULT_COD_ANEL);
        assertThat(testAnelViario.getDescAnel()).isEqualTo(DEFAULT_DESC_ANEL);

        // Validate the AnelViario in Elasticsearch
        verify(mockAnelViarioSearchRepository, times(1)).save(testAnelViario);
    }

    @Test
    @Transactional
    public void createAnelViarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anelViarioRepository.findAll().size();

        // Create the AnelViario with an existing ID
        anelViario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnelViarioMockMvc.perform(post("/api/anel-viarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anelViario)))
            .andExpect(status().isBadRequest());

        // Validate the AnelViario in the database
        List<AnelViario> anelViarioList = anelViarioRepository.findAll();
        assertThat(anelViarioList).hasSize(databaseSizeBeforeCreate);

        // Validate the AnelViario in Elasticsearch
        verify(mockAnelViarioSearchRepository, times(0)).save(anelViario);
    }

    @Test
    @Transactional
    public void checkCodAnelIsRequired() throws Exception {
        int databaseSizeBeforeTest = anelViarioRepository.findAll().size();
        // set the field null
        anelViario.setCodAnel(null);

        // Create the AnelViario, which fails.

        restAnelViarioMockMvc.perform(post("/api/anel-viarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anelViario)))
            .andExpect(status().isBadRequest());

        List<AnelViario> anelViarioList = anelViarioRepository.findAll();
        assertThat(anelViarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnelViarios() throws Exception {
        // Initialize the database
        anelViarioRepository.saveAndFlush(anelViario);

        // Get all the anelViarioList
        restAnelViarioMockMvc.perform(get("/api/anel-viarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anelViario.getId().intValue())))
            .andExpect(jsonPath("$.[*].codAnel").value(hasItem(DEFAULT_COD_ANEL)))
            .andExpect(jsonPath("$.[*].descAnel").value(hasItem(DEFAULT_DESC_ANEL.toString())));
    }
    

    @Test
    @Transactional
    public void getAnelViario() throws Exception {
        // Initialize the database
        anelViarioRepository.saveAndFlush(anelViario);

        // Get the anelViario
        restAnelViarioMockMvc.perform(get("/api/anel-viarios/{id}", anelViario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anelViario.getId().intValue()))
            .andExpect(jsonPath("$.codAnel").value(DEFAULT_COD_ANEL))
            .andExpect(jsonPath("$.descAnel").value(DEFAULT_DESC_ANEL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAnelViario() throws Exception {
        // Get the anelViario
        restAnelViarioMockMvc.perform(get("/api/anel-viarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnelViario() throws Exception {
        // Initialize the database
        anelViarioRepository.saveAndFlush(anelViario);

        int databaseSizeBeforeUpdate = anelViarioRepository.findAll().size();

        // Update the anelViario
        AnelViario updatedAnelViario = anelViarioRepository.findById(anelViario.getId()).get();
        // Disconnect from session so that the updates on updatedAnelViario are not directly saved in db
        em.detach(updatedAnelViario);
        updatedAnelViario
            .codAnel(UPDATED_COD_ANEL)
            .descAnel(UPDATED_DESC_ANEL);

        restAnelViarioMockMvc.perform(put("/api/anel-viarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnelViario)))
            .andExpect(status().isOk());

        // Validate the AnelViario in the database
        List<AnelViario> anelViarioList = anelViarioRepository.findAll();
        assertThat(anelViarioList).hasSize(databaseSizeBeforeUpdate);
        AnelViario testAnelViario = anelViarioList.get(anelViarioList.size() - 1);
        assertThat(testAnelViario.getCodAnel()).isEqualTo(UPDATED_COD_ANEL);
        assertThat(testAnelViario.getDescAnel()).isEqualTo(UPDATED_DESC_ANEL);

        // Validate the AnelViario in Elasticsearch
        verify(mockAnelViarioSearchRepository, times(1)).save(testAnelViario);
    }

    @Test
    @Transactional
    public void updateNonExistingAnelViario() throws Exception {
        int databaseSizeBeforeUpdate = anelViarioRepository.findAll().size();

        // Create the AnelViario

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnelViarioMockMvc.perform(put("/api/anel-viarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anelViario)))
            .andExpect(status().isBadRequest());

        // Validate the AnelViario in the database
        List<AnelViario> anelViarioList = anelViarioRepository.findAll();
        assertThat(anelViarioList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AnelViario in Elasticsearch
        verify(mockAnelViarioSearchRepository, times(0)).save(anelViario);
    }

    @Test
    @Transactional
    public void deleteAnelViario() throws Exception {
        // Initialize the database
        anelViarioRepository.saveAndFlush(anelViario);

        int databaseSizeBeforeDelete = anelViarioRepository.findAll().size();

        // Get the anelViario
        restAnelViarioMockMvc.perform(delete("/api/anel-viarios/{id}", anelViario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AnelViario> anelViarioList = anelViarioRepository.findAll();
        assertThat(anelViarioList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AnelViario in Elasticsearch
        verify(mockAnelViarioSearchRepository, times(1)).deleteById(anelViario.getId());
    }

    @Test
    @Transactional
    public void searchAnelViario() throws Exception {
        // Initialize the database
        anelViarioRepository.saveAndFlush(anelViario);
        when(mockAnelViarioSearchRepository.search(queryStringQuery("id:" + anelViario.getId())))
            .thenReturn(Collections.singletonList(anelViario));
        // Search the anelViario
        restAnelViarioMockMvc.perform(get("/api/_search/anel-viarios?query=id:" + anelViario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anelViario.getId().intValue())))
            .andExpect(jsonPath("$.[*].codAnel").value(hasItem(DEFAULT_COD_ANEL)))
            .andExpect(jsonPath("$.[*].descAnel").value(hasItem(DEFAULT_DESC_ANEL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnelViario.class);
        AnelViario anelViario1 = new AnelViario();
        anelViario1.setId(1L);
        AnelViario anelViario2 = new AnelViario();
        anelViario2.setId(anelViario1.getId());
        assertThat(anelViario1).isEqualTo(anelViario2);
        anelViario2.setId(2L);
        assertThat(anelViario1).isNotEqualTo(anelViario2);
        anelViario1.setId(null);
        assertThat(anelViario1).isNotEqualTo(anelViario2);
    }
}
