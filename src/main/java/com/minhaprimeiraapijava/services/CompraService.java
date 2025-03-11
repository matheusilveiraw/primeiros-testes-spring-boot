package com.minhaprimeiraapijava.services;

import com.minhaprimeiraapijava.models.Compra;
import com.minhaprimeiraapijava.repositories.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

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
}