package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.imagens.ImagemNotFoundException;
import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.model.requests.CriarImagemRequest;
import com.kings.okdhvi.repositories.ImagemRepository;
import com.kings.okdhvi.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class ImagemService {

    @Autowired
    ImagemRepository ir;
    @Autowired
    UsuarioService us;

    public Imagem criarImagem(CriarImagemRequest cir) {
        Imagem i = new Imagem();
        byte[] imageByte = new byte[0];
        try {
            imageByte = Base64.getDecoder().decode(cir.imageBase64());
        } catch (Exception e) {
            e.printStackTrace();
        }
        i.setImage(imageByte);
        Usuario u = us.encontrarPorId(cir.usuarioId());
        i.setDonoImagem("("+u.getIdUsuario()+")" + u.getNome());
        i.setTituloImagem(cir.titulo());
        i.setDataImagem(Date.from(Instant.now()));
        i.setDecricaoImagem(cir.descricao());
        return ir.save(i);
    }

    public Imagem retornarImagemPeloId(Long id) {
        return ir.findById(id).orElseThrow(() -> new ImagemNotFoundException("Imagem não encontrada!"));
    }

    public void excluirImagemPeloId(Long id) {
        ir.delete(retornarImagemPeloId(id));
    }

}
