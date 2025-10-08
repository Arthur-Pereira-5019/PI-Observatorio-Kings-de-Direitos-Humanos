package com.kings.okdhvi.services;

import com.kings.okdhvi.model.Comentario;
import com.kings.okdhvi.model.requests.ComentarioCDTO;
import com.kings.okdhvi.repositories.ComentarioRepository;
import com.kings.okdhvi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Date;


public class ComentarioServices {
    @Autowired
    ComentarioRepository cr;

    @Autowired
    UsuarioService us;

    public Comentario criarComentario(ComentarioCDTO ccdto, Long id) {
        Comentario c = new Comentario();
        c.setAutor(us.encontrarPorId(id, false));
        c.setTextComentario(ccdto.textoComentario());
        c.setDataComentario(Date.from(Instant.now()));
        return cr.save(c);
    }
}
