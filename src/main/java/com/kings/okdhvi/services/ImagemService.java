package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.exception.imagens.InvalidBase64ImageEncoding;
import com.kings.okdhvi.infra.security.TokenService;
import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.model.DTOs.CriarImagemRequest;
import com.kings.okdhvi.repositories.ImagemRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class ImagemService {

    @Autowired
    ImagemRepository ir;

    @Autowired
    TokenService ts;

    @Autowired
    ArquivosSalvosService ass;

    @Value("file_saving.allowed-mime")
    private List<String> mimes;

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
        i.setDescricaoImagem(cir.descricao());
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

    private void validarImagem(MultipartFile i) {
        if(i.isEmpty()) {
            throw new NullResourceException("Imagem vazia!");
        }

        String tipo = i.getContentType();
        if(tipo == null || !mimes.contains(tipo)) {
            throw new InvalidBase64ImageEncoding("Formato de arquivo inválido!");
        }
    }

}
