package com.kings.okdhvi.mapper;

import com.kings.okdhvi.model.Comentario;
import com.kings.okdhvi.model.DTOs.ForumECDTO;
import com.kings.okdhvi.model.DTOs.ForumESDTO;
import com.kings.okdhvi.model.DTOs.PostagemECDTO;
import com.kings.okdhvi.model.Forum;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.services.ComentarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForumMapper {
    @Autowired
    ComentarioServices cs;
    @Autowired
    UsuarioMapper um;

    public ForumESDTO parseForumToESDTO(Forum f) {
        String textoForum = f.getTextoForum();
        Long id = f.getId();
        String prefixoOculto = f.isOculto() ? "[OCULTO] " : "";
        long numeroRespostas = cs.contarComentarios('F', id);
        return new ForumESDTO(f.getId(),
                f.getTituloForum(),
                numeroRespostas,
                f.getDataDeAtualizacao(),
                f.getDataDeCriacao(),
                um.usuarioForum(f.getAutor()),
                f.isOculto()
        );
    }

    public ForumECDTO parseForumToECDTO(Forum f) {
        String textoForum = f.getTextoForum();
        Long id = f.getId();
        String prefixoOculto = f.isOculto() ? "[OCULTO] " : "";
        long numeroRespostas = cs.contarComentarios('F', id);
        return new ForumECDTO(f.getId(),
                f.getTituloForum(),
                numeroRespostas,
                f.getDataDeAtualizacao(),
                f.getDataDeCriacao(),
                um.usuarioForum(f.getAutor()),
                f.isOculto(),
                f.getTextoForum()
        );
    }

}
