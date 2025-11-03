package com.kings.okdhvi.mapper;

import com.kings.okdhvi.model.Comentario;
import com.kings.okdhvi.model.DTOs.ComentarioDTO;
import com.kings.okdhvi.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ComentarioMapper {
    @Autowired
    UsuarioMapper um;

    public ComentarioDTO apresentarComentario(Comentario c, Usuario u) {
        ComentarioDTO r = new ComentarioDTO();
        r.setAutor(um.usuarioComentador(c.getAutor()));
        r.setId(c.getIdComentario());
        r.setTexto(c.getTextComentario());
        r.setProprio(c.getAutor() == u);
        return r;
    }
}
