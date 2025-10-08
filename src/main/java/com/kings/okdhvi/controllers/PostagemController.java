package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.*;
import com.kings.okdhvi.model.requests.*;
import com.kings.okdhvi.services.PostagemServices;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postagem")
public class PostagemController {

    @Autowired
    PostagemServices ps;
    @Autowired
    UsuarioService us;


    @PreAuthorize("hasRole('ROLE_ESPEC')")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Postagem criarPostagem(@RequestBody PostagemCDTO p, @AuthenticationPrincipal UserDetails user) {
        return ps.criarPostagem(p, us.buscarId(user));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Postagem encontrarPostagemPeloId(@PathVariable("id") Long id) {
        return ps.encontrarPostagemPeloId(id);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Postagem atualizarPostagem(@RequestBody Postagem p, @PathVariable("id") Long id) {
        return ps.atualizarPostagem(p,id);
    }

    @PreAuthorize("hasRole('MODER')")
    @PutMapping(value = "ocultar/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public DecisaoModeradora ocultarPostagem(@PathVariable("id") Long idPostagem, @AuthenticationPrincipal UserDetails ud, @RequestBody DecisaoModeradoraOPDTO dmopdto) {
        return ps.ocultarPostagem(idPostagem,us.buscarId(ud), dmopdto);
    }

    @DeleteMapping(value = "/{id}")
    public void deletarPostagem(@PathVariable Long id) {
        ps.deletarPeloId(id);
    }


    @PutMapping(value = "revisar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Postagem revisarPostagem(@RequestBody RevisorPostagemRequest rpr) {
        return ps.revisarPostagem(rpr);
    }

    @PostMapping(value = "busca_paginada", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<PostagemESDTO> buscaPaginada(@RequestBody BuscaPaginada bp) {
        return ps.encontrarPostagens(bp);
    }
}
