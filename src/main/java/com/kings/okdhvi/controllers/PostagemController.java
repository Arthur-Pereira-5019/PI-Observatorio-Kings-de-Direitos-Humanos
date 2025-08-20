package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.PedidoLogin;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.services.PostagemServices;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/postagem")
public class PostagemController {

    @Autowired
    PostagemServices ps;

    @GetMapping(value = "/mock", produces = MediaType.APPLICATION_JSON_VALUE)
    public Postagem createUser() {
        return ps.mockPostagem();
    }

}
