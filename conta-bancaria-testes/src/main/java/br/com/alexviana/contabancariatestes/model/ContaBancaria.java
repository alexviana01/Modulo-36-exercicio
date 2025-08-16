// src/main/java/br/com/alexviana/contabancariatestes/model/ContaBancaria.java
package br.com.alexviana.contabancariatestes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column; // Importar Column
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "conta_bancaria") // Mapeia para a tabela conta_bancaria
public class ContaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-incremento no PostgreSQL
    private Long id;

    @Column(name = "numero_conta", unique = true, nullable = false, length = 20)
    private String numeroConta;

    @Column(name = "saldo", nullable = false, precision = 19, scale = 2) // precisão 19, 2 casas decimais
    private BigDecimal saldo;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    // Construtor vazio (necessário para JPA)
    public ContaBancaria() {
        this.dataCriacao = LocalDate.now(); // Define a data de criação por padrão
    }

    // Construtor para criar contas sem ID (o banco gera)
    public ContaBancaria(String numeroConta, BigDecimal saldoInicial) {
        this(); // Chama o construtor vazio para inicializar dataCriacao
        if (numeroConta == null || numeroConta.trim().isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser vazio.");
        }
        if (saldoInicial == null || saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Saldo inicial não pode ser negativo.");
        }
        this.numeroConta = numeroConta;
        this.saldo = saldoInicial;
    }

    // --- Métodos de Negócio (lógica da conta) ---
    public void depositar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de depósito inválido: deve ser positivo.");
        }
        this.saldo = this.saldo.add(valor);
    }

    public void sacar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de saque inválido: deve ser positivo.");
        }
        if (valor.compareTo(this.saldo) > 0) {
            throw new IllegalArgumentException("Saldo insuficiente para o saque.");
        }
        this.saldo = this.saldo.subtract(valor);
    }

    public void transferir(ContaBancaria destino, BigDecimal valor) {
        if (destino == null) {
            throw new IllegalArgumentException("Conta de destino não pode ser nula.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de transferência inválido: deve ser positivo.");
        }
        this.sacar(valor); // Saque da origem (já valida saldo)
        destino.depositar(valor); // Depósito no destino
    }

    // --- Getters e Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    // --- Métodos equals e hashCode (boa prática para entidades, usando o ID) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaBancaria that = (ContaBancaria) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ContaBancaria{" +
               "id=" + id +
               ", numeroConta='" + numeroConta + '\'' +
               ", saldo=" + saldo +
               ", dataCriacao=" + dataCriacao +
               '}';
    }
}