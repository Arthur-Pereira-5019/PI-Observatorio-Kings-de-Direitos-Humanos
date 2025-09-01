package com.kings.okdhvi.services;

import com.kings.okdhvi.model.EstadoDaConta;
import com.kings.okdhvi.repositories.EstadoDaContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoDaContaServices {

    @Autowired
    EstadoDaContaRepository edcr;

    public EstadoDaConta criarEstadoDaConta(EstadoDaConta edc) {
        edcr.save(edc);
    }

    public void deletarEstadoDaContaPeloId(Long id) {
        EstadoDaConta edc =
        edcr.delete;
    }

    public EstadoDaConta encontrarEstadoDaConta
}
