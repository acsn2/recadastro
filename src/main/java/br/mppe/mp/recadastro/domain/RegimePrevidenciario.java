package br.mppe.mp.recadastro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RegimePrevidenciario.
 */
@Entity
@Table(name = "regime_previdenciario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "regimeprevidenciario")
public class RegimePrevidenciario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cod_reg_prev", nullable = false)
    private Integer codRegPrev;

    @Column(name = "desc_reg_prev")
    private String descRegPrev;

    @OneToOne
    @JoinColumn(unique = true)
    private Servidor regimePrevidenciario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodRegPrev() {
        return codRegPrev;
    }

    public RegimePrevidenciario codRegPrev(Integer codRegPrev) {
        this.codRegPrev = codRegPrev;
        return this;
    }

    public void setCodRegPrev(Integer codRegPrev) {
        this.codRegPrev = codRegPrev;
    }

    public String getDescRegPrev() {
        return descRegPrev;
    }

    public RegimePrevidenciario descRegPrev(String descRegPrev) {
        this.descRegPrev = descRegPrev;
        return this;
    }

    public void setDescRegPrev(String descRegPrev) {
        this.descRegPrev = descRegPrev;
    }

    public Servidor getRegimePrevidenciario() {
        return regimePrevidenciario;
    }

    public RegimePrevidenciario regimePrevidenciario(Servidor servidor) {
        this.regimePrevidenciario = servidor;
        return this;
    }

    public void setRegimePrevidenciario(Servidor servidor) {
        this.regimePrevidenciario = servidor;
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
        RegimePrevidenciario regimePrevidenciario = (RegimePrevidenciario) o;
        if (regimePrevidenciario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regimePrevidenciario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegimePrevidenciario{" +
            "id=" + getId() +
            ", codRegPrev=" + getCodRegPrev() +
            ", descRegPrev='" + getDescRegPrev() + "'" +
            "}";
    }
}
