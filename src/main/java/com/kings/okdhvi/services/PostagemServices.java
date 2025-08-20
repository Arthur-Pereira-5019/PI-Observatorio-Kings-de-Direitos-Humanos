package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.PostagemNotFoundException;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.PostagemRequest;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.repositories.PostagemRepository;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostagemServices {
    @Autowired
    PostagemRepository pr;
    @Autowired
    UsuarioService us;

    public Postagem mockPostagem() {
        Postagem p = new Postagem();

        try {
            Usuario u = us.mockUsuario();
            p.setAutor(u);
            p.setTags("noticia;crime");
            List<Usuario> lu = new ArrayList<>();
            lu.add(u);
            p.setRevisor(lu);
            p.setTextoPostagem("Mais um negro assassinado.");
            p.setOculto(false);
            p.setTituloPostagem("Morte");
            p.setIdPostagem(1234L);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public Postagem criarPostagem(PostagemRequest p) {
        Postagem post = p.postagem();
            post.setAutor(us.encontrarPorId(p.id()));
        post.setDataDaPostagem(Date.from(Instant.now()));
        try {
            pr.save(post);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pr.save(post);
    }

    public Postagem atualizarPostagem (Postagem p, Long id) {
        Postagem original = encontrarPostagemPeloId(id);
        original = p;
        p.setIdPostagem(id);
        return pr.save(original);
    }

    public void deletarPeloId(Long id) {
       pr.delete(encontrarPostagemPeloId(id));
    }

    public Postagem encontrarPostagemPeloId(Long id) {
        return pr.findById(id).orElseThrow(() -> new PostagemNotFoundException("Postagem não encontrada!"));
    }
}
