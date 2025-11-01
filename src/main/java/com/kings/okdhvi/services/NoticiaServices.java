package com.kings.okdhvi.services;

import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.DTOs.NoticiaESDTO;
import org.springframework.stereotype.Service;

@Service
public class NoticiaServices {

    public NoticiaESDTO parsePostagemToNoticiaESDTO(Postagem p) {
        String prefixoOculto = p.isOculto() ? "[OCULTO] " : "";
        String nomeAutor = p.getAutor() == null ? "Externo" : p.getAutor().getNome();

        return new NoticiaESDTO(p.getId(), prefixoOculto + p.getTituloPostagem(), p.getCapa(), nomeAutor, p.isExterna());
    }
}
