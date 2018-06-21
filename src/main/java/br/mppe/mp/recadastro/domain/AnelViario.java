package br.mppe.mp.recadastro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AnelViario.
 */
@Entity
@Table(name = "anel_viario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "anelviario")
public class AnelViario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cod_anel", nullable = false)
    private Integer codAnel;

    @Column(name = "desc_anel")
    private String descAnel;

    @OneToOne
    @JoinColumn(unique = true)
    private Servidor task;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodAnel() {
        return codAnel;
    }

    public AnelViario codAnel(Integer codAnel) {
        this.codAnel = codAnel;
        return this;
    }

    public void setCodAnel(Integer codAnel) {
        this.codAnel = codAnel;
    }

    public String getDescAnel() {
        return descAnel;
    }

    public AnelViario descAnel(String descAnel) {
        this.descAnel = descAnel;
        return this;
    }

    public void setDescAnel(String descAnel) {
        this.descAnel = descAnel;
    }

    public Servidor getTask() {
        return task;
    }

    public AnelViario task(Servidor servidor) {
        this.task = servidor;
        return this;
    }

    public void setTask(Servidor servidor) {
        this.task = servidor;
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
        AnelViario anelViario = (AnelViario) o;
        if (anelViario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anelViario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnelViario{" +
            "id=" + getId() +
            ", codAnel=" + getCodAnel() +
            ", descAnel='" + getDescAnel() + "'" +
            "}";
    }
}
