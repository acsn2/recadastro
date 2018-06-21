package br.mppe.mp.recadastro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ParenteServidor.
 */
@Entity
@Table(name = "parente_servidor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "parenteservidor")
public class ParenteServidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fk_parente_servidor", nullable = false)
    private Long fkParenteServidor;

    @Column(name = "matricula_parente")
    private Long matriculaParente;

    @Column(name = "fk_grau_parentesco")
    private String fkGrauParentesco;

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

    public Long getFkParenteServidor() {
        return fkParenteServidor;
    }

    public ParenteServidor fkParenteServidor(Long fkParenteServidor) {
        this.fkParenteServidor = fkParenteServidor;
        return this;
    }

    public void setFkParenteServidor(Long fkParenteServidor) {
        this.fkParenteServidor = fkParenteServidor;
    }

    public Long getMatriculaParente() {
        return matriculaParente;
    }

    public ParenteServidor matriculaParente(Long matriculaParente) {
        this.matriculaParente = matriculaParente;
        return this;
    }

    public void setMatriculaParente(Long matriculaParente) {
        this.matriculaParente = matriculaParente;
    }

    public String getFkGrauParentesco() {
        return fkGrauParentesco;
    }

    public ParenteServidor fkGrauParentesco(String fkGrauParentesco) {
        this.fkGrauParentesco = fkGrauParentesco;
        return this;
    }

    public void setFkGrauParentesco(String fkGrauParentesco) {
        this.fkGrauParentesco = fkGrauParentesco;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public ParenteServidor servidor(Servidor servidor) {
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
        ParenteServidor parenteServidor = (ParenteServidor) o;
        if (parenteServidor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parenteServidor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParenteServidor{" +
            "id=" + getId() +
            ", fkParenteServidor=" + getFkParenteServidor() +
            ", matriculaParente=" + getMatriculaParente() +
            ", fkGrauParentesco='" + getFkGrauParentesco() + "'" +
            "}";
    }
}
