package br.mppe.mp.recadastro.web.rest;

import br.mppe.mp.recadastro.RecadastroApp;

import br.mppe.mp.recadastro.domain.Servidor;
import br.mppe.mp.recadastro.repository.ServidorRepository;
import br.mppe.mp.recadastro.repository.search.ServidorSearchRepository;
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
 * Test class for the ServidorResource REST controller.
 *
 * @see ServidorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecadastroApp.class)
public class ServidorResourceIntTest {

    private static final Long DEFAULT_MATRICULA = 1L;
    private static final Long UPDATED_MATRICULA = 2L;

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_NOME_SOCIAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DAT_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAT_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOME_PAI = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PAI = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_MAE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MAE = "BBBBBBBBBB";

    private static final Long DEFAULT_CPF = 1L;
    private static final Long UPDATED_CPF = 2L;

    private static final Long DEFAULT_CPF_PAI = 1L;
    private static final Long UPDATED_CPF_PAI = 2L;

    private static final Long DEFAULT_CPF_MAE = 1L;
    private static final Long UPDATED_CPF_MAE = 2L;

    private static final String DEFAULT_RG_NUM = "AAAAAAAAAA";
    private static final String UPDATED_RG_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_RG_UF = "AAAAAAAAAA";
    private static final String UPDATED_RG_UF = "BBBBBBBBBB";

    private static final String DEFAULT_RG_ORGAO_EXP = "AAAAAAAAAA";
    private static final String UPDATED_RG_ORGAO_EXP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RG_DATA_EXP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RG_DATA_EXP = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_SANGUE = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SANGUE = "BBBBBBBBBB";

    private static final String DEFAULT_FATOR_RH = "AAAAAAAAAA";
    private static final String UPDATED_FATOR_RH = "BBBBBBBBBB";

    private static final String DEFAULT_DOADOR_SANGUE = "AAAAAAAAAA";
    private static final String UPDATED_DOADOR_SANGUE = "BBBBBBBBBB";

    private static final String DEFAULT_DEFICIENCIA_FISICA = "AAAAAAAAAA";
    private static final String UPDATED_DEFICIENCIA_FISICA = "BBBBBBBBBB";

    private static final String DEFAULT_DEFICIENCIA_VISUAL = "AAAAAAAAAA";
    private static final String UPDATED_DEFICIENCIA_VISUAL = "BBBBBBBBBB";

    private static final String DEFAULT_DEFICIENCIA_AUDITIVA = "AAAAAAAAAA";
    private static final String UPDATED_DEFICIENCIA_AUDITIVA = "BBBBBBBBBB";

    private static final String DEFAULT_DEFICIENCIA_MENTAL = "AAAAAAAAAA";
    private static final String UPDATED_DEFICIENCIA_MENTAL = "BBBBBBBBBB";

    private static final String DEFAULT_DEFICIENCIA_INTELEC = "AAAAAAAAAA";
    private static final String UPDATED_DEFICIENCIA_INTELEC = "BBBBBBBBBB";

    private static final String DEFAULT_DEFIC_REAB_READAPTADO = "AAAAAAAAAA";
    private static final String UPDATED_DEFIC_REAB_READAPTADO = "BBBBBBBBBB";

    private static final String DEFAULT_DEFIC_OBSERVACAO = "AAAAAAAAAA";
    private static final String UPDATED_DEFIC_OBSERVACAO = "BBBBBBBBBB";

    private static final String DEFAULT_PREENCHE_COTA = "AAAAAAAAAA";
    private static final String UPDATED_PREENCHE_COTA = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_PESSOAL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_PESSOAL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ALTERN = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ALTERN = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR_OPERADORA = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR_OPERADORA = "BBBBBBBBBB";

    private static final String DEFAULT_PASEP = "AAAAAAAAAA";
    private static final String UPDATED_PASEP = "BBBBBBBBBB";

    private static final String DEFAULT_IPSEP = "AAAAAAAAAA";
    private static final String UPDATED_IPSEP = "BBBBBBBBBB";

    private static final String DEFAULT_INSS = "AAAAAAAAAA";
    private static final String UPDATED_INSS = "BBBBBBBBBB";

    private static final String DEFAULT_CTPS_NUM = "AAAAAAAAAA";
    private static final String UPDATED_CTPS_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_CTPS_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_CTPS_SERIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CTPS_EMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CTPS_EMISSAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CTPS_UF = "AAAAAAAAAA";
    private static final String UPDATED_CTPS_UF = "BBBBBBBBBB";

    private static final String DEFAULT_ORGAO_CLASSE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_ORGAO_CLASSE_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_ORGAO_CL_NOME = "AAAAAAAAAA";
    private static final String UPDATED_ORGAO_CL_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_ORGAO_CL_UF = "AAAAAAAAAA";
    private static final String UPDATED_ORGAO_CL_UF = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ORCL_DTA_EXP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORCL_DTA_EXP = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ORCL_DTA_VALID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORCL_DTA_VALID = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RESERVISTA = "AAAAAAAAAA";
    private static final String UPDATED_RESERVISTA = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVISTA_CLASSE = "AAAAAAAAAA";
    private static final String UPDATED_RESERVISTA_CLASSE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_RIC = "AAAAAAAAAA";
    private static final String UPDATED_NUM_RIC = "BBBBBBBBBB";

    private static final String DEFAULT_ORGAO_EMISSOR_RIC = "AAAAAAAAAA";
    private static final String UPDATED_ORGAO_EMISSOR_RIC = "BBBBBBBBBB";

    private static final String DEFAULT_UF_RIC = "AAAAAAAAAA";
    private static final String UPDATED_UF_RIC = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DAT_EXPED_RIC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAT_EXPED_RIC = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TIT_ELEITOR_NUM = "AAAAAAAAAA";
    private static final String UPDATED_TIT_ELEITOR_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_TIT_ELEITOR_ZONA = "AAAAAAAAAA";
    private static final String UPDATED_TIT_ELEITOR_ZONA = "BBBBBBBBBB";

    private static final String DEFAULT_TIT_ELEITOR_SECAO = "AAAAAAAAAA";
    private static final String UPDATED_TIT_ELEITOR_SECAO = "BBBBBBBBBB";

    private static final String DEFAULT_TIT_ELEITOR_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_TIT_ELEITOR_LOCAL = "BBBBBBBBBB";

    private static final String DEFAULT_CNH_NUM = "AAAAAAAAAA";
    private static final String UPDATED_CNH_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_CNH_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CNH_CATEGORIA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CNH_DAT_VALIDADE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CNH_DAT_VALIDADE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CNH_DAT_EXPED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CNH_DAT_EXPED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CNH_UF_EMISSOR = "AAAAAAAAAA";
    private static final String UPDATED_CNH_UF_EMISSOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CNH_DAT_PRIM_HAB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CNH_DAT_PRIM_HAB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SASSEPE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_SASSEPE_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_UF_NATURAL = "AAAAAAAAAA";
    private static final String UPDATED_UF_NATURAL = "BBBBBBBBBB";

    private static final String DEFAULT_PRIM_EMPREGO = "AAAAAAAAAA";
    private static final String UPDATED_PRIM_EMPREGO = "BBBBBBBBBB";

    private static final String DEFAULT_EXERC_MAGISTERIO = "AAAAAAAAAA";
    private static final String UPDATED_EXERC_MAGISTERIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIPO_LOGRADOURO = 1;
    private static final Integer UPDATED_TIPO_LOGRADOURO = 2;

    private static final String DEFAULT_DESC_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_DESC_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_NR_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_NR_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPL_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_COMPL_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO_ENDERECO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CEP_ENDERECO = 1;
    private static final Integer UPDATED_CEP_ENDERECO = 2;

    private static final String DEFAULT_UF_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_UF_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_LOGRA_EXT = "AAAAAAAAAA";
    private static final String UPDATED_DESC_LOGRA_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_NR_LOGRA_EXT = "AAAAAAAAAA";
    private static final String UPDATED_NR_LOGRA_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_COMPL_LOGRA_EXT = "AAAAAAAAAA";
    private static final String UPDATED_COMPL_LOGRA_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO_END_EXT = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO_END_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE_END_EXT = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE_END_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_COD_POSTAL_END_EXT = "AAAAAAAAAA";
    private static final String UPDATED_COD_POSTAL_END_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_RNE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_RNE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGAO_EMISSOR_RNE = "AAAAAAAAAA";
    private static final String UPDATED_ORGAO_EMISSOR_RNE = "BBBBBBBBBB";

    private static final String DEFAULT_UF_EMISSOR_RNE = "AAAAAAAAAA";
    private static final String UPDATED_UF_EMISSOR_RNE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DAT_EXPE_RNE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAT_EXPE_RNE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DAT_CHEGADA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAT_CHEGADA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CLASS_TRAB_ESTR = 1;
    private static final Integer UPDATED_CLASS_TRAB_ESTR = 2;

    private static final String DEFAULT_CASADO_BR = "AAAAAAAAAA";
    private static final String UPDATED_CASADO_BR = "BBBBBBBBBB";

    private static final String DEFAULT_FILHOS_B_RR = "AAAAAAAAAA";
    private static final String UPDATED_FILHOS_B_RR = "BBBBBBBBBB";

    private static final String DEFAULT_AUX_TRANSPORTE = "AAAAAAAAAA";
    private static final String UPDATED_AUX_TRANSPORTE = "BBBBBBBBBB";

    private static final String DEFAULT_AUX_TRANS_LINHA_1 = "AAAAAAAAAA";
    private static final String UPDATED_AUX_TRANS_LINHA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_AUX_TRANS_LINHA_2 = "AAAAAAAAAA";
    private static final String UPDATED_AUX_TRANS_LINHA_2 = "BBBBBBBBBB";

    private static final String DEFAULT_AUX_TRANS_LINHA_3 = "AAAAAAAAAA";
    private static final String UPDATED_AUX_TRANS_LINHA_3 = "BBBBBBBBBB";

    private static final String DEFAULT_AUX_TRANS_LINHA_4 = "AAAAAAAAAA";
    private static final String UPDATED_AUX_TRANS_LINHA_4 = "BBBBBBBBBB";

