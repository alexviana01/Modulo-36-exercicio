// src/main/java/br/com/alexviana/contabancariatestes/repository/ContaBancariaRepository.java
package br.com.alexviana.contabancariatestes.repository;

import br.com.alexviana.contabancariatestes.model.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Marca como um componente de repositório do Spring
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {
    // JpaRepository já fornece métodos como save(), findById(), findAll(), deleteById(), etc.

    // Exemplo de método customizado (Spring Data JPA pode gerar a query automaticamente)
    Optional<ContaBancaria> findByNumeroConta(String numeroConta);
}