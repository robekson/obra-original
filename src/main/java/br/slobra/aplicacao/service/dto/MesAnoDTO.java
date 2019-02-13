package br.slobra.aplicacao.service.dto;
import java.io.Serializable;
import java.util.Date;

public class MesAnoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7285925707009280807L;
	private String data;
	private Date dataNaoFormatada;

	public Date getDataNaoFormatada() {
		return dataNaoFormatada;
	}

	public void setDataNaoFormatada(Date dataNaoFormatada) {
		this.dataNaoFormatada = dataNaoFormatada;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


}
