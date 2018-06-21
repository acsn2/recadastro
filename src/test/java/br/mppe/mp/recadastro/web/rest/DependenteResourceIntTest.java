package br.mppe.mp.recadastro.web.rest;

import br.mppe.mp.recadastro.RecadastroApp;

import br.mppe.mp.recadastro.domain.Dependente;
import br.mppe.mp.recadastro.repository.DependenteRepository;
import br.mppe.mp.recadastro.repository.search.DependenteSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the DependenteResource REST controller.
 *
 * @see DependenteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecadastroApp.class)
public class DependenteResourceIntTest {

    private static final Long DEFAULT_FK_DEPENDENTE = 1L;
    private static final Long UPDATED_FK_DEPENDENTE = 2L;

    private static final Integer DEFAULT_TP_DEPENDENTE = 1;
    private static final Integer UPDATED_TP_DEPENDENTE = 2;

    private static final String DEFAULT_NOME_DEPEND = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DEPEND = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DAT_NAC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAT_NAC = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CPF_DEPEND = "AAAAAAAAAA";
    private static final String UPDATED_CPF_DEPEND = "BBBBBBBBBB";

    private static final String DEFAULT_DEP_IRRF = "AAAAAAAAAA";
    private static final String UPDATED_DEP_IRRF = "BBBBBBBBBB";

    private static final String DEFAULT_DEP_SF = "AAAAAAAAAA";
    private static final String UPDATED_DEP_SF = "BBBBBBBBBB";

    private static final String DEFAULT_INCAPAC_TRAB = "AAAAAAAAAA";
    private static final String UPDATED_INCAPAC_TRAB = "BBBBBBBBBB";

    @Autowired
    private DependenteRepository dependenteRepository;


    /**
     * This repository is mocked in the br.mppe.mp.recadastro.repository.search test package.
     *
     * @see br.mppe.mp.recadastro.repository.search.DependenteSearchRepositoryMockConfiguration
     */
    @Autowired
    private DependenteSearchRepository mockDependenteSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDependenteMockMvc;

    private Dependente dependente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DependenteResource dependenteResource = new DependenteResource(dependenteRepository, mockDependenteSearchRepository);
        this.restDependenteMockMvc = MockMvcBuilders.standaloneSetup(dependenteResource)
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
    public static Dependente createEntity(EntityManager em) {
        Dependente dependente = new Dependente()
            .fkDependente(DEFAULT_FK_DEPENDENTE)
            .tpDependente(DEFAULT_TP_DEPENDENTE)
            .nomeDepend(DEFAULT_NOME_DEPEND)
            .datNac(DEFAULT_DAT_NAC)
            .cpfDepend(DEFAULT_CPF_DEPEND)
            .depIRRF(DEFAULT_DEP_IRRF)
            .depSF(DEFAULT_DEP_SF)
            .incapacTrab(DEFAULT_INCAPAC_TRAB);
        return dependente;
    }

    @Before
    public void initTest() {
        dependente = createEntity(em);
    }

