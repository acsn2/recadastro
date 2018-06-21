package br.mppe.mp.recadastro.web.rest;

import br.mppe.mp.recadastro.RecadastroApp;

import br.mppe.mp.recadastro.domain.Escolaridade;
import br.mppe.mp.recadastro.repository.EscolaridadeRepository;
import br.mppe.mp.recadastro.repository.search.EscolaridadeSearchRepository;
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
 * Test class for the EscolaridadeResource REST controller.
 *
 * @see EscolaridadeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecadastroApp.class)
public class EscolaridadeResourceIntTest {

    private static final Integer DEFAULT_COD_ESCOLARIDADE = 1;
    private static final Integer UPDATED_COD_ESCOLARIDADE = 2;

    private static final String DEFAULT_DESC_ESCOLARIDADE = "AAAAAAAAAA";
    private static final String UPDATED_DESC_ESCOLARIDADE = "BBBBBBBBBB";

    @Autowired
    private EscolaridadeRepository escolaridadeRepository;


    /**
     * This repository is mocked in the br.mppe.mp.recadastro.repository.search test package.
     *
     * @see br.mppe.mp.recadastro.repository.search.EscolaridadeSearchRepositoryMockConfiguration
     */
    @Autowired
    private EscolaridadeSearchRepository mockEscolaridadeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEscolaridadeMockMvc;

    private Escolaridade escolaridade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EscolaridadeResource escolaridadeResource = new EscolaridadeResource(escolaridadeRepository, mockEscolaridadeSearchRepository);
        this.restEscolaridadeMockMvc = MockMvcBuilders.standaloneSetup(escolaridadeResource)
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
    public static Escolaridade createEntity(EntityManager em) {
        Escolaridade escolaridade = new Escolaridade()
            .codEscolaridade(DEFAULT_COD_ESCOLARIDADE)
            .descEscolaridade(DEFAULT_DESC_ESCOLARIDADE);
        return escolaridade;
    }

    @Before
    public void initTest() {
        escolaridade = createEntity(em);
    }

