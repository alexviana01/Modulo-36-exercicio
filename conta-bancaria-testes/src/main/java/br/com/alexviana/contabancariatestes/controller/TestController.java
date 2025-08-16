// Em um novo arquivo: src/main/java/br/com.alexviana.contabancariatestes.controller/TestController.java
package br.com.alexviana.contabancariatestes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "Aplicação rodando com sucesso!";
    }
}