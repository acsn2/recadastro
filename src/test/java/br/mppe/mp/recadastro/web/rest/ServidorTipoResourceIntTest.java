package br.mppe.mp.recadastro.web.rest;

import br.mppe.mp.recadastro.RecadastroApp;

import br.mppe.mp.recadastro.domain.ServidorTipo;
import br.mppe.mp.recadastro.repository.ServidorTipoRepository;
import br.mppe.mp.recadastro.repository.search.ServidorTipoSearchRepository;
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
 * Test class for the ServidorTipoResource REST controller.
 *
 * @see ServidorTipoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecadastroApp.class)
public class ServidorTipoResourceIntTest {

    private static final Long DEFAULT_NUMERO = 1L;
    private static final Long UPDATED_NUMERO = 2L;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_INDICATIVO = "AAAAAAAAAA";
    private static final String UPDATED_INDICATIVO = "BBBBBBBBBB";

    @Autowired
    private ServidorTipoRepository servidorTipoRepository;


    /**
     * This repository is mocked in the br.mppe.mp.recadastro.repository.search test package.
     *
     * @see br.mppe.mp.recadastro.repository.search.ServidorTipoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServidorTipoSearchRepository mockServidorTipoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServidorTipoMockMvc;

    private ServidorTipo servidorTipo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServidorTipoResource servidorTipoResource = new ServidorTipoResource(servidorTipoRepository, mockServidorTipoSearchRepository);
        this.restServidorTipoMockMvc = MockMvcBuilders.standaloneSetup(servidorTipoResource)
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
    public static ServidorTipo createEntity(EntityManager em) {
        ServidorTipo servidorTipo = new ServidorTipo()
            .numero(DEFAULT_NUMERO)
            .nome(DEFAULT_NOME)
            .indicativo(DEFAULT_INDICATIVO);
        return servidorTipo;
    }

    @Before
    public void initTest() {
        servidorTipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createServidorTipo() throws Exception {
        int databaseSizeBeforeCreate = servidorTipoRepository.findAll().size();

        // Create the ServidorTipo
        restServidorTipoMockMvc.perform(post("/api/servidor-tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidorTipo)))
            .andExpect(status().isCreated());

        // Validate the ServidorTipo in the database
        List<ServidorTipo> servidorTipoList = servidorTipoRepository.findAll();
        assertThat(servidorTipoList).hasSize(databaseSizeBeforeCreate + 1);
        ServidorTipo testServidorTipo = servidorTipoList.get(servidorTipoList.size() - 1);
        assertThat(testServidorTipo.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testServidorTipo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testServidorTipo.getIndicativo()).isEqualTo(DEFAULT_INDICATIVO);

        // Validate the ServidorTipo in Elasticsearch
        verify(mockServidorTipoSearchRepository, times(1)).save(testServidorTipo);
    }

    @Test
    @Transactional
    public void createServidorTipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servidorTipoRepository.findAll().size();

        // Create the ServidorTipo with an existing ID
        servidorTipo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServidorTipoMockMvc.perform(post("/api/servidor-tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidorTipo)))
            .andExpect(status().isBadRequest());

        // Validate the ServidorTipo in the database
        List<ServidorTipo> servidorTipoList = servidorTipoRepository.findAll();
        assertThat(servidorTipoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ServidorTipo in Elasticsearch
        verify(mockServidorTipoSearchRepository, times(0)).save(servidorTipo);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorTipoRepository.findAll().size();
        // set the field null
        servidorTipo.setNumero(null);

        // Create the ServidorTipo, which fails.

        restServidorTipoMockMvc.perform(post("/api/servidor-tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidorTipo)))
            .andExpect(status().isBadRequest());

        List<ServidorTipo> servidorTipoList = servidorTipoRepository.findAll();
        assertThat(servidorTipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServidorTipos() throws Exception {
        // Initialize the database
        servidorTipoRepository.saveAndFlush(servidorTipo);

        // Get all the servidorTipoList
        restServidorTipoMockMvc.perform(get("/api/servidor-tipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servidorTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].indicativo").value(hasItem(DEFAULT_INDICATIVO.toString())));
    }
    

    @Test
    @Transactional
    public void getServidorTipo() throws Exception {
        // Initialize the database
        servidorTipoRepository.saveAndFlush(servidorTipo);

        // Get the servidorTipo
        restServidorTipoMockMvc.perform(get("/api/servidor-tipos/{id}", servidorTipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(servidorTipo.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.indicativo").value(DEFAULT_INDICATIVO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingServidorTipo() throws Exception {
        // Get the servidorTipo
        restServidorTipoMockMvc.perform(get("/api/servidor-tipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServidorTipo() throws Exception {
        // Initialize the database
        servidorTipoRepository.saveAndFlush(servidorTipo);

        int databaseSizeBeforeUpdate = servidorTipoRepository.findAll().size();

        // Update the servidorTipo
        ServidorTipo updatedServidorTipo = servidorTipoRepository.findById(servidorTipo.getId()).get();
        // Disconnect from session so that the updates on updatedServidorTipo are not directly saved in db
        em.detach(updatedServidorTipo);
        updatedServidorTipo
            .numero(UPDATED_NUMERO)
            .nome(UPDATED_NOME)
            .indicativo(UPDATED_INDICATIVO);

        restServidorTipoMockMvc.perform(put("/api/servidor-tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServidorTipo)))
            .andExpect(status().isOk());

        // Validate the ServidorTipo in the database
        List<ServidorTipo> servidorTipoList = servidorTipoRepository.findAll();
        assertThat(servidorTipoList).hasSize(databaseSizeBeforeUpdate);
        ServidorTipo testServidorTipo = servidorTipoList.get(servidorTipoList.size() - 1);
        assertThat(testServidorTipo.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testServidorTipo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testServidorTipo.getIndicativo()).isEqualTo(UPDATED_INDICATIVO);

        // Validate the ServidorTipo in Elasticsearch
        verify(mockServidorTipoSearchRepository, times(1)).save(testServidorTipo);
    }

    @Test
    @Transactional
    public void updateNonExistingServidorTipo() throws Exception {
        int databaseSizeBeforeUpdate = servidorTipoRepository.findAll().size();

        // Create the ServidorTipo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServidorTipoMockMvc.perform(put("/api/servidor-tipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidorTipo)))
            .andExpect(status().isBadRequest());

        // Validate the ServidorTipo in the database
        List<ServidorTipo> servidorTipoList = servidorTipoRepository.findAll();
        assertThat(servidorTipoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ServidorTipo in Elasticsearch
        verify(mockServidorTipoSearchRepository, times(0)).save(servidorTipo);
    }

    @Test
    @Transactional
    public void deleteServidorTipo() throws Exception {
        // Initialize the database
        servidorTipoRepository.saveAndFlush(servidorTipo);

        int databaseSizeBeforeDelete = servidorTipoRepository.findAll().size();

        // Get the servidorTipo
        restServidorTipoMockMvc.perform(delete("/api/servidor-tipos/{id}", servidorTipo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ServidorTipo> servidorTipoList = servidorTipoRepository.findAll();
        assertThat(servidorTipoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ServidorTipo in Elasticsearch
        verify(mockServidorTipoSearchRepository, times(1)).deleteById(servidorTipo.getId());
    }

    @Test
    @Transactional
    public void searchServidorTipo() throws Exception {
        // Initialize the database
        servidorTipoRepository.saveAndFlush(servidorTipo);
        when(mockServidorTipoSearchRepository.search(queryStringQuery("id:" + servidorTipo.getId())))
            .thenReturn(Collections.singletonList(servidorTipo));
        // Search the servidorTipo
        restServidorTipoMockMvc.perform(get("/api/_search/servidor-tipos?query=id:" + servidorTipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servidorTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].indicativo").value(hasItem(DEFAULT_INDICATIVO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServidorTipo.class);
        ServidorTipo servidorTipo1 = new ServidorTipo();
        servidorTipo1.setId(1L);
        ServidorTipo servidorTipo2 = new ServidorTipo();
        servidorTipo2.setId(servidorTipo1.getId());
        assertThat(servidorTipo1).isEqualTo(servidorTipo2);
        servidorTipo2.setId(2L);
        assertThat(servidorTipo1).isNotEqualTo(servidorTipo2);
        servidorTipo1.setId(null);
        assertThat(servidorTipo1).isNotEqualTo(servidorTipo2);
    }
}
