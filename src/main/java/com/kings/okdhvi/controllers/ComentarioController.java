package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.Comentario;
import com.kings.okdhvi.model.requests.ComentarioCDTO;
import com.kings.okdhvi.services.ComentarioServices;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
