package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.exception.postagem.RevisaoPostagemException;
import com.kings.okdhvi.exception.usuario.NullResourceException;
import com.kings.okdhvi.model.*;
import com.kings.okdhvi.model.requests.OcultarRecursoRequest;
import com.kings.okdhvi.model.requests.PostagemRequest;
import com.kings.okdhvi.model.requests.RevisorPostagemRequest;
import com.kings.okdhvi.repositories.PostagemRepository;
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
        return p;
    }

    @Transactional
    public Postagem criarPostagem(PostagemRequest p) {
        if(p == null) {
            throw new NullResourceException("Postagem nula submetida!");
        }
        Postagem post = p.postagem();
        post.setAutor(us.encontrarPorId(p.id()));
        post.setDataDaPostagem(Date.from(Instant.now()));
        post.setRevisor(null);
        return pr.save(post);
    }

    public Postagem atualizarPostagem (Postagem p, Long id) {
        if(p == null) {
            throw new NullResourceException("Postagem nula submetido");
        }
        Postagem original = encontrarPostagemPeloId(id);
        original = p;
        p.setIdPostagem(id);
        return pr.save(original);
    }

    public void deletarPeloId(Long id) {
       pr.delete(encontrarPostagemPeloId(id));
    }

    public Postagem encontrarPostagemPeloId(Long id) {
        return pr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Postagem não encontrada!"));
    }

    @Transactional
    public Postagem revisarPostagem(RevisorPostagemRequest rpr) {
        Postagem p = encontrarPostagemPeloId(rpr.idPostagem());
        Usuario u = us.encontrarPorId(rpr.idUsuario());

        if(p.getRevisor().contains(u)) {
            throw new RevisaoPostagemException("Postagem já revisada pelo usuário fornecido!");
        }

        p.getRevisor().add(u);
        return pr.save(p);
    }

    public void ocultar(OcultarRecursoRequest r) {
        Postagem p = encontrarPostagemPeloId(r.idPostagem());
        p.setOculto(false);
        DecisaoModeradora dm = new DecisaoModeradora();
        dm.setData(Date.from(Instant.now()));
        dm.setMotivacao(r.motivacao());
        dm.setResponsavel(r.moderador());
        dm.setTipo("Postagem");
        dm.setUsuarioModerado(r.moderado());
        dm.setNomeModerado(p.getTituloPostagem());
    }
}
