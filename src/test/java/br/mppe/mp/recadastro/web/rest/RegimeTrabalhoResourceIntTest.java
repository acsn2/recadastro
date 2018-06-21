package br.mppe.mp.recadastro.web.rest;

import br.mppe.mp.recadastro.RecadastroApp;

import br.mppe.mp.recadastro.domain.RegimeTrabalho;
import br.mppe.mp.recadastro.repository.RegimeTrabalhoRepository;
import br.mppe.mp.recadastro.repository.search.RegimeTrabalhoSearchRepository;
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
 * Test class for the RegimeTrabalhoResource REST controller.
 *
 * @see RegimeTrabalhoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecadastroApp.class)
public class RegimeTrabalhoResourceIntTest {

    private static final Integer DEFAULT_COD_REG_TRAB = 1;
    private static final Integer UPDATED_COD_REG_TRAB = 2;

    private static final String DEFAULT_DESC_REG_TRAB = "AAAAAAAAAA";
    private static final String UPDATED_DESC_REG_TRAB = "BBBBBBBBBB";

    @Autowired
    private RegimeTrabalhoRepository regimeTrabalhoRepository;


    /**
     * This repository is mocked in the br.mppe.mp.recadastro.repository.search test package.
     *
     * @see br.mppe.mp.recadastro.repository.search.RegimeTrabalhoSearchRepositoryMockConfiguration
     */
    @Autowired
    private RegimeTrabalhoSearchRepository mockRegimeTrabalhoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegimeTrabalhoMockMvc;

    private RegimeTrabalho regimeTrabalho;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegimeTrabalhoResource regimeTrabalhoResource = new RegimeTrabalhoResource(regimeTrabalhoRepository, mockRegimeTrabalhoSearchRepository);
        this.restRegimeTrabalhoMockMvc = MockMvcBuilders.standaloneSetup(regimeTrabalhoResource)
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
    public static RegimeTrabalho createEntity(EntityManager em) {
        RegimeTrabalho regimeTrabalho = new RegimeTrabalho()
            .codRegTrab(DEFAULT_COD_REG_TRAB)
            .descRegTrab(DEFAULT_DESC_REG_TRAB);
        return regimeTrabalho;
    }

