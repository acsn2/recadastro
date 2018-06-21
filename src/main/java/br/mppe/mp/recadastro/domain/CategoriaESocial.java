package br.mppe.mp.recadastro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CategoriaESocial.
 */
@Entity
@Table(name = "categoria_e_social")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "categoriaesocial")
public class CategoriaESocial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cod_categoria", nullable = false)
    private Integer codCategoria;

    @Column(name = "desc_categoria")
    private String descCategoria;

    @Column(name = "grupo_cat")
    private String grupoCat;

    @OneToOne
    @JoinColumn(unique = true)
    private Servidor categoriaESocial;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodCategoria() {
        return codCategoria;
    }

    public CategoriaESocial codCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
        return this;
    }

    public void setCodCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getDescCategoria() {
        return descCategoria;
    }

    public CategoriaESocial descCategoria(String descCategoria) {
        this.descCategoria = descCategoria;
        return this;
    }

    public void setDescCategoria(String descCategoria) {
        this.descCategoria = descCategoria;
    }

    public String getGrupoCat() {
        return grupoCat;
    }

    public CategoriaESocial grupoCat(String grupoCat) {
        this.grupoCat = grupoCat;
        return this;
    }

    public void setGrupoCat(String grupoCat) {
        this.grupoCat = grupoCat;
    }

    public Servidor getCategoriaESocial() {
        return categoriaESocial;
    }

    public CategoriaESocial categoriaESocial(Servidor servidor) {
        this.categoriaESocial = servidor;
        return this;
    }

    public void setCategoriaESocial(Servidor servidor) {
        this.categoriaESocial = servidor;
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
        CategoriaESocial categoriaESocial = (CategoriaESocial) o;
        if (categoriaESocial.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoriaESocial.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoriaESocial{" +
            "id=" + getId() +
            ", codCategoria=" + getCodCategoria() +
            ", descCategoria='" + getDescCategoria() + "'" +
            ", grupoCat='" + getGrupoCat() + "'" +
            "}";
    }
}
