package br.slobra.aplicacao.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * A DTO for the ResumoConta entity.
 */
public class ResumoContaDTO implements Serializable {

    private Long id;

    private BigDecimal honorarioAdministracao;

    private BigDecimal valorDeposito;
    
    private BigDecimal valorMetroQuadrado;
    
    private BigDecimal valorCaixa;

    private Long quantidadeComNota;

    private Long quantidadeSemNota;

    private LocalDate mesAno;

    private BigDecimal despesaComNota;

    private BigDecimal despesaSemNota;

    private BigDecimal despesaGeralSubTotal;

    private String mesAnoFormatado;

    private String mesAnoFormatadoExtenso;

    private BigDecimal totalDespesas;

    private ObraDTO obraDTO;

    public ObraDTO getObraDTO() {
        return obraDTO;
    }

    public void setObraDTO(ObraDTO obraDTO) {
        this.obraDTO = obraDTO;
    }

    public BigDecimal getTotalDespesas() {
		return totalDespesas;
	}

	public void setTotalDespesas(BigDecimal totalDespesas) {
		this.totalDespesas = totalDespesas;
	}

	public String getMesAnoFormatadoExtenso() {
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MMMM/yyyy").withLocale(new Locale("pt", "br"));
       if(mesAno!=null)mesAnoFormatadoExtenso = mesAno.format(formatador);
		return mesAnoFormatadoExtenso;
	}

	public void setMesAnoFormatadoExtenso(String mesAnoFormatadoExtenso) {
		this.mesAnoFormatadoExtenso = mesAnoFormatadoExtenso;
	}

	public String getMesAnoFormatado() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MMM/yyyy").withLocale(new Locale("pt", "br"));
        if(mesAno!=null)mesAnoFormatado = mesAno.format(formatador);
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
    
    public BigDecimal getValorCaixa() {
        return valorCaixa;
    }

    public void setValorCaixa(BigDecimal valorCaixa) {
        this.valorCaixa = valorCaixa;
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
    
    public BigDecimal getValorMetroQuadrado() {
		return valorMetroQuadrado;
	}

	public void setValorMetroQuadrado(BigDecimal valorMetroQuadrado) {
		this.valorMetroQuadrado = valorMetroQuadrado;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