    @Test
    @Transactional
    public void createEscolaridade() throws Exception {
        int databaseSizeBeforeCreate = escolaridadeRepository.findAll().size();

        // Create the Escolaridade
        restEscolaridadeMockMvc.perform(post("/api/escolaridades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escolaridade)))
            .andExpect(status().isCreated());

        // Validate the Escolaridade in the database
        List<Escolaridade> escolaridadeList = escolaridadeRepository.findAll();
        assertThat(escolaridadeList).hasSize(databaseSizeBeforeCreate + 1);
        Escolaridade testEscolaridade = escolaridadeList.get(escolaridadeList.size() - 1);
        assertThat(testEscolaridade.getCodEscolaridade()).isEqualTo(DEFAULT_COD_ESCOLARIDADE);
        assertThat(testEscolaridade.getDescEscolaridade()).isEqualTo(DEFAULT_DESC_ESCOLARIDADE);

        // Validate the Escolaridade in Elasticsearch
        verify(mockEscolaridadeSearchRepository, times(1)).save(testEscolaridade);
    }

    @Test
    @Transactional
    public void createEscolaridadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = escolaridadeRepository.findAll().size();

        // Create the Escolaridade with an existing ID
        escolaridade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEscolaridadeMockMvc.perform(post("/api/escolaridades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escolaridade)))
            .andExpect(status().isBadRequest());

        // Validate the Escolaridade in the database
        List<Escolaridade> escolaridadeList = escolaridadeRepository.findAll();
        assertThat(escolaridadeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Escolaridade in Elasticsearch
        verify(mockEscolaridadeSearchRepository, times(0)).save(escolaridade);
    }

    @Test
    @Transactional
    public void checkCodEscolaridadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = escolaridadeRepository.findAll().size();
        // set the field null
        escolaridade.setCodEscolaridade(null);

        // Create the Escolaridade, which fails.

        restEscolaridadeMockMvc.perform(post("/api/escolaridades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escolaridade)))
            .andExpect(status().isBadRequest());

        List<Escolaridade> escolaridadeList = escolaridadeRepository.findAll();
        assertThat(escolaridadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEscolaridades() throws Exception {
        // Initialize the database
        escolaridadeRepository.saveAndFlush(escolaridade);

        // Get all the escolaridadeList
        restEscolaridadeMockMvc.perform(get("/api/escolaridades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escolaridade.getId().intValue())))
            .andExpect(jsonPath("$.[*].codEscolaridade").value(hasItem(DEFAULT_COD_ESCOLARIDADE)))
            .andExpect(jsonPath("$.[*].descEscolaridade").value(hasItem(DEFAULT_DESC_ESCOLARIDADE.toString())));
    }
    

    @Test
    @Transactional
    public void getEscolaridade() throws Exception {
        // Initialize the database
        escolaridadeRepository.saveAndFlush(escolaridade);

        // Get the escolaridade
        restEscolaridadeMockMvc.perform(get("/api/escolaridades/{id}", escolaridade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(escolaridade.getId().intValue()))
            .andExpect(jsonPath("$.codEscolaridade").value(DEFAULT_COD_ESCOLARIDADE))
            .andExpect(jsonPath("$.descEscolaridade").value(DEFAULT_DESC_ESCOLARIDADE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEscolaridade() throws Exception {
        // Get the escolaridade
        restEscolaridadeMockMvc.perform(get("/api/escolaridades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEscolaridade() throws Exception {
        // Initialize the database
        escolaridadeRepository.saveAndFlush(escolaridade);

        int databaseSizeBeforeUpdate = escolaridadeRepository.findAll().size();

        // Update the escolaridade
        Escolaridade updatedEscolaridade = escolaridadeRepository.findById(escolaridade.getId()).get();
        // Disconnect from session so that the updates on updatedEscolaridade are not directly saved in db
        em.detach(updatedEscolaridade);
        updatedEscolaridade
            .codEscolaridade(UPDATED_COD_ESCOLARIDADE)
            .descEscolaridade(UPDATED_DESC_ESCOLARIDADE);

        restEscolaridadeMockMvc.perform(put("/api/escolaridades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEscolaridade)))
            .andExpect(status().isOk());

        // Validate the Escolaridade in the database
        List<Escolaridade> escolaridadeList = escolaridadeRepository.findAll();
        assertThat(escolaridadeList).hasSize(databaseSizeBeforeUpdate);
        Escolaridade testEscolaridade = escolaridadeList.get(escolaridadeList.size() - 1);
        assertThat(testEscolaridade.getCodEscolaridade()).isEqualTo(UPDATED_COD_ESCOLARIDADE);
        assertThat(testEscolaridade.getDescEscolaridade()).isEqualTo(UPDATED_DESC_ESCOLARIDADE);

        // Validate the Escolaridade in Elasticsearch
        verify(mockEscolaridadeSearchRepository, times(1)).save(testEscolaridade);
    }

    @Test
    @Transactional
    public void updateNonExistingEscolaridade() throws Exception {
        int databaseSizeBeforeUpdate = escolaridadeRepository.findAll().size();

        // Create the Escolaridade

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEscolaridadeMockMvc.perform(put("/api/escolaridades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escolaridade)))
            .andExpect(status().isBadRequest());

        // Validate the Escolaridade in the database
        List<Escolaridade> escolaridadeList = escolaridadeRepository.findAll();
        assertThat(escolaridadeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Escolaridade in Elasticsearch
        verify(mockEscolaridadeSearchRepository, times(0)).save(escolaridade);
    }

    @Test
    @Transactional
    public void deleteEscolaridade() throws Exception {
        // Initialize the database
        escolaridadeRepository.saveAndFlush(escolaridade);

        int databaseSizeBeforeDelete = escolaridadeRepository.findAll().size();

        // Get the escolaridade
        restEscolaridadeMockMvc.perform(delete("/api/escolaridades/{id}", escolaridade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Escolaridade> escolaridadeList = escolaridadeRepository.findAll();
        assertThat(escolaridadeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Escolaridade in Elasticsearch
        verify(mockEscolaridadeSearchRepository, times(1)).deleteById(escolaridade.getId());
    }

    @Test
    @Transactional
    public void searchEscolaridade() throws Exception {
        // Initialize the database
        escolaridadeRepository.saveAndFlush(escolaridade);
        when(mockEscolaridadeSearchRepository.search(queryStringQuery("id:" + escolaridade.getId())))
            .thenReturn(Collections.singletonList(escolaridade));
        // Search the escolaridade
        restEscolaridadeMockMvc.perform(get("/api/_search/escolaridades?query=id:" + escolaridade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escolaridade.getId().intValue())))
            .andExpect(jsonPath("$.[*].codEscolaridade").value(hasItem(DEFAULT_COD_ESCOLARIDADE)))
            .andExpect(jsonPath("$.[*].descEscolaridade").value(hasItem(DEFAULT_DESC_ESCOLARIDADE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Escolaridade.class);
        Escolaridade escolaridade1 = new Escolaridade();
        escolaridade1.setId(1L);
        Escolaridade escolaridade2 = new Escolaridade();
        escolaridade2.setId(escolaridade1.getId());
        assertThat(escolaridade1).isEqualTo(escolaridade2);
        escolaridade2.setId(2L);
        assertThat(escolaridade1).isNotEqualTo(escolaridade2);
        escolaridade1.setId(null);
        assertThat(escolaridade1).isNotEqualTo(escolaridade2);
    }
}
