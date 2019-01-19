package br.slobra.aplicacao.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A ResumoGasto.
 */
@Entity
@Table(name = "resumo_gasto")
public class ResumoGasto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome_obra")
    private String nomeObra;

    @Column(name = "valor_deposito", precision = 10, scale = 2)
    private BigDecimal valorDeposito;

    @Column(name = "valor_despesa", precision = 10, scale = 2)
    private BigDecimal valorDespesa;

    @Column(name = "valor_saldo", precision = 10, scale = 2)
    private BigDecimal valorSaldo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeObra() {
        return nomeObra;
    }

    public ResumoGasto nomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
        return this;
    }

    public void setNomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
    }

    public BigDecimal getValorDeposito() {
        return valorDeposito;
    }

    public ResumoGasto valorDeposito(BigDecimal valorDeposito) {
        this.valorDeposito = valorDeposito;
        return this;
    }

    public void setValorDeposito(BigDecimal valorDeposito) {
        this.valorDeposito = valorDeposito;
    }

    public BigDecimal getValorDespesa() {
        return valorDespesa;
    }

    public ResumoGasto valorDespesa(BigDecimal valorDespesa) {
        this.valorDespesa = valorDespesa;
        return this;
    }

    public void setValorDespesa(BigDecimal valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public BigDecimal getValorSaldo() {
        return valorSaldo;
    }

    public ResumoGasto valorSaldo(BigDecimal valorSaldo) {
        this.valorSaldo = valorSaldo;
        return this;
    }

    public void setValorSaldo(BigDecimal valorSaldo) {
        this.valorSaldo = valorSaldo;
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
        ResumoGasto resumoGasto = (ResumoGasto) o;
        if (resumoGasto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resumoGasto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResumoGasto{" +
            "id=" + getId() +
            ", nomeObra='" + getNomeObra() + "'" +
            ", valorDeposito=" + getValorDeposito() +
            ", valorDespesa=" + getValorDespesa() +
            ", valorSaldo=" + getValorSaldo() +
            "}";
    }
}
