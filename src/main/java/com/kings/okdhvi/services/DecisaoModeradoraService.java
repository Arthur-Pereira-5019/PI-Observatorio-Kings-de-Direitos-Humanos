package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.DTOs.DecisaoModeradoraOPDTO;
import com.kings.okdhvi.model.DecisaoModeradora;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.repositories.DecisaoModeradoraRepository;
import com.kings.okdhvi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class DecisaoModeradoraService {

    @Autowired
    DecisaoModeradoraRepository dmr;

    @Autowired
    UsuarioRepository usuarioRepository;


    public DecisaoModeradora mock() {
        UsuarioService us = new UsuarioService();
        DecisaoModeradora dm = new DecisaoModeradora();
        dm.setData(Date.from(Instant.now()));
        dm.setMotivacao("Discurso de ódio");
        dm.setTipo("Postagem");
        Usuario u = us.mockUsuario();
        dm.setUsuarioModerado(u);
        dm.setResponsavel(u);
        dm.setNomeModerado("Algo racista");
        return dm;
    }

    public DecisaoModeradora criarDecisaoModeradora(DecisaoModeradora dm) {
        if(dm == null) {
            throw new NullResourceException("Decisão Moderadora nula submetido");
        }
        return dmr.save(dm);
    }

    public DecisaoModeradora criarDecisaoModeradora(DecisaoModeradoraOPDTO dm, String tipo, Usuario moderador, Usuario moderado, String nomeModerado) {
        if(dm == null) {
            throw new NullResourceException("Decisão Moderadora nula submetido");
        }
        DecisaoModeradora d = new DecisaoModeradora();
        d.setData(Date.from(Instant.now()));
        d.setMotivacao(dm.motivacao());
        d.setTipo(tipo);
        d.setResponsavel(moderador);
        d.setUsuarioModerado(moderado);
        d.setNomeModerado(nomeModerado);
        return dmr.save(d);
    }

    public DecisaoModeradora encontrarDecisaoPeloId(String hex) {
        Long id = Long.parseLong(hex, 16);
        return dmr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Decisão Moderadora não encontrada!"));
    }


    public List<DecisaoModeradora> encontrarTodasDecisoes() {
        return dmr.findAll();
    }

}
