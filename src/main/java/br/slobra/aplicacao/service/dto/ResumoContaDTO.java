package br.slobra.aplicacao.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.time.format.DateTimeFormatter;

/**
 * A DTO for the ResumoConta entity.
 */
public class ResumoContaDTO implements Serializable {

    private Long id;

    private BigDecimal honorarioAdministracao;

    private BigDecimal valorDeposito;

    private Long quantidadeComNota;

    private Long quantidadeSemNota;

    private LocalDate mesAno;

    private BigDecimal despesaComNota;

    private BigDecimal despesaSemNota;

    private BigDecimal despesaGeralSubTotal;
    
    private String mesAnoFormatado;

    public String getMesAnoFormatado() {   	
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MM/yyyy");
    	mesAnoFormatado = mesAno.format(formatador);	
		return mesAnoFormatado;
	}

	public void setMesAnoFormatado(String mesAnoFormatado) {
		this.mesAnoFormatado = mesAnoFormatado;
	}

	public LocalDate getMesAno() {
        return mesAno;
    }

    public void setMesAno(LocalDate mesAno) {
        this.mesAno = mesAno;
    }

    public BigDecimal getDespesaComNota() {
        return despesaComNota;
    }

    public void setDespesaComNota(BigDecimal despesaComNota) {
        this.despesaComNota = despesaComNota;
    }

    public BigDecimal getDespesaSemNota() {
        return despesaSemNota;
    }

    public void setDespesaSemNota(BigDecimal despesaSemNota) {
        this.despesaSemNota = despesaSemNota;
    }

    public BigDecimal getDespesaGeralSubTotal() {
        return despesaGeralSubTotal;
    }

    public void setDespesaGeralSubTotal(BigDecimal despesaGeralSubTotal) {
        this.despesaGeralSubTotal = despesaGeralSubTotal;
    }

    public BigDecimal getHonorarioAdministracao() {
        return honorarioAdministracao;
    }

    public void setHonorarioAdministracao(BigDecimal honorarioAdministracao) {
        this.honorarioAdministracao = honorarioAdministracao;
    }

    public BigDecimal getValorDeposito() {
        return valorDeposito;
    }

    public void setValorDeposito(BigDecimal valorDeposito) {
        this.valorDeposito = valorDeposito;
    }

    public Long getQuantidadeComNota() {
        return quantidadeComNota;
    }

    public void setQuantidadeComNota(Long quantidadeComNota) {
        this.quantidadeComNota = quantidadeComNota;
    }

    public Long getQuantidadeSemNota() {
        return quantidadeSemNota;
    }

    public void setQuantidadeSemNota(Long quantidadeSemNota) {
        this.quantidadeSemNota = quantidadeSemNota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}