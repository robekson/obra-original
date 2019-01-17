package br.slobra.aplicacao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LancamentoGastos.
 */
@Entity
@Table(name = "lancamento_gastos")
public class LancamentoGastos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome_obra")
    private String nomeObra;

    @Column(name = "valor_deposito")
    private Long valorDeposito;

    @Column(name = "valor_despesa")
    private Long valorDespesa;

    @Column(name = "valor_saldo")
    private Long valorSaldo;

    @OneToMany(mappedBy = "lancamentoGastos")
    private Set<Obras> lancamentos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeObra() {
        return nomeObra;
    }

    public LancamentoGastos nomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
        return this;
    }

    public void setNomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
    }

    public Long getValorDeposito() {
        return valorDeposito;
    }

    public LancamentoGastos valorDeposito(Long valorDeposito) {
        this.valorDeposito = valorDeposito;
        return this;
    }

    public void setValorDeposito(Long valorDeposito) {
        this.valorDeposito = valorDeposito;
    }

    public Long getValorDespesa() {
        return valorDespesa;
    }

    public LancamentoGastos valorDespesa(Long valorDespesa) {
        this.valorDespesa = valorDespesa;
        return this;
    }

    public void setValorDespesa(Long valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public Long getValorSaldo() {
        return valorSaldo;
    }

    public LancamentoGastos valorSaldo(Long valorSaldo) {
        this.valorSaldo = valorSaldo;
        return this;
    }

    public void setValorSaldo(Long valorSaldo) {
        this.valorSaldo = valorSaldo;
    }

    public Set<Obras> getLancamentos() {
        return lancamentos;
    }

    public LancamentoGastos lancamentos(Set<Obras> obras) {
        this.lancamentos = obras;
        return this;
    }

    public LancamentoGastos addLancamento(Obras obras) {
        this.lancamentos.add(obras);
        obras.setLancamentoGastos(this);
        return this;
    }

    public LancamentoGastos removeLancamento(Obras obras) {
        this.lancamentos.remove(obras);
        obras.setLancamentoGastos(null);
        return this;
    }

    public void setLancamentos(Set<Obras> obras) {
        this.lancamentos = obras;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LancamentoGastos lancamentoGastos = (LancamentoGastos) o;
        if (lancamentoGastos.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lancamentoGastos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LancamentoGastos{" +
            "id=" + getId() +
            ", nomeObra='" + getNomeObra() + "'" +
            ", valorDeposito=" + getValorDeposito() +
            ", valorDespesa=" + getValorDespesa() +
            ", valorSaldo=" + getValorSaldo() +
            "}";
    }
}
