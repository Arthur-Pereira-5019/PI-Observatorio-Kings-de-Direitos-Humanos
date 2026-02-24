package com.kings.okdhvi.mapper;

import com.kings.okdhvi.model.DTOs.forum.ForumECDTO;
import com.kings.okdhvi.model.DTOs.forum.ForumESDTO;
import com.kings.okdhvi.model.Forum;
import com.kings.okdhvi.services.ComentarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ForumMapper {
    @Autowired
    ComentarioServices cs;
    @Autowired
    UsuarioMapper um;

    public ForumESDTO parseForumToESDTO(Forum f) {
        Long id = f.getId();
        String prefixoOculto = f.isOculto() ? "[OCULTO] " : "";
        long numeroRespostas = cs.contarComentarios('F', id);
        return new ForumESDTO(f.getId(),
                prefixoOculto + f.getTituloForum(),
                numeroRespostas,
                f.getDataDeAtualizacao(),
                f.getDataDeCriacao(),
                um.usuarioForum(f.getAutor()),
                f.isOculto()
        );
    }

    public ForumECDTO parseForumToECDTO(Forum f, Long idUsuario) {
        String textoForum = f.getTextoForum();
        Long id = f.getId();
        String prefixoOculto = f.isOculto() ? "[OCULTO] " : "";
        long numeroRespostas = cs.contarComentarios('F', id);
        return new ForumECDTO(f.getId(),
                prefixoOculto + f.getTituloForum(),
                numeroRespostas,
                f.getDataDeAtualizacao(),
                f.getDataDeCriacao(),
                um.usuarioForum(f.getAutor()),
                f.isOculto(),
                f.getTextoForum(),
                Objects.equals(idUsuario, f.getAutor().getIdUsuario())
        );
    }

}
