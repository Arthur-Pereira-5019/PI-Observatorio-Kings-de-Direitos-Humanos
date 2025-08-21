package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.PostagemNotFoundException;
import com.kings.okdhvi.exception.RevisaoPostagemException;
import com.kings.okdhvi.model.*;
import com.kings.okdhvi.repositories.PostagemRepository;
import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;
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
        post.setRevisor(null);
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

    @Transactional
    public Postagem revisarPostagem(RevisorPostagemRequest rpr) {
        //TODO: Verificar se o usuário já não revisou
        Postagem p = encontrarPostagemPeloId(rpr.idPostagem());
        Usuario u = us.encontrarPorId(rpr.idUsuario());
        if(p.getRevisor().contains(u)) {
            throw new RevisaoPostagemException("Postagem já revisada pelo usuário fornecido!");
        }
        p.getRevisor().add(u);
        return pr.save(p);
    }

    public void ocultar(Long id) {
        Postagem p = encontrarPostagemPeloId(id);
        p.setOculto(false);
        DecisaoModeradora dm = new DecisaoModeradora();
        dm.setData(Date.from(Instant.now()));
        // dm.setMotivacao();
        // dm.setResponsavel();
        dm.setTipo("Postagem");
        // dm.setUsuarioModerado();
    }
}
