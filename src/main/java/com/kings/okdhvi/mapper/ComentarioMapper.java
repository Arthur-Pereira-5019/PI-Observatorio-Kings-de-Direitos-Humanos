package com.kings.okdhvi.mapper;

import com.kings.okdhvi.model.Comentario;
import com.kings.okdhvi.model.DTOs.ComentarioDTO;
import com.kings.okdhvi.model.DTOs.ComentarioLogDTO;
import com.kings.okdhvi.model.DTOs.ComentarioFDTO;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.services.ForumServices;
import com.kings.okdhvi.services.PostagemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ComentarioMapper {
    @Autowired
    UsuarioMapper um;

    @Autowired
    PostagemServices ps;

    @Autowired
    ForumServices fs;

    public ComentarioDTO apresentarComentario(Comentario c, Usuario u) {
        ComentarioDTO r = new ComentarioDTO();
        r.setAutor(um.usuarioComentador(c.getAutor()));
        r.setId(c.getIdComentario());
        r.setTexto(c.getTextComentario());
        r.setProprio(false);
        if(u != null && c.getAutor() != null) {
            r.setProprio(c.getAutor().getIdUsuario().equals(u.getIdUsuario()));
        }
        return r;
    }

    public ComentarioLogDTO comentarioLog(Comentario c) {
        ComentarioLogDTO r = new ComentarioLogDTO();
        Long id = c.getIdDono();
        r.setId(c.getIdComentario());
        r.setIdDono(id);
        r.setTexto(c.getTextComentario());
        r.setDataComentario(c.getDataComentario());
        r.setTipo(c.getTipo());
        String titulo = "";
        try {
            titulo = c.getTipo() == 'F' ? fs.encontrarForumPeloId(id).getTituloForum() : ps.encontrarPostagemPeloId(id).getTituloPostagem();
        } finally {
            r.setTituloDono(titulo);
        }
        return r;
    }

    public ComentarioFDTO apresentarComentarioF(Comentario c, Usuario u) {
        ComentarioFDTO r = new ComentarioFDTO();
        r.setAutor(um.usuarioComentador(c.getAutor()));
        r.setId(c.getIdComentario());
        r.setTexto(c.getTextComentario());
        r.setProprio(c.getAutor().getIdUsuario().equals(u.getIdUsuario()));
        r.setDate(c.getDataComentario());
        return r;
    }
}
