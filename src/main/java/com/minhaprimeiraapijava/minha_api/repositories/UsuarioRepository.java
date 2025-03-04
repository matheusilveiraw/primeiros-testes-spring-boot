package com.minhaprimeiraapijava.minha_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.minhaprimeiraapijava.minha_api.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}