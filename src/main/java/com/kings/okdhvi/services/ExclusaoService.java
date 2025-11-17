package com.kings.okdhvi.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExclusaoService {

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public void executarDelecao(Long id) {
        usuarioService.delecaoProgramada(id);
    }
}
