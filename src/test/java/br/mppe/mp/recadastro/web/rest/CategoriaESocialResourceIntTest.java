package br.mppe.mp.recadastro.web.rest;

import br.mppe.mp.recadastro.RecadastroApp;

import br.mppe.mp.recadastro.domain.CategoriaESocial;
import br.mppe.mp.recadastro.repository.CategoriaESocialRepository;
import br.mppe.mp.recadastro.repository.search.CategoriaESocialSearchRepository;
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
 * Test class for the CategoriaESocialResource REST controller.
 *
 * @see CategoriaESocialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecadastroApp.class)
public class CategoriaESocialResourceIntTest {

    private static final Integer DEFAULT_COD_CATEGORIA = 1;
    private static final Integer UPDATED_COD_CATEGORIA = 2;

    private static final String DEFAULT_DESC_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_DESC_CATEGORIA = "BBBBBBBBBB";

    private static final String DEFAULT_GRUPO_CAT = "AAAAAAAAAA";
    private static final String UPDATED_GRUPO_CAT = "BBBBBBBBBB";

    @Autowired
    private CategoriaESocialRepository categoriaESocialRepository;


    /**
     * This repository is mocked in the br.mppe.mp.recadastro.repository.search test package.
     *
     * @see br.mppe.mp.recadastro.repository.search.CategoriaESocialSearchRepositoryMockConfiguration
     */
    @Autowired
    private CategoriaESocialSearchRepository mockCategoriaESocialSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCategoriaESocialMockMvc;

    private CategoriaESocial categoriaESocial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoriaESocialResource categoriaESocialResource = new CategoriaESocialResource(categoriaESocialRepository, mockCategoriaESocialSearchRepository);
        this.restCategoriaESocialMockMvc = MockMvcBuilders.standaloneSetup(categoriaESocialResource)
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
    public static CategoriaESocial createEntity(EntityManager em) {
        CategoriaESocial categoriaESocial = new CategoriaESocial()
            .codCategoria(DEFAULT_COD_CATEGORIA)
            .descCategoria(DEFAULT_DESC_CATEGORIA)
            .grupoCat(DEFAULT_GRUPO_CAT);
        return categoriaESocial;
    }

