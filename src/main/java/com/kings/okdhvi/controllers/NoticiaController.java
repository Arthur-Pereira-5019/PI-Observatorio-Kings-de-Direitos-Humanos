package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.requests.*;
import com.kings.okdhvi.services.NoticiaServices;
import com.kings.okdhvi.services.PostagemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

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
        BuscaPaginada bp = new BuscaPaginada(pagina, 2, bpt.parametro(), bpt.ascending());
        postagens = ps.buscaFiltrada(bp, texto, ud);

        postagens.forEach(b -> retorno.add(ns.parsePostagemToNoticiaESDTO(b)));
        return retorno;
    }

    @PostMapping(value="/listar_publicacoes/{texto}")
    public List<NoticiaESDTO> listarPublicacoesTexto(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @AuthenticationPrincipal UserDetails ud) {
        return listarNoticias(bpt, texto,0, ud);
    }
}
