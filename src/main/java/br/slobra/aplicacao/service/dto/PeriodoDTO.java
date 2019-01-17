package br.slobra.aplicacao.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Periodo entity.
 */
public class PeriodoDTO implements Serializable {

    private Long id;

    private String idConta;

    private LocalDate data;

    private Long obrasId;

    private Long periodoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdConta() {
        return idConta;
    }

    public void setIdConta(String idConta) {
        this.idConta = idConta;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getObrasId() {
        return obrasId;
    }

    public void setObrasId(Long obrasId) {
        this.obrasId = obrasId;
    }

    public Long getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Long contaId) {
        this.periodoId = contaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PeriodoDTO periodoDTO = (PeriodoDTO) o;
        if (periodoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), periodoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeriodoDTO{" +
            "id=" + getId() +
            ", idConta='" + getIdConta() + "'" +
            ", data='" + getData() + "'" +
            ", obras=" + getObrasId() +
            ", periodo=" + getPeriodoId() +
            "}";
    }
}
