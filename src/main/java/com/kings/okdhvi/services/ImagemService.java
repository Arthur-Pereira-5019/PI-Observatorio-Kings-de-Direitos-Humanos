package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.imagens.ImagemNotFoundException;
import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.model.requests.CriarImagemRequest;
import com.kings.okdhvi.repositories.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Date;

public class ImagemService {

    @Autowired
    ImagemRepository ir;
    @Autowired
    UsuarioService us;

    public Imagem criarImagem(CriarImagemRequest cir) {
        Imagem i = new Imagem();
        Usuario u = us.encontrarPorId(cir.usuarioId());
        i.setDonoImagem("("+u.getIdUsuario()+")" + u.getNome());
        i.setTituloImagem(cir.titulo());
        i.setDataImagem(Date.from(Instant.now()));
        i.setDecricaoImagem(cir.descricao());
        i.setImage(cir.imagem());
        return ir.save(i);
    }

    public Imagem retornarImagemPeloId(Long id) {
        return ir.findById(id).orElseThrow(() -> new ImagemNotFoundException("Imagem não encontrada!"));
    }

    public void excluirImagemPeloId(Long id) {
        ir.delete(retornarImagemPeloId(id));
    }

}
