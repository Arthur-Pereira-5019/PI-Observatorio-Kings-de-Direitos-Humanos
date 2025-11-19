package com.kings.okdhvi.mapper;

import com.kings.okdhvi.model.DTOs.PostagemPaginaDTO;
import com.kings.okdhvi.model.DTOs.UsuarioComDTO;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.DTOs.PostagemECDTO;
import com.kings.okdhvi.model.DTOs.PostagemESDTO;
import com.kings.okdhvi.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostagemMapper {
    @Autowired
    UsuarioMapper um;

    public PostagemESDTO parsePostagemToESDTO(Postagem p) {
        String prefixoOculto = p.isOculto() ? "[OCULTO] " : "";
        return new PostagemESDTO(p.getId(), prefixoOculto + p.getTituloPostagem(), p.getCapa());
    }

    public PostagemECDTO parsePostagemToECDTO(Postagem p) {
        String textoPostagem = p.getTextoPostagem();
        String prefixoOculto = p.isOculto() ? "[OCULTO] " : "";
        String nomeAutor = p.getAutor() == null ? "Desconhecido" : p.getAutor().getNome();
        return new PostagemECDTO(p.getId(),
                prefixoOculto + p.getTituloPostagem(),
                p.getCapa(),
                textoPostagem.substring(0, textoPostagem.length() > 255 ? 255 : textoPostagem.length()),
                nomeAutor,
                p.getDataDaPostagem()
        );
    }

    public PostagemPaginaDTO paginaPostagem(Postagem p, Usuario u) {
        boolean proprio = false;
        if(u != null) {
            if(p.getAutor().getIdUsuario().equals(u.getIdUsuario())) {
                proprio = true;
            }
        }
        return new PostagemPaginaDTO(
                p.getId(), p.getTituloPostagem(), p.getDataDaPostagem(), um.usuarioComentador(p.getAutor()), p.getTextoPostagem(), p.getCapa(), p.isOculto(), proprio
        );
    }

}
