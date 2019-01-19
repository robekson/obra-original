package br.slobra.aplicacao.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.slobra.aplicacao.domain.enumeration.Pago;
import br.slobra.aplicacao.domain.enumeration.NotaFiscal;
import br.slobra.aplicacao.domain.enumeration.TipoConta;

/**
 * A DTO for the Gasto entity.
 */
public class GastoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private BigDecimal valor;

    @NotNull
    private LocalDate dataVencimento;

    private Pago pagamento;

    private NotaFiscal nota;

    private TipoConta tipo;

    private Integer parcelado;

    private LocalDate mesAno;

    private Long obraId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Pago getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pago pagamento) {
        this.pagamento = pagamento;
    }

    public NotaFiscal getNota() {
        return nota;
    }

    public void setNota(NotaFiscal nota) {
        this.nota = nota;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public Integer getParcelado() {
        return parcelado;
    }

    public void setParcelado(Integer parcelado) {
        this.parcelado = parcelado;
    }

    public LocalDate getMesAno() {
        return mesAno;
    }

    public void setMesAno(LocalDate mesAno) {
        this.mesAno = mesAno;
    }

    public Long getObraId() {
        return obraId;
    }

    public void setObraId(Long obraId) {
        this.obraId = obraId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GastoDTO gastoDTO = (GastoDTO) o;
        if (gastoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gastoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GastoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", valor=" + getValor() +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", pagamento='" + getPagamento() + "'" +
            ", nota='" + getNota() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", parcelado=" + getParcelado() +
            ", mesAno='" + getMesAno() + "'" +
            ", obra=" + getObraId() +
            "}";
    }
}
