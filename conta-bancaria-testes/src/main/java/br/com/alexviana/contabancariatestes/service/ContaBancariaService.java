// src/main/java/br/com/alexviana/contabancariatestes/service/ContaBancariaService.java
package br.com.alexviana.contabancariatestes.service;

import br.com.alexviana.contabancariatestes.model.ContaBancaria;
import br.com.alexviana.contabancariatestes.repository.ContaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContaBancariaService {

    @Autowired // Injeta o repositório automaticamente
    private ContaBancariaRepository contaBancariaRepository;

    @Transactional // Garante que a operação seja transacional (all or nothing)
    public ContaBancaria criarConta(String numeroConta, BigDecimal saldoInicial) {
        if (contaBancariaRepository.findByNumeroConta(numeroConta).isPresent()) {
            throw new IllegalArgumentException("Já existe uma conta com este número: " + numeroConta);
        }
        ContaBancaria novaConta = new ContaBancaria(numeroConta, saldoInicial);
        return contaBancariaRepository.save(novaConta);
    }

    @Transactional
    public ContaBancaria depositar(Long idConta, BigDecimal valor) {
        ContaBancaria conta = contaBancariaRepository.findById(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada com ID: " + idConta));
        conta.depositar(valor); // Lógica de negócio na entidade
        return contaBancariaRepository.save(conta); // Persiste a mudança
    }

    @Transactional
    public ContaBancaria sacar(Long idConta, BigDecimal valor) {
        ContaBancaria conta = contaBancariaRepository.findById(idConta)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada com ID: " + idConta));
        conta.sacar(valor); // Lógica de negócio na entidade
        return contaBancariaRepository.save(conta); // Persiste a mudança
    }

    @Transactional
    public void transferir(Long idOrigem, Long idDestino, BigDecimal valor) {
        ContaBancaria origem = contaBancariaRepository.findById(idOrigem)
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada com ID: " + idOrigem));
        ContaBancaria destino = contaBancariaRepository.findById(idDestino)
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada com ID: " + idDestino));

        origem.transferir(destino, valor); // Lógica de negócio na entidade
        contaBancariaRepository.save(origem);
        contaBancariaRepository.save(destino);
    }

    public Optional<ContaBancaria> buscarPorId(Long id) {
        return contaBancariaRepository.findById(id);
    }

    public Optional<ContaBancaria> buscarPorNumeroConta(String numeroConta) {
        return contaBancariaRepository.findByNumeroConta(numeroConta);
    }

    public List<ContaBancaria> listarTodasContas() {
        return contaBancariaRepository.findAll();
    }

    @Transactional
    public void deletarConta(Long id) {
        contaBancariaRepository.deleteById(id);
    }
}