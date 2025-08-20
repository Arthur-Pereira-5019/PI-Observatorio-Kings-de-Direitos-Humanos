package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.PostagemNotFoundException;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.repositories.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostagemServices {
    @Autowired
    PostagemRepository pr;
    @Autowired
    UsuarioService us;

    public Postagem mockPostagem() {
        UsuarioService us = new UsuarioService();
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

    public Postagem criarPostagem(Postagem p) {
        return pr.save(p);
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
