package br.slobra.aplicacao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Obras.
 */
@Entity
@Table(name = "obras")
public class Obras implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "jhi_local")
    private String local;

    @Column(name = "metragem")
    private Long metragem;

    @Column(name = "status")
    private String status;

    @Column(name = "data_inicio")
    private Instant dataInicio;

    @Column(name = "data_fim")
    private Instant dataFim;

    @OneToOne    @JoinColumn(unique = true)
    private LancamentoGastos obra;

    @OneToMany(mappedBy = "obras")
    private Set<Periodo> obras = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Obras nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public Obras local(String local) {
        this.local = local;
        return this;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Long getMetragem() {
        return metragem;
    }

    public Obras metragem(Long metragem) {
        this.metragem = metragem;
        return this;
    }

    public void setMetragem(Long metragem) {
        this.metragem = metragem;
    }

    public String getStatus() {
        return status;
    }

    public Obras status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getDataInicio() {
        return dataInicio;
    }

    public Obras dataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getDataFim() {
        return dataFim;
    }

    public Obras dataFim(Instant dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    public void setDataFim(Instant dataFim) {
        this.dataFim = dataFim;
    }

    public LancamentoGastos getObra() {
        return obra;
    }

    public Obras obra(LancamentoGastos lancamentoGastos) {
        this.obra = lancamentoGastos;
        return this;
    }

    public void setObra(LancamentoGastos lancamentoGastos) {
        this.obra = lancamentoGastos;
    }

    public Set<Periodo> getObras() {
        return obras;
    }

    public Obras obras(Set<Periodo> periodos) {
        this.obras = periodos;
        return this;
    }

    public Obras addObra(Periodo periodo) {
        this.obras.add(periodo);
        periodo.setObras(this);
        return this;
    }

    public Obras removeObra(Periodo periodo) {
        this.obras.remove(periodo);
        periodo.setObras(null);
        return this;
    }

    public void setObras(Set<Periodo> periodos) {
        this.obras = periodos;
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
        Obras obras = (Obras) o;
        if (obras.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), obras.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Obras{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", local='" + getLocal() + "'" +
            ", metragem=" + getMetragem() +
            ", status='" + getStatus() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            "}";
    }
}
