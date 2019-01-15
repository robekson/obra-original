package br.slobra.aplicacao.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Obras entity.
 */
public class ObrasDTO implements Serializable {

    private Long id;

    private String nome;

    private String local;

    private Long metragem;

    private String status;

    private Instant dataInicio;

    private Instant dataFim;

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

    public Instant getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getDataFim() {
        return dataFim;
    }

    public void setDataFim(Instant dataFim) {
        this.dataFim = dataFim;
    }

    public Long getObraId() {
        return obraId;
    }

    public void setObraId(Long lancamentoGastosId) {
        this.obraId = lancamentoGastosId;
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
            ", obra=" + getObraId() +
            "}";
    }
}
