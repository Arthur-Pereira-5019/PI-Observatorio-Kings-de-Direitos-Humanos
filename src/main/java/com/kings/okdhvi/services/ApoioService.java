package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.Apoio;
import com.kings.okdhvi.model.DTOs.ApoioCDTO;
import com.kings.okdhvi.model.DTOs.CriarImagemRequest;
import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.repositories.ApoioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApoioService {
    @Autowired
    ApoioRepository apr;

    @Autowired
    ImagemService is;

    public Apoio criarNovoApoio(ApoioCDTO ap, Usuario u) {
        Apoio a = new Apoio();
        a.setNomeInstituicao(ap.nomeInstituicao());
        a.setLinkedin(ap.linkedin());
        a.setLocalizacao(ap.localizacao());
        a.setTwitter(ap.twitter());
        a.setSobreInstituicao(ap.sobreInstituicao());
        a.setSite(ap.site());
        a.setInstagram(ap.instagram());
        if(ap.ImagemBase64() != null) {
            if(!ap.ImagemBase64().isEmpty()) {
                Imagem i = is.criarImagem(new CriarImagemRequest(ap.ImagemBase64(), "Logo de " + ap.nomeInstituicao(), "Logo de " + ap.nomeInstituicao(), ""), u);
                a.setFoto(i);
            }
        }
        return apr.save(a);
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

    public Apoio atualizarApoio(ApoioCDTO ap, Long id, Usuario u) {
        Apoio velhoAp = encontrarApoio(id);
        velhoAp.setNomeInstituicao(ap.nomeInstituicao());
        velhoAp.setSobreInstituicao(ap.sobreInstituicao());
        velhoAp.setTwitter(ap.twitter());
        velhoAp.setTelefone(ap.telefone());
        velhoAp.setLocalizacao(ap.localizacao());
        velhoAp.setSite(ap.site());
        velhoAp.setInstagram(ap.instagram());
        velhoAp.setLinkedin(ap.linkedin());
        Imagem i = is.criarImagem(new CriarImagemRequest(ap.ImagemBase64(), "Logo de "+ap.nomeInstituicao(), "Logo de "+ap.nomeInstituicao(),""),u);
        velhoAp.setFoto(i);
        return apr.save(velhoAp);
    }
}
