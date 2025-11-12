package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.Apoio;
import com.kings.okdhvi.repositories.ApoioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ApoioService {
    @Autowired
    ApoioRepository apr;

    public Apoio criarNovoApoio(Apoio ap) {
        return apr.save(ap);
    }

    public Apoio encontrarApoio(Long id) {
        return apr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Apoio não encontrado!"));
    }

    public List<Apoio> encontrarTodosApoios() {
        return apr.findAll();
    }

    public void deletarApoio(Long id) {
        apr.delete(encontrarApoio(id));
    }

    public Apoio atualizarApoio(Apoio ap) {
        Apoio velhoAp = encontrarApoio(ap.getId());
        velhoAp.setNomeInstituicao(ap.getNomeInstituicao());
        velhoAp.setSobreInstituicao(ap.getSobreInstituicao());
        velhoAp.setTwitter(ap.getTwitter());
        velhoAp.setTelefone(ap.getTelefone());
        velhoAp.setLocalizacao(ap.getLocalizacao());
        velhoAp.setSite(ap.getSite());
        return apr.save(velhoAp);
    }
}