    private static final String DEFAULT_TRAB_APOSENTADO = "AAAAAAAAAA";
    private static final String UPDATED_TRAB_APOSENTADO = "BBBBBBBBBB";

    @Autowired
    private ServidorRepository servidorRepository;


    /**
     * This repository is mocked in the br.mppe.mp.recadastro.repository.search test package.
     *
     * @see br.mppe.mp.recadastro.repository.search.ServidorSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServidorSearchRepository mockServidorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServidorMockMvc;

    private Servidor servidor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServidorResource servidorResource = new ServidorResource(servidorRepository, mockServidorSearchRepository);
        this.restServidorMockMvc = MockMvcBuilders.standaloneSetup(servidorResource)
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
    public static Servidor createEntity(EntityManager em) {
        Servidor servidor = new Servidor()
            .matricula(DEFAULT_MATRICULA)
            .username(DEFAULT_USERNAME)
            .nomeSocial(DEFAULT_NOME_SOCIAL)
            .datNascimento(DEFAULT_DAT_NASCIMENTO)
            .nomePai(DEFAULT_NOME_PAI)
            .nomeMae(DEFAULT_NOME_MAE)
            .cpf(DEFAULT_CPF)
            .cpfPai(DEFAULT_CPF_PAI)
            .cpfMae(DEFAULT_CPF_MAE)
            .rgNum(DEFAULT_RG_NUM)
            .rgUf(DEFAULT_RG_UF)
            .rgOrgaoExp(DEFAULT_RG_ORGAO_EXP)
            .rgDataExp(DEFAULT_RG_DATA_EXP)
            .sexo(DEFAULT_SEXO)
            .tipoSangue(DEFAULT_TIPO_SANGUE)
            .fatorRh(DEFAULT_FATOR_RH)
            .doadorSangue(DEFAULT_DOADOR_SANGUE)
            .deficienciaFisica(DEFAULT_DEFICIENCIA_FISICA)
            .deficienciaVisual(DEFAULT_DEFICIENCIA_VISUAL)
            .deficienciaAuditiva(DEFAULT_DEFICIENCIA_AUDITIVA)
            .deficienciaMental(DEFAULT_DEFICIENCIA_MENTAL)
            .deficienciaIntelec(DEFAULT_DEFICIENCIA_INTELEC)
            .deficReabReadaptado(DEFAULT_DEFIC_REAB_READAPTADO)
            .deficObservacao(DEFAULT_DEFIC_OBSERVACAO)
            .preencheCota(DEFAULT_PREENCHE_COTA)
            .emailPessoal(DEFAULT_EMAIL_PESSOAL)
            .emailAltern(DEFAULT_EMAIL_ALTERN)
            .celular(DEFAULT_CELULAR)
            .celularOperadora(DEFAULT_CELULAR_OPERADORA)
            .pasep(DEFAULT_PASEP)
            .ipsep(DEFAULT_IPSEP)
            .inss(DEFAULT_INSS)
            .ctpsNum(DEFAULT_CTPS_NUM)
            .ctpsSerie(DEFAULT_CTPS_SERIE)
            .ctpsEmissao(DEFAULT_CTPS_EMISSAO)
            .ctpsUf(DEFAULT_CTPS_UF)
            .orgaoClasseNum(DEFAULT_ORGAO_CLASSE_NUM)
            .orgaoClNome(DEFAULT_ORGAO_CL_NOME)
            .orgaoClUf(DEFAULT_ORGAO_CL_UF)
            .orclDtaExp(DEFAULT_ORCL_DTA_EXP)
            .orclDtaValid(DEFAULT_ORCL_DTA_VALID)
            .reservista(DEFAULT_RESERVISTA)
            .reservistaClasse(DEFAULT_RESERVISTA_CLASSE)
            .numRIC(DEFAULT_NUM_RIC)
            .orgaoEmissorRIC(DEFAULT_ORGAO_EMISSOR_RIC)
            .ufRIC(DEFAULT_UF_RIC)
            .datExpedRIC(DEFAULT_DAT_EXPED_RIC)
            .titEleitorNum(DEFAULT_TIT_ELEITOR_NUM)
            .titEleitorZona(DEFAULT_TIT_ELEITOR_ZONA)
            .titEleitorSecao(DEFAULT_TIT_ELEITOR_SECAO)
            .titEleitorLocal(DEFAULT_TIT_ELEITOR_LOCAL)
            .cnhNum(DEFAULT_CNH_NUM)
            .cnhCategoria(DEFAULT_CNH_CATEGORIA)
            .cnhDatValidade(DEFAULT_CNH_DAT_VALIDADE)
            .cnhDatExped(DEFAULT_CNH_DAT_EXPED)
            .cnhUfEmissor(DEFAULT_CNH_UF_EMISSOR)
            .cnhDatPrimHab(DEFAULT_CNH_DAT_PRIM_HAB)
            .sassepeNum(DEFAULT_SASSEPE_NUM)
            .ufNatural(DEFAULT_UF_NATURAL)
            .primEmprego(DEFAULT_PRIM_EMPREGO)
            .exercMagisterio(DEFAULT_EXERC_MAGISTERIO)
            .tipoLogradouro(DEFAULT_TIPO_LOGRADOURO)
            .descLogradouro(DEFAULT_DESC_LOGRADOURO)
            .nrEndereco(DEFAULT_NR_ENDERECO)
            .complEndereco(DEFAULT_COMPL_ENDERECO)
            .bairroEndereco(DEFAULT_BAIRRO_ENDERECO)
            .cepEndereco(DEFAULT_CEP_ENDERECO)
            .ufEndereco(DEFAULT_UF_ENDERECO)
            .telefone1(DEFAULT_TELEFONE_1)
            .telefone2(DEFAULT_TELEFONE_2)
            .descLograExt(DEFAULT_DESC_LOGRA_EXT)
            .nrLograExt(DEFAULT_NR_LOGRA_EXT)
            .complLograExt(DEFAULT_COMPL_LOGRA_EXT)
            .bairroEndExt(DEFAULT_BAIRRO_END_EXT)
            .cidadeEndExt(DEFAULT_CIDADE_END_EXT)
            .codPostalEndExt(DEFAULT_COD_POSTAL_END_EXT)
            .numRne(DEFAULT_NUM_RNE)
            .orgaoEmissorRNE(DEFAULT_ORGAO_EMISSOR_RNE)
            .ufEmissorRNE(DEFAULT_UF_EMISSOR_RNE)
            .datExpeRNE(DEFAULT_DAT_EXPE_RNE)
            .datChegada(DEFAULT_DAT_CHEGADA)
            .classTrabEstr(DEFAULT_CLASS_TRAB_ESTR)
            .casadoBR(DEFAULT_CASADO_BR)
            .filhosBRr(DEFAULT_FILHOS_B_RR)
            .auxTransporte(DEFAULT_AUX_TRANSPORTE)
            .auxTransLinha1(DEFAULT_AUX_TRANS_LINHA_1)
            .auxTransLinha2(DEFAULT_AUX_TRANS_LINHA_2)
            .auxTransLinha3(DEFAULT_AUX_TRANS_LINHA_3)
            .auxTransLinha4(DEFAULT_AUX_TRANS_LINHA_4)
            .trabAposentado(DEFAULT_TRAB_APOSENTADO);
        return servidor;
    }

    @Before
    public void initTest() {
        servidor = createEntity(em);
    }

    @Test
    @Transactional
    public void createServidor() throws Exception {
        int databaseSizeBeforeCreate = servidorRepository.findAll().size();

        // Create the Servidor
        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isCreated());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeCreate + 1);
        Servidor testServidor = servidorList.get(servidorList.size() - 1);
        assertThat(testServidor.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
        assertThat(testServidor.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testServidor.getNomeSocial()).isEqualTo(DEFAULT_NOME_SOCIAL);
        assertThat(testServidor.getDatNascimento()).isEqualTo(DEFAULT_DAT_NASCIMENTO);
        assertThat(testServidor.getNomePai()).isEqualTo(DEFAULT_NOME_PAI);
        assertThat(testServidor.getNomeMae()).isEqualTo(DEFAULT_NOME_MAE);
        assertThat(testServidor.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testServidor.getCpfPai()).isEqualTo(DEFAULT_CPF_PAI);
        assertThat(testServidor.getCpfMae()).isEqualTo(DEFAULT_CPF_MAE);
        assertThat(testServidor.getRgNum()).isEqualTo(DEFAULT_RG_NUM);
        assertThat(testServidor.getRgUf()).isEqualTo(DEFAULT_RG_UF);
        assertThat(testServidor.getRgOrgaoExp()).isEqualTo(DEFAULT_RG_ORGAO_EXP);
        assertThat(testServidor.getRgDataExp()).isEqualTo(DEFAULT_RG_DATA_EXP);
        assertThat(testServidor.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testServidor.getTipoSangue()).isEqualTo(DEFAULT_TIPO_SANGUE);
        assertThat(testServidor.getFatorRh()).isEqualTo(DEFAULT_FATOR_RH);
        assertThat(testServidor.getDoadorSangue()).isEqualTo(DEFAULT_DOADOR_SANGUE);
        assertThat(testServidor.getDeficienciaFisica()).isEqualTo(DEFAULT_DEFICIENCIA_FISICA);
        assertThat(testServidor.getDeficienciaVisual()).isEqualTo(DEFAULT_DEFICIENCIA_VISUAL);
        assertThat(testServidor.getDeficienciaAuditiva()).isEqualTo(DEFAULT_DEFICIENCIA_AUDITIVA);
        assertThat(testServidor.getDeficienciaMental()).isEqualTo(DEFAULT_DEFICIENCIA_MENTAL);
        assertThat(testServidor.getDeficienciaIntelec()).isEqualTo(DEFAULT_DEFICIENCIA_INTELEC);
        assertThat(testServidor.getDeficReabReadaptado()).isEqualTo(DEFAULT_DEFIC_REAB_READAPTADO);
        assertThat(testServidor.getDeficObservacao()).isEqualTo(DEFAULT_DEFIC_OBSERVACAO);
        assertThat(testServidor.getPreencheCota()).isEqualTo(DEFAULT_PREENCHE_COTA);
        assertThat(testServidor.getEmailPessoal()).isEqualTo(DEFAULT_EMAIL_PESSOAL);
        assertThat(testServidor.getEmailAltern()).isEqualTo(DEFAULT_EMAIL_ALTERN);
        assertThat(testServidor.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testServidor.getCelularOperadora()).isEqualTo(DEFAULT_CELULAR_OPERADORA);
        assertThat(testServidor.getPasep()).isEqualTo(DEFAULT_PASEP);
        assertThat(testServidor.getIpsep()).isEqualTo(DEFAULT_IPSEP);
        assertThat(testServidor.getInss()).isEqualTo(DEFAULT_INSS);
        assertThat(testServidor.getCtpsNum()).isEqualTo(DEFAULT_CTPS_NUM);
        assertThat(testServidor.getCtpsSerie()).isEqualTo(DEFAULT_CTPS_SERIE);
        assertThat(testServidor.getCtpsEmissao()).isEqualTo(DEFAULT_CTPS_EMISSAO);
        assertThat(testServidor.getCtpsUf()).isEqualTo(DEFAULT_CTPS_UF);
        assertThat(testServidor.getOrgaoClasseNum()).isEqualTo(DEFAULT_ORGAO_CLASSE_NUM);
        assertThat(testServidor.getOrgaoClNome()).isEqualTo(DEFAULT_ORGAO_CL_NOME);
        assertThat(testServidor.getOrgaoClUf()).isEqualTo(DEFAULT_ORGAO_CL_UF);
        assertThat(testServidor.getOrclDtaExp()).isEqualTo(DEFAULT_ORCL_DTA_EXP);
        assertThat(testServidor.getOrclDtaValid()).isEqualTo(DEFAULT_ORCL_DTA_VALID);
        assertThat(testServidor.getReservista()).isEqualTo(DEFAULT_RESERVISTA);
        assertThat(testServidor.getReservistaClasse()).isEqualTo(DEFAULT_RESERVISTA_CLASSE);
        assertThat(testServidor.getNumRIC()).isEqualTo(DEFAULT_NUM_RIC);
        assertThat(testServidor.getOrgaoEmissorRIC()).isEqualTo(DEFAULT_ORGAO_EMISSOR_RIC);
        assertThat(testServidor.getUfRIC()).isEqualTo(DEFAULT_UF_RIC);
        assertThat(testServidor.getDatExpedRIC()).isEqualTo(DEFAULT_DAT_EXPED_RIC);
        assertThat(testServidor.getTitEleitorNum()).isEqualTo(DEFAULT_TIT_ELEITOR_NUM);
        assertThat(testServidor.getTitEleitorZona()).isEqualTo(DEFAULT_TIT_ELEITOR_ZONA);
        assertThat(testServidor.getTitEleitorSecao()).isEqualTo(DEFAULT_TIT_ELEITOR_SECAO);
        assertThat(testServidor.getTitEleitorLocal()).isEqualTo(DEFAULT_TIT_ELEITOR_LOCAL);
        assertThat(testServidor.getCnhNum()).isEqualTo(DEFAULT_CNH_NUM);
        assertThat(testServidor.getCnhCategoria()).isEqualTo(DEFAULT_CNH_CATEGORIA);
        assertThat(testServidor.getCnhDatValidade()).isEqualTo(DEFAULT_CNH_DAT_VALIDADE);
        assertThat(testServidor.getCnhDatExped()).isEqualTo(DEFAULT_CNH_DAT_EXPED);
        assertThat(testServidor.getCnhUfEmissor()).isEqualTo(DEFAULT_CNH_UF_EMISSOR);
        assertThat(testServidor.getCnhDatPrimHab()).isEqualTo(DEFAULT_CNH_DAT_PRIM_HAB);
        assertThat(testServidor.getSassepeNum()).isEqualTo(DEFAULT_SASSEPE_NUM);
        assertThat(testServidor.getUfNatural()).isEqualTo(DEFAULT_UF_NATURAL);
        assertThat(testServidor.getPrimEmprego()).isEqualTo(DEFAULT_PRIM_EMPREGO);
        assertThat(testServidor.getExercMagisterio()).isEqualTo(DEFAULT_EXERC_MAGISTERIO);
        assertThat(testServidor.getTipoLogradouro()).isEqualTo(DEFAULT_TIPO_LOGRADOURO);
        assertThat(testServidor.getDescLogradouro()).isEqualTo(DEFAULT_DESC_LOGRADOURO);
        assertThat(testServidor.getNrEndereco()).isEqualTo(DEFAULT_NR_ENDERECO);
        assertThat(testServidor.getComplEndereco()).isEqualTo(DEFAULT_COMPL_ENDERECO);
        assertThat(testServidor.getBairroEndereco()).isEqualTo(DEFAULT_BAIRRO_ENDERECO);
        assertThat(testServidor.getCepEndereco()).isEqualTo(DEFAULT_CEP_ENDERECO);
        assertThat(testServidor.getUfEndereco()).isEqualTo(DEFAULT_UF_ENDERECO);
        assertThat(testServidor.getTelefone1()).isEqualTo(DEFAULT_TELEFONE_1);
        assertThat(testServidor.getTelefone2()).isEqualTo(DEFAULT_TELEFONE_2);
        assertThat(testServidor.getDescLograExt()).isEqualTo(DEFAULT_DESC_LOGRA_EXT);
        assertThat(testServidor.getNrLograExt()).isEqualTo(DEFAULT_NR_LOGRA_EXT);
        assertThat(testServidor.getComplLograExt()).isEqualTo(DEFAULT_COMPL_LOGRA_EXT);
        assertThat(testServidor.getBairroEndExt()).isEqualTo(DEFAULT_BAIRRO_END_EXT);
        assertThat(testServidor.getCidadeEndExt()).isEqualTo(DEFAULT_CIDADE_END_EXT);
        assertThat(testServidor.getCodPostalEndExt()).isEqualTo(DEFAULT_COD_POSTAL_END_EXT);
        assertThat(testServidor.getNumRne()).isEqualTo(DEFAULT_NUM_RNE);
        assertThat(testServidor.getOrgaoEmissorRNE()).isEqualTo(DEFAULT_ORGAO_EMISSOR_RNE);
        assertThat(testServidor.getUfEmissorRNE()).isEqualTo(DEFAULT_UF_EMISSOR_RNE);
        assertThat(testServidor.getDatExpeRNE()).isEqualTo(DEFAULT_DAT_EXPE_RNE);
        assertThat(testServidor.getDatChegada()).isEqualTo(DEFAULT_DAT_CHEGADA);
        assertThat(testServidor.getClassTrabEstr()).isEqualTo(DEFAULT_CLASS_TRAB_ESTR);
        assertThat(testServidor.getCasadoBR()).isEqualTo(DEFAULT_CASADO_BR);
        assertThat(testServidor.getFilhosBRr()).isEqualTo(DEFAULT_FILHOS_B_RR);
        assertThat(testServidor.getAuxTransporte()).isEqualTo(DEFAULT_AUX_TRANSPORTE);
        assertThat(testServidor.getAuxTransLinha1()).isEqualTo(DEFAULT_AUX_TRANS_LINHA_1);
        assertThat(testServidor.getAuxTransLinha2()).isEqualTo(DEFAULT_AUX_TRANS_LINHA_2);
        assertThat(testServidor.getAuxTransLinha3()).isEqualTo(DEFAULT_AUX_TRANS_LINHA_3);
        assertThat(testServidor.getAuxTransLinha4()).isEqualTo(DEFAULT_AUX_TRANS_LINHA_4);
        assertThat(testServidor.getTrabAposentado()).isEqualTo(DEFAULT_TRAB_APOSENTADO);

        // Validate the Servidor in Elasticsearch
        verify(mockServidorSearchRepository, times(1)).save(testServidor);
    }

    @Test
    @Transactional
    public void createServidorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servidorRepository.findAll().size();

        // Create the Servidor with an existing ID
        servidor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Servidor in Elasticsearch
        verify(mockServidorSearchRepository, times(0)).save(servidor);
    }

    @Test
    @Transactional
    public void checkMatriculaIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setMatricula(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setUsername(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setDatNascimento(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomePaiIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setNomePai(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeMaeIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setNomeMae(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setCpf(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRgNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setRgNum(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRgUfIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setRgUf(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRgOrgaoExpIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setRgOrgaoExp(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRgDataExpIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setRgDataExp(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setSexo(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoSangueIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setTipoSangue(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFatorRhIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setFatorRh(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDoadorSangueIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setDoadorSangue(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeficienciaFisicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setDeficienciaFisica(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeficienciaVisualIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setDeficienciaVisual(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeficienciaAuditivaIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setDeficienciaAuditiva(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeficienciaMentalIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setDeficienciaMental(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeficienciaIntelecIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setDeficienciaIntelec(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeficReabReadaptadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setDeficReabReadaptado(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreencheCotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setPreencheCota(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailPessoalIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setEmailPessoal(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasepIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setPasep(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitEleitorNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setTitEleitorNum(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitEleitorZonaIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setTitEleitorZona(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitEleitorSecaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setTitEleitorSecao(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitEleitorLocalIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setTitEleitorLocal(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoLogradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setTipoLogradouro(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescLogradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setDescLogradouro(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNrEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setNrEndereco(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComplEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setComplEndereco(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBairroEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setBairroEndereco(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCepEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setCepEndereco(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUfEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().size();
        // set the field null
        servidor.setUfEndereco(null);

        // Create the Servidor, which fails.

        restServidorMockMvc.perform(post("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServidors() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        // Get all the servidorList
        restServidorMockMvc.perform(get("/api/servidors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA.intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].nomeSocial").value(hasItem(DEFAULT_NOME_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].datNascimento").value(hasItem(DEFAULT_DAT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nomePai").value(hasItem(DEFAULT_NOME_PAI.toString())))
            .andExpect(jsonPath("$.[*].nomeMae").value(hasItem(DEFAULT_NOME_MAE.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.intValue())))
            .andExpect(jsonPath("$.[*].cpfPai").value(hasItem(DEFAULT_CPF_PAI.intValue())))
            .andExpect(jsonPath("$.[*].cpfMae").value(hasItem(DEFAULT_CPF_MAE.intValue())))
            .andExpect(jsonPath("$.[*].rgNum").value(hasItem(DEFAULT_RG_NUM.toString())))
            .andExpect(jsonPath("$.[*].rgUf").value(hasItem(DEFAULT_RG_UF.toString())))
            .andExpect(jsonPath("$.[*].rgOrgaoExp").value(hasItem(DEFAULT_RG_ORGAO_EXP.toString())))
            .andExpect(jsonPath("$.[*].rgDataExp").value(hasItem(DEFAULT_RG_DATA_EXP.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].tipoSangue").value(hasItem(DEFAULT_TIPO_SANGUE.toString())))
            .andExpect(jsonPath("$.[*].fatorRh").value(hasItem(DEFAULT_FATOR_RH.toString())))
            .andExpect(jsonPath("$.[*].doadorSangue").value(hasItem(DEFAULT_DOADOR_SANGUE.toString())))
            .andExpect(jsonPath("$.[*].deficienciaFisica").value(hasItem(DEFAULT_DEFICIENCIA_FISICA.toString())))
            .andExpect(jsonPath("$.[*].deficienciaVisual").value(hasItem(DEFAULT_DEFICIENCIA_VISUAL.toString())))
            .andExpect(jsonPath("$.[*].deficienciaAuditiva").value(hasItem(DEFAULT_DEFICIENCIA_AUDITIVA.toString())))
            .andExpect(jsonPath("$.[*].deficienciaMental").value(hasItem(DEFAULT_DEFICIENCIA_MENTAL.toString())))
            .andExpect(jsonPath("$.[*].deficienciaIntelec").value(hasItem(DEFAULT_DEFICIENCIA_INTELEC.toString())))
            .andExpect(jsonPath("$.[*].deficReabReadaptado").value(hasItem(DEFAULT_DEFIC_REAB_READAPTADO.toString())))
            .andExpect(jsonPath("$.[*].deficObservacao").value(hasItem(DEFAULT_DEFIC_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].preencheCota").value(hasItem(DEFAULT_PREENCHE_COTA.toString())))
            .andExpect(jsonPath("$.[*].emailPessoal").value(hasItem(DEFAULT_EMAIL_PESSOAL.toString())))
            .andExpect(jsonPath("$.[*].emailAltern").value(hasItem(DEFAULT_EMAIL_ALTERN.toString())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())))
            .andExpect(jsonPath("$.[*].celularOperadora").value(hasItem(DEFAULT_CELULAR_OPERADORA.toString())))
            .andExpect(jsonPath("$.[*].pasep").value(hasItem(DEFAULT_PASEP.toString())))
            .andExpect(jsonPath("$.[*].ipsep").value(hasItem(DEFAULT_IPSEP.toString())))
            .andExpect(jsonPath("$.[*].inss").value(hasItem(DEFAULT_INSS.toString())))
            .andExpect(jsonPath("$.[*].ctpsNum").value(hasItem(DEFAULT_CTPS_NUM.toString())))
            .andExpect(jsonPath("$.[*].ctpsSerie").value(hasItem(DEFAULT_CTPS_SERIE.toString())))
            .andExpect(jsonPath("$.[*].ctpsEmissao").value(hasItem(DEFAULT_CTPS_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].ctpsUf").value(hasItem(DEFAULT_CTPS_UF.toString())))
            .andExpect(jsonPath("$.[*].orgaoClasseNum").value(hasItem(DEFAULT_ORGAO_CLASSE_NUM.toString())))
            .andExpect(jsonPath("$.[*].orgaoClNome").value(hasItem(DEFAULT_ORGAO_CL_NOME.toString())))
            .andExpect(jsonPath("$.[*].orgaoClUf").value(hasItem(DEFAULT_ORGAO_CL_UF.toString())))
            .andExpect(jsonPath("$.[*].orclDtaExp").value(hasItem(DEFAULT_ORCL_DTA_EXP.toString())))
            .andExpect(jsonPath("$.[*].orclDtaValid").value(hasItem(DEFAULT_ORCL_DTA_VALID.toString())))
            .andExpect(jsonPath("$.[*].reservista").value(hasItem(DEFAULT_RESERVISTA.toString())))
            .andExpect(jsonPath("$.[*].reservistaClasse").value(hasItem(DEFAULT_RESERVISTA_CLASSE.toString())))
            .andExpect(jsonPath("$.[*].numRIC").value(hasItem(DEFAULT_NUM_RIC.toString())))
            .andExpect(jsonPath("$.[*].orgaoEmissorRIC").value(hasItem(DEFAULT_ORGAO_EMISSOR_RIC.toString())))
            .andExpect(jsonPath("$.[*].ufRIC").value(hasItem(DEFAULT_UF_RIC.toString())))
            .andExpect(jsonPath("$.[*].datExpedRIC").value(hasItem(DEFAULT_DAT_EXPED_RIC.toString())))
            .andExpect(jsonPath("$.[*].titEleitorNum").value(hasItem(DEFAULT_TIT_ELEITOR_NUM.toString())))
            .andExpect(jsonPath("$.[*].titEleitorZona").value(hasItem(DEFAULT_TIT_ELEITOR_ZONA.toString())))
            .andExpect(jsonPath("$.[*].titEleitorSecao").value(hasItem(DEFAULT_TIT_ELEITOR_SECAO.toString())))
            .andExpect(jsonPath("$.[*].titEleitorLocal").value(hasItem(DEFAULT_TIT_ELEITOR_LOCAL.toString())))
            .andExpect(jsonPath("$.[*].cnhNum").value(hasItem(DEFAULT_CNH_NUM.toString())))
            .andExpect(jsonPath("$.[*].cnhCategoria").value(hasItem(DEFAULT_CNH_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].cnhDatValidade").value(hasItem(DEFAULT_CNH_DAT_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].cnhDatExped").value(hasItem(DEFAULT_CNH_DAT_EXPED.toString())))
            .andExpect(jsonPath("$.[*].cnhUfEmissor").value(hasItem(DEFAULT_CNH_UF_EMISSOR.toString())))
            .andExpect(jsonPath("$.[*].cnhDatPrimHab").value(hasItem(DEFAULT_CNH_DAT_PRIM_HAB.toString())))
            .andExpect(jsonPath("$.[*].sassepeNum").value(hasItem(DEFAULT_SASSEPE_NUM.toString())))
            .andExpect(jsonPath("$.[*].ufNatural").value(hasItem(DEFAULT_UF_NATURAL.toString())))
            .andExpect(jsonPath("$.[*].primEmprego").value(hasItem(DEFAULT_PRIM_EMPREGO.toString())))
            .andExpect(jsonPath("$.[*].exercMagisterio").value(hasItem(DEFAULT_EXERC_MAGISTERIO.toString())))
            .andExpect(jsonPath("$.[*].tipoLogradouro").value(hasItem(DEFAULT_TIPO_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].descLogradouro").value(hasItem(DEFAULT_DESC_LOGRADOURO.toString())))
            .andExpect(jsonPath("$.[*].nrEndereco").value(hasItem(DEFAULT_NR_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].complEndereco").value(hasItem(DEFAULT_COMPL_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].bairroEndereco").value(hasItem(DEFAULT_BAIRRO_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].cepEndereco").value(hasItem(DEFAULT_CEP_ENDERECO)))
            .andExpect(jsonPath("$.[*].ufEndereco").value(hasItem(DEFAULT_UF_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].telefone1").value(hasItem(DEFAULT_TELEFONE_1.toString())))
            .andExpect(jsonPath("$.[*].telefone2").value(hasItem(DEFAULT_TELEFONE_2.toString())))
            .andExpect(jsonPath("$.[*].descLograExt").value(hasItem(DEFAULT_DESC_LOGRA_EXT.toString())))
            .andExpect(jsonPath("$.[*].nrLograExt").value(hasItem(DEFAULT_NR_LOGRA_EXT.toString())))
            .andExpect(jsonPath("$.[*].complLograExt").value(hasItem(DEFAULT_COMPL_LOGRA_EXT.toString())))
            .andExpect(jsonPath("$.[*].bairroEndExt").value(hasItem(DEFAULT_BAIRRO_END_EXT.toString())))
            .andExpect(jsonPath("$.[*].cidadeEndExt").value(hasItem(DEFAULT_CIDADE_END_EXT.toString())))
            .andExpect(jsonPath("$.[*].codPostalEndExt").value(hasItem(DEFAULT_COD_POSTAL_END_EXT.toString())))
            .andExpect(jsonPath("$.[*].numRne").value(hasItem(DEFAULT_NUM_RNE.toString())))
            .andExpect(jsonPath("$.[*].orgaoEmissorRNE").value(hasItem(DEFAULT_ORGAO_EMISSOR_RNE.toString())))
            .andExpect(jsonPath("$.[*].ufEmissorRNE").value(hasItem(DEFAULT_UF_EMISSOR_RNE.toString())))
            .andExpect(jsonPath("$.[*].datExpeRNE").value(hasItem(DEFAULT_DAT_EXPE_RNE.toString())))
            .andExpect(jsonPath("$.[*].datChegada").value(hasItem(DEFAULT_DAT_CHEGADA.toString())))
            .andExpect(jsonPath("$.[*].classTrabEstr").value(hasItem(DEFAULT_CLASS_TRAB_ESTR)))
            .andExpect(jsonPath("$.[*].casadoBR").value(hasItem(DEFAULT_CASADO_BR.toString())))
            .andExpect(jsonPath("$.[*].filhosBRr").value(hasItem(DEFAULT_FILHOS_B_RR.toString())))
            .andExpect(jsonPath("$.[*].auxTransporte").value(hasItem(DEFAULT_AUX_TRANSPORTE.toString())))
            .andExpect(jsonPath("$.[*].auxTransLinha1").value(hasItem(DEFAULT_AUX_TRANS_LINHA_1.toString())))
            .andExpect(jsonPath("$.[*].auxTransLinha2").value(hasItem(DEFAULT_AUX_TRANS_LINHA_2.toString())))
            .andExpect(jsonPath("$.[*].auxTransLinha3").value(hasItem(DEFAULT_AUX_TRANS_LINHA_3.toString())))
            .andExpect(jsonPath("$.[*].auxTransLinha4").value(hasItem(DEFAULT_AUX_TRANS_LINHA_4.toString())))
            .andExpect(jsonPath("$.[*].trabAposentado").value(hasItem(DEFAULT_TRAB_APOSENTADO.toString())));
    }
    

    @Test
    @Transactional
    public void getServidor() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        // Get the servidor
        restServidorMockMvc.perform(get("/api/servidors/{id}", servidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(servidor.getId().intValue()))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA.intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.nomeSocial").value(DEFAULT_NOME_SOCIAL.toString()))
            .andExpect(jsonPath("$.datNascimento").value(DEFAULT_DAT_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.nomePai").value(DEFAULT_NOME_PAI.toString()))
            .andExpect(jsonPath("$.nomeMae").value(DEFAULT_NOME_MAE.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.intValue()))
            .andExpect(jsonPath("$.cpfPai").value(DEFAULT_CPF_PAI.intValue()))
            .andExpect(jsonPath("$.cpfMae").value(DEFAULT_CPF_MAE.intValue()))
            .andExpect(jsonPath("$.rgNum").value(DEFAULT_RG_NUM.toString()))
            .andExpect(jsonPath("$.rgUf").value(DEFAULT_RG_UF.toString()))
            .andExpect(jsonPath("$.rgOrgaoExp").value(DEFAULT_RG_ORGAO_EXP.toString()))
            .andExpect(jsonPath("$.rgDataExp").value(DEFAULT_RG_DATA_EXP.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.tipoSangue").value(DEFAULT_TIPO_SANGUE.toString()))
            .andExpect(jsonPath("$.fatorRh").value(DEFAULT_FATOR_RH.toString()))
            .andExpect(jsonPath("$.doadorSangue").value(DEFAULT_DOADOR_SANGUE.toString()))
            .andExpect(jsonPath("$.deficienciaFisica").value(DEFAULT_DEFICIENCIA_FISICA.toString()))
            .andExpect(jsonPath("$.deficienciaVisual").value(DEFAULT_DEFICIENCIA_VISUAL.toString()))
            .andExpect(jsonPath("$.deficienciaAuditiva").value(DEFAULT_DEFICIENCIA_AUDITIVA.toString()))
            .andExpect(jsonPath("$.deficienciaMental").value(DEFAULT_DEFICIENCIA_MENTAL.toString()))
            .andExpect(jsonPath("$.deficienciaIntelec").value(DEFAULT_DEFICIENCIA_INTELEC.toString()))
            .andExpect(jsonPath("$.deficReabReadaptado").value(DEFAULT_DEFIC_REAB_READAPTADO.toString()))
            .andExpect(jsonPath("$.deficObservacao").value(DEFAULT_DEFIC_OBSERVACAO.toString()))
            .andExpect(jsonPath("$.preencheCota").value(DEFAULT_PREENCHE_COTA.toString()))
            .andExpect(jsonPath("$.emailPessoal").value(DEFAULT_EMAIL_PESSOAL.toString()))
            .andExpect(jsonPath("$.emailAltern").value(DEFAULT_EMAIL_ALTERN.toString()))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR.toString()))
            .andExpect(jsonPath("$.celularOperadora").value(DEFAULT_CELULAR_OPERADORA.toString()))
            .andExpect(jsonPath("$.pasep").value(DEFAULT_PASEP.toString()))
            .andExpect(jsonPath("$.ipsep").value(DEFAULT_IPSEP.toString()))
            .andExpect(jsonPath("$.inss").value(DEFAULT_INSS.toString()))
            .andExpect(jsonPath("$.ctpsNum").value(DEFAULT_CTPS_NUM.toString()))
            .andExpect(jsonPath("$.ctpsSerie").value(DEFAULT_CTPS_SERIE.toString()))
            .andExpect(jsonPath("$.ctpsEmissao").value(DEFAULT_CTPS_EMISSAO.toString()))
            .andExpect(jsonPath("$.ctpsUf").value(DEFAULT_CTPS_UF.toString()))
            .andExpect(jsonPath("$.orgaoClasseNum").value(DEFAULT_ORGAO_CLASSE_NUM.toString()))
            .andExpect(jsonPath("$.orgaoClNome").value(DEFAULT_ORGAO_CL_NOME.toString()))
            .andExpect(jsonPath("$.orgaoClUf").value(DEFAULT_ORGAO_CL_UF.toString()))
            .andExpect(jsonPath("$.orclDtaExp").value(DEFAULT_ORCL_DTA_EXP.toString()))
            .andExpect(jsonPath("$.orclDtaValid").value(DEFAULT_ORCL_DTA_VALID.toString()))
            .andExpect(jsonPath("$.reservista").value(DEFAULT_RESERVISTA.toString()))
            .andExpect(jsonPath("$.reservistaClasse").value(DEFAULT_RESERVISTA_CLASSE.toString()))
            .andExpect(jsonPath("$.numRIC").value(DEFAULT_NUM_RIC.toString()))
            .andExpect(jsonPath("$.orgaoEmissorRIC").value(DEFAULT_ORGAO_EMISSOR_RIC.toString()))
            .andExpect(jsonPath("$.ufRIC").value(DEFAULT_UF_RIC.toString()))
            .andExpect(jsonPath("$.datExpedRIC").value(DEFAULT_DAT_EXPED_RIC.toString()))
            .andExpect(jsonPath("$.titEleitorNum").value(DEFAULT_TIT_ELEITOR_NUM.toString()))
            .andExpect(jsonPath("$.titEleitorZona").value(DEFAULT_TIT_ELEITOR_ZONA.toString()))
            .andExpect(jsonPath("$.titEleitorSecao").value(DEFAULT_TIT_ELEITOR_SECAO.toString()))
            .andExpect(jsonPath("$.titEleitorLocal").value(DEFAULT_TIT_ELEITOR_LOCAL.toString()))
            .andExpect(jsonPath("$.cnhNum").value(DEFAULT_CNH_NUM.toString()))
            .andExpect(jsonPath("$.cnhCategoria").value(DEFAULT_CNH_CATEGORIA.toString()))
            .andExpect(jsonPath("$.cnhDatValidade").value(DEFAULT_CNH_DAT_VALIDADE.toString()))
            .andExpect(jsonPath("$.cnhDatExped").value(DEFAULT_CNH_DAT_EXPED.toString()))
            .andExpect(jsonPath("$.cnhUfEmissor").value(DEFAULT_CNH_UF_EMISSOR.toString()))
            .andExpect(jsonPath("$.cnhDatPrimHab").value(DEFAULT_CNH_DAT_PRIM_HAB.toString()))
            .andExpect(jsonPath("$.sassepeNum").value(DEFAULT_SASSEPE_NUM.toString()))
            .andExpect(jsonPath("$.ufNatural").value(DEFAULT_UF_NATURAL.toString()))
            .andExpect(jsonPath("$.primEmprego").value(DEFAULT_PRIM_EMPREGO.toString()))
            .andExpect(jsonPath("$.exercMagisterio").value(DEFAULT_EXERC_MAGISTERIO.toString()))
            .andExpect(jsonPath("$.tipoLogradouro").value(DEFAULT_TIPO_LOGRADOURO))
            .andExpect(jsonPath("$.descLogradouro").value(DEFAULT_DESC_LOGRADOURO.toString()))
            .andExpect(jsonPath("$.nrEndereco").value(DEFAULT_NR_ENDERECO.toString()))
            .andExpect(jsonPath("$.complEndereco").value(DEFAULT_COMPL_ENDERECO.toString()))
            .andExpect(jsonPath("$.bairroEndereco").value(DEFAULT_BAIRRO_ENDERECO.toString()))
            .andExpect(jsonPath("$.cepEndereco").value(DEFAULT_CEP_ENDERECO))
            .andExpect(jsonPath("$.ufEndereco").value(DEFAULT_UF_ENDERECO.toString()))
            .andExpect(jsonPath("$.telefone1").value(DEFAULT_TELEFONE_1.toString()))
            .andExpect(jsonPath("$.telefone2").value(DEFAULT_TELEFONE_2.toString()))
            .andExpect(jsonPath("$.descLograExt").value(DEFAULT_DESC_LOGRA_EXT.toString()))
            .andExpect(jsonPath("$.nrLograExt").value(DEFAULT_NR_LOGRA_EXT.toString()))
            .andExpect(jsonPath("$.complLograExt").value(DEFAULT_COMPL_LOGRA_EXT.toString()))
            .andExpect(jsonPath("$.bairroEndExt").value(DEFAULT_BAIRRO_END_EXT.toString()))
            .andExpect(jsonPath("$.cidadeEndExt").value(DEFAULT_CIDADE_END_EXT.toString()))
            .andExpect(jsonPath("$.codPostalEndExt").value(DEFAULT_COD_POSTAL_END_EXT.toString()))
            .andExpect(jsonPath("$.numRne").value(DEFAULT_NUM_RNE.toString()))
            .andExpect(jsonPath("$.orgaoEmissorRNE").value(DEFAULT_ORGAO_EMISSOR_RNE.toString()))
            .andExpect(jsonPath("$.ufEmissorRNE").value(DEFAULT_UF_EMISSOR_RNE.toString()))
            .andExpect(jsonPath("$.datExpeRNE").value(DEFAULT_DAT_EXPE_RNE.toString()))
            .andExpect(jsonPath("$.datChegada").value(DEFAULT_DAT_CHEGADA.toString()))
            .andExpect(jsonPath("$.classTrabEstr").value(DEFAULT_CLASS_TRAB_ESTR))
            .andExpect(jsonPath("$.casadoBR").value(DEFAULT_CASADO_BR.toString()))
            .andExpect(jsonPath("$.filhosBRr").value(DEFAULT_FILHOS_B_RR.toString()))
            .andExpect(jsonPath("$.auxTransporte").value(DEFAULT_AUX_TRANSPORTE.toString()))
            .andExpect(jsonPath("$.auxTransLinha1").value(DEFAULT_AUX_TRANS_LINHA_1.toString()))
            .andExpect(jsonPath("$.auxTransLinha2").value(DEFAULT_AUX_TRANS_LINHA_2.toString()))
            .andExpect(jsonPath("$.auxTransLinha3").value(DEFAULT_AUX_TRANS_LINHA_3.toString()))
            .andExpect(jsonPath("$.auxTransLinha4").value(DEFAULT_AUX_TRANS_LINHA_4.toString()))
            .andExpect(jsonPath("$.trabAposentado").value(DEFAULT_TRAB_APOSENTADO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingServidor() throws Exception {
        // Get the servidor
        restServidorMockMvc.perform(get("/api/servidors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServidor() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();

        // Update the servidor
        Servidor updatedServidor = servidorRepository.findById(servidor.getId()).get();
        // Disconnect from session so that the updates on updatedServidor are not directly saved in db
        em.detach(updatedServidor);
        updatedServidor
            .matricula(UPDATED_MATRICULA)
            .username(UPDATED_USERNAME)
            .nomeSocial(UPDATED_NOME_SOCIAL)
            .datNascimento(UPDATED_DAT_NASCIMENTO)
            .nomePai(UPDATED_NOME_PAI)
            .nomeMae(UPDATED_NOME_MAE)
            .cpf(UPDATED_CPF)
            .cpfPai(UPDATED_CPF_PAI)
            .cpfMae(UPDATED_CPF_MAE)
            .rgNum(UPDATED_RG_NUM)
            .rgUf(UPDATED_RG_UF)
            .rgOrgaoExp(UPDATED_RG_ORGAO_EXP)
            .rgDataExp(UPDATED_RG_DATA_EXP)
            .sexo(UPDATED_SEXO)
            .tipoSangue(UPDATED_TIPO_SANGUE)
            .fatorRh(UPDATED_FATOR_RH)
            .doadorSangue(UPDATED_DOADOR_SANGUE)
            .deficienciaFisica(UPDATED_DEFICIENCIA_FISICA)
            .deficienciaVisual(UPDATED_DEFICIENCIA_VISUAL)
            .deficienciaAuditiva(UPDATED_DEFICIENCIA_AUDITIVA)
            .deficienciaMental(UPDATED_DEFICIENCIA_MENTAL)
            .deficienciaIntelec(UPDATED_DEFICIENCIA_INTELEC)
            .deficReabReadaptado(UPDATED_DEFIC_REAB_READAPTADO)
            .deficObservacao(UPDATED_DEFIC_OBSERVACAO)
            .preencheCota(UPDATED_PREENCHE_COTA)
            .emailPessoal(UPDATED_EMAIL_PESSOAL)
            .emailAltern(UPDATED_EMAIL_ALTERN)
            .celular(UPDATED_CELULAR)
            .celularOperadora(UPDATED_CELULAR_OPERADORA)
            .pasep(UPDATED_PASEP)
            .ipsep(UPDATED_IPSEP)
            .inss(UPDATED_INSS)
            .ctpsNum(UPDATED_CTPS_NUM)
            .ctpsSerie(UPDATED_CTPS_SERIE)
            .ctpsEmissao(UPDATED_CTPS_EMISSAO)
            .ctpsUf(UPDATED_CTPS_UF)
            .orgaoClasseNum(UPDATED_ORGAO_CLASSE_NUM)
            .orgaoClNome(UPDATED_ORGAO_CL_NOME)
            .orgaoClUf(UPDATED_ORGAO_CL_UF)
            .orclDtaExp(UPDATED_ORCL_DTA_EXP)
            .orclDtaValid(UPDATED_ORCL_DTA_VALID)
            .reservista(UPDATED_RESERVISTA)
            .reservistaClasse(UPDATED_RESERVISTA_CLASSE)
            .numRIC(UPDATED_NUM_RIC)
            .orgaoEmissorRIC(UPDATED_ORGAO_EMISSOR_RIC)
            .ufRIC(UPDATED_UF_RIC)
            .datExpedRIC(UPDATED_DAT_EXPED_RIC)
            .titEleitorNum(UPDATED_TIT_ELEITOR_NUM)
            .titEleitorZona(UPDATED_TIT_ELEITOR_ZONA)
            .titEleitorSecao(UPDATED_TIT_ELEITOR_SECAO)
            .titEleitorLocal(UPDATED_TIT_ELEITOR_LOCAL)
            .cnhNum(UPDATED_CNH_NUM)
            .cnhCategoria(UPDATED_CNH_CATEGORIA)
            .cnhDatValidade(UPDATED_CNH_DAT_VALIDADE)
            .cnhDatExped(UPDATED_CNH_DAT_EXPED)
            .cnhUfEmissor(UPDATED_CNH_UF_EMISSOR)
            .cnhDatPrimHab(UPDATED_CNH_DAT_PRIM_HAB)
            .sassepeNum(UPDATED_SASSEPE_NUM)
            .ufNatural(UPDATED_UF_NATURAL)
            .primEmprego(UPDATED_PRIM_EMPREGO)
            .exercMagisterio(UPDATED_EXERC_MAGISTERIO)
            .tipoLogradouro(UPDATED_TIPO_LOGRADOURO)
            .descLogradouro(UPDATED_DESC_LOGRADOURO)
            .nrEndereco(UPDATED_NR_ENDERECO)
            .complEndereco(UPDATED_COMPL_ENDERECO)
            .bairroEndereco(UPDATED_BAIRRO_ENDERECO)
            .cepEndereco(UPDATED_CEP_ENDERECO)
            .ufEndereco(UPDATED_UF_ENDERECO)
            .telefone1(UPDATED_TELEFONE_1)
            .telefone2(UPDATED_TELEFONE_2)
            .descLograExt(UPDATED_DESC_LOGRA_EXT)
            .nrLograExt(UPDATED_NR_LOGRA_EXT)
            .complLograExt(UPDATED_COMPL_LOGRA_EXT)
            .bairroEndExt(UPDATED_BAIRRO_END_EXT)
            .cidadeEndExt(UPDATED_CIDADE_END_EXT)
            .codPostalEndExt(UPDATED_COD_POSTAL_END_EXT)
            .numRne(UPDATED_NUM_RNE)
            .orgaoEmissorRNE(UPDATED_ORGAO_EMISSOR_RNE)
            .ufEmissorRNE(UPDATED_UF_EMISSOR_RNE)
            .datExpeRNE(UPDATED_DAT_EXPE_RNE)
            .datChegada(UPDATED_DAT_CHEGADA)
            .classTrabEstr(UPDATED_CLASS_TRAB_ESTR)
            .casadoBR(UPDATED_CASADO_BR)
            .filhosBRr(UPDATED_FILHOS_B_RR)
            .auxTransporte(UPDATED_AUX_TRANSPORTE)
            .auxTransLinha1(UPDATED_AUX_TRANS_LINHA_1)
            .auxTransLinha2(UPDATED_AUX_TRANS_LINHA_2)
            .auxTransLinha3(UPDATED_AUX_TRANS_LINHA_3)
            .auxTransLinha4(UPDATED_AUX_TRANS_LINHA_4)
            .trabAposentado(UPDATED_TRAB_APOSENTADO);

        restServidorMockMvc.perform(put("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServidor)))
            .andExpect(status().isOk());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
        Servidor testServidor = servidorList.get(servidorList.size() - 1);
        assertThat(testServidor.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testServidor.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testServidor.getNomeSocial()).isEqualTo(UPDATED_NOME_SOCIAL);
        assertThat(testServidor.getDatNascimento()).isEqualTo(UPDATED_DAT_NASCIMENTO);
        assertThat(testServidor.getNomePai()).isEqualTo(UPDATED_NOME_PAI);
        assertThat(testServidor.getNomeMae()).isEqualTo(UPDATED_NOME_MAE);
        assertThat(testServidor.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testServidor.getCpfPai()).isEqualTo(UPDATED_CPF_PAI);
        assertThat(testServidor.getCpfMae()).isEqualTo(UPDATED_CPF_MAE);
        assertThat(testServidor.getRgNum()).isEqualTo(UPDATED_RG_NUM);
        assertThat(testServidor.getRgUf()).isEqualTo(UPDATED_RG_UF);
        assertThat(testServidor.getRgOrgaoExp()).isEqualTo(UPDATED_RG_ORGAO_EXP);
        assertThat(testServidor.getRgDataExp()).isEqualTo(UPDATED_RG_DATA_EXP);
        assertThat(testServidor.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testServidor.getTipoSangue()).isEqualTo(UPDATED_TIPO_SANGUE);
        assertThat(testServidor.getFatorRh()).isEqualTo(UPDATED_FATOR_RH);
        assertThat(testServidor.getDoadorSangue()).isEqualTo(UPDATED_DOADOR_SANGUE);
        assertThat(testServidor.getDeficienciaFisica()).isEqualTo(UPDATED_DEFICIENCIA_FISICA);
        assertThat(testServidor.getDeficienciaVisual()).isEqualTo(UPDATED_DEFICIENCIA_VISUAL);
        assertThat(testServidor.getDeficienciaAuditiva()).isEqualTo(UPDATED_DEFICIENCIA_AUDITIVA);
        assertThat(testServidor.getDeficienciaMental()).isEqualTo(UPDATED_DEFICIENCIA_MENTAL);
        assertThat(testServidor.getDeficienciaIntelec()).isEqualTo(UPDATED_DEFICIENCIA_INTELEC);
        assertThat(testServidor.getDeficReabReadaptado()).isEqualTo(UPDATED_DEFIC_REAB_READAPTADO);
        assertThat(testServidor.getDeficObservacao()).isEqualTo(UPDATED_DEFIC_OBSERVACAO);
        assertThat(testServidor.getPreencheCota()).isEqualTo(UPDATED_PREENCHE_COTA);
        assertThat(testServidor.getEmailPessoal()).isEqualTo(UPDATED_EMAIL_PESSOAL);
        assertThat(testServidor.getEmailAltern()).isEqualTo(UPDATED_EMAIL_ALTERN);
        assertThat(testServidor.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testServidor.getCelularOperadora()).isEqualTo(UPDATED_CELULAR_OPERADORA);
        assertThat(testServidor.getPasep()).isEqualTo(UPDATED_PASEP);
        assertThat(testServidor.getIpsep()).isEqualTo(UPDATED_IPSEP);
        assertThat(testServidor.getInss()).isEqualTo(UPDATED_INSS);
        assertThat(testServidor.getCtpsNum()).isEqualTo(UPDATED_CTPS_NUM);
        assertThat(testServidor.getCtpsSerie()).isEqualTo(UPDATED_CTPS_SERIE);
        assertThat(testServidor.getCtpsEmissao()).isEqualTo(UPDATED_CTPS_EMISSAO);
        assertThat(testServidor.getCtpsUf()).isEqualTo(UPDATED_CTPS_UF);
        assertThat(testServidor.getOrgaoClasseNum()).isEqualTo(UPDATED_ORGAO_CLASSE_NUM);
        assertThat(testServidor.getOrgaoClNome()).isEqualTo(UPDATED_ORGAO_CL_NOME);
        assertThat(testServidor.getOrgaoClUf()).isEqualTo(UPDATED_ORGAO_CL_UF);
        assertThat(testServidor.getOrclDtaExp()).isEqualTo(UPDATED_ORCL_DTA_EXP);
        assertThat(testServidor.getOrclDtaValid()).isEqualTo(UPDATED_ORCL_DTA_VALID);
        assertThat(testServidor.getReservista()).isEqualTo(UPDATED_RESERVISTA);
        assertThat(testServidor.getReservistaClasse()).isEqualTo(UPDATED_RESERVISTA_CLASSE);
        assertThat(testServidor.getNumRIC()).isEqualTo(UPDATED_NUM_RIC);
        assertThat(testServidor.getOrgaoEmissorRIC()).isEqualTo(UPDATED_ORGAO_EMISSOR_RIC);
        assertThat(testServidor.getUfRIC()).isEqualTo(UPDATED_UF_RIC);
        assertThat(testServidor.getDatExpedRIC()).isEqualTo(UPDATED_DAT_EXPED_RIC);
        assertThat(testServidor.getTitEleitorNum()).isEqualTo(UPDATED_TIT_ELEITOR_NUM);
        assertThat(testServidor.getTitEleitorZona()).isEqualTo(UPDATED_TIT_ELEITOR_ZONA);
        assertThat(testServidor.getTitEleitorSecao()).isEqualTo(UPDATED_TIT_ELEITOR_SECAO);
        assertThat(testServidor.getTitEleitorLocal()).isEqualTo(UPDATED_TIT_ELEITOR_LOCAL);
        assertThat(testServidor.getCnhNum()).isEqualTo(UPDATED_CNH_NUM);
        assertThat(testServidor.getCnhCategoria()).isEqualTo(UPDATED_CNH_CATEGORIA);
        assertThat(testServidor.getCnhDatValidade()).isEqualTo(UPDATED_CNH_DAT_VALIDADE);
        assertThat(testServidor.getCnhDatExped()).isEqualTo(UPDATED_CNH_DAT_EXPED);
        assertThat(testServidor.getCnhUfEmissor()).isEqualTo(UPDATED_CNH_UF_EMISSOR);
        assertThat(testServidor.getCnhDatPrimHab()).isEqualTo(UPDATED_CNH_DAT_PRIM_HAB);
        assertThat(testServidor.getSassepeNum()).isEqualTo(UPDATED_SASSEPE_NUM);
        assertThat(testServidor.getUfNatural()).isEqualTo(UPDATED_UF_NATURAL);
        assertThat(testServidor.getPrimEmprego()).isEqualTo(UPDATED_PRIM_EMPREGO);
        assertThat(testServidor.getExercMagisterio()).isEqualTo(UPDATED_EXERC_MAGISTERIO);
        assertThat(testServidor.getTipoLogradouro()).isEqualTo(UPDATED_TIPO_LOGRADOURO);
        assertThat(testServidor.getDescLogradouro()).isEqualTo(UPDATED_DESC_LOGRADOURO);
        assertThat(testServidor.getNrEndereco()).isEqualTo(UPDATED_NR_ENDERECO);
        assertThat(testServidor.getComplEndereco()).isEqualTo(UPDATED_COMPL_ENDERECO);
        assertThat(testServidor.getBairroEndereco()).isEqualTo(UPDATED_BAIRRO_ENDERECO);
        assertThat(testServidor.getCepEndereco()).isEqualTo(UPDATED_CEP_ENDERECO);
        assertThat(testServidor.getUfEndereco()).isEqualTo(UPDATED_UF_ENDERECO);
        assertThat(testServidor.getTelefone1()).isEqualTo(UPDATED_TELEFONE_1);
        assertThat(testServidor.getTelefone2()).isEqualTo(UPDATED_TELEFONE_2);
        assertThat(testServidor.getDescLograExt()).isEqualTo(UPDATED_DESC_LOGRA_EXT);
        assertThat(testServidor.getNrLograExt()).isEqualTo(UPDATED_NR_LOGRA_EXT);
        assertThat(testServidor.getComplLograExt()).isEqualTo(UPDATED_COMPL_LOGRA_EXT);
        assertThat(testServidor.getBairroEndExt()).isEqualTo(UPDATED_BAIRRO_END_EXT);
        assertThat(testServidor.getCidadeEndExt()).isEqualTo(UPDATED_CIDADE_END_EXT);
        assertThat(testServidor.getCodPostalEndExt()).isEqualTo(UPDATED_COD_POSTAL_END_EXT);
        assertThat(testServidor.getNumRne()).isEqualTo(UPDATED_NUM_RNE);
        assertThat(testServidor.getOrgaoEmissorRNE()).isEqualTo(UPDATED_ORGAO_EMISSOR_RNE);
        assertThat(testServidor.getUfEmissorRNE()).isEqualTo(UPDATED_UF_EMISSOR_RNE);
        assertThat(testServidor.getDatExpeRNE()).isEqualTo(UPDATED_DAT_EXPE_RNE);
        assertThat(testServidor.getDatChegada()).isEqualTo(UPDATED_DAT_CHEGADA);
        assertThat(testServidor.getClassTrabEstr()).isEqualTo(UPDATED_CLASS_TRAB_ESTR);
        assertThat(testServidor.getCasadoBR()).isEqualTo(UPDATED_CASADO_BR);
        assertThat(testServidor.getFilhosBRr()).isEqualTo(UPDATED_FILHOS_B_RR);
        assertThat(testServidor.getAuxTransporte()).isEqualTo(UPDATED_AUX_TRANSPORTE);
        assertThat(testServidor.getAuxTransLinha1()).isEqualTo(UPDATED_AUX_TRANS_LINHA_1);
        assertThat(testServidor.getAuxTransLinha2()).isEqualTo(UPDATED_AUX_TRANS_LINHA_2);
        assertThat(testServidor.getAuxTransLinha3()).isEqualTo(UPDATED_AUX_TRANS_LINHA_3);
        assertThat(testServidor.getAuxTransLinha4()).isEqualTo(UPDATED_AUX_TRANS_LINHA_4);
        assertThat(testServidor.getTrabAposentado()).isEqualTo(UPDATED_TRAB_APOSENTADO);

        // Validate the Servidor in Elasticsearch
        verify(mockServidorSearchRepository, times(1)).save(testServidor);
    }

    @Test
    @Transactional
    public void updateNonExistingServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().size();

        // Create the Servidor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServidorMockMvc.perform(put("/api/servidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servidor)))
            .andExpect(status().isBadRequest());

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Servidor in Elasticsearch
        verify(mockServidorSearchRepository, times(0)).save(servidor);
    }

    @Test
    @Transactional
    public void deleteServidor() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);

        int databaseSizeBeforeDelete = servidorRepository.findAll().size();

        // Get the servidor
        restServidorMockMvc.perform(delete("/api/servidors/{id}", servidor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Servidor> servidorList = servidorRepository.findAll();
        assertThat(servidorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Servidor in Elasticsearch
        verify(mockServidorSearchRepository, times(1)).deleteById(servidor.getId());
    }

    @Test
    @Transactional
    public void searchServidor() throws Exception {
        // Initialize the database
        servidorRepository.saveAndFlush(servidor);
        when(mockServidorSearchRepository.search(queryStringQuery("id:" + servidor.getId())))
            .thenReturn(Collections.singletonList(servidor));
        // Search the servidor
        restServidorMockMvc.perform(get("/api/_search/servidors?query=id:" + servidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA.intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].nomeSocial").value(hasItem(DEFAULT_NOME_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].datNascimento").value(hasItem(DEFAULT_DAT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nomePai").value(hasItem(DEFAULT_NOME_PAI.toString())))
            .andExpect(jsonPath("$.[*].nomeMae").value(hasItem(DEFAULT_NOME_MAE.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.intValue())))
            .andExpect(jsonPath("$.[*].cpfPai").value(hasItem(DEFAULT_CPF_PAI.intValue())))
            .andExpect(jsonPath("$.[*].cpfMae").value(hasItem(DEFAULT_CPF_MAE.intValue())))
            .andExpect(jsonPath("$.[*].rgNum").value(hasItem(DEFAULT_RG_NUM.toString())))
            .andExpect(jsonPath("$.[*].rgUf").value(hasItem(DEFAULT_RG_UF.toString())))
            .andExpect(jsonPath("$.[*].rgOrgaoExp").value(hasItem(DEFAULT_RG_ORGAO_EXP.toString())))
            .andExpect(jsonPath("$.[*].rgDataExp").value(hasItem(DEFAULT_RG_DATA_EXP.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].tipoSangue").value(hasItem(DEFAULT_TIPO_SANGUE.toString())))
            .andExpect(jsonPath("$.[*].fatorRh").value(hasItem(DEFAULT_FATOR_RH.toString())))
            .andExpect(jsonPath("$.[*].doadorSangue").value(hasItem(DEFAULT_DOADOR_SANGUE.toString())))
            .andExpect(jsonPath("$.[*].deficienciaFisica").value(hasItem(DEFAULT_DEFICIENCIA_FISICA.toString())))
            .andExpect(jsonPath("$.[*].deficienciaVisual").value(hasItem(DEFAULT_DEFICIENCIA_VISUAL.toString())))
            .andExpect(jsonPath("$.[*].deficienciaAuditiva").value(hasItem(DEFAULT_DEFICIENCIA_AUDITIVA.toString())))
            .andExpect(jsonPath("$.[*].deficienciaMental").value(hasItem(DEFAULT_DEFICIENCIA_MENTAL.toString())))
            .andExpect(jsonPath("$.[*].deficienciaIntelec").value(hasItem(DEFAULT_DEFICIENCIA_INTELEC.toString())))
            .andExpect(jsonPath("$.[*].deficReabReadaptado").value(hasItem(DEFAULT_DEFIC_REAB_READAPTADO.toString())))
            .andExpect(jsonPath("$.[*].deficObservacao").value(hasItem(DEFAULT_DEFIC_OBSERVACAO.toString())))
            .andExpect(jsonPath("$.[*].preencheCota").value(hasItem(DEFAULT_PREENCHE_COTA.toString())))
            .andExpect(jsonPath("$.[*].emailPessoal").value(hasItem(DEFAULT_EMAIL_PESSOAL.toString())))
            .andExpect(jsonPath("$.[*].emailAltern").value(hasItem(DEFAULT_EMAIL_ALTERN.toString())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())))
            .andExpect(jsonPath("$.[*].celularOperadora").value(hasItem(DEFAULT_CELULAR_OPERADORA.toString())))
            .andExpect(jsonPath("$.[*].pasep").value(hasItem(DEFAULT_PASEP.toString())))
            .andExpect(jsonPath("$.[*].ipsep").value(hasItem(DEFAULT_IPSEP.toString())))
            .andExpect(jsonPath("$.[*].inss").value(hasItem(DEFAULT_INSS.toString())))
            .andExpect(jsonPath("$.[*].ctpsNum").value(hasItem(DEFAULT_CTPS_NUM.toString())))
            .andExpect(jsonPath("$.[*].ctpsSerie").value(hasItem(DEFAULT_CTPS_SERIE.toString())))
            .andExpect(jsonPath("$.[*].ctpsEmissao").value(hasItem(DEFAULT_CTPS_EMISSAO.toString())))
            .andExpect(jsonPath("$.[*].ctpsUf").value(hasItem(DEFAULT_CTPS_UF.toString())))
            .andExpect(jsonPath("$.[*].orgaoClasseNum").value(hasItem(DEFAULT_ORGAO_CLASSE_NUM.toString())))
            .andExpect(jsonPath("$.[*].orgaoClNome").value(hasItem(DEFAULT_ORGAO_CL_NOME.toString())))
            .andExpect(jsonPath("$.[*].orgaoClUf").value(hasItem(DEFAULT_ORGAO_CL_UF.toString())))
            .andExpect(jsonPath("$.[*].orclDtaExp").value(hasItem(DEFAULT_ORCL_DTA_EXP.toString())))
            .andExpect(jsonPath("$.[*].orclDtaValid").value(hasItem(DEFAULT_ORCL_DTA_VALID.toString())))
            .andExpect(jsonPath("$.[*].reservista").value(hasItem(DEFAULT_RESERVISTA.toString())))
            .andExpect(jsonPath("$.[*].reservistaClasse").value(hasItem(DEFAULT_RESERVISTA_CLASSE.toString())))
            .andExpect(jsonPath("$.[*].numRIC").value(hasItem(DEFAULT_NUM_RIC.toString())))
            .andExpect(jsonPath("$.[*].orgaoEmissorRIC").value(hasItem(DEFAULT_ORGAO_EMISSOR_RIC.toString())))
            .andExpect(jsonPath("$.[*].ufRIC").value(hasItem(DEFAULT_UF_RIC.toString())))
            .andExpect(jsonPath("$.[*].datExpedRIC").value(hasItem(DEFAULT_DAT_EXPED_RIC.toString())))
            .andExpect(jsonPath("$.[*].titEleitorNum").value(hasItem(DEFAULT_TIT_ELEITOR_NUM.toString())))
            .andExpect(jsonPath("$.[*].titEleitorZona").value(hasItem(DEFAULT_TIT_ELEITOR_ZONA.toString())))
            .andExpect(jsonPath("$.[*].titEleitorSecao").value(hasItem(DEFAULT_TIT_ELEITOR_SECAO.toString())))
            .andExpect(jsonPath("$.[*].titEleitorLocal").value(hasItem(DEFAULT_TIT_ELEITOR_LOCAL.toString())))
            .andExpect(jsonPath("$.[*].cnhNum").value(hasItem(DEFAULT_CNH_NUM.toString())))
            .andExpect(jsonPath("$.[*].cnhCategoria").value(hasItem(DEFAULT_CNH_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].cnhDatValidade").value(hasItem(DEFAULT_CNH_DAT_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].cnhDatExped").value(hasItem(DEFAULT_CNH_DAT_EXPED.toString())))
            .andExpect(jsonPath("$.[*].cnhUfEmissor").value(hasItem(DEFAULT_CNH_UF_EMISSOR.toString())))
            .andExpect(jsonPath("$.[*].cnhDatPrimHab").value(hasItem(DEFAULT_CNH_DAT_PRIM_HAB.toString())))
            .andExpect(jsonPath("$.[*].sassepeNum").value(hasItem(DEFAULT_SASSEPE_NUM.toString())))
            .andExpect(jsonPath("$.[*].ufNatural").value(hasItem(DEFAULT_UF_NATURAL.toString())))
            .andExpect(jsonPath("$.[*].primEmprego").value(hasItem(DEFAULT_PRIM_EMPREGO.toString())))
            .andExpect(jsonPath("$.[*].exercMagisterio").value(hasItem(DEFAULT_EXERC_MAGISTERIO.toString())))
            .andExpect(jsonPath("$.[*].tipoLogradouro").value(hasItem(DEFAULT_TIPO_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].descLogradouro").value(hasItem(DEFAULT_DESC_LOGRADOURO.toString())))
            .andExpect(jsonPath("$.[*].nrEndereco").value(hasItem(DEFAULT_NR_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].complEndereco").value(hasItem(DEFAULT_COMPL_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].bairroEndereco").value(hasItem(DEFAULT_BAIRRO_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].cepEndereco").value(hasItem(DEFAULT_CEP_ENDERECO)))
            .andExpect(jsonPath("$.[*].ufEndereco").value(hasItem(DEFAULT_UF_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].telefone1").value(hasItem(DEFAULT_TELEFONE_1.toString())))
            .andExpect(jsonPath("$.[*].telefone2").value(hasItem(DEFAULT_TELEFONE_2.toString())))
            .andExpect(jsonPath("$.[*].descLograExt").value(hasItem(DEFAULT_DESC_LOGRA_EXT.toString())))
            .andExpect(jsonPath("$.[*].nrLograExt").value(hasItem(DEFAULT_NR_LOGRA_EXT.toString())))
            .andExpect(jsonPath("$.[*].complLograExt").value(hasItem(DEFAULT_COMPL_LOGRA_EXT.toString())))
            .andExpect(jsonPath("$.[*].bairroEndExt").value(hasItem(DEFAULT_BAIRRO_END_EXT.toString())))
            .andExpect(jsonPath("$.[*].cidadeEndExt").value(hasItem(DEFAULT_CIDADE_END_EXT.toString())))
            .andExpect(jsonPath("$.[*].codPostalEndExt").value(hasItem(DEFAULT_COD_POSTAL_END_EXT.toString())))
            .andExpect(jsonPath("$.[*].numRne").value(hasItem(DEFAULT_NUM_RNE.toString())))
            .andExpect(jsonPath("$.[*].orgaoEmissorRNE").value(hasItem(DEFAULT_ORGAO_EMISSOR_RNE.toString())))
            .andExpect(jsonPath("$.[*].ufEmissorRNE").value(hasItem(DEFAULT_UF_EMISSOR_RNE.toString())))
            .andExpect(jsonPath("$.[*].datExpeRNE").value(hasItem(DEFAULT_DAT_EXPE_RNE.toString())))
            .andExpect(jsonPath("$.[*].datChegada").value(hasItem(DEFAULT_DAT_CHEGADA.toString())))
            .andExpect(jsonPath("$.[*].classTrabEstr").value(hasItem(DEFAULT_CLASS_TRAB_ESTR)))
            .andExpect(jsonPath("$.[*].casadoBR").value(hasItem(DEFAULT_CASADO_BR.toString())))
            .andExpect(jsonPath("$.[*].filhosBRr").value(hasItem(DEFAULT_FILHOS_B_RR.toString())))
            .andExpect(jsonPath("$.[*].auxTransporte").value(hasItem(DEFAULT_AUX_TRANSPORTE.toString())))
            .andExpect(jsonPath("$.[*].auxTransLinha1").value(hasItem(DEFAULT_AUX_TRANS_LINHA_1.toString())))
            .andExpect(jsonPath("$.[*].auxTransLinha2").value(hasItem(DEFAULT_AUX_TRANS_LINHA_2.toString())))
            .andExpect(jsonPath("$.[*].auxTransLinha3").value(hasItem(DEFAULT_AUX_TRANS_LINHA_3.toString())))
            .andExpect(jsonPath("$.[*].auxTransLinha4").value(hasItem(DEFAULT_AUX_TRANS_LINHA_4.toString())))
            .andExpect(jsonPath("$.[*].trabAposentado").value(hasItem(DEFAULT_TRAB_APOSENTADO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servidor.class);
        Servidor servidor1 = new Servidor();
        servidor1.setId(1L);
        Servidor servidor2 = new Servidor();
        servidor2.setId(servidor1.getId());
        assertThat(servidor1).isEqualTo(servidor2);
        servidor2.setId(2L);
        assertThat(servidor1).isNotEqualTo(servidor2);
        servidor1.setId(null);
        assertThat(servidor1).isNotEqualTo(servidor2);
    }
}
