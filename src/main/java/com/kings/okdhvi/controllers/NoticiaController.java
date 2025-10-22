package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.requests.*;
import com.kings.okdhvi.services.NoticiaServices;
import com.kings.okdhvi.services.PostagemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/noticia")
public class NoticiaController {
    @Autowired
    PostagemController pc;

    @Autowired
    PostagemServices ps;

    @Autowired
    NoticiaServices ns;

    @PostMapping(value="/listar_noticias/{texto}/{pagina}")
    public List<NoticiaESDTO> listarNoticias(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @PathVariable("pagina") Integer pagina, @AuthenticationPrincipal UserDetails ud) {
        List<Postagem> postagens;
        ArrayList<NoticiaESDTO> retorno = new ArrayList<>();
        BuscaPaginada bp = new BuscaPaginada(pagina, 16, bpt.parametro(), bpt.ascending());
        postagens = ps.buscaFiltrada(bp, texto, ud);

        postagens.forEach(b -> retorno.add(ns.parsePostagemToNoticiaESDTO(b)));
        return retorno;
    }

    @PostMapping(value="/listar_publicacoes/{texto}")
    public List<NoticiaESDTO> listarPublicacoesTexto(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @AuthenticationPrincipal UserDetails ud) {
        return listarNoticias(bpt, texto,0, ud);
    }
}
