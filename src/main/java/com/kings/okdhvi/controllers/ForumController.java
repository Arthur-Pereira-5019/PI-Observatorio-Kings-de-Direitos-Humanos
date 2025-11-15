package com.kings.okdhvi.controllers;

import com.kings.okdhvi.mapper.ForumMapper;
import com.kings.okdhvi.model.*;
import com.kings.okdhvi.model.DTOs.*;
import com.kings.okdhvi.services.ForumServices;
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
@RequestMapping("/api/forum")
public class ForumController {

    @Autowired
    ForumServices fs;
    @Autowired
    UsuarioService us;

    @Autowired
    ForumMapper fm;

    @GetMapping("/usuario/{id}")
    public List<Forum> encontrarPeloUsuario(@PathVariable Long id) {
        return fs.encontrarPeloUsuario(id);
    }

    @PreAuthorize("hasRole('ROLE_PADRAO')")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Forum criarForum(@RequestBody ForumCDTO f, @AuthenticationPrincipal UserDetails user) {
        return fs.criarForum(f, us.buscarId(user));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ForumECDTO encontrarForumPeloId(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails ud, HttpServletResponse response) throws NoResourceFoundException {
        ForumECDTO retorno = fm.parseForumToECDTO(fs.encontrarForumPeloId(id));
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
    public void ocultarForum(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails ud, @RequestBody DecisaoModeradoraOPDTO dmdto) {
        fs.ocultar(us.buscarId(ud), id, dmdto);
    }

    @PostMapping(value="/listar_publicacoes/{texto}/{pagina}")
    public BuscaPaginadaResultado<ForumESDTO> listarForuns(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @PathVariable("pagina") Integer pagina, @AuthenticationPrincipal UserDetails ud) {
        List<Forum> foruns;
        List<ForumESDTO> resultadosDTO = new ArrayList<>();
        BuscaPaginada bp = new BuscaPaginada(pagina, 10, bpt.parametro(), bpt.ascending());
        BuscaPaginadaResultado<Forum> bpr = fs.buscaFiltrada(bp, texto, ud);
        foruns = bpr.getResultado();

        foruns.forEach(f -> resultadosDTO.add(fm.parseForumToESDTO(f)));

        BuscaPaginadaResultado<ForumESDTO> retorno = new BuscaPaginadaResultado<>();
        retorno.setResultado(resultadosDTO);
        retorno.setProximosIndexes(bpr.getProximosIndexes());
        return retorno;
    }

    @PostMapping(value = "busca_paginada", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<? extends ForumESDTO> buscaPaginada(@RequestBody BuscaPaginada bp, @AuthenticationPrincipal UserDetails ud) {

        ArrayList<ForumESDTO> retorno = new ArrayList<>();
        fs.buscaFiltrada(bp,null, ud).getResultado().forEach(p -> {retorno.add(fm.parseForumToESDTO(p));});
        return retorno;
    }


    @PostMapping(value="/listar_foruns/{texto}")
    public BuscaPaginadaResultado<ForumESDTO> listarForunsTexto(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @AuthenticationPrincipal UserDetails ud) {
        return listarForuns(bpt, texto,0, ud);
    }


}
