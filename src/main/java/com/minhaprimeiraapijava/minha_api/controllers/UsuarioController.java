package com.minhaprimeiraapijava.minha_api.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private List<String> usuarios = new ArrayList<>();

    @GetMapping
    public List<String> listarUsuarios() {
        return usuarios;
    }

    @PostMapping
    public String criarUsuario(@RequestBody String nome) {
        usuarios.add(nome);
        return "Usuário " + nome + " criado com sucesso!";
    }

    @PutMapping("/{index}")
    public String atualizarUsuario(@PathVariable int index, @RequestBody String nome) {
        if (index >= 0 && index < usuarios.size()) {
            usuarios.set(index, nome);
            return "Usuário atualizado para: " + nome;
        }
        return "Usuário não encontrado!";
    }

    @DeleteMapping("/{index}")
    public String deletarUsuario(@PathVariable int index) {
        if (index >= 0 && index < usuarios.size()) {
            String removido = usuarios.remove(index);
            return "Usuário " + removido + " removido com sucesso!";
        }
        return "Usuário não encontrado!";
    }
}
