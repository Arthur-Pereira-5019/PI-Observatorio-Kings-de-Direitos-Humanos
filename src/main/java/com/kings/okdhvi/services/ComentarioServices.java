package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.Comentario;
import com.kings.okdhvi.model.Comentavel;
import com.kings.okdhvi.model.Postagem;
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

    @Autowired
    PostagemServices ps;

    public Comentario criarComentario(ComentarioCDTO ccdto, Long id) {
        Comentario c = new Comentario();
        Comentavel comentavel;

        c.setAutor(us.encontrarPorId(id, false));
        c.setTextComentario(ccdto.textoComentario());
        c.setDataComentario(Date.from(Instant.now()));

        if(ccdto.tipo() == 'P') {
            comentavel = ps.encontrarPostagemPeloId(ccdto.idComentavel());
            comentavel.getComentarios().add(c);
            ps.atualizarPostagem((Postagem) comentavel, id);
        }else {
            //Implementar para fóruns
            comentavel = ps.encontrarPostagemPeloId(ccdto.idComentavel());
        }
        return cr.save(c);
    }


    public Comentario ocultarComentario(Long id) {
        Comentario c = encontrarComentario(id);
        c.setOculto(true);
        return c;
    }

    public Comentario restaurarComentario(Long id) {
        Comentario c = encontrarComentario(id);
        c.setOculto(false);
        return c;
    }


    public void deletarComentario(Long id) {
        Comentario c = encontrarComentario(id);
        cr.delete(c);
    }

    public Comentario encontrarComentario(Long id) {
        return cr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado!"));
    }
}
