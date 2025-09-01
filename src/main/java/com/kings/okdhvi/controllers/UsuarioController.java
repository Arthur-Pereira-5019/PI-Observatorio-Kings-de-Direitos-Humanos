package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.requests.PedidoLogin;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.model.requests.RetornoLogin;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="")
@RestController
@RequestMapping("/api/user")
public class UsuarioController {

    @Autowired
    UsuarioService us;
    @GetMapping("/login")
    public RetornoLogin login(@RequestBody PedidoLogin lr) {
        return us.login(lr.senha(),lr.email());
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Usuario criarUsuario(@RequestBody Usuario u) {
        return us.saveUsuario(u);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Usuario encontrarUsuarioPeloId(@PathVariable("id") Long id) {
        return us.encontrarPorId(id);
    }

    @GetMapping(value = "/mock", produces = MediaType.APPLICATION_JSON_VALUE)
    public Usuario mock() {
        return us.mockUsuario();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Usuario atualizarUsuario(@RequestBody Usuario u) {
        return us.atualizarUsuario(u);
    }

    @PutMapping(value = "/{id}")
    public Usuario atualizarImagem(@RequestBody Imagem i, @PathVariable("id") Long id) {
        return us.atualizarImagem(id, i);
    }

    @DeleteMapping(value = "/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        us.deletarPeloId(id);
    }



}
