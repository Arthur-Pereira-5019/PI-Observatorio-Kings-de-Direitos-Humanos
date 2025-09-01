package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.exception.decisao_moderadora.DecisaoModeradoraNotFoundException;
import com.kings.okdhvi.exception.usuario.NullResourceException;
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

    public DecisaoModeradora encontrarDecisaoPeloId(Long id) {
        return dmr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Decisão Moderadora não encontrada!"));
    }

    public List<DecisaoModeradora> encontrarTodasDecisoes() {
        return dmr.findAll();
    }

}