    @Before
    public void initTest() {
        categoriaESocial = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriaESocial() throws Exception {
        int databaseSizeBeforeCreate = categoriaESocialRepository.findAll().size();

        // Create the CategoriaESocial
        restCategoriaESocialMockMvc.perform(post("/api/categoria-e-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaESocial)))
            .andExpect(status().isCreated());

        // Validate the CategoriaESocial in the database
        List<CategoriaESocial> categoriaESocialList = categoriaESocialRepository.findAll();
        assertThat(categoriaESocialList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaESocial testCategoriaESocial = categoriaESocialList.get(categoriaESocialList.size() - 1);
        assertThat(testCategoriaESocial.getCodCategoria()).isEqualTo(DEFAULT_COD_CATEGORIA);
        assertThat(testCategoriaESocial.getDescCategoria()).isEqualTo(DEFAULT_DESC_CATEGORIA);
        assertThat(testCategoriaESocial.getGrupoCat()).isEqualTo(DEFAULT_GRUPO_CAT);

        // Validate the CategoriaESocial in Elasticsearch
        verify(mockCategoriaESocialSearchRepository, times(1)).save(testCategoriaESocial);
    }

    @Test
    @Transactional
    public void createCategoriaESocialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaESocialRepository.findAll().size();

        // Create the CategoriaESocial with an existing ID
        categoriaESocial.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaESocialMockMvc.perform(post("/api/categoria-e-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaESocial)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaESocial in the database
        List<CategoriaESocial> categoriaESocialList = categoriaESocialRepository.findAll();
        assertThat(categoriaESocialList).hasSize(databaseSizeBeforeCreate);

        // Validate the CategoriaESocial in Elasticsearch
        verify(mockCategoriaESocialSearchRepository, times(0)).save(categoriaESocial);
    }

    @Test
    @Transactional
    public void checkCodCategoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriaESocialRepository.findAll().size();
        // set the field null
        categoriaESocial.setCodCategoria(null);

        // Create the CategoriaESocial, which fails.

        restCategoriaESocialMockMvc.perform(post("/api/categoria-e-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaESocial)))
            .andExpect(status().isBadRequest());

        List<CategoriaESocial> categoriaESocialList = categoriaESocialRepository.findAll();
        assertThat(categoriaESocialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoriaESocials() throws Exception {
        // Initialize the database
        categoriaESocialRepository.saveAndFlush(categoriaESocial);

        // Get all the categoriaESocialList
        restCategoriaESocialMockMvc.perform(get("/api/categoria-e-socials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaESocial.getId().intValue())))
            .andExpect(jsonPath("$.[*].codCategoria").value(hasItem(DEFAULT_COD_CATEGORIA)))
            .andExpect(jsonPath("$.[*].descCategoria").value(hasItem(DEFAULT_DESC_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].grupoCat").value(hasItem(DEFAULT_GRUPO_CAT.toString())));
    }
    

    @Test
    @Transactional
    public void getCategoriaESocial() throws Exception {
        // Initialize the database
        categoriaESocialRepository.saveAndFlush(categoriaESocial);

        // Get the categoriaESocial
        restCategoriaESocialMockMvc.perform(get("/api/categoria-e-socials/{id}", categoriaESocial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaESocial.getId().intValue()))
            .andExpect(jsonPath("$.codCategoria").value(DEFAULT_COD_CATEGORIA))
            .andExpect(jsonPath("$.descCategoria").value(DEFAULT_DESC_CATEGORIA.toString()))
            .andExpect(jsonPath("$.grupoCat").value(DEFAULT_GRUPO_CAT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCategoriaESocial() throws Exception {
        // Get the categoriaESocial
        restCategoriaESocialMockMvc.perform(get("/api/categoria-e-socials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriaESocial() throws Exception {
        // Initialize the database
        categoriaESocialRepository.saveAndFlush(categoriaESocial);

        int databaseSizeBeforeUpdate = categoriaESocialRepository.findAll().size();

        // Update the categoriaESocial
        CategoriaESocial updatedCategoriaESocial = categoriaESocialRepository.findById(categoriaESocial.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaESocial are not directly saved in db
        em.detach(updatedCategoriaESocial);
        updatedCategoriaESocial
            .codCategoria(UPDATED_COD_CATEGORIA)
            .descCategoria(UPDATED_DESC_CATEGORIA)
            .grupoCat(UPDATED_GRUPO_CAT);

        restCategoriaESocialMockMvc.perform(put("/api/categoria-e-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategoriaESocial)))
            .andExpect(status().isOk());

        // Validate the CategoriaESocial in the database
        List<CategoriaESocial> categoriaESocialList = categoriaESocialRepository.findAll();
        assertThat(categoriaESocialList).hasSize(databaseSizeBeforeUpdate);
        CategoriaESocial testCategoriaESocial = categoriaESocialList.get(categoriaESocialList.size() - 1);
        assertThat(testCategoriaESocial.getCodCategoria()).isEqualTo(UPDATED_COD_CATEGORIA);
        assertThat(testCategoriaESocial.getDescCategoria()).isEqualTo(UPDATED_DESC_CATEGORIA);
        assertThat(testCategoriaESocial.getGrupoCat()).isEqualTo(UPDATED_GRUPO_CAT);

        // Validate the CategoriaESocial in Elasticsearch
        verify(mockCategoriaESocialSearchRepository, times(1)).save(testCategoriaESocial);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriaESocial() throws Exception {
        int databaseSizeBeforeUpdate = categoriaESocialRepository.findAll().size();

        // Create the CategoriaESocial

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCategoriaESocialMockMvc.perform(put("/api/categoria-e-socials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaESocial)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaESocial in the database
        List<CategoriaESocial> categoriaESocialList = categoriaESocialRepository.findAll();
        assertThat(categoriaESocialList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CategoriaESocial in Elasticsearch
        verify(mockCategoriaESocialSearchRepository, times(0)).save(categoriaESocial);
    }

    @Test
    @Transactional
    public void deleteCategoriaESocial() throws Exception {
        // Initialize the database
        categoriaESocialRepository.saveAndFlush(categoriaESocial);

        int databaseSizeBeforeDelete = categoriaESocialRepository.findAll().size();

        // Get the categoriaESocial
        restCategoriaESocialMockMvc.perform(delete("/api/categoria-e-socials/{id}", categoriaESocial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CategoriaESocial> categoriaESocialList = categoriaESocialRepository.findAll();
        assertThat(categoriaESocialList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CategoriaESocial in Elasticsearch
        verify(mockCategoriaESocialSearchRepository, times(1)).deleteById(categoriaESocial.getId());
    }

    @Test
    @Transactional
    public void searchCategoriaESocial() throws Exception {
        // Initialize the database
        categoriaESocialRepository.saveAndFlush(categoriaESocial);
        when(mockCategoriaESocialSearchRepository.search(queryStringQuery("id:" + categoriaESocial.getId())))
            .thenReturn(Collections.singletonList(categoriaESocial));
        // Search the categoriaESocial
        restCategoriaESocialMockMvc.perform(get("/api/_search/categoria-e-socials?query=id:" + categoriaESocial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaESocial.getId().intValue())))
            .andExpect(jsonPath("$.[*].codCategoria").value(hasItem(DEFAULT_COD_CATEGORIA)))
            .andExpect(jsonPath("$.[*].descCategoria").value(hasItem(DEFAULT_DESC_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].grupoCat").value(hasItem(DEFAULT_GRUPO_CAT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaESocial.class);
        CategoriaESocial categoriaESocial1 = new CategoriaESocial();
        categoriaESocial1.setId(1L);
        CategoriaESocial categoriaESocial2 = new CategoriaESocial();
        categoriaESocial2.setId(categoriaESocial1.getId());
        assertThat(categoriaESocial1).isEqualTo(categoriaESocial2);
        categoriaESocial2.setId(2L);
        assertThat(categoriaESocial1).isNotEqualTo(categoriaESocial2);
        categoriaESocial1.setId(null);
        assertThat(categoriaESocial1).isNotEqualTo(categoriaESocial2);
    }
}
