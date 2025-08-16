// src/test/java/br/com/alexviana/contabancariatestes/negocio/ContaBancariaTest.java
package br.com.alexviana.contabancariatestes.negocio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*; // Importa todas as asserções estáticas

public class ContaBancariaTest {

    private ContaBancaria contaOrigem;
    private ContaBancaria contaDestino;

    // O @BeforeEach garante que uma nova conta (e, portanto, um estado limpo)
    // seja criada antes de cada método de teste, isolando os testes uns dos outros.
    @BeforeEach
    void setUp() {
        System.out.println("Configurando contas para um novo teste...");
        contaOrigem = new ContaBancaria("12345-6", new BigDecimal("100.00"));
        contaDestino = new ContaBancaria("78901-2", new BigDecimal("50.00"));
    }

    @Test
    void testDepositarComSucesso() {
        System.out.println("Executando: testDepositarComSucesso");
        contaOrigem.depositar(new BigDecimal("50.00"));
        assertEquals(new BigDecimal("150.00"), contaOrigem.getSaldo(), "O saldo deve ser 150.00 após o depósito.");
        System.out.println("Saldo da conta origem após depósito: " + contaOrigem.getSaldo());
    }

    @Test
    void testDepositarValorInvalido() {
        System.out.println("Executando: testDepositarValorInvalido");
        // Teste com valor zero
        IllegalArgumentException thrownZero = assertThrows(IllegalArgumentException.class, () ->
            contaOrigem.depositar(BigDecimal.ZERO)
        );
        assertEquals("Valor de depósito inválido: deve ser positivo.", thrownZero.getMessage());

        // Teste com valor negativo
        IllegalArgumentException thrownNegative = assertThrows(IllegalArgumentException.class, () ->
            contaOrigem.depositar(new BigDecimal("-10.00"))
        );
        assertEquals("Valor de depósito inválido: deve ser positivo.", thrownNegative.getMessage());
        System.out.println("Teste de depósito com valor inválido finalizado. Exceção esperada lançada.");
    }

    @Test
    void testSacarComSucesso() {
        System.out.println("Executando: testSacarComSucesso");
        contaOrigem.sacar(new BigDecimal("30.00"));
        assertEquals(new BigDecimal("70.00"), contaOrigem.getSaldo(), "O saldo deve ser 70.00 após o saque.");
        System.out.println("Saldo da conta origem após saque: " + contaOrigem.getSaldo());
    }

    @Test
    void testSacarComSaldoInsuficiente() {
        System.out.println("Executando: testSacarComSaldoInsuficiente");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
            contaOrigem.sacar(new BigDecimal("150.00"))
        );
        assertEquals("Saldo insuficiente para o saque.", thrown.getMessage());
        assertEquals(new BigDecimal("100.00"), contaOrigem.getSaldo(), "O saldo não deve ser alterado em caso de saque insuficiente.");
        System.out.println("Teste de saque com saldo insuficiente finalizado. Exceção esperada lançada.");
    }

    @Test
    void testSacarValorInvalido() {
        System.out.println("Executando: testSacarValorInvalido");
        // Teste com valor zero
        IllegalArgumentException thrownZero = assertThrows(IllegalArgumentException.class, () ->
            contaOrigem.sacar(BigDecimal.ZERO)
        );
        assertEquals("Valor de saque inválido: deve ser positivo.", thrownZero.getMessage());

        // Teste com valor negativo
        IllegalArgumentException thrownNegative = assertThrows(IllegalArgumentException.class, () ->
            contaOrigem.sacar(new BigDecimal("-10.00"))
        );
        assertEquals("Valor de saque inválido: deve ser positivo.", thrownNegative.getMessage());
        System.out.println("Teste de saque com valor inválido finalizado. Exceção esperada lançada.");
    }

    @Test
    void testTransferirComSucesso() {
        System.out.println("Executando: testTransferirComSucesso");
        contaOrigem.transferir(contaDestino, new BigDecimal("40.00"));

        assertEquals(new BigDecimal("60.00"), contaOrigem.getSaldo(), "Saldo da conta origem deve ser 60.00.");
        assertEquals(new BigDecimal("90.00"), contaDestino.getSaldo(), "Saldo da conta destino deve ser 90.00.");
        System.out.println("Transferência de 40.00 realizada.");
        System.out.println("Saldo origem: " + contaOrigem.getSaldo() + ", Saldo destino: " + contaDestino.getSaldo());
    }

    @Test
    void testTransferirComSaldoInsuficienteNaOrigem() {
        System.out.println("Executando: testTransferirComSaldoInsuficienteNaOrigem");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
            contaOrigem.transferir(contaDestino, new BigDecimal("120.00"))
        );
        assertEquals("Saldo insuficiente para o saque.", thrown.getMessage());
        // Verifica se os saldos não foram alterados
        assertEquals(new BigDecimal("100.00"), contaOrigem.getSaldo(), "Saldo da origem não deve ser alterado.");
        assertEquals(new BigDecimal("50.00"), contaDestino.getSaldo(), "Saldo do destino não deve ser alterado.");
        System.out.println("Teste de transferência com saldo insuficiente finalizado. Exceção esperada lançada.");
    }

    @Test
    void testTransferirValorInvalido() {
        System.out.println("Executando: testTransferirValorInvalido");
        // Teste com valor zero
        IllegalArgumentException thrownZero = assertThrows(IllegalArgumentException.class, () ->
            contaOrigem.transferir(contaDestino, BigDecimal.ZERO)
        );
        assertEquals("Valor de transferência inválido: deve ser positivo.", thrownZero.getMessage());

        // Teste com valor negativo
        IllegalArgumentException thrownNegative = assertThrows(IllegalArgumentException.class, () ->
            contaOrigem.transferir(contaDestino, new BigDecimal("-10.00"))
        );
        assertEquals("Valor de transferência inválido: deve ser positivo.", thrownNegative.getMessage());
        System.out.println("Teste de transferência com valor inválido finalizado. Exceção esperada lançada.");
    }

    @Test
    void testTransferirParaContaDestinoNula() {
        System.out.println("Executando: testTransferirParaContaDestinoNula");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
            contaOrigem.transferir(null, new BigDecimal("10.00"))
        );
        assertEquals("Conta de destino não pode ser nula.", thrown.getMessage());
        assertEquals(new BigDecimal("100.00"), contaOrigem.getSaldo(), "Saldo da origem não deve ser alterado.");
        System.out.println("Teste de transferência para conta destino nula finalizado. Exceção esperada lançada.");
    }
}