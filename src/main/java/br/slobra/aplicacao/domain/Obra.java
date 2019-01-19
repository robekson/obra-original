package br.slobra.aplicacao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import br.slobra.aplicacao.domain.enumeration.TipoCorretagem;

import br.slobra.aplicacao.domain.enumeration.StatusObra;

/**
 * A Obra.
 */
@Entity
@Table(name = "obra")
public class Obra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "metragem")
    private Long metragem;

    @Column(name = "valor_terreno", precision = 10, scale = 2)
    private BigDecimal valorTerreno;

    @Column(name = "valor_escritura", precision = 10, scale = 2)
    private BigDecimal valorEscritura;

    @Column(name = "porcentagem_corretagem")
    private Integer porcentagemCorretagem;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_corretagem")
    private TipoCorretagem tipoCorretagem;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusObra status;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @OneToOne    @JoinColumn(unique = true)
    private ResumoGasto resumoGasto;

    @OneToMany(mappedBy = "obra")
    private Set<Gasto> gastos = new HashSet<>();
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

    public Obra nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public Obra endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getMetragem() {
        return metragem;
    }

    public Obra metragem(Long metragem) {
        this.metragem = metragem;
        return this;
    }

    public void setMetragem(Long metragem) {
        this.metragem = metragem;
    }

    public BigDecimal getValorTerreno() {
        return valorTerreno;
    }

    public Obra valorTerreno(BigDecimal valorTerreno) {
        this.valorTerreno = valorTerreno;
        return this;
    }

    public void setValorTerreno(BigDecimal valorTerreno) {
        this.valorTerreno = valorTerreno;
    }

    public BigDecimal getValorEscritura() {
        return valorEscritura;
    }

    public Obra valorEscritura(BigDecimal valorEscritura) {
        this.valorEscritura = valorEscritura;
        return this;
    }

    public void setValorEscritura(BigDecimal valorEscritura) {
        this.valorEscritura = valorEscritura;
    }

    public Integer getPorcentagemCorretagem() {
        return porcentagemCorretagem;
    }

    public Obra porcentagemCorretagem(Integer porcentagemCorretagem) {
        this.porcentagemCorretagem = porcentagemCorretagem;
        return this;
    }

    public void setPorcentagemCorretagem(Integer porcentagemCorretagem) {
        this.porcentagemCorretagem = porcentagemCorretagem;
    }

    public TipoCorretagem getTipoCorretagem() {
        return tipoCorretagem;
    }

    public Obra tipoCorretagem(TipoCorretagem tipoCorretagem) {
        this.tipoCorretagem = tipoCorretagem;
        return this;
    }

    public void setTipoCorretagem(TipoCorretagem tipoCorretagem) {
        this.tipoCorretagem = tipoCorretagem;
    }

    public StatusObra getStatus() {
        return status;
    }

    public Obra status(StatusObra status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusObra status) {
        this.status = status;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public Obra dataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public Obra dataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public ResumoGasto getResumoGasto() {
        return resumoGasto;
    }

    public Obra resumoGasto(ResumoGasto resumoGasto) {
        this.resumoGasto = resumoGasto;
        return this;
    }

    public void setResumoGasto(ResumoGasto resumoGasto) {
        this.resumoGasto = resumoGasto;
    }

    public Set<Gasto> getGastos() {
        return gastos;
    }

    public Obra gastos(Set<Gasto> gastos) {
        this.gastos = gastos;
        return this;
    }

    public Obra addGasto(Gasto gasto) {
        this.gastos.add(gasto);
        gasto.setObra(this);
        return this;
    }

    public Obra removeGasto(Gasto gasto) {
        this.gastos.remove(gasto);
        gasto.setObra(null);
        return this;
    }

    public void setGastos(Set<Gasto> gastos) {
        this.gastos = gastos;
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
        Obra obra = (Obra) o;
        if (obra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), obra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Obra{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", metragem=" + getMetragem() +
            ", valorTerreno=" + getValorTerreno() +
            ", valorEscritura=" + getValorEscritura() +
            ", porcentagemCorretagem=" + getPorcentagemCorretagem() +
            ", tipoCorretagem='" + getTipoCorretagem() + "'" +
            ", status='" + getStatus() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            "}";
    }
}
