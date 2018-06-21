package br.mppe.mp.recadastro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RacaCor.
 */
@Entity
@Table(name = "raca_cor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "racacor")
public class RacaCor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cod_raca_cor", nullable = false)
    private Integer codRacaCor;

    @Column(name = "desc_raca_cor")
    private String descRacaCor;

    @OneToOne
    @JoinColumn(unique = true)
    private Servidor racaCor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodRacaCor() {
        return codRacaCor;
    }

    public RacaCor codRacaCor(Integer codRacaCor) {
        this.codRacaCor = codRacaCor;
        return this;
    }

    public void setCodRacaCor(Integer codRacaCor) {
        this.codRacaCor = codRacaCor;
    }

    public String getDescRacaCor() {
        return descRacaCor;
    }

    public RacaCor descRacaCor(String descRacaCor) {
        this.descRacaCor = descRacaCor;
        return this;
    }

    public void setDescRacaCor(String descRacaCor) {
        this.descRacaCor = descRacaCor;
    }

    public Servidor getRacaCor() {
        return racaCor;
    }

    public RacaCor racaCor(Servidor servidor) {
        this.racaCor = servidor;
        return this;
    }

    public void setRacaCor(Servidor servidor) {
        this.racaCor = servidor;
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
        RacaCor racaCor = (RacaCor) o;
        if (racaCor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), racaCor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RacaCor{" +
            "id=" + getId() +
            ", codRacaCor=" + getCodRacaCor() +
            ", descRacaCor='" + getDescRacaCor() + "'" +
            "}";
    }
}
