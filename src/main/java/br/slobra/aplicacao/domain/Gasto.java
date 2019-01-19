package br.slobra.aplicacao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import br.slobra.aplicacao.domain.enumeration.Pago;

import br.slobra.aplicacao.domain.enumeration.NotaFiscal;

import br.slobra.aplicacao.domain.enumeration.TipoConta;

/**
 * A Gasto.
 */
@Entity
@Table(name = "gasto")
public class Gasto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

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

    @Column(name = "mes_ano")
    private LocalDate mesAno;

    @ManyToOne
    @JsonIgnoreProperties("gastos")
    private Obra obra;

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

    public Gasto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Gasto valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public Gasto dataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Pago getPagamento() {
        return pagamento;
    }

    public Gasto pagamento(Pago pagamento) {
        this.pagamento = pagamento;
        return this;
    }

    public void setPagamento(Pago pagamento) {
        this.pagamento = pagamento;
    }

    public NotaFiscal getNota() {
        return nota;
    }

    public Gasto nota(NotaFiscal nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(NotaFiscal nota) {
        this.nota = nota;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public Gasto tipo(TipoConta tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public Integer getParcelado() {
        return parcelado;
    }

    public Gasto parcelado(Integer parcelado) {
        this.parcelado = parcelado;
        return this;
    }

    public void setParcelado(Integer parcelado) {
        this.parcelado = parcelado;
    }

    public LocalDate getMesAno() {
        return mesAno;
    }

    public Gasto mesAno(LocalDate mesAno) {
        this.mesAno = mesAno;
        return this;
    }

    public void setMesAno(LocalDate mesAno) {
        this.mesAno = mesAno;
    }

    public Obra getObra() {
        return obra;
    }

    public Gasto obra(Obra obra) {
        this.obra = obra;
        return this;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
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
        Gasto gasto = (Gasto) o;
        if (gasto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gasto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gasto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", valor=" + getValor() +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", pagamento='" + getPagamento() + "'" +
            ", nota='" + getNota() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", parcelado=" + getParcelado() +
            ", mesAno='" + getMesAno() + "'" +
            "}";
    }
}
