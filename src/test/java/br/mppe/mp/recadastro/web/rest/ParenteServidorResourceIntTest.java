package br.mppe.mp.recadastro.web.rest;

import br.mppe.mp.recadastro.RecadastroApp;

import br.mppe.mp.recadastro.domain.ParenteServidor;
import br.mppe.mp.recadastro.repository.ParenteServidorRepository;
import br.mppe.mp.recadastro.repository.search.ParenteServidorSearchRepository;
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
 * Test class for the ParenteServidorResource REST controller.
 *
 * @see ParenteServidorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecadastroApp.class)
public class ParenteServidorResourceIntTest {

    private static final Long DEFAULT_FK_PARENTE_SERVIDOR = 1L;
    private static final Long UPDATED_FK_PARENTE_SERVIDOR = 2L;

    private static final Long DEFAULT_MATRICULA_PARENTE = 1L;
    private static final Long UPDATED_MATRICULA_PARENTE = 2L;

    private static final String DEFAULT_FK_GRAU_PARENTESCO = "AAAAAAAAAA";
    private static final String UPDATED_FK_GRAU_PARENTESCO = "BBBBBBBBBB";

    @Autowired
    private ParenteServidorRepository parenteServidorRepository;


    /**
     * This repository is mocked in the br.mppe.mp.recadastro.repository.search test package.
     *
     * @see br.mppe.mp.recadastro.repository.search.ParenteServidorSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParenteServidorSearchRepository mockParenteServidorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParenteServidorMockMvc;

    private ParenteServidor parenteServidor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParenteServidorResource parenteServidorResource = new ParenteServidorResource(parenteServidorRepository, mockParenteServidorSearchRepository);
        this.restParenteServidorMockMvc = MockMvcBuilders.standaloneSetup(parenteServidorResource)
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
    public static ParenteServidor createEntity(EntityManager em) {
        ParenteServidor parenteServidor = new ParenteServidor()
            .fkParenteServidor(DEFAULT_FK_PARENTE_SERVIDOR)
            .matriculaParente(DEFAULT_MATRICULA_PARENTE)
            .fkGrauParentesco(DEFAULT_FK_GRAU_PARENTESCO);
        return parenteServidor;
    }

    @Before
    public void initTest() {
        parenteServidor = createEntity(em);
    }

    @Test
    @Transactional
    public void createParenteServidor() throws Exception {
        int databaseSizeBeforeCreate = parenteServidorRepository.findAll().size();

        // Create the ParenteServidor
        restParenteServidorMockMvc.perform(post("/api/parente-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parenteServidor)))
            .andExpect(status().isCreated());

        // Validate the ParenteServidor in the database
        List<ParenteServidor> parenteServidorList = parenteServidorRepository.findAll();
        assertThat(parenteServidorList).hasSize(databaseSizeBeforeCreate + 1);
        ParenteServidor testParenteServidor = parenteServidorList.get(parenteServidorList.size() - 1);
        assertThat(testParenteServidor.getFkParenteServidor()).isEqualTo(DEFAULT_FK_PARENTE_SERVIDOR);
        assertThat(testParenteServidor.getMatriculaParente()).isEqualTo(DEFAULT_MATRICULA_PARENTE);
        assertThat(testParenteServidor.getFkGrauParentesco()).isEqualTo(DEFAULT_FK_GRAU_PARENTESCO);

        // Validate the ParenteServidor in Elasticsearch
        verify(mockParenteServidorSearchRepository, times(1)).save(testParenteServidor);
    }

    @Test
    @Transactional
    public void createParenteServidorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parenteServidorRepository.findAll().size();

        // Create the ParenteServidor with an existing ID
        parenteServidor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParenteServidorMockMvc.perform(post("/api/parente-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parenteServidor)))
            .andExpect(status().isBadRequest());

        // Validate the ParenteServidor in the database
        List<ParenteServidor> parenteServidorList = parenteServidorRepository.findAll();
        assertThat(parenteServidorList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParenteServidor in Elasticsearch
        verify(mockParenteServidorSearchRepository, times(0)).save(parenteServidor);
    }

    @Test
    @Transactional
    public void checkFkParenteServidorIsRequired() throws Exception {
        int databaseSizeBeforeTest = parenteServidorRepository.findAll().size();
        // set the field null
        parenteServidor.setFkParenteServidor(null);

        // Create the ParenteServidor, which fails.

        restParenteServidorMockMvc.perform(post("/api/parente-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parenteServidor)))
            .andExpect(status().isBadRequest());

        List<ParenteServidor> parenteServidorList = parenteServidorRepository.findAll();
        assertThat(parenteServidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParenteServidors() throws Exception {
        // Initialize the database
        parenteServidorRepository.saveAndFlush(parenteServidor);

        // Get all the parenteServidorList
        restParenteServidorMockMvc.perform(get("/api/parente-servidors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parenteServidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].fkParenteServidor").value(hasItem(DEFAULT_FK_PARENTE_SERVIDOR.intValue())))
            .andExpect(jsonPath("$.[*].matriculaParente").value(hasItem(DEFAULT_MATRICULA_PARENTE.intValue())))
            .andExpect(jsonPath("$.[*].fkGrauParentesco").value(hasItem(DEFAULT_FK_GRAU_PARENTESCO.toString())));
    }
    

    @Test
    @Transactional
    public void getParenteServidor() throws Exception {
        // Initialize the database
        parenteServidorRepository.saveAndFlush(parenteServidor);

        // Get the parenteServidor
        restParenteServidorMockMvc.perform(get("/api/parente-servidors/{id}", parenteServidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parenteServidor.getId().intValue()))
            .andExpect(jsonPath("$.fkParenteServidor").value(DEFAULT_FK_PARENTE_SERVIDOR.intValue()))
            .andExpect(jsonPath("$.matriculaParente").value(DEFAULT_MATRICULA_PARENTE.intValue()))
            .andExpect(jsonPath("$.fkGrauParentesco").value(DEFAULT_FK_GRAU_PARENTESCO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingParenteServidor() throws Exception {
        // Get the parenteServidor
        restParenteServidorMockMvc.perform(get("/api/parente-servidors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParenteServidor() throws Exception {
        // Initialize the database
        parenteServidorRepository.saveAndFlush(parenteServidor);

        int databaseSizeBeforeUpdate = parenteServidorRepository.findAll().size();

        // Update the parenteServidor
        ParenteServidor updatedParenteServidor = parenteServidorRepository.findById(parenteServidor.getId()).get();
        // Disconnect from session so that the updates on updatedParenteServidor are not directly saved in db
        em.detach(updatedParenteServidor);
        updatedParenteServidor
            .fkParenteServidor(UPDATED_FK_PARENTE_SERVIDOR)
            .matriculaParente(UPDATED_MATRICULA_PARENTE)
            .fkGrauParentesco(UPDATED_FK_GRAU_PARENTESCO);

        restParenteServidorMockMvc.perform(put("/api/parente-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParenteServidor)))
            .andExpect(status().isOk());

        // Validate the ParenteServidor in the database
        List<ParenteServidor> parenteServidorList = parenteServidorRepository.findAll();
        assertThat(parenteServidorList).hasSize(databaseSizeBeforeUpdate);
        ParenteServidor testParenteServidor = parenteServidorList.get(parenteServidorList.size() - 1);
        assertThat(testParenteServidor.getFkParenteServidor()).isEqualTo(UPDATED_FK_PARENTE_SERVIDOR);
        assertThat(testParenteServidor.getMatriculaParente()).isEqualTo(UPDATED_MATRICULA_PARENTE);
        assertThat(testParenteServidor.getFkGrauParentesco()).isEqualTo(UPDATED_FK_GRAU_PARENTESCO);

        // Validate the ParenteServidor in Elasticsearch
        verify(mockParenteServidorSearchRepository, times(1)).save(testParenteServidor);
    }

    @Test
    @Transactional
    public void updateNonExistingParenteServidor() throws Exception {
        int databaseSizeBeforeUpdate = parenteServidorRepository.findAll().size();

        // Create the ParenteServidor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParenteServidorMockMvc.perform(put("/api/parente-servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parenteServidor)))
            .andExpect(status().isBadRequest());

        // Validate the ParenteServidor in the database
        List<ParenteServidor> parenteServidorList = parenteServidorRepository.findAll();
        assertThat(parenteServidorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParenteServidor in Elasticsearch
        verify(mockParenteServidorSearchRepository, times(0)).save(parenteServidor);
    }

    @Test
    @Transactional
    public void deleteParenteServidor() throws Exception {
        // Initialize the database
        parenteServidorRepository.saveAndFlush(parenteServidor);

        int databaseSizeBeforeDelete = parenteServidorRepository.findAll().size();

        // Get the parenteServidor
        restParenteServidorMockMvc.perform(delete("/api/parente-servidors/{id}", parenteServidor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParenteServidor> parenteServidorList = parenteServidorRepository.findAll();
        assertThat(parenteServidorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParenteServidor in Elasticsearch
        verify(mockParenteServidorSearchRepository, times(1)).deleteById(parenteServidor.getId());
    }

    @Test
    @Transactional
    public void searchParenteServidor() throws Exception {
        // Initialize the database
        parenteServidorRepository.saveAndFlush(parenteServidor);
        when(mockParenteServidorSearchRepository.search(queryStringQuery("id:" + parenteServidor.getId())))
            .thenReturn(Collections.singletonList(parenteServidor));
        // Search the parenteServidor
        restParenteServidorMockMvc.perform(get("/api/_search/parente-servidors?query=id:" + parenteServidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parenteServidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].fkParenteServidor").value(hasItem(DEFAULT_FK_PARENTE_SERVIDOR.intValue())))
            .andExpect(jsonPath("$.[*].matriculaParente").value(hasItem(DEFAULT_MATRICULA_PARENTE.intValue())))
            .andExpect(jsonPath("$.[*].fkGrauParentesco").value(hasItem(DEFAULT_FK_GRAU_PARENTESCO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParenteServidor.class);
        ParenteServidor parenteServidor1 = new ParenteServidor();
        parenteServidor1.setId(1L);
        ParenteServidor parenteServidor2 = new ParenteServidor();
        parenteServidor2.setId(parenteServidor1.getId());
        assertThat(parenteServidor1).isEqualTo(parenteServidor2);
        parenteServidor2.setId(2L);
        assertThat(parenteServidor1).isNotEqualTo(parenteServidor2);
        parenteServidor1.setId(null);
        assertThat(parenteServidor1).isNotEqualTo(parenteServidor2);
    }
}
