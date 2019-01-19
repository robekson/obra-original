package br.slobra.aplicacao.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the ResumoGasto entity.
 */
public class ResumoGastoDTO implements Serializable {

    private Long id;

    private String nomeObra;

    private BigDecimal valorDeposito;

    private BigDecimal valorDespesa;

    private BigDecimal valorSaldo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeObra() {
        return nomeObra;
    }

    public void setNomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
    }

    public BigDecimal getValorDeposito() {
        return valorDeposito;
    }

    public void setValorDeposito(BigDecimal valorDeposito) {
        this.valorDeposito = valorDeposito;
    }

    public BigDecimal getValorDespesa() {
        return valorDespesa;
    }

    public void setValorDespesa(BigDecimal valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public BigDecimal getValorSaldo() {
        return valorSaldo;
    }

    public void setValorSaldo(BigDecimal valorSaldo) {
        this.valorSaldo = valorSaldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResumoGastoDTO resumoGastoDTO = (ResumoGastoDTO) o;
        if (resumoGastoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resumoGastoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResumoGastoDTO{" +
            "id=" + getId() +
            ", nomeObra='" + getNomeObra() + "'" +
            ", valorDeposito=" + getValorDeposito() +
            ", valorDespesa=" + getValorDespesa() +
            ", valorSaldo=" + getValorSaldo() +
            "}";
    }
}
