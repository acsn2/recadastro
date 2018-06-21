package br.mppe.mp.recadastro.web.rest;

import br.mppe.mp.recadastro.RecadastroApp;

import br.mppe.mp.recadastro.domain.RegimePrevidenciario;
import br.mppe.mp.recadastro.repository.RegimePrevidenciarioRepository;
import br.mppe.mp.recadastro.repository.search.RegimePrevidenciarioSearchRepository;
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
 * Test class for the RegimePrevidenciarioResource REST controller.
 *
 * @see RegimePrevidenciarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecadastroApp.class)
public class RegimePrevidenciarioResourceIntTest {

    private static final Integer DEFAULT_COD_REG_PREV = 1;
    private static final Integer UPDATED_COD_REG_PREV = 2;

    private static final String DEFAULT_DESC_REG_PREV = "AAAAAAAAAA";
    private static final String UPDATED_DESC_REG_PREV = "BBBBBBBBBB";

    @Autowired
    private RegimePrevidenciarioRepository regimePrevidenciarioRepository;


    /**
     * This repository is mocked in the br.mppe.mp.recadastro.repository.search test package.
     *
     * @see br.mppe.mp.recadastro.repository.search.RegimePrevidenciarioSearchRepositoryMockConfiguration
     */
    @Autowired
    private RegimePrevidenciarioSearchRepository mockRegimePrevidenciarioSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegimePrevidenciarioMockMvc;

    private RegimePrevidenciario regimePrevidenciario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegimePrevidenciarioResource regimePrevidenciarioResource = new RegimePrevidenciarioResource(regimePrevidenciarioRepository, mockRegimePrevidenciarioSearchRepository);
        this.restRegimePrevidenciarioMockMvc = MockMvcBuilders.standaloneSetup(regimePrevidenciarioResource)
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
    public static RegimePrevidenciario createEntity(EntityManager em) {
        RegimePrevidenciario regimePrevidenciario = new RegimePrevidenciario()
            .codRegPrev(DEFAULT_COD_REG_PREV)
            .descRegPrev(DEFAULT_DESC_REG_PREV);
        return regimePrevidenciario;
    }

