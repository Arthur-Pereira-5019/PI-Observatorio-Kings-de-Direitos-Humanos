package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.exception.imagens.InvalidBase64ImageEncoding;
import com.kings.okdhvi.exception.usuario.UnableToAuthenticateUser;
import com.kings.okdhvi.infra.security.TokenService;
import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.model.requests.CriarImagemRequest;
import com.kings.okdhvi.repositories.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class ImagemService {

    @Autowired
    ImagemRepository ir;
    @Autowired
    UsuarioService us;
    @Autowired
    TokenService ts;

    public Imagem criarImagem(CriarImagemRequest cir, String token) {
        if(cir == null) {
            throw new NullResourceException("Request de Criação de Imagem nula submetido");
        }
        if(token == null || token.isBlank()) {
            throw new UnableToAuthenticateUser("Token de Autenticação Vazio!");
        }

        var login = ts.validateToken(token);
        Usuario u = us.encontrarPorEmail(login,false);

        Imagem i = new Imagem();
        byte[] imageByte = new byte[0];
        try {
            imageByte = Base64.getDecoder().decode(cir.imageBase64());
        } catch (Exception e) {
            throw new InvalidBase64ImageEncoding("A imagem não foi codificada na Base 64 corretamente! " + e.getMessage());
        }
        i.setImagem(imageByte);
        i.setDonoImagem("("+u.getIdUsuario()+")" + u.getNome());
        i.setTituloImagem(cir.titulo());
        i.setDataImagem(Date.from(Instant.now()));
        i.setDecricaoImagem(cir.descricao());
        return ir.save(i);
    }

    public Imagem retornarImagemPeloId(Long id) {
        return ir.findById(id).orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada!"));
    }

    public void excluirImagemPeloId(Long id) {
        ir.delete(retornarImagemPeloId(id));
    }

}