    @Test
    @Transactional
    public void createDependente() throws Exception {
        int databaseSizeBeforeCreate = dependenteRepository.findAll().size();

        // Create the Dependente
        restDependenteMockMvc.perform(post("/api/dependentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependente)))
            .andExpect(status().isCreated());

        // Validate the Dependente in the database
        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeCreate + 1);
        Dependente testDependente = dependenteList.get(dependenteList.size() - 1);
        assertThat(testDependente.getFkDependente()).isEqualTo(DEFAULT_FK_DEPENDENTE);
        assertThat(testDependente.getTpDependente()).isEqualTo(DEFAULT_TP_DEPENDENTE);
        assertThat(testDependente.getNomeDepend()).isEqualTo(DEFAULT_NOME_DEPEND);
        assertThat(testDependente.getDatNac()).isEqualTo(DEFAULT_DAT_NAC);
        assertThat(testDependente.getCpfDepend()).isEqualTo(DEFAULT_CPF_DEPEND);
        assertThat(testDependente.getDepIRRF()).isEqualTo(DEFAULT_DEP_IRRF);
        assertThat(testDependente.getDepSF()).isEqualTo(DEFAULT_DEP_SF);
        assertThat(testDependente.getIncapacTrab()).isEqualTo(DEFAULT_INCAPAC_TRAB);

        // Validate the Dependente in Elasticsearch
        verify(mockDependenteSearchRepository, times(1)).save(testDependente);
    }

    @Test
    @Transactional
    public void createDependenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dependenteRepository.findAll().size();

        // Create the Dependente with an existing ID
        dependente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDependenteMockMvc.perform(post("/api/dependentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependente)))
            .andExpect(status().isBadRequest());

        // Validate the Dependente in the database
        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Dependente in Elasticsearch
        verify(mockDependenteSearchRepository, times(0)).save(dependente);
    }

    @Test
    @Transactional
    public void checkFkDependenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = dependenteRepository.findAll().size();
        // set the field null
        dependente.setFkDependente(null);

        // Create the Dependente, which fails.

        restDependenteMockMvc.perform(post("/api/dependentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependente)))
            .andExpect(status().isBadRequest());

        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTpDependenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = dependenteRepository.findAll().size();
        // set the field null
        dependente.setTpDependente(null);

        // Create the Dependente, which fails.

        restDependenteMockMvc.perform(post("/api/dependentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependente)))
            .andExpect(status().isBadRequest());

        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeDependIsRequired() throws Exception {
        int databaseSizeBeforeTest = dependenteRepository.findAll().size();
        // set the field null
        dependente.setNomeDepend(null);

        // Create the Dependente, which fails.

        restDependenteMockMvc.perform(post("/api/dependentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependente)))
            .andExpect(status().isBadRequest());

        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatNacIsRequired() throws Exception {
        int databaseSizeBeforeTest = dependenteRepository.findAll().size();
        // set the field null
        dependente.setDatNac(null);

        // Create the Dependente, which fails.

        restDependenteMockMvc.perform(post("/api/dependentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependente)))
            .andExpect(status().isBadRequest());

        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDependentes() throws Exception {
        // Initialize the database
        dependenteRepository.saveAndFlush(dependente);

        // Get all the dependenteList
        restDependenteMockMvc.perform(get("/api/dependentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dependente.getId().intValue())))
            .andExpect(jsonPath("$.[*].fkDependente").value(hasItem(DEFAULT_FK_DEPENDENTE.intValue())))
            .andExpect(jsonPath("$.[*].tpDependente").value(hasItem(DEFAULT_TP_DEPENDENTE)))
            .andExpect(jsonPath("$.[*].nomeDepend").value(hasItem(DEFAULT_NOME_DEPEND.toString())))
            .andExpect(jsonPath("$.[*].datNac").value(hasItem(DEFAULT_DAT_NAC.toString())))
            .andExpect(jsonPath("$.[*].cpfDepend").value(hasItem(DEFAULT_CPF_DEPEND.toString())))
            .andExpect(jsonPath("$.[*].depIRRF").value(hasItem(DEFAULT_DEP_IRRF.toString())))
            .andExpect(jsonPath("$.[*].depSF").value(hasItem(DEFAULT_DEP_SF.toString())))
            .andExpect(jsonPath("$.[*].incapacTrab").value(hasItem(DEFAULT_INCAPAC_TRAB.toString())));
    }
    

    @Test
    @Transactional
    public void getDependente() throws Exception {
        // Initialize the database
        dependenteRepository.saveAndFlush(dependente);

        // Get the dependente
        restDependenteMockMvc.perform(get("/api/dependentes/{id}", dependente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dependente.getId().intValue()))
            .andExpect(jsonPath("$.fkDependente").value(DEFAULT_FK_DEPENDENTE.intValue()))
            .andExpect(jsonPath("$.tpDependente").value(DEFAULT_TP_DEPENDENTE))
            .andExpect(jsonPath("$.nomeDepend").value(DEFAULT_NOME_DEPEND.toString()))
            .andExpect(jsonPath("$.datNac").value(DEFAULT_DAT_NAC.toString()))
            .andExpect(jsonPath("$.cpfDepend").value(DEFAULT_CPF_DEPEND.toString()))
            .andExpect(jsonPath("$.depIRRF").value(DEFAULT_DEP_IRRF.toString()))
            .andExpect(jsonPath("$.depSF").value(DEFAULT_DEP_SF.toString()))
            .andExpect(jsonPath("$.incapacTrab").value(DEFAULT_INCAPAC_TRAB.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDependente() throws Exception {
        // Get the dependente
        restDependenteMockMvc.perform(get("/api/dependentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDependente() throws Exception {
        // Initialize the database
        dependenteRepository.saveAndFlush(dependente);

        int databaseSizeBeforeUpdate = dependenteRepository.findAll().size();

        // Update the dependente
        Dependente updatedDependente = dependenteRepository.findById(dependente.getId()).get();
        // Disconnect from session so that the updates on updatedDependente are not directly saved in db
        em.detach(updatedDependente);
        updatedDependente
            .fkDependente(UPDATED_FK_DEPENDENTE)
            .tpDependente(UPDATED_TP_DEPENDENTE)
            .nomeDepend(UPDATED_NOME_DEPEND)
            .datNac(UPDATED_DAT_NAC)
            .cpfDepend(UPDATED_CPF_DEPEND)
            .depIRRF(UPDATED_DEP_IRRF)
            .depSF(UPDATED_DEP_SF)
            .incapacTrab(UPDATED_INCAPAC_TRAB);

        restDependenteMockMvc.perform(put("/api/dependentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDependente)))
            .andExpect(status().isOk());

        // Validate the Dependente in the database
        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeUpdate);
        Dependente testDependente = dependenteList.get(dependenteList.size() - 1);
        assertThat(testDependente.getFkDependente()).isEqualTo(UPDATED_FK_DEPENDENTE);
        assertThat(testDependente.getTpDependente()).isEqualTo(UPDATED_TP_DEPENDENTE);
        assertThat(testDependente.getNomeDepend()).isEqualTo(UPDATED_NOME_DEPEND);
        assertThat(testDependente.getDatNac()).isEqualTo(UPDATED_DAT_NAC);
        assertThat(testDependente.getCpfDepend()).isEqualTo(UPDATED_CPF_DEPEND);
        assertThat(testDependente.getDepIRRF()).isEqualTo(UPDATED_DEP_IRRF);
        assertThat(testDependente.getDepSF()).isEqualTo(UPDATED_DEP_SF);
        assertThat(testDependente.getIncapacTrab()).isEqualTo(UPDATED_INCAPAC_TRAB);

        // Validate the Dependente in Elasticsearch
        verify(mockDependenteSearchRepository, times(1)).save(testDependente);
    }

    @Test
    @Transactional
    public void updateNonExistingDependente() throws Exception {
        int databaseSizeBeforeUpdate = dependenteRepository.findAll().size();

        // Create the Dependente

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDependenteMockMvc.perform(put("/api/dependentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dependente)))
            .andExpect(status().isBadRequest());

        // Validate the Dependente in the database
        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dependente in Elasticsearch
        verify(mockDependenteSearchRepository, times(0)).save(dependente);
    }

    @Test
    @Transactional
    public void deleteDependente() throws Exception {
        // Initialize the database
        dependenteRepository.saveAndFlush(dependente);

        int databaseSizeBeforeDelete = dependenteRepository.findAll().size();

        // Get the dependente
        restDependenteMockMvc.perform(delete("/api/dependentes/{id}", dependente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Dependente in Elasticsearch
        verify(mockDependenteSearchRepository, times(1)).deleteById(dependente.getId());
    }

    @Test
    @Transactional
    public void searchDependente() throws Exception {
        // Initialize the database
        dependenteRepository.saveAndFlush(dependente);
        when(mockDependenteSearchRepository.search(queryStringQuery("id:" + dependente.getId())))
            .thenReturn(Collections.singletonList(dependente));
        // Search the dependente
        restDependenteMockMvc.perform(get("/api/_search/dependentes?query=id:" + dependente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dependente.getId().intValue())))
            .andExpect(jsonPath("$.[*].fkDependente").value(hasItem(DEFAULT_FK_DEPENDENTE.intValue())))
            .andExpect(jsonPath("$.[*].tpDependente").value(hasItem(DEFAULT_TP_DEPENDENTE)))
            .andExpect(jsonPath("$.[*].nomeDepend").value(hasItem(DEFAULT_NOME_DEPEND.toString())))
            .andExpect(jsonPath("$.[*].datNac").value(hasItem(DEFAULT_DAT_NAC.toString())))
            .andExpect(jsonPath("$.[*].cpfDepend").value(hasItem(DEFAULT_CPF_DEPEND.toString())))
            .andExpect(jsonPath("$.[*].depIRRF").value(hasItem(DEFAULT_DEP_IRRF.toString())))
            .andExpect(jsonPath("$.[*].depSF").value(hasItem(DEFAULT_DEP_SF.toString())))
            .andExpect(jsonPath("$.[*].incapacTrab").value(hasItem(DEFAULT_INCAPAC_TRAB.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dependente.class);
        Dependente dependente1 = new Dependente();
        dependente1.setId(1L);
        Dependente dependente2 = new Dependente();
        dependente2.setId(dependente1.getId());
        assertThat(dependente1).isEqualTo(dependente2);
        dependente2.setId(2L);
        assertThat(dependente1).isNotEqualTo(dependente2);
        dependente1.setId(null);
        assertThat(dependente1).isNotEqualTo(dependente2);
    }
}
