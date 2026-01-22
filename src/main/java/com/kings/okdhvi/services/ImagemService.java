package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.exception.PersistenceException;
import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.exception.imagens.InvalidBase64ImageEncoding;
import com.kings.okdhvi.exception.imagens.InvalidDocumentFormat;
import com.kings.okdhvi.infra.security.TokenService;
import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.model.DTOs.CriarImagemMetaRequest;
import com.kings.okdhvi.repositories.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ImagemService {

    @Autowired
    ImagemRepository ir;

    @Autowired
    TokenService ts;

    @Autowired
    ArquivosSalvosService as;

    @Value("${file_saving.allowed-mime}")
    private List<String> mimes;

    public Imagem criarImagem(MultipartFile file, CriarImagemMetaRequest cir, Usuario u) {
        if(cir == null) {
            throw new NullResourceException("Ocorreu um erro no envio da imagem!");
        }

        validarImagem(file);

        String caminho;
        try(InputStream inputStream = file.getInputStream()) {
            caminho = as.salvarArquivo(inputStream, file.getOriginalFilename());
        } catch (IOException ex) {
            throw new InvalidDocumentFormat("Erro desconhecido ao receber imagem!");
        }

        Imagem i = new Imagem();
        i.setCaminho(caminho);
        i.setTipoImagem(file.getContentType());
        i.setDonoImagem("("+u.getIdUsuario()+")" + u.getNome());
        i.setTituloImagem(cir.titulo());
        i.setDataImagem(Date.from(Instant.now()));
        i.setDescricaoImagem(cir.descricao());
        try {
            return ir.save(i);
        } catch (Exception e) {
            throw new PersistenceException("Erro desconhecido ao persistir imagem!");
        }
    }

    public Resource retornarImagemPeloId(Long id) {
        try {
            return as.buscarArquivo(retornarDadosImagemPeloId(id).getCaminho());
        } catch (IOException e) {
            throw new ResourceNotFoundException("Problema ao buscar imagem no sistema!");
        }
    }

    public Imagem retornarDadosImagemPeloId(Long id) {
        return ir.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Imagem não encontrada.")
        );
    }

    public void excluirImagemPeloId(Long id) {
        ir.delete(retornarDadosImagemPeloId(id));
    }

    private void validarImagem(MultipartFile i) {
        if(i.isEmpty()) {
            throw new NullResourceException("Imagem vazia!");
        }

        String tipo = i.getContentType();
        if(tipo == null || !mimes.contains(tipo)) {
            mimes.forEach(System.out::println);
            System.out.println(tipo);
            throw new InvalidDocumentFormat("Formato de arquivo inválido!");
        }
    }

}
