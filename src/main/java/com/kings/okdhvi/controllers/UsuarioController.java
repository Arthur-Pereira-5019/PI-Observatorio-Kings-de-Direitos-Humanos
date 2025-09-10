package com.kings.okdhvi.controllers;

import com.kings.okdhvi.infra.security.TokenService;
import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.requests.PedidoLogin;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.model.requests.RetornoLogin;
import com.kings.okdhvi.services.UsuarioService;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="")
@RestController
@RequestMapping("/api/user")

public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UsuarioService us;
    @Autowired
    TokenService ts;


    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Usuario criarUsuario(@RequestBody Usuario u) {
        return us.saveUsuario(u);
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Usuario encontrarUsuarioPeloId(@PathVariable("id") Long id) {
        return us.encontrarPorId(id, false);
    }

    @PreAuthorize("hasRole('MODER')")
    @GetMapping(value = "/mock", produces = MediaType.APPLICATION_JSON_VALUE)
    public Usuario mock() {
        return us.mockUsuario();
    }

    @PreAuthorize("hasRole('PADRAO')")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Usuario atualizarUsuario(@RequestBody Usuario u) {
        return us.atualizarUsuario(u);
    }


    @PreAuthorize("hasRole('PADRAO')")
    @PutMapping(value = "/{id}")
    public Usuario atualizarImagem(@RequestBody Imagem i, @PathVariable("id") Long id) {
        return us.atualizarImagem(id, i);
    }

    @PreAuthorize("hasRole('MODER')")
    @DeleteMapping(value = "/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        us.deletarPeloId(id);
    }


    @PostMapping("/login")
    public RetornoLogin login(@RequestBody PedidoLogin pl) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(pl.email(), pl.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = ts.gerarToken((Usuario) auth.getPrincipal());

        return new RetornoLogin(token);
    }

    @PostMapping("/registrar")
    public Usuario register(@RequestBody Usuario u) {
        return us.saveUsuario(u);
    }

}
