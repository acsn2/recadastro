package br.mppe.mp.recadastro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ServidorTipo.
 */
@Entity
@Table(name = "servidor_tipo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "servidortipo")
public class ServidorTipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Long numero;

    @Column(name = "nome")
    private String nome;

    @Column(name = "indicativo")
    private String indicativo;

    @OneToOne
    @JoinColumn(unique = true)
    private Servidor servidorTipo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public ServidorTipo numero(Long numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public ServidorTipo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndicativo() {
        return indicativo;
    }

    public ServidorTipo indicativo(String indicativo) {
        this.indicativo = indicativo;
        return this;
    }

    public void setIndicativo(String indicativo) {
        this.indicativo = indicativo;
    }

    public Servidor getServidorTipo() {
        return servidorTipo;
    }

    public ServidorTipo servidorTipo(Servidor servidor) {
        this.servidorTipo = servidor;
        return this;
    }

    public void setServidorTipo(Servidor servidor) {
        this.servidorTipo = servidor;
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
        ServidorTipo servidorTipo = (ServidorTipo) o;
        if (servidorTipo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), servidorTipo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServidorTipo{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", nome='" + getNome() + "'" +
            ", indicativo='" + getIndicativo() + "'" +
            "}";
    }
}
