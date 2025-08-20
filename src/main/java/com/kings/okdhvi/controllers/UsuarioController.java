package com.kings.okdhvi.controllers;

import com.kings.okdhvi.exception.PostagemNotFoundException;
import com.kings.okdhvi.model.PedidoLogin;
import com.kings.okdhvi.model.Postagem;
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

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Usuario criarUsuario(@RequestBody Usuario u) {
        return us.saveUsuario(u);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Usuario encontrarUsuarioPeloId(@PathVariable("id") Long id) {
        return us.encontrarPorId(id);
    }


    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Usuario atualizarUsuario(@RequestBody Usuario u) {
        return us.atualizarUsuario(u);
    }

    @DeleteMapping(value = "/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        us.deletarPeloId(id);
    }



}
