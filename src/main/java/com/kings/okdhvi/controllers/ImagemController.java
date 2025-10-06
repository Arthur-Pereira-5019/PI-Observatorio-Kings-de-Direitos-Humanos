package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.requests.CriarImagemRequest;
import com.kings.okdhvi.services.ImagemService;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@RestController
@RequestMapping("/api/imagem")
public class ImagemController {

    @Autowired
    ImagemService is;
    @Autowired
    UsuarioService us;

    @PostMapping(value = "")
    public Imagem criarImagem(@RequestBody CriarImagemRequest cir, @AuthenticationPrincipal UserDetails ud){
        return is.criarImagem(cir, us.buscarId(ud));
    }

    @GetMapping("/{id}")
    public Imagem procurarImagemPeloId(@PathVariable("id") Long id) {
          return is.retornarImagemPeloId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarImagemPeloId(@PathVariable("id") Long id) {
        is.excluirImagemPeloId(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

}
