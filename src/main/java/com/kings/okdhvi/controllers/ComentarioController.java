package com.kings.okdhvi.controllers;

import com.kings.okdhvi.exception.usuario.UnauthorizedActionException;
import com.kings.okdhvi.mapper.ComentarioMapper;
import com.kings.okdhvi.mapper.UsuarioMapper;
import com.kings.okdhvi.model.Comentario;
import com.kings.okdhvi.model.DTOs.*;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.services.ComentarioServices;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/com")
public class ComentarioController {

    @Autowired
    ComentarioServices cs;

    @Autowired
    UsuarioService us;

    @Autowired
    ComentarioMapper cm;

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public Comentario comentar(@RequestBody ComentarioCDTO cc, @AuthenticationPrincipal UserDetails ud) {
        if(ud.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUSPEN"))) {
            throw new UnauthorizedActionException("Contas suspensas não podem comentar");
        }
        return cs.criarComentario(cc,us.buscarId(ud));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/encontrar_comentarios/{id}")
    public List<ComentarioLogDTO> listarComentarios(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails ud) {
        ArrayList<ComentarioLogDTO> cdto = new ArrayList<>();
        Usuario u = us.encontrarPorId(id, false);
        cs.encontrarPeloUsuario(id).forEach(c -> {cdto.add(cm.comentarioLog(c));});
        return cdto;
    }

    @PostMapping(value="/listar_comentarios/{id}/{tipo}/{pagina}")
    public BuscaPaginadaResultado<ComentarioDTO> listarPostagens(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("id") Long id, @PathVariable("tipo") Character tipo, @AuthenticationPrincipal UserDetails ud, @PathVariable("pagina") Integer pagina) {
        List<Comentario> comentarios;
        List<ComentarioDTO> comentariosTratados = new ArrayList<>();
        BuscaPaginadaResultado<ComentarioDTO> retorno = new BuscaPaginadaResultado<>();

        BuscaPaginada bp = new BuscaPaginada(pagina, 18, bpt.parametro(), bpt.ascending());
        BuscaPaginadaResultado<Comentario> bpr = cs.buscaFiltrada(bp, id, tipo, ud);
        comentarios = bpr.getResultado();

        Usuario u;
        if(ud == null) {
            u = null;
        } else {
            u = us.encontrarPorId(us.buscarId(ud),true);
        }

        comentarios.forEach(c -> {comentariosTratados.add(cm.apresentarComentarioF(c, u));});
        retorno.setResultado(comentariosTratados);
        retorno.setProximosIndexes(bpr.getProximosIndexes());
        return retorno;
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value="/excluir/{id}")
    public void excluirComentario(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails ud, @RequestBody DecisaoModeradoraOPDTO dmdto) {

        cs.deletarComentario(id, us.encontrarPorId(us.buscarId(ud), true), dmdto);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value="/excluir_proprio/{id}")
    public void excluirComentario(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails ud) {

        cs.deletarComentario(id, us.encontrarPorId(us.buscarId(ud), true), null);
    }

}
