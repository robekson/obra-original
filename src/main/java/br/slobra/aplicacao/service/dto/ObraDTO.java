package br.slobra.aplicacao.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.slobra.aplicacao.domain.enumeration.TipoCorretagem;
import br.slobra.aplicacao.domain.enumeration.StatusObra;

/**
 * A DTO for the Obra entity.
 */
public class ObraDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String endereco;

    private Long metragem;

    private BigDecimal valorTerreno;

    private BigDecimal valorEscritura;

    private BigDecimal valorEscrituraCompra;

    private BigDecimal valorCorretagemVenda;

    private BigDecimal valorFiscal;

    private Integer porcentagemCorretagem;

    private TipoCorretagem tipoCorretagem;

    @NotNull
    private StatusObra status;

    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataFim;

    private Long resumoGastoId;

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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getMetragem() {
        return metragem;
    }

    public void setMetragem(Long metragem) {
        this.metragem = metragem;
    }

    public BigDecimal getValorTerreno() {
        return valorTerreno;
    }

    public void setValorTerreno(BigDecimal valorTerreno) {
        this.valorTerreno = valorTerreno;
    }

    public BigDecimal getValorEscritura() {
        return valorEscritura;
    }

    public void setValorEscritura(BigDecimal valorEscritura) {
        this.valorEscritura = valorEscritura;
    }

    public BigDecimal getValorEscrituraCompra() {
        return valorEscrituraCompra;
    }

    public void setValorEscrituraCompra(BigDecimal valorEscrituraCompra) {
        this.valorEscrituraCompra = valorEscrituraCompra;
    }

    public BigDecimal getValorCorretagemVenda() {
        return valorCorretagemVenda;
    }

    public void setValorCorretagemVenda(BigDecimal valorCorretagemVenda) {
        this.valorCorretagemVenda = valorCorretagemVenda;
    }

    public BigDecimal getValorFiscal() {
        return valorFiscal;
    }

    public void setValorFiscal(BigDecimal valorFiscal) {
        this.valorFiscal = valorFiscal;
    }

    public Integer getPorcentagemCorretagem() {
        return porcentagemCorretagem;
    }

    public void setPorcentagemCorretagem(Integer porcentagemCorretagem) {
        this.porcentagemCorretagem = porcentagemCorretagem;
    }

    public TipoCorretagem getTipoCorretagem() {
        return tipoCorretagem;
    }

    public void setTipoCorretagem(TipoCorretagem tipoCorretagem) {
        this.tipoCorretagem = tipoCorretagem;
    }

    public StatusObra getStatus() {
        return status;
    }

    public void setStatus(StatusObra status) {
        this.status = status;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Long getResumoGastoId() {
        return resumoGastoId;
    }

    public void setResumoGastoId(Long resumoGastoId) {
        this.resumoGastoId = resumoGastoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObraDTO obraDTO = (ObraDTO) o;
        if (obraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), obraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObraDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", metragem=" + getMetragem() +
            ", valorTerreno=" + getValorTerreno() +
            ", valorEscritura=" + getValorEscritura() +
            ", valorEscrituraCompra=" + getValorEscrituraCompra() +
            ", valorCorretagemVenda=" + getValorCorretagemVenda() +
            ", valorFiscal=" + getValorFiscal() +
            ", porcentagemCorretagem=" + getPorcentagemCorretagem() +
            ", tipoCorretagem='" + getTipoCorretagem() + "'" +
            ", status='" + getStatus() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            ", resumoGasto=" + getResumoGastoId() +
            "}";
    }
}
