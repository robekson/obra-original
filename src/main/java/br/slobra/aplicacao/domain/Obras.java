package br.slobra.aplicacao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
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

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "jhi_local", nullable = false)
    private String local;

    @Column(name = "metragem")
    private Long metragem;

    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @OneToMany(mappedBy = "obras")
    private Set<Periodo> obras = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("lancamentos")
    private LancamentoGastos lancamentoGastos;

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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public Obras dataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public Obras dataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
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

    public LancamentoGastos getLancamentoGastos() {
        return lancamentoGastos;
    }

    public Obras lancamentoGastos(LancamentoGastos lancamentoGastos) {
        this.lancamentoGastos = lancamentoGastos;
        return this;
    }

    public void setLancamentoGastos(LancamentoGastos lancamentoGastos) {
        this.lancamentoGastos = lancamentoGastos;
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
