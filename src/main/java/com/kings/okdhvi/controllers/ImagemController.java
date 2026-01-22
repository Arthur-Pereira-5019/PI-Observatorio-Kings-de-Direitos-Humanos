package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.DTOs.CriarImagemMetaRequest;
import com.kings.okdhvi.services.ImagemService;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/imagem")
public class ImagemController {

    @Autowired
    ImagemService is;
    @Autowired
    UsuarioService us;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "")
    public Imagem criarImagem(@RequestPart("meta") CriarImagemMetaRequest meta, @RequestPart(value = "imagem") MultipartFile imagem, @AuthenticationPrincipal UserDetails ud){
        return is.criarImagem(imagem,meta,us.encontrarPorId(us.buscarId(ud),false));
    }

    /*@GetMapping("/{id}")
    public MultiValueMap<String, Object> procurarImagemPeloId(@PathVariable("id") Long id) {
        Imagem meta = is.retornarDadosImagemPeloId(id);
        Resource imagem = is.retornarImagemPeloId(id);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("meta", meta);
        body.add("image", imagem);

        return body;
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Resource> procurarImagemPeloId(@PathVariable Long id) {
        Imagem meta = is.retornarDadosImagemPeloId(id);
        Resource imagem = is.retornarImagemPeloId(id);

        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + meta.getTituloImagem() + "\""
                ).header("descricao",meta.getDescricaoImagem())
                .contentType(MediaType.parseMediaType(meta.getTipoImagem()))
                .body(imagem);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarImagemPeloId(@PathVariable("id") Long id) {
        is.excluirImagemPeloId(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

}
