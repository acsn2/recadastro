package br.mppe.mp.recadastro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Servidor.
 */
@Entity
@Table(name = "servidor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "servidor")
public class Servidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "matricula", nullable = false)
    private Long matricula;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "nome_social")
    private String nomeSocial;

    @NotNull
    @Column(name = "dat_nascimento", nullable = false)
    private LocalDate datNascimento;

    @NotNull
    @Column(name = "nome_pai", nullable = false)
    private String nomePai;

    @NotNull
    @Column(name = "nome_mae", nullable = false)
    private String nomeMae;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private Long cpf;

    @Column(name = "cpf_pai")
    private Long cpfPai;

    @Column(name = "cpf_mae")
    private Long cpfMae;

    @NotNull
    @Column(name = "rg_num", nullable = false)
    private String rgNum;

    @NotNull
    @Column(name = "rg_uf", nullable = false)
    private String rgUf;

    @NotNull
    @Column(name = "rg_orgao_exp", nullable = false)
    private String rgOrgaoExp;

    @NotNull
    @Column(name = "rg_data_exp", nullable = false)
    private LocalDate rgDataExp;

    @NotNull
    @Column(name = "sexo", nullable = false)
    private String sexo;

    @NotNull
    @Column(name = "tipo_sangue", nullable = false)
    private String tipoSangue;

    @NotNull
    @Column(name = "fator_rh", nullable = false)
    private String fatorRh;

    @NotNull
    @Column(name = "doador_sangue", nullable = false)
    private String doadorSangue;

    @NotNull
    @Column(name = "deficiencia_fisica", nullable = false)
    private String deficienciaFisica;

    @NotNull
    @Column(name = "deficiencia_visual", nullable = false)
    private String deficienciaVisual;

    @NotNull
    @Column(name = "deficiencia_auditiva", nullable = false)
    private String deficienciaAuditiva;

    @NotNull
    @Column(name = "deficiencia_mental", nullable = false)
    private String deficienciaMental;

    @NotNull
    @Column(name = "deficiencia_intelec", nullable = false)
    private String deficienciaIntelec;

    @NotNull
    @Column(name = "defic_reab_readaptado", nullable = false)
    private String deficReabReadaptado;

    @Column(name = "defic_observacao")
    private String deficObservacao;

    @NotNull
    @Column(name = "preenche_cota", nullable = false)
    private String preencheCota;

    @NotNull
    @Column(name = "email_pessoal", nullable = false)
    private String emailPessoal;

    @Column(name = "email_altern")
    private String emailAltern;

    @Column(name = "celular")
    private String celular;

    @Column(name = "celular_operadora")
    private String celularOperadora;

    @NotNull
    @Column(name = "pasep", nullable = false)
    private String pasep;

    @Column(name = "ipsep")
    private String ipsep;

    @Column(name = "inss")
    private String inss;

    @Column(name = "ctps_num")
    private String ctpsNum;

    @Column(name = "ctps_serie")
    private String ctpsSerie;

    @Column(name = "ctps_emissao")
    private LocalDate ctpsEmissao;

    @Column(name = "ctps_uf")
    private String ctpsUf;

    @Column(name = "orgao_classe_num")
    private String orgaoClasseNum;

    @Column(name = "orgao_cl_nome")
    private String orgaoClNome;

    @Column(name = "orgao_cl_uf")
    private String orgaoClUf;

    @Column(name = "orcl_dta_exp")
    private LocalDate orclDtaExp;

    @Column(name = "orcl_dta_valid")
    private LocalDate orclDtaValid;

    @Column(name = "reservista")
    private String reservista;

    @Column(name = "reservista_classe")
    private String reservistaClasse;

    @Column(name = "num_ric")
    private String numRIC;

    @Column(name = "orgao_emissor_ric")
    private String orgaoEmissorRIC;

    @Column(name = "uf_ric")
    private String ufRIC;

    @Column(name = "dat_exped_ric")
    private LocalDate datExpedRIC;

    @NotNull
    @Column(name = "tit_eleitor_num", nullable = false)
    private String titEleitorNum;

    @NotNull
    @Column(name = "tit_eleitor_zona", nullable = false)
    private String titEleitorZona;

    @NotNull
    @Column(name = "tit_eleitor_secao", nullable = false)
    private String titEleitorSecao;

    @NotNull
    @Column(name = "tit_eleitor_local", nullable = false)
    private String titEleitorLocal;

    @Column(name = "cnh_num")
    private String cnhNum;

    @Column(name = "cnh_categoria")
    private String cnhCategoria;

    @Column(name = "cnh_dat_validade")
    private LocalDate cnhDatValidade;

    @Column(name = "cnh_dat_exped")
    private LocalDate cnhDatExped;

    @Column(name = "cnh_uf_emissor")
    private String cnhUfEmissor;

    @Column(name = "cnh_dat_prim_hab")
    private LocalDate cnhDatPrimHab;

    @Column(name = "sassepe_num")
    private String sassepeNum;

    @Column(name = "uf_natural")
    private String ufNatural;

    @Column(name = "prim_emprego")
    private String primEmprego;

    @Column(name = "exerc_magisterio")
    private String exercMagisterio;

    /**
     * 1- Regime Geral da Previdência Social - RGPS
     * 2- Regime Próprio de Previdência Social - RPPS
     * 3- Regime de Previdência Social no Exterior
     */
    @NotNull
    @ApiModelProperty(value = "1- Regime Geral da Previdência Social - RGPS 2- Regime Próprio de Previdência Social - RPPS 3- Regime de Previdência Social no Exterior", required = true)
    @Column(name = "tipo_logradouro", nullable = false)
    private Integer tipoLogradouro;

    @NotNull
    @Column(name = "desc_logradouro", nullable = false)
    private String descLogradouro;

    @NotNull
    @Column(name = "nr_endereco", nullable = false)
    private String nrEndereco;

    @NotNull
    @Column(name = "compl_endereco", nullable = false)
    private String complEndereco;

    @NotNull
    @Column(name = "bairro_endereco", nullable = false)
    private String bairroEndereco;

    @NotNull
    @Column(name = "cep_endereco", nullable = false)
    private Integer cepEndereco;

    @NotNull
    @Column(name = "uf_endereco", nullable = false)
    private String ufEndereco;

    @Column(name = "telefone_1")
    private String telefone1;

    @Column(name = "telefone_2")
    private String telefone2;

    @Column(name = "desc_logra_ext")
    private String descLograExt;

    @Column(name = "nr_logra_ext")
    private String nrLograExt;

    @Column(name = "compl_logra_ext")
    private String complLograExt;

    @Column(name = "bairro_end_ext")
    private String bairroEndExt;

    @Column(name = "cidade_end_ext")
    private String cidadeEndExt;

    @Column(name = "cod_postal_end_ext")
    private String codPostalEndExt;

    @Column(name = "num_rne")
    private String numRne;

    @Column(name = "orgao_emissor_rne")
    private String orgaoEmissorRNE;

    @Column(name = "uf_emissor_rne")
    private String ufEmissorRNE;

    @Column(name = "dat_expe_rne")
    private LocalDate datExpeRNE;

    @Column(name = "dat_chegada")
    private LocalDate datChegada;

    @Column(name = "class_trab_estr")
    private Integer classTrabEstr;

    @Column(name = "casado_br")
    private String casadoBR;

    @Column(name = "filhos_b_rr")
    private String filhosBRr;

    @Column(name = "aux_transporte")
    private String auxTransporte;

    @Column(name = "aux_trans_linha_1")
    private String auxTransLinha1;

    @Column(name = "aux_trans_linha_2")
    private String auxTransLinha2;

    @Column(name = "aux_trans_linha_3")
    private String auxTransLinha3;

    @Column(name = "aux_trans_linha_4")
    private String auxTransLinha4;

    @Column(name = "trab_aposentado")
    private String trabAposentado;

    @OneToMany(mappedBy = "servidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dependente> serMatriculas = new HashSet<>();

    @OneToMany(mappedBy = "servidor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ParenteServidor> serMatriculas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatricula() {
        return matricula;
    }

    public Servidor matricula(Long matricula) {
        this.matricula = matricula;
        return this;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getUsername() {
        return username;
    }

    public Servidor username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public Servidor nomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
        return this;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public LocalDate getDatNascimento() {
        return datNascimento;
    }

    public Servidor datNascimento(LocalDate datNascimento) {
        this.datNascimento = datNascimento;
        return this;
    }

    public void setDatNascimento(LocalDate datNascimento) {
        this.datNascimento = datNascimento;
    }

    public String getNomePai() {
        return nomePai;
    }

    public Servidor nomePai(String nomePai) {
        this.nomePai = nomePai;
        return this;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public Servidor nomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
        return this;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public Long getCpf() {
        return cpf;
    }

    public Servidor cpf(Long cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getCpfPai() {
        return cpfPai;
    }

    public Servidor cpfPai(Long cpfPai) {
        this.cpfPai = cpfPai;
        return this;
    }

    public void setCpfPai(Long cpfPai) {
        this.cpfPai = cpfPai;
    }

    public Long getCpfMae() {
        return cpfMae;
    }

    public Servidor cpfMae(Long cpfMae) {
        this.cpfMae = cpfMae;
        return this;
    }

    public void setCpfMae(Long cpfMae) {
        this.cpfMae = cpfMae;
    }

    public String getRgNum() {
        return rgNum;
    }

    public Servidor rgNum(String rgNum) {
        this.rgNum = rgNum;
        return this;
    }

    public void setRgNum(String rgNum) {
        this.rgNum = rgNum;
    }

    public String getRgUf() {
        return rgUf;
    }

    public Servidor rgUf(String rgUf) {
        this.rgUf = rgUf;
        return this;
    }

    public void setRgUf(String rgUf) {
        this.rgUf = rgUf;
    }

    public String getRgOrgaoExp() {
        return rgOrgaoExp;
    }

    public Servidor rgOrgaoExp(String rgOrgaoExp) {
        this.rgOrgaoExp = rgOrgaoExp;
        return this;
    }

    public void setRgOrgaoExp(String rgOrgaoExp) {
        this.rgOrgaoExp = rgOrgaoExp;
    }

    public LocalDate getRgDataExp() {
        return rgDataExp;
    }

    public Servidor rgDataExp(LocalDate rgDataExp) {
        this.rgDataExp = rgDataExp;
        return this;
    }

    public void setRgDataExp(LocalDate rgDataExp) {
        this.rgDataExp = rgDataExp;
    }

    public String getSexo() {
        return sexo;
    }

    public Servidor sexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoSangue() {
        return tipoSangue;
    }

    public Servidor tipoSangue(String tipoSangue) {
        this.tipoSangue = tipoSangue;
        return this;
    }

    public void setTipoSangue(String tipoSangue) {
        this.tipoSangue = tipoSangue;
    }

    public String getFatorRh() {
        return fatorRh;
    }

    public Servidor fatorRh(String fatorRh) {
        this.fatorRh = fatorRh;
        return this;
    }

    public void setFatorRh(String fatorRh) {
        this.fatorRh = fatorRh;
    }

    public String getDoadorSangue() {
        return doadorSangue;
    }

    public Servidor doadorSangue(String doadorSangue) {
        this.doadorSangue = doadorSangue;
        return this;
    }

    public void setDoadorSangue(String doadorSangue) {
        this.doadorSangue = doadorSangue;
    }

    public String getDeficienciaFisica() {
        return deficienciaFisica;
    }

    public Servidor deficienciaFisica(String deficienciaFisica) {
        this.deficienciaFisica = deficienciaFisica;
        return this;
    }

    public void setDeficienciaFisica(String deficienciaFisica) {
        this.deficienciaFisica = deficienciaFisica;
    }

    public String getDeficienciaVisual() {
        return deficienciaVisual;
    }

    public Servidor deficienciaVisual(String deficienciaVisual) {
        this.deficienciaVisual = deficienciaVisual;
        return this;
    }

    public void setDeficienciaVisual(String deficienciaVisual) {
        this.deficienciaVisual = deficienciaVisual;
    }

    public String getDeficienciaAuditiva() {
        return deficienciaAuditiva;
    }

    public Servidor deficienciaAuditiva(String deficienciaAuditiva) {
        this.deficienciaAuditiva = deficienciaAuditiva;
        return this;
    }

    public void setDeficienciaAuditiva(String deficienciaAuditiva) {
        this.deficienciaAuditiva = deficienciaAuditiva;
    }

    public String getDeficienciaMental() {
        return deficienciaMental;
    }

    public Servidor deficienciaMental(String deficienciaMental) {
        this.deficienciaMental = deficienciaMental;
        return this;
    }

    public void setDeficienciaMental(String deficienciaMental) {
        this.deficienciaMental = deficienciaMental;
    }

    public String getDeficienciaIntelec() {
        return deficienciaIntelec;
    }

    public Servidor deficienciaIntelec(String deficienciaIntelec) {
        this.deficienciaIntelec = deficienciaIntelec;
        return this;
    }

    public void setDeficienciaIntelec(String deficienciaIntelec) {
        this.deficienciaIntelec = deficienciaIntelec;
    }

    public String getDeficReabReadaptado() {
        return deficReabReadaptado;
    }

    public Servidor deficReabReadaptado(String deficReabReadaptado) {
        this.deficReabReadaptado = deficReabReadaptado;
        return this;
    }

    public void setDeficReabReadaptado(String deficReabReadaptado) {
        this.deficReabReadaptado = deficReabReadaptado;
    }

    public String getDeficObservacao() {
        return deficObservacao;
    }

    public Servidor deficObservacao(String deficObservacao) {
        this.deficObservacao = deficObservacao;
        return this;
    }

    public void setDeficObservacao(String deficObservacao) {
        this.deficObservacao = deficObservacao;
    }

    public String getPreencheCota() {
        return preencheCota;
    }

    public Servidor preencheCota(String preencheCota) {
        this.preencheCota = preencheCota;
        return this;
    }

    public void setPreencheCota(String preencheCota) {
        this.preencheCota = preencheCota;
    }

    public String getEmailPessoal() {
        return emailPessoal;
    }

    public Servidor emailPessoal(String emailPessoal) {
        this.emailPessoal = emailPessoal;
        return this;
    }

    public void setEmailPessoal(String emailPessoal) {
        this.emailPessoal = emailPessoal;
    }

    public String getEmailAltern() {
        return emailAltern;
    }

    public Servidor emailAltern(String emailAltern) {
        this.emailAltern = emailAltern;
        return this;
    }

    public void setEmailAltern(String emailAltern) {
        this.emailAltern = emailAltern;
    }

    public String getCelular() {
        return celular;
    }

    public Servidor celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCelularOperadora() {
        return celularOperadora;
    }

    public Servidor celularOperadora(String celularOperadora) {
        this.celularOperadora = celularOperadora;
        return this;
    }

    public void setCelularOperadora(String celularOperadora) {
        this.celularOperadora = celularOperadora;
    }

    public String getPasep() {
        return pasep;
    }

    public Servidor pasep(String pasep) {
        this.pasep = pasep;
        return this;
    }

    public void setPasep(String pasep) {
        this.pasep = pasep;
    }

    public String getIpsep() {
        return ipsep;
    }

    public Servidor ipsep(String ipsep) {
        this.ipsep = ipsep;
        return this;
    }

    public void setIpsep(String ipsep) {
        this.ipsep = ipsep;
    }

    public String getInss() {
        return inss;
    }

    public Servidor inss(String inss) {
        this.inss = inss;
        return this;
    }

    public void setInss(String inss) {
        this.inss = inss;
    }

    public String getCtpsNum() {
        return ctpsNum;
    }

    public Servidor ctpsNum(String ctpsNum) {
        this.ctpsNum = ctpsNum;
        return this;
    }

    public void setCtpsNum(String ctpsNum) {
        this.ctpsNum = ctpsNum;
    }

    public String getCtpsSerie() {
        return ctpsSerie;
    }

    public Servidor ctpsSerie(String ctpsSerie) {
        this.ctpsSerie = ctpsSerie;
        return this;
    }

    public void setCtpsSerie(String ctpsSerie) {
        this.ctpsSerie = ctpsSerie;
    }

    public LocalDate getCtpsEmissao() {
        return ctpsEmissao;
    }

    public Servidor ctpsEmissao(LocalDate ctpsEmissao) {
        this.ctpsEmissao = ctpsEmissao;
        return this;
    }

    public void setCtpsEmissao(LocalDate ctpsEmissao) {
        this.ctpsEmissao = ctpsEmissao;
    }

    public String getCtpsUf() {
        return ctpsUf;
    }

    public Servidor ctpsUf(String ctpsUf) {
        this.ctpsUf = ctpsUf;
        return this;
    }

    public void setCtpsUf(String ctpsUf) {
        this.ctpsUf = ctpsUf;
    }

    public String getOrgaoClasseNum() {
        return orgaoClasseNum;
    }

    public Servidor orgaoClasseNum(String orgaoClasseNum) {
        this.orgaoClasseNum = orgaoClasseNum;
        return this;
    }

    public void setOrgaoClasseNum(String orgaoClasseNum) {
        this.orgaoClasseNum = orgaoClasseNum;
    }

    public String getOrgaoClNome() {
        return orgaoClNome;
    }

    public Servidor orgaoClNome(String orgaoClNome) {
        this.orgaoClNome = orgaoClNome;
        return this;
    }

    public void setOrgaoClNome(String orgaoClNome) {
        this.orgaoClNome = orgaoClNome;
    }

    public String getOrgaoClUf() {
        return orgaoClUf;
    }

    public Servidor orgaoClUf(String orgaoClUf) {
        this.orgaoClUf = orgaoClUf;
        return this;
    }

    public void setOrgaoClUf(String orgaoClUf) {
        this.orgaoClUf = orgaoClUf;
    }

    public LocalDate getOrclDtaExp() {
        return orclDtaExp;
    }

    public Servidor orclDtaExp(LocalDate orclDtaExp) {
        this.orclDtaExp = orclDtaExp;
        return this;
    }

    public void setOrclDtaExp(LocalDate orclDtaExp) {
        this.orclDtaExp = orclDtaExp;
    }

    public LocalDate getOrclDtaValid() {
        return orclDtaValid;
    }

    public Servidor orclDtaValid(LocalDate orclDtaValid) {
        this.orclDtaValid = orclDtaValid;
        return this;
    }

    public void setOrclDtaValid(LocalDate orclDtaValid) {
        this.orclDtaValid = orclDtaValid;
    }

    public String getReservista() {
        return reservista;
    }

    public Servidor reservista(String reservista) {
        this.reservista = reservista;
        return this;
    }

    public void setReservista(String reservista) {
        this.reservista = reservista;
    }

    public String getReservistaClasse() {
        return reservistaClasse;
    }

    public Servidor reservistaClasse(String reservistaClasse) {
        this.reservistaClasse = reservistaClasse;
        return this;
    }

    public void setReservistaClasse(String reservistaClasse) {
        this.reservistaClasse = reservistaClasse;
    }

    public String getNumRIC() {
        return numRIC;
    }

    public Servidor numRIC(String numRIC) {
        this.numRIC = numRIC;
        return this;
    }

    public void setNumRIC(String numRIC) {
        this.numRIC = numRIC;
    }

    public String getOrgaoEmissorRIC() {
        return orgaoEmissorRIC;
    }

    public Servidor orgaoEmissorRIC(String orgaoEmissorRIC) {
        this.orgaoEmissorRIC = orgaoEmissorRIC;
        return this;
    }

    public void setOrgaoEmissorRIC(String orgaoEmissorRIC) {
        this.orgaoEmissorRIC = orgaoEmissorRIC;
    }

    public String getUfRIC() {
        return ufRIC;
    }

    public Servidor ufRIC(String ufRIC) {
        this.ufRIC = ufRIC;
        return this;
    }

    public void setUfRIC(String ufRIC) {
        this.ufRIC = ufRIC;
    }

    public LocalDate getDatExpedRIC() {
        return datExpedRIC;
    }

    public Servidor datExpedRIC(LocalDate datExpedRIC) {
        this.datExpedRIC = datExpedRIC;
        return this;
    }

    public void setDatExpedRIC(LocalDate datExpedRIC) {
        this.datExpedRIC = datExpedRIC;
    }

    public String getTitEleitorNum() {
        return titEleitorNum;
    }

    public Servidor titEleitorNum(String titEleitorNum) {
        this.titEleitorNum = titEleitorNum;
        return this;
    }

    public void setTitEleitorNum(String titEleitorNum) {
        this.titEleitorNum = titEleitorNum;
    }

    public String getTitEleitorZona() {
        return titEleitorZona;
    }

    public Servidor titEleitorZona(String titEleitorZona) {
        this.titEleitorZona = titEleitorZona;
        return this;
    }

    public void setTitEleitorZona(String titEleitorZona) {
        this.titEleitorZona = titEleitorZona;
    }

    public String getTitEleitorSecao() {
        return titEleitorSecao;
    }

    public Servidor titEleitorSecao(String titEleitorSecao) {
        this.titEleitorSecao = titEleitorSecao;
        return this;
    }

    public void setTitEleitorSecao(String titEleitorSecao) {
        this.titEleitorSecao = titEleitorSecao;
    }

    public String getTitEleitorLocal() {
        return titEleitorLocal;
    }

    public Servidor titEleitorLocal(String titEleitorLocal) {
        this.titEleitorLocal = titEleitorLocal;
        return this;
    }

    public void setTitEleitorLocal(String titEleitorLocal) {
        this.titEleitorLocal = titEleitorLocal;
    }

    public String getCnhNum() {
        return cnhNum;
    }

    public Servidor cnhNum(String cnhNum) {
        this.cnhNum = cnhNum;
        return this;
    }

    public void setCnhNum(String cnhNum) {
        this.cnhNum = cnhNum;
    }

    public String getCnhCategoria() {
        return cnhCategoria;
    }

    public Servidor cnhCategoria(String cnhCategoria) {
        this.cnhCategoria = cnhCategoria;
        return this;
    }

    public void setCnhCategoria(String cnhCategoria) {
        this.cnhCategoria = cnhCategoria;
    }

    public LocalDate getCnhDatValidade() {
        return cnhDatValidade;
    }

    public Servidor cnhDatValidade(LocalDate cnhDatValidade) {
        this.cnhDatValidade = cnhDatValidade;
        return this;
    }

    public void setCnhDatValidade(LocalDate cnhDatValidade) {
        this.cnhDatValidade = cnhDatValidade;
    }

    public LocalDate getCnhDatExped() {
        return cnhDatExped;
    }

    public Servidor cnhDatExped(LocalDate cnhDatExped) {
        this.cnhDatExped = cnhDatExped;
        return this;
    }

    public void setCnhDatExped(LocalDate cnhDatExped) {
        this.cnhDatExped = cnhDatExped;
    }

    public String getCnhUfEmissor() {
        return cnhUfEmissor;
    }

    public Servidor cnhUfEmissor(String cnhUfEmissor) {
        this.cnhUfEmissor = cnhUfEmissor;
        return this;
    }

    public void setCnhUfEmissor(String cnhUfEmissor) {
        this.cnhUfEmissor = cnhUfEmissor;
    }

    public LocalDate getCnhDatPrimHab() {
        return cnhDatPrimHab;
    }

    public Servidor cnhDatPrimHab(LocalDate cnhDatPrimHab) {
        this.cnhDatPrimHab = cnhDatPrimHab;
        return this;
    }

    public void setCnhDatPrimHab(LocalDate cnhDatPrimHab) {
        this.cnhDatPrimHab = cnhDatPrimHab;
    }

    public String getSassepeNum() {
        return sassepeNum;
    }

    public Servidor sassepeNum(String sassepeNum) {
        this.sassepeNum = sassepeNum;
        return this;
    }

    public void setSassepeNum(String sassepeNum) {
        this.sassepeNum = sassepeNum;
    }

    public String getUfNatural() {
        return ufNatural;
    }

    public Servidor ufNatural(String ufNatural) {
        this.ufNatural = ufNatural;
        return this;
    }

    public void setUfNatural(String ufNatural) {
        this.ufNatural = ufNatural;
    }

    public String getPrimEmprego() {
        return primEmprego;
    }

    public Servidor primEmprego(String primEmprego) {
        this.primEmprego = primEmprego;
        return this;
    }

    public void setPrimEmprego(String primEmprego) {
        this.primEmprego = primEmprego;
    }

    public String getExercMagisterio() {
        return exercMagisterio;
    }

    public Servidor exercMagisterio(String exercMagisterio) {
        this.exercMagisterio = exercMagisterio;
        return this;
    }

    public void setExercMagisterio(String exercMagisterio) {
        this.exercMagisterio = exercMagisterio;
    }

    public Integer getTipoLogradouro() {
        return tipoLogradouro;
    }

    public Servidor tipoLogradouro(Integer tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
        return this;
    }

    public void setTipoLogradouro(Integer tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getDescLogradouro() {
        return descLogradouro;
    }

    public Servidor descLogradouro(String descLogradouro) {
        this.descLogradouro = descLogradouro;
        return this;
    }

    public void setDescLogradouro(String descLogradouro) {
        this.descLogradouro = descLogradouro;
    }

    public String getNrEndereco() {
        return nrEndereco;
    }

    public Servidor nrEndereco(String nrEndereco) {
        this.nrEndereco = nrEndereco;
        return this;
    }

    public void setNrEndereco(String nrEndereco) {
        this.nrEndereco = nrEndereco;
    }

    public String getComplEndereco() {
        return complEndereco;
    }

    public Servidor complEndereco(String complEndereco) {
        this.complEndereco = complEndereco;
        return this;
    }

    public void setComplEndereco(String complEndereco) {
        this.complEndereco = complEndereco;
    }

    public String getBairroEndereco() {
        return bairroEndereco;
    }

    public Servidor bairroEndereco(String bairroEndereco) {
        this.bairroEndereco = bairroEndereco;
        return this;
    }

    public void setBairroEndereco(String bairroEndereco) {
        this.bairroEndereco = bairroEndereco;
    }

    public Integer getCepEndereco() {
        return cepEndereco;
    }

    public Servidor cepEndereco(Integer cepEndereco) {
        this.cepEndereco = cepEndereco;
        return this;
    }

    public void setCepEndereco(Integer cepEndereco) {
        this.cepEndereco = cepEndereco;
    }

    public String getUfEndereco() {
        return ufEndereco;
    }

    public Servidor ufEndereco(String ufEndereco) {
        this.ufEndereco = ufEndereco;
        return this;
    }

    public void setUfEndereco(String ufEndereco) {
        this.ufEndereco = ufEndereco;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public Servidor telefone1(String telefone1) {
        this.telefone1 = telefone1;
        return this;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public Servidor telefone2(String telefone2) {
        this.telefone2 = telefone2;
        return this;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getDescLograExt() {
        return descLograExt;
    }

    public Servidor descLograExt(String descLograExt) {
        this.descLograExt = descLograExt;
        return this;
    }

    public void setDescLograExt(String descLograExt) {
        this.descLograExt = descLograExt;
    }

    public String getNrLograExt() {
        return nrLograExt;
    }

    public Servidor nrLograExt(String nrLograExt) {
        this.nrLograExt = nrLograExt;
        return this;
    }

    public void setNrLograExt(String nrLograExt) {
        this.nrLograExt = nrLograExt;
    }

    public String getComplLograExt() {
        return complLograExt;
    }

    public Servidor complLograExt(String complLograExt) {
        this.complLograExt = complLograExt;
        return this;
    }

    public void setComplLograExt(String complLograExt) {
        this.complLograExt = complLograExt;
    }

    public String getBairroEndExt() {
        return bairroEndExt;
    }

    public Servidor bairroEndExt(String bairroEndExt) {
        this.bairroEndExt = bairroEndExt;
        return this;
    }

    public void setBairroEndExt(String bairroEndExt) {
        this.bairroEndExt = bairroEndExt;
    }

    public String getCidadeEndExt() {
        return cidadeEndExt;
    }

    public Servidor cidadeEndExt(String cidadeEndExt) {
        this.cidadeEndExt = cidadeEndExt;
        return this;
    }

    public void setCidadeEndExt(String cidadeEndExt) {
        this.cidadeEndExt = cidadeEndExt;
    }

    public String getCodPostalEndExt() {
        return codPostalEndExt;
    }

    public Servidor codPostalEndExt(String codPostalEndExt) {
        this.codPostalEndExt = codPostalEndExt;
        return this;
    }

    public void setCodPostalEndExt(String codPostalEndExt) {
        this.codPostalEndExt = codPostalEndExt;
    }

    public String getNumRne() {
        return numRne;
    }

    public Servidor numRne(String numRne) {
        this.numRne = numRne;
        return this;
    }

    public void setNumRne(String numRne) {
        this.numRne = numRne;
    }

    public String getOrgaoEmissorRNE() {
        return orgaoEmissorRNE;
    }

    public Servidor orgaoEmissorRNE(String orgaoEmissorRNE) {
        this.orgaoEmissorRNE = orgaoEmissorRNE;
        return this;
    }

    public void setOrgaoEmissorRNE(String orgaoEmissorRNE) {
        this.orgaoEmissorRNE = orgaoEmissorRNE;
    }

    public String getUfEmissorRNE() {
        return ufEmissorRNE;
    }

    public Servidor ufEmissorRNE(String ufEmissorRNE) {
        this.ufEmissorRNE = ufEmissorRNE;
        return this;
    }

    public void setUfEmissorRNE(String ufEmissorRNE) {
        this.ufEmissorRNE = ufEmissorRNE;
    }

    public LocalDate getDatExpeRNE() {
        return datExpeRNE;
    }

    public Servidor datExpeRNE(LocalDate datExpeRNE) {
        this.datExpeRNE = datExpeRNE;
        return this;
    }

    public void setDatExpeRNE(LocalDate datExpeRNE) {
        this.datExpeRNE = datExpeRNE;
    }

    public LocalDate getDatChegada() {
        return datChegada;
    }

    public Servidor datChegada(LocalDate datChegada) {
        this.datChegada = datChegada;
        return this;
    }

    public void setDatChegada(LocalDate datChegada) {
        this.datChegada = datChegada;
    }

    public Integer getClassTrabEstr() {
        return classTrabEstr;
    }

    public Servidor classTrabEstr(Integer classTrabEstr) {
        this.classTrabEstr = classTrabEstr;
        return this;
    }

    public void setClassTrabEstr(Integer classTrabEstr) {
        this.classTrabEstr = classTrabEstr;
    }

    public String getCasadoBR() {
        return casadoBR;
    }

    public Servidor casadoBR(String casadoBR) {
        this.casadoBR = casadoBR;
        return this;
    }

    public void setCasadoBR(String casadoBR) {
        this.casadoBR = casadoBR;
    }

    public String getFilhosBRr() {
        return filhosBRr;
    }

    public Servidor filhosBRr(String filhosBRr) {
        this.filhosBRr = filhosBRr;
        return this;
    }

    public void setFilhosBRr(String filhosBRr) {
        this.filhosBRr = filhosBRr;
    }

    public String getAuxTransporte() {
        return auxTransporte;
    }

    public Servidor auxTransporte(String auxTransporte) {
        this.auxTransporte = auxTransporte;
        return this;
    }

    public void setAuxTransporte(String auxTransporte) {
        this.auxTransporte = auxTransporte;
    }

    public String getAuxTransLinha1() {
        return auxTransLinha1;
    }

    public Servidor auxTransLinha1(String auxTransLinha1) {
        this.auxTransLinha1 = auxTransLinha1;
        return this;
    }

    public void setAuxTransLinha1(String auxTransLinha1) {
        this.auxTransLinha1 = auxTransLinha1;
    }

    public String getAuxTransLinha2() {
        return auxTransLinha2;
    }

    public Servidor auxTransLinha2(String auxTransLinha2) {
        this.auxTransLinha2 = auxTransLinha2;
        return this;
    }

    public void setAuxTransLinha2(String auxTransLinha2) {
        this.auxTransLinha2 = auxTransLinha2;
    }

    public String getAuxTransLinha3() {
        return auxTransLinha3;
    }

    public Servidor auxTransLinha3(String auxTransLinha3) {
        this.auxTransLinha3 = auxTransLinha3;
        return this;
    }

    public void setAuxTransLinha3(String auxTransLinha3) {
        this.auxTransLinha3 = auxTransLinha3;
    }

    public String getAuxTransLinha4() {
        return auxTransLinha4;
    }

    public Servidor auxTransLinha4(String auxTransLinha4) {
        this.auxTransLinha4 = auxTransLinha4;
        return this;
    }

    public void setAuxTransLinha4(String auxTransLinha4) {
        this.auxTransLinha4 = auxTransLinha4;
    }

    public String getTrabAposentado() {
        return trabAposentado;
    }

    public Servidor trabAposentado(String trabAposentado) {
        this.trabAposentado = trabAposentado;
        return this;
    }

    public void setTrabAposentado(String trabAposentado) {
        this.trabAposentado = trabAposentado;
    }

    public Set<Dependente> getSerMatriculas() {
        return serMatriculas;
    }

    public Servidor serMatriculas(Set<Dependente> dependentes) {
        this.serMatriculas = dependentes;
        return this;
    }

    public Servidor addSerMatricula(Dependente dependente) {
        this.serMatriculas.add(dependente);
        dependente.setServidor(this);
        return this;
    }

    public Servidor removeSerMatricula(Dependente dependente) {
        this.serMatriculas.remove(dependente);
        dependente.setServidor(null);
        return this;
    }

    public void setSerMatriculas(Set<Dependente> dependentes) {
        this.serMatriculas = dependentes;
    }

    public Set<ParenteServidor> getSerMatriculas() {
        return serMatriculas;
    }

    public Servidor serMatriculas(Set<ParenteServidor> parenteServidors) {
        this.serMatriculas = parenteServidors;
        return this;
    }

    public Servidor addSerMatricula(ParenteServidor parenteServidor) {
        this.serMatriculas.add(parenteServidor);
        parenteServidor.setServidor(this);
        return this;
    }

    public Servidor removeSerMatricula(ParenteServidor parenteServidor) {
        this.serMatriculas.remove(parenteServidor);
        parenteServidor.setServidor(null);
        return this;
    }

    public void setSerMatriculas(Set<ParenteServidor> parenteServidors) {
        this.serMatriculas = parenteServidors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Servidor servidor = (Servidor) o;
        if (servidor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), servidor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Servidor{" +
            "id=" + getId() +
            ", matricula=" + getMatricula() +
            ", username='" + getUsername() + "'" +
            ", nomeSocial='" + getNomeSocial() + "'" +
            ", datNascimento='" + getDatNascimento() + "'" +
            ", nomePai='" + getNomePai() + "'" +
            ", nomeMae='" + getNomeMae() + "'" +
            ", cpf=" + getCpf() +
            ", cpfPai=" + getCpfPai() +
            ", cpfMae=" + getCpfMae() +
            ", rgNum='" + getRgNum() + "'" +
            ", rgUf='" + getRgUf() + "'" +
            ", rgOrgaoExp='" + getRgOrgaoExp() + "'" +
            ", rgDataExp='" + getRgDataExp() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", tipoSangue='" + getTipoSangue() + "'" +
            ", fatorRh='" + getFatorRh() + "'" +
            ", doadorSangue='" + getDoadorSangue() + "'" +
            ", deficienciaFisica='" + getDeficienciaFisica() + "'" +
            ", deficienciaVisual='" + getDeficienciaVisual() + "'" +
            ", deficienciaAuditiva='" + getDeficienciaAuditiva() + "'" +
            ", deficienciaMental='" + getDeficienciaMental() + "'" +
            ", deficienciaIntelec='" + getDeficienciaIntelec() + "'" +
            ", deficReabReadaptado='" + getDeficReabReadaptado() + "'" +
            ", deficObservacao='" + getDeficObservacao() + "'" +
            ", preencheCota='" + getPreencheCota() + "'" +
            ", emailPessoal='" + getEmailPessoal() + "'" +
            ", emailAltern='" + getEmailAltern() + "'" +
            ", celular='" + getCelular() + "'" +
            ", celularOperadora='" + getCelularOperadora() + "'" +
            ", pasep='" + getPasep() + "'" +
            ", ipsep='" + getIpsep() + "'" +
            ", inss='" + getInss() + "'" +
            ", ctpsNum='" + getCtpsNum() + "'" +
            ", ctpsSerie='" + getCtpsSerie() + "'" +
            ", ctpsEmissao='" + getCtpsEmissao() + "'" +
            ", ctpsUf='" + getCtpsUf() + "'" +
            ", orgaoClasseNum='" + getOrgaoClasseNum() + "'" +
            ", orgaoClNome='" + getOrgaoClNome() + "'" +
            ", orgaoClUf='" + getOrgaoClUf() + "'" +
            ", orclDtaExp='" + getOrclDtaExp() + "'" +
            ", orclDtaValid='" + getOrclDtaValid() + "'" +
            ", reservista='" + getReservista() + "'" +
            ", reservistaClasse='" + getReservistaClasse() + "'" +
            ", numRIC='" + getNumRIC() + "'" +
            ", orgaoEmissorRIC='" + getOrgaoEmissorRIC() + "'" +
            ", ufRIC='" + getUfRIC() + "'" +
            ", datExpedRIC='" + getDatExpedRIC() + "'" +
            ", titEleitorNum='" + getTitEleitorNum() + "'" +
            ", titEleitorZona='" + getTitEleitorZona() + "'" +
            ", titEleitorSecao='" + getTitEleitorSecao() + "'" +
            ", titEleitorLocal='" + getTitEleitorLocal() + "'" +
            ", cnhNum='" + getCnhNum() + "'" +
            ", cnhCategoria='" + getCnhCategoria() + "'" +
            ", cnhDatValidade='" + getCnhDatValidade() + "'" +
            ", cnhDatExped='" + getCnhDatExped() + "'" +
            ", cnhUfEmissor='" + getCnhUfEmissor() + "'" +
            ", cnhDatPrimHab='" + getCnhDatPrimHab() + "'" +
            ", sassepeNum='" + getSassepeNum() + "'" +
            ", ufNatural='" + getUfNatural() + "'" +
            ", primEmprego='" + getPrimEmprego() + "'" +
            ", exercMagisterio='" + getExercMagisterio() + "'" +
            ", tipoLogradouro=" + getTipoLogradouro() +
            ", descLogradouro='" + getDescLogradouro() + "'" +
            ", nrEndereco='" + getNrEndereco() + "'" +
            ", complEndereco='" + getComplEndereco() + "'" +
            ", bairroEndereco='" + getBairroEndereco() + "'" +
            ", cepEndereco=" + getCepEndereco() +
            ", ufEndereco='" + getUfEndereco() + "'" +
            ", telefone1='" + getTelefone1() + "'" +
            ", telefone2='" + getTelefone2() + "'" +
            ", descLograExt='" + getDescLograExt() + "'" +
            ", nrLograExt='" + getNrLograExt() + "'" +
            ", complLograExt='" + getComplLograExt() + "'" +
            ", bairroEndExt='" + getBairroEndExt() + "'" +
            ", cidadeEndExt='" + getCidadeEndExt() + "'" +
            ", codPostalEndExt='" + getCodPostalEndExt() + "'" +
            ", numRne='" + getNumRne() + "'" +
            ", orgaoEmissorRNE='" + getOrgaoEmissorRNE() + "'" +
            ", ufEmissorRNE='" + getUfEmissorRNE() + "'" +
            ", datExpeRNE='" + getDatExpeRNE() + "'" +
            ", datChegada='" + getDatChegada() + "'" +
            ", classTrabEstr=" + getClassTrabEstr() +
            ", casadoBR='" + getCasadoBR() + "'" +
            ", filhosBRr='" + getFilhosBRr() + "'" +
            ", auxTransporte='" + getAuxTransporte() + "'" +
            ", auxTransLinha1='" + getAuxTransLinha1() + "'" +
            ", auxTransLinha2='" + getAuxTransLinha2() + "'" +
            ", auxTransLinha3='" + getAuxTransLinha3() + "'" +
            ", auxTransLinha4='" + getAuxTransLinha4() + "'" +
            ", trabAposentado='" + getTrabAposentado() + "'" +
            "}";
    }
}
