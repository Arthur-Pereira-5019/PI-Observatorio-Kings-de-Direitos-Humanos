package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.NoticiaAgregada;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.DTOs.*;
import com.kings.okdhvi.services.NoticiaServices;
import com.kings.okdhvi.services.PostagemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public BuscaPaginadaResultado<NoticiaESDTO> listarNoticias(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @PathVariable("pagina") Integer pagina, @AuthenticationPrincipal UserDetails ud) {
        BuscaPaginadaResultado<Postagem> postagens;
        ArrayList<NoticiaESDTO> listaRetorno = new ArrayList<>();
        BuscaPaginadaResultado<NoticiaESDTO> retorno = new BuscaPaginadaResultado<>();
        BuscaPaginada bp = new BuscaPaginada(pagina, 16, bpt.parametro(), bpt.ascending());
        postagens = ps.buscaFiltrada(bp, texto, ud, true);

        postagens.getResultado().forEach(b -> listaRetorno.add(ns.parsePostagemToNoticiaESDTO(b)));
        retorno.setResultado(listaRetorno);
        retorno.setProximosIndexes(postagens.getProximosIndexes());
        return retorno;
    }

    @PostMapping(value="/listar_publicacoes/{texto}")
    public BuscaPaginadaResultado<NoticiaESDTO> listarPublicacoesTexto(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @AuthenticationPrincipal UserDetails ud) {
        return listarNoticias(bpt, texto,0, ud);
    }

    @PostMapping(value="/listar_agregadas/{texto}/{pagina}")
    public BuscaPaginadaResultado<NoticiaAgregada> listarAgregadas(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @PathVariable("pagina") Integer pagina) {
        BuscaPaginada bp = new BuscaPaginada(pagina, 16, bpt.parametro(), bpt.ascending());
        return ns.buscaFiltrada(bp, texto);
    }

    @PreAuthorize("hasRole('ROLE_ESPEC')")
    @DeleteMapping(value="/aprovar/{id}")
    public void aprovar(@PathVariable("id") Long id ) {
        ns.indexarNoticia(id);
    }

    @PreAuthorize("hasRole('ROLE_ESPEC')")
    @DeleteMapping(value="/eliminar/{id}")
    public void eliminar(@PathVariable("id") Long id ) {
        ns.indexarNoticia(id);
    }


}
