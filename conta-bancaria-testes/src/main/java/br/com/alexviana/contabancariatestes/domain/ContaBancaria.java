// src/main/java/br/com/alexviana/contabancariatestes/negocio/ContaBancaria.java
package br.com.alexviana.contabancariatestes.negocio;

import java.math.BigDecimal; // Usaremos BigDecimal para precisão com dinheiro

public class ContaBancaria {
    private String numeroConta; // Adicionando um número de conta para identificação
    private BigDecimal saldo;

    public ContaBancaria(String numeroConta, BigDecimal saldoInicial) {
        if (numeroConta == null || numeroConta.trim().isEmpty()) {
            throw new IllegalArgumentException("Número da conta não pode ser vazio.");
        }
        if (saldoInicial == null || saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Saldo inicial não pode ser negativo.");
        }
        this.numeroConta = numeroConta;
        this.saldo = saldoInicial;
    }

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

    public BigDecimal getSaldo() {
        return this.saldo;
    }

    public String getNumeroConta() {
        return this.numeroConta;
    }

    public void transferir(ContaBancaria destino, BigDecimal valor) {
        if (destino == null) {
            throw new IllegalArgumentException("Conta de destino não pode ser nula.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de transferência inválido: deve ser positivo.");
        }
        // Primeiro saca da conta de origem, o que já valida o saldo
        this.sacar(valor);
        // Se o saque for bem-sucedido, deposita na conta de destino
        destino.depositar(valor);
    }

    @Override
    public String toString() {
        return "ContaBancaria{" +
               "numeroConta='" + numeroConta + '\'' +
               ", saldo=" + saldo +
               '}';
    }
}