package br.slobra.aplicacao.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Obras entity.
 */
public class ObrasDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String local;

    private Long metragem;

    private String status;

    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataFim;

    private Long lancamentoGastosId;

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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Long getMetragem() {
        return metragem;
    }

    public void setMetragem(Long metragem) {
        this.metragem = metragem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public Long getLancamentoGastosId() {
        return lancamentoGastosId;
    }

    public void setLancamentoGastosId(Long lancamentoGastosId) {
        this.lancamentoGastosId = lancamentoGastosId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObrasDTO obrasDTO = (ObrasDTO) o;
        if (obrasDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), obrasDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObrasDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", local='" + getLocal() + "'" +
            ", metragem=" + getMetragem() +
            ", status='" + getStatus() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            ", lancamentoGastos=" + getLancamentoGastosId() +
            "}";
    }
}
