package br.mppe.mp.recadastro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Dependente.
 */
@Entity
@Table(name = "dependente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dependente")
public class Dependente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fk_dependente", nullable = false)
    private Long fkDependente;

    @NotNull
    @Column(name = "tp_dependente", nullable = false)
    private Integer tpDependente;

    @NotNull
    @Column(name = "nome_depend", nullable = false)
    private String nomeDepend;

    @NotNull
    @Column(name = "dat_nac", nullable = false)
    private LocalDate datNac;

    @Column(name = "cpf_depend")
    private String cpfDepend;

    @Column(name = "dep_irrf")
    private String depIRRF;

    @Column(name = "dep_sf")
    private String depSF;

    @Column(name = "incapac_trab")
    private String incapacTrab;

    @ManyToOne
    @JsonIgnoreProperties("serMatriculas")
    private Servidor servidor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFkDependente() {
        return fkDependente;
    }

    public Dependente fkDependente(Long fkDependente) {
        this.fkDependente = fkDependente;
        return this;
    }

    public void setFkDependente(Long fkDependente) {
        this.fkDependente = fkDependente;
    }

    public Integer getTpDependente() {
        return tpDependente;
    }

    public Dependente tpDependente(Integer tpDependente) {
        this.tpDependente = tpDependente;
        return this;
    }

    public void setTpDependente(Integer tpDependente) {
        this.tpDependente = tpDependente;
    }

    public String getNomeDepend() {
        return nomeDepend;
    }

    public Dependente nomeDepend(String nomeDepend) {
        this.nomeDepend = nomeDepend;
        return this;
    }

    public void setNomeDepend(String nomeDepend) {
        this.nomeDepend = nomeDepend;
    }

    public LocalDate getDatNac() {
        return datNac;
    }

    public Dependente datNac(LocalDate datNac) {
        this.datNac = datNac;
        return this;
    }

    public void setDatNac(LocalDate datNac) {
        this.datNac = datNac;
    }

    public String getCpfDepend() {
        return cpfDepend;
    }

    public Dependente cpfDepend(String cpfDepend) {
        this.cpfDepend = cpfDepend;
        return this;
    }

    public void setCpfDepend(String cpfDepend) {
        this.cpfDepend = cpfDepend;
    }

    public String getDepIRRF() {
        return depIRRF;
    }

    public Dependente depIRRF(String depIRRF) {
        this.depIRRF = depIRRF;
        return this;
    }

    public void setDepIRRF(String depIRRF) {
        this.depIRRF = depIRRF;
    }

    public String getDepSF() {
        return depSF;
    }

    public Dependente depSF(String depSF) {
        this.depSF = depSF;
        return this;
    }

    public void setDepSF(String depSF) {
        this.depSF = depSF;
    }

    public String getIncapacTrab() {
        return incapacTrab;
    }

    public Dependente incapacTrab(String incapacTrab) {
        this.incapacTrab = incapacTrab;
        return this;
    }

    public void setIncapacTrab(String incapacTrab) {
        this.incapacTrab = incapacTrab;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public Dependente servidor(Servidor servidor) {
        this.servidor = servidor;
        return this;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
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
        Dependente dependente = (Dependente) o;
        if (dependente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dependente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dependente{" +
            "id=" + getId() +
            ", fkDependente=" + getFkDependente() +
            ", tpDependente=" + getTpDependente() +
            ", nomeDepend='" + getNomeDepend() + "'" +
            ", datNac='" + getDatNac() + "'" +
            ", cpfDepend='" + getCpfDepend() + "'" +
            ", depIRRF='" + getDepIRRF() + "'" +
            ", depSF='" + getDepSF() + "'" +
            ", incapacTrab='" + getIncapacTrab() + "'" +
            "}";
    }
}
