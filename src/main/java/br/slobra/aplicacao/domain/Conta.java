package br.slobra.aplicacao.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.slobra.aplicacao.domain.enumeration.Pago;

import br.slobra.aplicacao.domain.enumeration.NotaFiscal;

import br.slobra.aplicacao.domain.enumeration.TipoConta;

/**
 * A Conta.
 */
@Entity
@Table(name = "conta")
public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "valor")
    private Long valor;

    @NotNull
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "pagamento")
    private Pago pagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "nota")
    private NotaFiscal nota;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoConta tipo;

    @Column(name = "parcelado")
    private Integer parcelado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Conta nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getValor() {
        return valor;
    }

    public Conta valor(Long valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public Conta dataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Pago getPagamento() {
        return pagamento;
    }

    public Conta pagamento(Pago pagamento) {
        this.pagamento = pagamento;
        return this;
    }

    public void setPagamento(Pago pagamento) {
        this.pagamento = pagamento;
    }

    public NotaFiscal getNota() {
        return nota;
    }

    public Conta nota(NotaFiscal nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(NotaFiscal nota) {
        this.nota = nota;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public Conta tipo(TipoConta tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public Integer getParcelado() {
        return parcelado;
    }

    public Conta parcelado(Integer parcelado) {
        this.parcelado = parcelado;
        return this;
    }

    public void setParcelado(Integer parcelado) {
        this.parcelado = parcelado;
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
        Conta conta = (Conta) o;
        if (conta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Conta{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", valor=" + getValor() +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", pagamento='" + getPagamento() + "'" +
            ", nota='" + getNota() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", parcelado=" + getParcelado() +
            "}";
    }
}
