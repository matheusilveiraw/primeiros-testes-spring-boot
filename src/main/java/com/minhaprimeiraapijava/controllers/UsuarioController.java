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


    //ResponseEntity é uma classe do Spring Framework que representa uma resposta HTTP completa, incluindo o corpo da resposta, os cabeçalhos (headers) e o status HTTP. Ele é utilizado para personalizar a resposta que você deseja enviar para o cliente em uma aplicação Spring.

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
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();

            if (usuarios.isEmpty()) {
            }

            if (usuarios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //só da o retorno do 204
                //204 - deu certo mas vazio

                //minha ideia era criar uma resposta personalizada para quando estivesse vazio mas o spring boot limpa o retorno do corpo toda vez que for 204 pois não é uma prática recomendada, então não adianta fazer esse tipo de customização pelo menos no spring boot

                //segue como ia ficar a customização para fins didaticos
                //Map<String, Object> response = new HashMap<>();
                //response.put("status", HttpStatus.NO_CONTENT.value());
                //response.put("message", "Nenhum usuário encontrado.");
                //return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }

            return ResponseEntity.ok(usuarios); // Retorna a lista de usuários caso não esteja vazia
        } catch (Exception e) {
            // Caso ocorra um erro, retorna a resposta de erro
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Erro ao listar usuários: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuario(@PathVariable Long id) {
        try {
            Optional<Usuario> usuario = usuarioService.buscarPorId(id);

            if (usuario.isEmpty()) { //se não encontrar
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Usuário com ID " + id + " não encontrado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            return ResponseEntity.ok(usuario.get()); //deu boa, não trás nenhuma msg pois já trás os dados
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Erro ao buscar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Optional<Usuario> usuarioExistente = usuarioService.buscarPorId(id);

            if (usuarioExistente.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Usuário com ID " + id + " não encontrado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); //erro caso id não exista
            }

            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Usuário atualizado com sucesso.");
            response.put("usuario", usuarioAtualizado);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Erro ao atualizar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        try {
            Optional<Usuario> usuarioExistente = usuarioService.buscarPorId(id); //optinal é para representar um valor que pode ou não estar presente

            if (usuarioExistente.isEmpty()) { //não pode ser um null pq o optional nunca retorna um null
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "Usuário com ID " + id + " não encontrado.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            usuarioService.deletarUsuario(id);

            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Usuário deletado com sucesso.");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Erro ao deletar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
