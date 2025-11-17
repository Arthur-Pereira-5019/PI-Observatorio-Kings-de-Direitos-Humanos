package com.kings.okdhvi.controllers;

import com.kings.okdhvi.mapper.PostagemMapper;
import com.kings.okdhvi.model.*;
import com.kings.okdhvi.model.DTOs.*;
import com.kings.okdhvi.services.PostagemServices;
import com.kings.okdhvi.services.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
    public List<PostagemECDTO> encontrarPeloUsuario(@PathVariable Long id) {
        ArrayList<PostagemECDTO> posts = new ArrayList<>();
        ps.encontrarPeloUsuario(id).forEach(p -> {posts.add(pm.parsePostagemToECDTO(p));});
        return posts;
    }

    @PreAuthorize("hasRole('ROLE_ESPEC')")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Postagem criarPostagem(@RequestBody PostagemCDTO p, @AuthenticationPrincipal UserDetails user) {
        return ps.criarPostagem(p, us.buscarId(user));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostagemPaginaDTO encontrarPostagemPeloId(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails ud, HttpServletResponse response) throws NoResourceFoundException {
        PostagemPaginaDTO retorno = pm.paginaPostagem(ps.encontrarPostagemPeloId(id));
        if(retorno.isOculto()) {
            if(ud==null) {
                throw new NoResourceFoundException(HttpMethod.GET, "");
            } else if(!ud.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODER"))) {
                throw new NoResourceFoundException(HttpMethod.GET, "");
            }
        }
        return retorno;
    }

    @PreAuthorize("hasRole('ROLE_MODER')")
    @PutMapping(value="/ocultar/{id}")
    public void ocultarPostagem(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails ud, @RequestBody DecisaoModeradoraOPDTO dmdto) {
        ps.ocultar(us.buscarId(ud), id, dmdto);
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

    @GetMapping(value = "busca_paginada/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<? extends PostagemESDTO> buscaPaginada(@PathVariable("page") int page, @AuthenticationPrincipal UserDetails ud) {

        ArrayList<PostagemESDTO> retorno = new ArrayList<>();
        ps.buscaFiltrada(new BuscaPaginada(page, 3, "dataDaPostagem", false),null, ud).getResultado().forEach(p -> {retorno.add(pm.parsePostagemToESDTO(p));});
        return retorno;
    }


    @PostMapping(value="/listar_publicacoes/{texto}")
    public BuscaPaginadaResultado<PostagemECDTO> listarPublicacoesTexto(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @AuthenticationPrincipal UserDetails ud) {
        return listarPostagens(bpt, texto,0, ud);
    }

    @PreAuthorize("hasRole('ROLE_ESPEC')")
    @PutMapping(value = "revisar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Postagem revisarPostagem(@RequestBody RevisorPostagemRequest rpr) {
        return ps.revisarPostagem(rpr);
    }

    
}
