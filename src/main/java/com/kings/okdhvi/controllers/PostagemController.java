package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.*;
import com.kings.okdhvi.services.PostagemServices;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/postagem")
public class PostagemController {

    @Autowired
    PostagemServices ps;

    @GetMapping(value = "/mock", produces = MediaType.APPLICATION_JSON_VALUE)
    public Postagem mockPostagem() {
        return ps.mockPostagem();
    }


    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Postagem criarUsuario(@RequestBody PostagemRequest p) {
        return ps.criarPostagem(p);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Postagem encontrarPostagemPeloId(@PathVariable("id") Long id) {
        return ps.encontrarPostagemPeloId(id);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Postagem atualizarPostagem(@RequestBody Postagem p, @PathVariable("id") Long id) {
        return ps.atualizarPostagem(p,id);
    }

    @DeleteMapping(value = "/{id}")
    public void deletarPostagem(@PathVariable Long id) {
        ps.deletarPeloId(id);
    }

    @PutMapping(value = "revisar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Postagem revisarPostagem(@RequestBody RevisorPostagemRequest rpr) {
        return ps.revisarPostagem(rpr);
    }
}
