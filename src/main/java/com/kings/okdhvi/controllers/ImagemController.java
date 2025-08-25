package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.requests.CriarImagemRequest;
import com.kings.okdhvi.services.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@RestController
@RequestMapping("/api/imagem")
public class ImagemController {

    @Autowired
    ImagemService is;

    @PostMapping(value = "/")
    public Imagem criarImagem(@RequestBody CriarImagemRequest cir){
        return is.criarImagem(cir);
    }

    @GetMapping("/{id}")
    public Imagem procurarImagemPeloId(@PathVariable("id") Long id) {
          return is.retornarImagemPeloId(id);
    }

}
