package br.slobra.aplicacao.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

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

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor")
    private Long valor;

    @Column(name = "data_vencimento")
    private Instant dataVencimento;

    @Column(name = "pagamento")
    private String pagamento;

    @Column(name = "nota")
    private String nota;

    @Column(name = "tipo")
    private String tipo;

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

    public Instant getDataVencimento() {
        return dataVencimento;
    }

    public Conta dataVencimento(Instant dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public void setDataVencimento(Instant dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getPagamento() {
        return pagamento;
    }

    public Conta pagamento(String pagamento) {
        this.pagamento = pagamento;
        return this;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getNota() {
        return nota;
    }

    public Conta nota(String nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getTipo() {
        return tipo;
    }

    public Conta tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
            "}";
    }
}
