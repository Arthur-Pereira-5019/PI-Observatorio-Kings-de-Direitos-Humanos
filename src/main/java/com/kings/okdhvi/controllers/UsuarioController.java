package com.kings.okdhvi.controllers;

import com.kings.okdhvi.infra.security.TokenService;
import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.PedidoExclusaoConta;
import com.kings.okdhvi.model.requests.PedidoLogin;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.services.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="")
@RestController
@RequestMapping("/api/user")

public class UsuarioController {

    //AuthenticationPrincipal UserDetails user
    //Long userId = user.getId();

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
    public Usuario encontrarUsuarioPeloId(@AuthenticationPrincipal UserDetails user) {
        Long id = us.buscarId(user);
        return us.encontrarPorId(id, false);
    }

    //@PreAuthorize("hasRole('MODER')")
    @GetMapping(value = "/mock", produces = MediaType.APPLICATION_JSON_VALUE)
    public Usuario mock() {
        return us.mockUsuario();
    }

    @PreAuthorize("hasRole('PADRAO')")
    @PutMapping(value = "/atualizarUsuario", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Usuario atualizarUsuario(@RequestBody Usuario u, @AuthenticationPrincipal UserDetails user) {
        Long idRequisicao = us.buscarId(user);
        return us.atualizarUsuario(u, idRequisicao);
    }

    @PreAuthorize("hasRole('PADRAO')")
    @PutMapping(value = "/{id}")
    public Usuario atualizarImagem(@RequestBody Imagem i, @PathVariable("id") Long id) {
        return us.atualizarFoto(id, i);
    }

    @PreAuthorize("hasRole('MODER')")
    @DeleteMapping(value = "/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        us.deletarPeloId(id);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PedidoLogin pl, HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(pl.email(), pl.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = ts.gerarToken((Usuario) auth.getPrincipal());

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(1209600);
        response.addCookie(cookie);

        return ResponseEntity.ok("Logado com sucesso!");
    }

    @PostMapping("/registrar")
    public Usuario register(@RequestBody Usuario u) {
        return us.saveUsuario(u);
    }

    @PostMapping("/requisitar_exclusao")
    public PedidoExclusaoConta requisitarExclusao(@AuthenticationPrincipal UserDetails user) {
        return us.requisitarExclusao(us.buscarId(user));
    }



}
