package br.mppe.mp.recadastro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EstadoCivil.
 */
@Entity
@Table(name = "estado_civil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "estadocivil")
public class EstadoCivil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "num_est_civil", nullable = false)
    private Integer numEstCivil;

    @Column(name = "desc_est_civil")
    private String descEstCivil;

    @OneToOne
    @JoinColumn(unique = true)
    private Servidor estadoCivil;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumEstCivil() {
        return numEstCivil;
    }

    public EstadoCivil numEstCivil(Integer numEstCivil) {
        this.numEstCivil = numEstCivil;
        return this;
    }

    public void setNumEstCivil(Integer numEstCivil) {
        this.numEstCivil = numEstCivil;
    }

    public String getDescEstCivil() {
        return descEstCivil;
    }

    public EstadoCivil descEstCivil(String descEstCivil) {
        this.descEstCivil = descEstCivil;
        return this;
    }

    public void setDescEstCivil(String descEstCivil) {
        this.descEstCivil = descEstCivil;
    }

    public Servidor getEstadoCivil() {
        return estadoCivil;
    }

    public EstadoCivil estadoCivil(Servidor servidor) {
        this.estadoCivil = servidor;
        return this;
    }

    public void setEstadoCivil(Servidor servidor) {
        this.estadoCivil = servidor;
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
        EstadoCivil estadoCivil = (EstadoCivil) o;
        if (estadoCivil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estadoCivil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstadoCivil{" +
            "id=" + getId() +
            ", numEstCivil=" + getNumEstCivil() +
            ", descEstCivil='" + getDescEstCivil() + "'" +
            "}";
    }
}
