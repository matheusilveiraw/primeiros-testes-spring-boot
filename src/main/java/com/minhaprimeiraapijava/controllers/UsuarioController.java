package com.minhaprimeiraapijava.controllers;

import com.minhaprimeiraapijava.models.Usuario;
import com.minhaprimeiraapijava.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import java.util.HashMap;
import java.util.Map;

@RestController
//no Spring Boot significa que a classe marcada com essa anotação será um controlador REST, ou seja, ela lida com requisições HTTP e retorna respostas no formato JSON
@RequestMapping("/api/usuarios")
//aqui to dizendo que vai chamar sempre esse caminho e todas minhas requisições vão passar por isso
public class UsuarioController {
    //no controller deve ficar as requisições http e os tratamentos dela

    @Autowired //evita a necessidade de instanciar manualmente classes dependentes.
    private UsuarioService usuarioService;


    //instanciar a classe:
    /*public class UsuarioController {
        private UsuarioService usuarioService = new UsuarioService();
    }*/

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    //O construtor existe para que o Spring consiga passar a instância correta de UsuarioService sem que você precise criar manualmente.
    //Se o Spring não encontrar um construtor público que receba UsuarioService, ele não conseguirá injetar a dependência e lançará um erro.

    @PostMapping //define que é uma rota post
    public ResponseEntity<Map<String, Object>> criarUsuario(@RequestBody Usuario usuario) { //ResponseEntity<Map<String, Object>>: O método retorna um objeto ResponseEntity que encapsula um Map contendo pares de chave/valor. Esse Map é convertido para JSON automaticamente pelo Spring Boot.
        Map<String, Object> response = new HashMap<>();
        try {
            usuarioService.criarUsuario(usuario); //chamando o service
            // Prepara a resposta em formato JSON
            response.put("status", HttpStatus.CREATED.value()); // 201
            response.put("message", "Usuário criado com sucesso"); //message é a chave e o resto é o valor do map
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Prepara a resposta de erro em formato JSON
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
            response.put("message", "Erro ao criar o usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    response);
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
