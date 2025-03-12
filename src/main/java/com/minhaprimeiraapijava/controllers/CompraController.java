package com.minhaprimeiraapijava.controllers;

import com.minhaprimeiraapijava.models.Compra;
import com.minhaprimeiraapijava.repositories.CompraRepository;
import com.minhaprimeiraapijava.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<?> listarCompras() {
        try {
            List<Compra> compras = compraService.listarCompras();

            if (compras.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            return ResponseEntity.ok(compras);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Erro ao listar compras: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> criarCompra(@RequestBody Compra compra) {
        Map<String, Object> response = new HashMap<>();
        try {
            Compra novaCompra = compraService.salvarCompra(compra);

            response.put("status", HttpStatus.CREATED.value());
            response.put("message", "Compra criada com sucesso");
            response.put("compra", novaCompra);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Erro ao criar a compra: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
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