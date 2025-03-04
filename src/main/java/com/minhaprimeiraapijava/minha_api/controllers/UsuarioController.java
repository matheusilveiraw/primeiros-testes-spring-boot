package com.minhaprimeiraapijava.minha_api.controllers;

import com.minhaprimeiraapijava.minha_api.models.Usuario;
import com.minhaprimeiraapijava.minha_api.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> criarUsuario(@RequestBody Usuario usuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            usuarioService.criarUsuario(usuario);
            // Prepara a resposta em formato JSON
            response.put("status", HttpStatus.CREATED.value()); // 201
            response.put("message", "Usuário criado com sucesso");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Prepara a resposta de erro em formato JSON
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
            response.put("message", "Erro ao criar o usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> buscarUsuario(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.atualizarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }
}
