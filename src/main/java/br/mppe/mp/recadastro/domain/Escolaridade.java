package br.mppe.mp.recadastro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Escolaridade.
 */
@Entity
@Table(name = "escolaridade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "escolaridade")
public class Escolaridade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cod_escolaridade", nullable = false)
    private Integer codEscolaridade;

    @Column(name = "desc_escolaridade")
    private String descEscolaridade;

    @OneToOne
    @JoinColumn(unique = true)
    private Servidor escolaridade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodEscolaridade() {
        return codEscolaridade;
    }

    public Escolaridade codEscolaridade(Integer codEscolaridade) {
        this.codEscolaridade = codEscolaridade;
        return this;
    }

    public void setCodEscolaridade(Integer codEscolaridade) {
        this.codEscolaridade = codEscolaridade;
    }

    public String getDescEscolaridade() {
        return descEscolaridade;
    }

    public Escolaridade descEscolaridade(String descEscolaridade) {
        this.descEscolaridade = descEscolaridade;
        return this;
    }

    public void setDescEscolaridade(String descEscolaridade) {
        this.descEscolaridade = descEscolaridade;
    }

    public Servidor getEscolaridade() {
        return escolaridade;
    }

    public Escolaridade escolaridade(Servidor servidor) {
        this.escolaridade = servidor;
        return this;
    }

    public void setEscolaridade(Servidor servidor) {
        this.escolaridade = servidor;
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
        Escolaridade escolaridade = (Escolaridade) o;
        if (escolaridade.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), escolaridade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Escolaridade{" +
            "id=" + getId() +
            ", codEscolaridade=" + getCodEscolaridade() +
            ", descEscolaridade='" + getDescEscolaridade() + "'" +
            "}";
    }
}
