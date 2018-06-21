package br.mppe.mp.recadastro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Orgao.
 */
@Entity
@Table(name = "orgao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orgao")
public class Orgao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "num_orgao", nullable = false)
    private Integer numOrgao;

    @Column(name = "desc_orgao")
    private String descOrgao;

    @OneToOne
    @JoinColumn(unique = true)
    private Servidor orgao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumOrgao() {
        return numOrgao;
    }

    public Orgao numOrgao(Integer numOrgao) {
        this.numOrgao = numOrgao;
        return this;
    }

    public void setNumOrgao(Integer numOrgao) {
        this.numOrgao = numOrgao;
    }

    public String getDescOrgao() {
        return descOrgao;
    }

    public Orgao descOrgao(String descOrgao) {
        this.descOrgao = descOrgao;
        return this;
    }

    public void setDescOrgao(String descOrgao) {
        this.descOrgao = descOrgao;
    }

    public Servidor getOrgao() {
        return orgao;
    }

    public Orgao orgao(Servidor servidor) {
        this.orgao = servidor;
        return this;
    }

    public void setOrgao(Servidor servidor) {
        this.orgao = servidor;
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
        Orgao orgao = (Orgao) o;
        if (orgao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orgao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Orgao{" +
            "id=" + getId() +
            ", numOrgao=" + getNumOrgao() +
            ", descOrgao='" + getDescOrgao() + "'" +
            "}";
    }
}
