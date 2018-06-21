package br.mppe.mp.recadastro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Cidade.
 */
@Entity
@Table(name = "cidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cidade")
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cod_cidade", nullable = false)
    private Integer codCidade;

    @Column(name = "nome_cidade")
    private String nomeCidade;

    @OneToOne
    @JoinColumn(unique = true)
    private Servidor cidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodCidade() {
        return codCidade;
    }

    public Cidade codCidade(Integer codCidade) {
        this.codCidade = codCidade;
        return this;
    }

    public void setCodCidade(Integer codCidade) {
        this.codCidade = codCidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public Cidade nomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
        return this;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public Servidor getCidade() {
        return cidade;
    }

    public Cidade cidade(Servidor servidor) {
        this.cidade = servidor;
        return this;
    }

    public void setCidade(Servidor servidor) {
        this.cidade = servidor;
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
        Cidade cidade = (Cidade) o;
        if (cidade.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cidade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cidade{" +
            "id=" + getId() +
            ", codCidade=" + getCodCidade() +
            ", nomeCidade='" + getNomeCidade() + "'" +
            "}";
    }
}
