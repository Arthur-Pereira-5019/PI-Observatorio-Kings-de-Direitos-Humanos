package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.mapper.PostagemMapper;
import com.kings.okdhvi.model.NoticiaAgregada;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.DTOs.NoticiaESDTO;
import com.kings.okdhvi.repositories.NoticiaAgregadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticiaServices {

    @Autowired
    PostagemServices ps;

    @Autowired
    NoticiaAgregadaRepository nr;

    @Autowired
    PostagemMapper pm;

    public NoticiaESDTO parsePostagemToNoticiaESDTO(Postagem p) {
        String prefixoOculto = p.isOculto() ? "[OCULTO] " : "";
        String nomeAutor = p.getAutor() == null ? "Externo" : p.getAutor().getNome();

        return new NoticiaESDTO(p.getId(), prefixoOculto + p.getTituloPostagem(), p.getCapa(), nomeAutor, p.isExterna());
    }

    public Postagem indexarNoticia(Long id) {
        NoticiaAgregada na = encontrarNoticia(id);
        return ps.salvarPostagem(pm.parseNoticiaToPostagem(na));
    }

    public List<NoticiaAgregada> encontrarNoticias() {
        return nr.findAll();
    }

    public NoticiaAgregada encontrarNoticia(Long id) {
        return nr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notícia não encontrada!"));
    }

    public void deletarNoticia(Long id) {
        nr.delete(encontrarNoticia(id));
    }
}
