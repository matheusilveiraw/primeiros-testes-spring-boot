package com.minhaprimeiraapijava.services;

import com.minhaprimeiraapijava.models.Compra;
import com.minhaprimeiraapijava.models.Usuario;
import com.minhaprimeiraapijava.repositories.CompraRepository;
import com.minhaprimeiraapijava.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;
    private UsuarioRepository usuarioRepository;

    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    public Compra salvarCompra(Compra compra) {
        return compraRepository.save(compra);
    }

    public void deletarCompra(Long id) {
        if (!compraRepository.existsById(id)) {
            throw new RuntimeException("Compra não encontrada.");
        }
        compraRepository.deleteById(id);
    }

    public Compra atualizarCompra(Long id, Compra compraAtualizada) {
        return compraRepository.findById(id).map(compra -> {
            Usuario usuario = usuarioRepository.findById(compraAtualizada.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
            compra.setUsuario(usuario);

            compra.setDataHora(compraAtualizada.getDataHora());
            compra.setValorTotal(compraAtualizada.getValorTotal());

            return compraRepository.save(compra);
        }).orElseThrow(() -> new RuntimeException("Compra não encontrada."));
    }
}