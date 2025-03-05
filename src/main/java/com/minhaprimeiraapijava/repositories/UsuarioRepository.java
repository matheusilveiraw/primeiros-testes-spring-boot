package com.minhaprimeiraapijava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.minhaprimeiraapijava.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //repositorio faz as consultas no banco de dados, no caso tá extendendo o jparepository que já tem as consultas mais básicas feitas
}