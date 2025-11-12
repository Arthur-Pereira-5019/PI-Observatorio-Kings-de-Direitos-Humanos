package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.exception.imagens.InvalidBase64ImageEncoding;
import com.kings.okdhvi.infra.security.TokenService;
import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.model.DTOs.CriarImagemRequest;
import com.kings.okdhvi.repositories.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class ImagemService {

    @Autowired
    ImagemRepository ir;

    @Autowired
    TokenService ts;

    public Imagem criarImagem(CriarImagemRequest cir, Usuario u) {
        if(cir == null) {
            throw new NullResourceException("Ocorreu um erro no envio da imagem!");
        }

        Imagem i = new Imagem();
        byte[] imageByte = new byte[0];
        try {
            imageByte = Base64.getDecoder().decode(cir.imageBase64());
        } catch (Exception e) {
            throw new InvalidBase64ImageEncoding("Erro ao processar imagem! ");
        }
        i.setTipoImagem(cir.tipoImagem());
        i.setImagem(imageByte);
        i.setDonoImagem("("+u.getIdUsuario()+")" + u.getNome());
        i.setTituloImagem(cir.titulo());
        i.setDataImagem(Date.from(Instant.now()));
        i.setDecricaoImagem(cir.descricao());
        try {
            return ir.save(i);
        } catch (Exception e) {
            throw new InvalidBase64ImageEncoding("Erro ao salvar imagem!");
        }
    }

    public Imagem retornarImagemPeloId(Long id) {
        return ir.findById(id).orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada!"));
    }

    public void excluirImagemPeloId(Long id) {
        ir.delete(retornarImagemPeloId(id));
    }

}
