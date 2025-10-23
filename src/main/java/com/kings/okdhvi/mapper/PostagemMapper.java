package com.kings.okdhvi.mapper;

import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.requests.PostagemECDTO;
import com.kings.okdhvi.model.requests.PostagemESDTO;
import org.springframework.stereotype.Service;

@Service
public class PostagemMapper {
    public PostagemESDTO parsePostagemToESDTO(Postagem p) {
        String prefixoOculto = p.isOculto() ? "[OCULTO] " : "";
        return new PostagemESDTO(p.getIdPostagem(), prefixoOculto + p.getTituloPostagem(), p.getCapa());
    }

    public PostagemECDTO parsePostagemToECDTO(Postagem p) {
        String textoPostagem = p.getTextoPostagem();
        String prefixoOculto = p.isOculto() ? "[OCULTO] " : "";
        String nomeAutor = p.getAutor() == null ? "Externo" : p.getAutor().getNome();
        return new PostagemECDTO(p.getIdPostagem(),
                prefixoOculto + p.getTituloPostagem(),
                p.getCapa(),
                textoPostagem.substring(0, textoPostagem.length() > 255 ? 255 : textoPostagem.length()),
                nomeAutor,
                p.getDataDaPostagem()
        );
    }

}
