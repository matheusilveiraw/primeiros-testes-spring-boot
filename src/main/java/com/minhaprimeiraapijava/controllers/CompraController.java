package com.minhaprimeiraapijava.controllers;


import com.minhaprimeiraapijava.models.Compra;
import com.minhaprimeiraapijava.repositories.CompraRepository;
import com.minhaprimeiraapijava.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private CompraRepository compraRepository;

    @GetMapping
    public ResponseEntity<?> listarCompras() {
        List<Compra> compras = compraService.listarCompras();

        if (compras.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

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

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCompra(@PathVariable Long id, @RequestBody Compra compraAtualizada) {
        try {
            Compra compra = compraService.atualizarCompra(id, compraAtualizada);
            return ResponseEntity.ok(compra);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Compra não encontrada.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar compra: " + e.getMessage());
        }
    }

    /* POST
{
    "usuario": {
        "id": 1
    },
    "dataHora": "2025-03-11T15:00:00",
    "valorTotal": 200.50

    PUT

    {
    "usuarioId": 27,
    "valor": 150.0
}
}
     */


}