package br.slobra.aplicacao.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the LancamentoGastos entity.
 */
public class LancamentoGastosDTO implements Serializable {

    private Long id;

    private String nomeObra;

    private Long valorDeposito;

    private Long valorDespesa;

    private Long valorSaldo;

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

    public Long getValorDeposito() {
        return valorDeposito;
    }

    public void setValorDeposito(Long valorDeposito) {
        this.valorDeposito = valorDeposito;
    }

    public Long getValorDespesa() {
        return valorDespesa;
    }

    public void setValorDespesa(Long valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public Long getValorSaldo() {
        return valorSaldo;
    }

    public void setValorSaldo(Long valorSaldo) {
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

        LancamentoGastosDTO lancamentoGastosDTO = (LancamentoGastosDTO) o;
        if (lancamentoGastosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lancamentoGastosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LancamentoGastosDTO{" +
            "id=" + getId() +
            ", nomeObra='" + getNomeObra() + "'" +
            ", valorDeposito=" + getValorDeposito() +
            ", valorDespesa=" + getValorDespesa() +
            ", valorSaldo=" + getValorSaldo() +
            "}";
    }
}
