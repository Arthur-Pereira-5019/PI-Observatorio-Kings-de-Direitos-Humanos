package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.EstadoDaConta;
import com.kings.okdhvi.repositories.EstadoDaContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoDaContaServices {

    @Autowired
    EstadoDaContaRepository edcr;

    public EstadoDaConta criarEstadoDaConta(EstadoDaConta edc) {
        return edcr.save(edc);
    }

    public List<EstadoDaConta> encontrarTodosEstadosDaConta(EstadoDaConta edc) {
        return edcr.findAll();
    }

    public void deletarEstadoDaContaPeloId(Long id) {
        EstadoDaConta edc = encontrarEstadoDaContaPeloId(id);
        edcr.delete(edc);
    }

    public EstadoDaConta encontrarEstadoDaContaPeloId(Long id) {
        return edcr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estado da Conta não encontrado!"));
    }
}