    @Before
    public void initTest() {
        regimeTrabalho = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegimeTrabalho() throws Exception {
        int databaseSizeBeforeCreate = regimeTrabalhoRepository.findAll().size();

        // Create the RegimeTrabalho
        restRegimeTrabalhoMockMvc.perform(post("/api/regime-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimeTrabalho)))
            .andExpect(status().isCreated());

        // Validate the RegimeTrabalho in the database
        List<RegimeTrabalho> regimeTrabalhoList = regimeTrabalhoRepository.findAll();
        assertThat(regimeTrabalhoList).hasSize(databaseSizeBeforeCreate + 1);
        RegimeTrabalho testRegimeTrabalho = regimeTrabalhoList.get(regimeTrabalhoList.size() - 1);
        assertThat(testRegimeTrabalho.getCodRegTrab()).isEqualTo(DEFAULT_COD_REG_TRAB);
        assertThat(testRegimeTrabalho.getDescRegTrab()).isEqualTo(DEFAULT_DESC_REG_TRAB);

        // Validate the RegimeTrabalho in Elasticsearch
        verify(mockRegimeTrabalhoSearchRepository, times(1)).save(testRegimeTrabalho);
    }

    @Test
    @Transactional
    public void createRegimeTrabalhoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regimeTrabalhoRepository.findAll().size();

        // Create the RegimeTrabalho with an existing ID
        regimeTrabalho.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegimeTrabalhoMockMvc.perform(post("/api/regime-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimeTrabalho)))
            .andExpect(status().isBadRequest());

        // Validate the RegimeTrabalho in the database
        List<RegimeTrabalho> regimeTrabalhoList = regimeTrabalhoRepository.findAll();
        assertThat(regimeTrabalhoList).hasSize(databaseSizeBeforeCreate);

        // Validate the RegimeTrabalho in Elasticsearch
        verify(mockRegimeTrabalhoSearchRepository, times(0)).save(regimeTrabalho);
    }

    @Test
    @Transactional
    public void checkCodRegTrabIsRequired() throws Exception {
        int databaseSizeBeforeTest = regimeTrabalhoRepository.findAll().size();
        // set the field null
        regimeTrabalho.setCodRegTrab(null);

        // Create the RegimeTrabalho, which fails.

        restRegimeTrabalhoMockMvc.perform(post("/api/regime-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimeTrabalho)))
            .andExpect(status().isBadRequest());

        List<RegimeTrabalho> regimeTrabalhoList = regimeTrabalhoRepository.findAll();
        assertThat(regimeTrabalhoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegimeTrabalhos() throws Exception {
        // Initialize the database
        regimeTrabalhoRepository.saveAndFlush(regimeTrabalho);

        // Get all the regimeTrabalhoList
        restRegimeTrabalhoMockMvc.perform(get("/api/regime-trabalhos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regimeTrabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].codRegTrab").value(hasItem(DEFAULT_COD_REG_TRAB)))
            .andExpect(jsonPath("$.[*].descRegTrab").value(hasItem(DEFAULT_DESC_REG_TRAB.toString())));
    }
    

    @Test
    @Transactional
    public void getRegimeTrabalho() throws Exception {
        // Initialize the database
        regimeTrabalhoRepository.saveAndFlush(regimeTrabalho);

        // Get the regimeTrabalho
        restRegimeTrabalhoMockMvc.perform(get("/api/regime-trabalhos/{id}", regimeTrabalho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regimeTrabalho.getId().intValue()))
            .andExpect(jsonPath("$.codRegTrab").value(DEFAULT_COD_REG_TRAB))
            .andExpect(jsonPath("$.descRegTrab").value(DEFAULT_DESC_REG_TRAB.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRegimeTrabalho() throws Exception {
        // Get the regimeTrabalho
        restRegimeTrabalhoMockMvc.perform(get("/api/regime-trabalhos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegimeTrabalho() throws Exception {
        // Initialize the database
        regimeTrabalhoRepository.saveAndFlush(regimeTrabalho);

        int databaseSizeBeforeUpdate = regimeTrabalhoRepository.findAll().size();

        // Update the regimeTrabalho
        RegimeTrabalho updatedRegimeTrabalho = regimeTrabalhoRepository.findById(regimeTrabalho.getId()).get();
        // Disconnect from session so that the updates on updatedRegimeTrabalho are not directly saved in db
        em.detach(updatedRegimeTrabalho);
        updatedRegimeTrabalho
            .codRegTrab(UPDATED_COD_REG_TRAB)
            .descRegTrab(UPDATED_DESC_REG_TRAB);

        restRegimeTrabalhoMockMvc.perform(put("/api/regime-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegimeTrabalho)))
            .andExpect(status().isOk());

        // Validate the RegimeTrabalho in the database
        List<RegimeTrabalho> regimeTrabalhoList = regimeTrabalhoRepository.findAll();
        assertThat(regimeTrabalhoList).hasSize(databaseSizeBeforeUpdate);
        RegimeTrabalho testRegimeTrabalho = regimeTrabalhoList.get(regimeTrabalhoList.size() - 1);
        assertThat(testRegimeTrabalho.getCodRegTrab()).isEqualTo(UPDATED_COD_REG_TRAB);
        assertThat(testRegimeTrabalho.getDescRegTrab()).isEqualTo(UPDATED_DESC_REG_TRAB);

        // Validate the RegimeTrabalho in Elasticsearch
        verify(mockRegimeTrabalhoSearchRepository, times(1)).save(testRegimeTrabalho);
    }

    @Test
    @Transactional
    public void updateNonExistingRegimeTrabalho() throws Exception {
        int databaseSizeBeforeUpdate = regimeTrabalhoRepository.findAll().size();

        // Create the RegimeTrabalho

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegimeTrabalhoMockMvc.perform(put("/api/regime-trabalhos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimeTrabalho)))
            .andExpect(status().isBadRequest());

        // Validate the RegimeTrabalho in the database
        List<RegimeTrabalho> regimeTrabalhoList = regimeTrabalhoRepository.findAll();
        assertThat(regimeTrabalhoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RegimeTrabalho in Elasticsearch
        verify(mockRegimeTrabalhoSearchRepository, times(0)).save(regimeTrabalho);
    }

    @Test
    @Transactional
    public void deleteRegimeTrabalho() throws Exception {
        // Initialize the database
        regimeTrabalhoRepository.saveAndFlush(regimeTrabalho);

        int databaseSizeBeforeDelete = regimeTrabalhoRepository.findAll().size();

        // Get the regimeTrabalho
        restRegimeTrabalhoMockMvc.perform(delete("/api/regime-trabalhos/{id}", regimeTrabalho.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RegimeTrabalho> regimeTrabalhoList = regimeTrabalhoRepository.findAll();
        assertThat(regimeTrabalhoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RegimeTrabalho in Elasticsearch
        verify(mockRegimeTrabalhoSearchRepository, times(1)).deleteById(regimeTrabalho.getId());
    }

    @Test
    @Transactional
    public void searchRegimeTrabalho() throws Exception {
        // Initialize the database
        regimeTrabalhoRepository.saveAndFlush(regimeTrabalho);
        when(mockRegimeTrabalhoSearchRepository.search(queryStringQuery("id:" + regimeTrabalho.getId())))
            .thenReturn(Collections.singletonList(regimeTrabalho));
        // Search the regimeTrabalho
        restRegimeTrabalhoMockMvc.perform(get("/api/_search/regime-trabalhos?query=id:" + regimeTrabalho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regimeTrabalho.getId().intValue())))
            .andExpect(jsonPath("$.[*].codRegTrab").value(hasItem(DEFAULT_COD_REG_TRAB)))
            .andExpect(jsonPath("$.[*].descRegTrab").value(hasItem(DEFAULT_DESC_REG_TRAB.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegimeTrabalho.class);
        RegimeTrabalho regimeTrabalho1 = new RegimeTrabalho();
        regimeTrabalho1.setId(1L);
        RegimeTrabalho regimeTrabalho2 = new RegimeTrabalho();
        regimeTrabalho2.setId(regimeTrabalho1.getId());
        assertThat(regimeTrabalho1).isEqualTo(regimeTrabalho2);
        regimeTrabalho2.setId(2L);
        assertThat(regimeTrabalho1).isNotEqualTo(regimeTrabalho2);
        regimeTrabalho1.setId(null);
        assertThat(regimeTrabalho1).isNotEqualTo(regimeTrabalho2);
    }
}
