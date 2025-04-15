package br.com.seuapp.controller;

import br.com.seuapp.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pauta")
public class PautaController {

    @Autowired
    private PautaService service;

    @PostMapping("/atualizar")
    public ResponseEntity<String> atualizar() {
        try {
            service.atualizarProdutos();
            return ResponseEntity.ok("Produtos atualizados com sucesso.");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Erro ao atualizar produtos: " + ex.getMessage());
        }
    }
}
