package com.minhaprimeiraapijava.controllers;


import com.minhaprimeiraapijava.models.Compra;
import com.minhaprimeiraapijava.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<List<Compra>> listarCompras() {
        List<Compra> compras = compraService.listarCompras();
        return ResponseEntity.ok(compras);
    }

    @PostMapping
    public ResponseEntity<Compra> criarCompra(@RequestBody Compra compra) {
        Compra novaCompra = compraService.salvarCompra(compra);
        return ResponseEntity.ok(novaCompra);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCompra(@PathVariable Long id) {
        try {
            compraService.deletarCompra(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //sem conteudo
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Compra não encontrada.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar compra: " + e.getMessage());
        }
    }
}