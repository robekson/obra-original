package br.slobra.aplicacao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Periodo.
 */
@Entity
@Table(name = "periodo")
public class Periodo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_conta")
    private String idConta;

    @Column(name = "data")
    private Instant data;

    @ManyToOne
    @JsonIgnoreProperties("obras")
    private Obras obras;

    @OneToOne    @JoinColumn(unique = true)
    private Conta periodo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdConta() {
        return idConta;
    }

    public Periodo idConta(String idConta) {
        this.idConta = idConta;
        return this;
    }

    public void setIdConta(String idConta) {
        this.idConta = idConta;
    }

    public Instant getData() {
        return data;
    }

    public Periodo data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Obras getObras() {
        return obras;
    }

    public Periodo obras(Obras obras) {
        this.obras = obras;
        return this;
    }

    public void setObras(Obras obras) {
        this.obras = obras;
    }

    public Conta getPeriodo() {
        return periodo;
    }

    public Periodo periodo(Conta conta) {
        this.periodo = conta;
        return this;
    }

    public void setPeriodo(Conta conta) {
        this.periodo = conta;
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
        Periodo periodo = (Periodo) o;
        if (periodo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), periodo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Periodo{" +
            "id=" + getId() +
            ", idConta='" + getIdConta() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
