package br.mppe.mp.recadastro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Pais.
 */
@Entity
@Table(name = "pais")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pais")
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cod_pais", nullable = false)
    private Integer codPais;

    @Column(name = "nome_pais")
    private String nomePais;

    @OneToOne
    @JoinColumn(unique = true)
    private Servidor pais;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodPais() {
        return codPais;
    }

    public Pais codPais(Integer codPais) {
        this.codPais = codPais;
        return this;
    }

    public void setCodPais(Integer codPais) {
        this.codPais = codPais;
    }

    public String getNomePais() {
        return nomePais;
    }

    public Pais nomePais(String nomePais) {
        this.nomePais = nomePais;
        return this;
    }

    public void setNomePais(String nomePais) {
        this.nomePais = nomePais;
    }

    public Servidor getPais() {
        return pais;
    }

    public Pais pais(Servidor servidor) {
        this.pais = servidor;
        return this;
    }

    public void setPais(Servidor servidor) {
        this.pais = servidor;
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
        Pais pais = (Pais) o;
        if (pais.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pais.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pais{" +
            "id=" + getId() +
            ", codPais=" + getCodPais() +
            ", nomePais='" + getNomePais() + "'" +
            "}";
    }
}
