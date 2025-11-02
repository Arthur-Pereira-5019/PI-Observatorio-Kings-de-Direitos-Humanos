package com.kings.okdhvi.controllers;

import com.kings.okdhvi.mapper.PostagemMapper;
import com.kings.okdhvi.model.*;
import com.kings.okdhvi.model.DTOs.*;
import com.kings.okdhvi.services.PostagemServices;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/postagem")
public class PostagemController {

    @Autowired
    PostagemServices ps;
    @Autowired
    UsuarioService us;

    @Autowired
    PostagemMapper pm;

    @GetMapping("/usuario/{id}")
    public List<Postagem> encontrarPeloUsuario(@PathVariable Long id) {
        return ps.encontrarPeloUsuario(id);
    }

    @PreAuthorize("hasRole('ROLE_ESPEC')")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Postagem criarPostagem(@RequestBody PostagemCDTO p, @AuthenticationPrincipal UserDetails user) {
        return ps.criarPostagem(p, us.buscarId(user));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostagemPaginaDTO encontrarPostagemPeloId(@PathVariable("id") Long id) {
        return pm.paginaPostagem(ps.encontrarPostagemPeloId(id));
    }

    //NÃO USAR, ESSE SUJEITO AQUI NÃO TEM MEDO DE ANULAR TODOS SEUS CAMPOS
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Postagem atualizarPostagem(@RequestBody Postagem p, @PathVariable("id") Long id) {
        return ps.atualizarPostagem(p,id);
    }

    @PostMapping(value="/listar_publicacoes/{texto}/{pagina}")
    public BuscaPaginadaResultado<PostagemECDTO> listarPostagens(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @PathVariable("pagina") Integer pagina, @AuthenticationPrincipal UserDetails ud) {
        List<Postagem> postagens;
        List<PostagemECDTO> resultadosDTO = new ArrayList<>();
        BuscaPaginada bp = new BuscaPaginada(pagina, 10, bpt.parametro(), bpt.ascending());
        BuscaPaginadaResultado<Postagem> bpr = ps.buscaFiltrada(bp, texto, ud);
        postagens = bpr.getResultado();

        postagens.forEach(b -> resultadosDTO.add(pm.parsePostagemToECDTO(b)));

        BuscaPaginadaResultado<PostagemECDTO> retorno = new BuscaPaginadaResultado<>();
        retorno.setResultado(resultadosDTO);
        retorno.setProximosIndexes(bpr.getProximosIndexes());
        return retorno;
    }

    @PostMapping(value = "busca_paginada", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<? extends PostagemESDTO> buscaPaginada(@RequestBody BuscaPaginada bp, @AuthenticationPrincipal UserDetails ud) {

        ArrayList<PostagemESDTO> retorno = new ArrayList<>();
        ps.buscaFiltrada(bp,null, ud).getResultado().forEach(p -> {retorno.add(pm.parsePostagemToESDTO(p));});
        return retorno;
    }


    @PostMapping(value="/listar_publicacoes/{texto}")
    public BuscaPaginadaResultado<PostagemECDTO> listarPublicacoesTexto(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @AuthenticationPrincipal UserDetails ud) {
        return listarPostagens(bpt, texto,0, ud);
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