    @Before
    public void initTest() {
        regimePrevidenciario = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegimePrevidenciario() throws Exception {
        int databaseSizeBeforeCreate = regimePrevidenciarioRepository.findAll().size();

        // Create the RegimePrevidenciario
        restRegimePrevidenciarioMockMvc.perform(post("/api/regime-previdenciarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimePrevidenciario)))
            .andExpect(status().isCreated());

        // Validate the RegimePrevidenciario in the database
        List<RegimePrevidenciario> regimePrevidenciarioList = regimePrevidenciarioRepository.findAll();
        assertThat(regimePrevidenciarioList).hasSize(databaseSizeBeforeCreate + 1);
        RegimePrevidenciario testRegimePrevidenciario = regimePrevidenciarioList.get(regimePrevidenciarioList.size() - 1);
        assertThat(testRegimePrevidenciario.getCodRegPrev()).isEqualTo(DEFAULT_COD_REG_PREV);
        assertThat(testRegimePrevidenciario.getDescRegPrev()).isEqualTo(DEFAULT_DESC_REG_PREV);

        // Validate the RegimePrevidenciario in Elasticsearch
        verify(mockRegimePrevidenciarioSearchRepository, times(1)).save(testRegimePrevidenciario);
    }

    @Test
    @Transactional
    public void createRegimePrevidenciarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regimePrevidenciarioRepository.findAll().size();

        // Create the RegimePrevidenciario with an existing ID
        regimePrevidenciario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegimePrevidenciarioMockMvc.perform(post("/api/regime-previdenciarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimePrevidenciario)))
            .andExpect(status().isBadRequest());

        // Validate the RegimePrevidenciario in the database
        List<RegimePrevidenciario> regimePrevidenciarioList = regimePrevidenciarioRepository.findAll();
        assertThat(regimePrevidenciarioList).hasSize(databaseSizeBeforeCreate);

        // Validate the RegimePrevidenciario in Elasticsearch
        verify(mockRegimePrevidenciarioSearchRepository, times(0)).save(regimePrevidenciario);
    }

    @Test
    @Transactional
    public void checkCodRegPrevIsRequired() throws Exception {
        int databaseSizeBeforeTest = regimePrevidenciarioRepository.findAll().size();
        // set the field null
        regimePrevidenciario.setCodRegPrev(null);

        // Create the RegimePrevidenciario, which fails.

        restRegimePrevidenciarioMockMvc.perform(post("/api/regime-previdenciarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimePrevidenciario)))
            .andExpect(status().isBadRequest());

        List<RegimePrevidenciario> regimePrevidenciarioList = regimePrevidenciarioRepository.findAll();
        assertThat(regimePrevidenciarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegimePrevidenciarios() throws Exception {
        // Initialize the database
        regimePrevidenciarioRepository.saveAndFlush(regimePrevidenciario);

        // Get all the regimePrevidenciarioList
        restRegimePrevidenciarioMockMvc.perform(get("/api/regime-previdenciarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regimePrevidenciario.getId().intValue())))
            .andExpect(jsonPath("$.[*].codRegPrev").value(hasItem(DEFAULT_COD_REG_PREV)))
            .andExpect(jsonPath("$.[*].descRegPrev").value(hasItem(DEFAULT_DESC_REG_PREV.toString())));
    }
    

    @Test
    @Transactional
    public void getRegimePrevidenciario() throws Exception {
        // Initialize the database
        regimePrevidenciarioRepository.saveAndFlush(regimePrevidenciario);

        // Get the regimePrevidenciario
        restRegimePrevidenciarioMockMvc.perform(get("/api/regime-previdenciarios/{id}", regimePrevidenciario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regimePrevidenciario.getId().intValue()))
            .andExpect(jsonPath("$.codRegPrev").value(DEFAULT_COD_REG_PREV))
            .andExpect(jsonPath("$.descRegPrev").value(DEFAULT_DESC_REG_PREV.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRegimePrevidenciario() throws Exception {
        // Get the regimePrevidenciario
        restRegimePrevidenciarioMockMvc.perform(get("/api/regime-previdenciarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegimePrevidenciario() throws Exception {
        // Initialize the database
        regimePrevidenciarioRepository.saveAndFlush(regimePrevidenciario);

        int databaseSizeBeforeUpdate = regimePrevidenciarioRepository.findAll().size();

        // Update the regimePrevidenciario
        RegimePrevidenciario updatedRegimePrevidenciario = regimePrevidenciarioRepository.findById(regimePrevidenciario.getId()).get();
        // Disconnect from session so that the updates on updatedRegimePrevidenciario are not directly saved in db
        em.detach(updatedRegimePrevidenciario);
        updatedRegimePrevidenciario
            .codRegPrev(UPDATED_COD_REG_PREV)
            .descRegPrev(UPDATED_DESC_REG_PREV);

        restRegimePrevidenciarioMockMvc.perform(put("/api/regime-previdenciarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegimePrevidenciario)))
            .andExpect(status().isOk());

        // Validate the RegimePrevidenciario in the database
        List<RegimePrevidenciario> regimePrevidenciarioList = regimePrevidenciarioRepository.findAll();
        assertThat(regimePrevidenciarioList).hasSize(databaseSizeBeforeUpdate);
        RegimePrevidenciario testRegimePrevidenciario = regimePrevidenciarioList.get(regimePrevidenciarioList.size() - 1);
        assertThat(testRegimePrevidenciario.getCodRegPrev()).isEqualTo(UPDATED_COD_REG_PREV);
        assertThat(testRegimePrevidenciario.getDescRegPrev()).isEqualTo(UPDATED_DESC_REG_PREV);

        // Validate the RegimePrevidenciario in Elasticsearch
        verify(mockRegimePrevidenciarioSearchRepository, times(1)).save(testRegimePrevidenciario);
    }

    @Test
    @Transactional
    public void updateNonExistingRegimePrevidenciario() throws Exception {
        int databaseSizeBeforeUpdate = regimePrevidenciarioRepository.findAll().size();

        // Create the RegimePrevidenciario

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegimePrevidenciarioMockMvc.perform(put("/api/regime-previdenciarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regimePrevidenciario)))
            .andExpect(status().isBadRequest());

        // Validate the RegimePrevidenciario in the database
        List<RegimePrevidenciario> regimePrevidenciarioList = regimePrevidenciarioRepository.findAll();
        assertThat(regimePrevidenciarioList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RegimePrevidenciario in Elasticsearch
        verify(mockRegimePrevidenciarioSearchRepository, times(0)).save(regimePrevidenciario);
    }

    @Test
    @Transactional
    public void deleteRegimePrevidenciario() throws Exception {
        // Initialize the database
        regimePrevidenciarioRepository.saveAndFlush(regimePrevidenciario);

        int databaseSizeBeforeDelete = regimePrevidenciarioRepository.findAll().size();

        // Get the regimePrevidenciario
        restRegimePrevidenciarioMockMvc.perform(delete("/api/regime-previdenciarios/{id}", regimePrevidenciario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RegimePrevidenciario> regimePrevidenciarioList = regimePrevidenciarioRepository.findAll();
        assertThat(regimePrevidenciarioList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RegimePrevidenciario in Elasticsearch
        verify(mockRegimePrevidenciarioSearchRepository, times(1)).deleteById(regimePrevidenciario.getId());
    }

    @Test
    @Transactional
    public void searchRegimePrevidenciario() throws Exception {
        // Initialize the database
        regimePrevidenciarioRepository.saveAndFlush(regimePrevidenciario);
        when(mockRegimePrevidenciarioSearchRepository.search(queryStringQuery("id:" + regimePrevidenciario.getId())))
            .thenReturn(Collections.singletonList(regimePrevidenciario));
        // Search the regimePrevidenciario
        restRegimePrevidenciarioMockMvc.perform(get("/api/_search/regime-previdenciarios?query=id:" + regimePrevidenciario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regimePrevidenciario.getId().intValue())))
            .andExpect(jsonPath("$.[*].codRegPrev").value(hasItem(DEFAULT_COD_REG_PREV)))
            .andExpect(jsonPath("$.[*].descRegPrev").value(hasItem(DEFAULT_DESC_REG_PREV.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegimePrevidenciario.class);
        RegimePrevidenciario regimePrevidenciario1 = new RegimePrevidenciario();
        regimePrevidenciario1.setId(1L);
        RegimePrevidenciario regimePrevidenciario2 = new RegimePrevidenciario();
        regimePrevidenciario2.setId(regimePrevidenciario1.getId());
        assertThat(regimePrevidenciario1).isEqualTo(regimePrevidenciario2);
        regimePrevidenciario2.setId(2L);
        assertThat(regimePrevidenciario1).isNotEqualTo(regimePrevidenciario2);
        regimePrevidenciario1.setId(null);
        assertThat(regimePrevidenciario1).isNotEqualTo(regimePrevidenciario2);
    }
}
