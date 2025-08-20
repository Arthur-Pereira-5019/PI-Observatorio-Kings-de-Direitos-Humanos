package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.PedidoLogin;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UsuarioController {


    @Autowired
    UsuarioService us;
    @RequestMapping("/login")
    public String login(@RequestBody PedidoLogin lr) {
        if(us.login(lr.senha(),lr.email())) {
            return "Oieee";
        }
        return "Não";
    }


    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Usuario createUser(@RequestBody Usuario u) {
        return us.saveUsuario(u);
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Usuario getUserById(@PathVariable("id") Long id) {
        return us.encontrarPorId(id);
    }

}
