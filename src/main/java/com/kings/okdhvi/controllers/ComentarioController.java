package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.Comentario;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.requests.*;
import com.kings.okdhvi.services.ComentarioServices;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public Comentario comentar(@RequestBody ComentarioCDTO cc, @AuthenticationPrincipal UserDetails ud) {
        return cs.criarComentario(cc,us.buscarId(ud));
    }

    @PostMapping(value="/listar_comentarios/{id}/{tipo}/{pagina}")
    public BuscaPaginadaResultado<Comentario> listarPostagens(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") Long id, @PathVariable("pagina") Character tipo, @AuthenticationPrincipal UserDetails ud, @PathVariable("pagina") Integer pagina) {
        List<Comentario> comentarios;
        BuscaPaginada bp = new BuscaPaginada(pagina, 18, bpt.parametro(), bpt.ascending());
        BuscaPaginadaResultado<Comentario> bpr = cs.buscaFiltrada(bp, id, tipo, ud);
        comentarios = bpr.getResultado();

        BuscaPaginadaResultado<Comentario> retorno = new BuscaPaginadaResultado<>();
        retorno.setResultado(comentarios);
        retorno.setProximosIndexes(bpr.getProximosIndexes());
        return retorno;
    }

}
