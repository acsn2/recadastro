package br.mppe.mp.recadastro.web.rest;

import br.mppe.mp.recadastro.RecadastroApp;

import br.mppe.mp.recadastro.domain.Cidade;
import br.mppe.mp.recadastro.repository.CidadeRepository;
import br.mppe.mp.recadastro.repository.search.CidadeSearchRepository;
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
 * Test class for the CidadeResource REST controller.
 *
 * @see CidadeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecadastroApp.class)
public class CidadeResourceIntTest {

    private static final Integer DEFAULT_COD_CIDADE = 1;
    private static final Integer UPDATED_COD_CIDADE = 2;

    private static final String DEFAULT_NOME_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CIDADE = "BBBBBBBBBB";

    @Autowired
    private CidadeRepository cidadeRepository;


    /**
     * This repository is mocked in the br.mppe.mp.recadastro.repository.search test package.
     *
     * @see br.mppe.mp.recadastro.repository.search.CidadeSearchRepositoryMockConfiguration
     */
    @Autowired
    private CidadeSearchRepository mockCidadeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCidadeMockMvc;

    private Cidade cidade;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CidadeResource cidadeResource = new CidadeResource(cidadeRepository, mockCidadeSearchRepository);
        this.restCidadeMockMvc = MockMvcBuilders.standaloneSetup(cidadeResource)
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
    public static Cidade createEntity(EntityManager em) {
        Cidade cidade = new Cidade()
            .codCidade(DEFAULT_COD_CIDADE)
            .nomeCidade(DEFAULT_NOME_CIDADE);
        return cidade;
    }

    @Before
    public void initTest() {
        cidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createCidade() throws Exception {
        int databaseSizeBeforeCreate = cidadeRepository.findAll().size();

        // Create the Cidade
        restCidadeMockMvc.perform(post("/api/cidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cidade)))
            .andExpect(status().isCreated());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Cidade testCidade = cidadeList.get(cidadeList.size() - 1);
        assertThat(testCidade.getCodCidade()).isEqualTo(DEFAULT_COD_CIDADE);
        assertThat(testCidade.getNomeCidade()).isEqualTo(DEFAULT_NOME_CIDADE);

        // Validate the Cidade in Elasticsearch
        verify(mockCidadeSearchRepository, times(1)).save(testCidade);
    }

    @Test
    @Transactional
    public void createCidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cidadeRepository.findAll().size();

        // Create the Cidade with an existing ID
        cidade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCidadeMockMvc.perform(post("/api/cidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cidade)))
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Cidade in Elasticsearch
        verify(mockCidadeSearchRepository, times(0)).save(cidade);
    }

    @Test
    @Transactional
    public void checkCodCidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cidadeRepository.findAll().size();
        // set the field null
        cidade.setCodCidade(null);

        // Create the Cidade, which fails.

        restCidadeMockMvc.perform(post("/api/cidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cidade)))
            .andExpect(status().isBadRequest());

        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCidades() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList
        restCidadeMockMvc.perform(get("/api/cidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].codCidade").value(hasItem(DEFAULT_COD_CIDADE)))
            .andExpect(jsonPath("$.[*].nomeCidade").value(hasItem(DEFAULT_NOME_CIDADE.toString())));
    }
    

    @Test
    @Transactional
    public void getCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get the cidade
        restCidadeMockMvc.perform(get("/api/cidades/{id}", cidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cidade.getId().intValue()))
            .andExpect(jsonPath("$.codCidade").value(DEFAULT_COD_CIDADE))
            .andExpect(jsonPath("$.nomeCidade").value(DEFAULT_NOME_CIDADE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCidade() throws Exception {
        // Get the cidade
        restCidadeMockMvc.perform(get("/api/cidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();

        // Update the cidade
        Cidade updatedCidade = cidadeRepository.findById(cidade.getId()).get();
        // Disconnect from session so that the updates on updatedCidade are not directly saved in db
        em.detach(updatedCidade);
        updatedCidade
            .codCidade(UPDATED_COD_CIDADE)
            .nomeCidade(UPDATED_NOME_CIDADE);

        restCidadeMockMvc.perform(put("/api/cidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCidade)))
            .andExpect(status().isOk());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
        Cidade testCidade = cidadeList.get(cidadeList.size() - 1);
        assertThat(testCidade.getCodCidade()).isEqualTo(UPDATED_COD_CIDADE);
        assertThat(testCidade.getNomeCidade()).isEqualTo(UPDATED_NOME_CIDADE);

        // Validate the Cidade in Elasticsearch
        verify(mockCidadeSearchRepository, times(1)).save(testCidade);
    }

    @Test
    @Transactional
    public void updateNonExistingCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();

        // Create the Cidade

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCidadeMockMvc.perform(put("/api/cidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cidade)))
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cidade in Elasticsearch
        verify(mockCidadeSearchRepository, times(0)).save(cidade);
    }

    @Test
    @Transactional
    public void deleteCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        int databaseSizeBeforeDelete = cidadeRepository.findAll().size();

        // Get the cidade
        restCidadeMockMvc.perform(delete("/api/cidades/{id}", cidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Cidade in Elasticsearch
        verify(mockCidadeSearchRepository, times(1)).deleteById(cidade.getId());
    }

    @Test
    @Transactional
    public void searchCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);
        when(mockCidadeSearchRepository.search(queryStringQuery("id:" + cidade.getId())))
            .thenReturn(Collections.singletonList(cidade));
        // Search the cidade
        restCidadeMockMvc.perform(get("/api/_search/cidades?query=id:" + cidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].codCidade").value(hasItem(DEFAULT_COD_CIDADE)))
            .andExpect(jsonPath("$.[*].nomeCidade").value(hasItem(DEFAULT_NOME_CIDADE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cidade.class);
        Cidade cidade1 = new Cidade();
        cidade1.setId(1L);
        Cidade cidade2 = new Cidade();
        cidade2.setId(cidade1.getId());
        assertThat(cidade1).isEqualTo(cidade2);
        cidade2.setId(2L);
        assertThat(cidade1).isNotEqualTo(cidade2);
        cidade1.setId(null);
        assertThat(cidade1).isNotEqualTo(cidade2);
    }
}
