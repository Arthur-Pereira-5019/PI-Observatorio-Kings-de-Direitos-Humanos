package com.kings.okdhvi.controllers;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.infra.security.TokenService;
import com.kings.okdhvi.mapper.UsuarioMapper;
import com.kings.okdhvi.model.DTOs.*;
import com.kings.okdhvi.model.PedidoExclusaoConta;
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
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    UsuarioMapper um;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Usuario encontrarUsuarioLogado(@AuthenticationPrincipal UserDetails ud) {
        Long idBusca = null;
        if(ud != null) {
            idBusca = us.buscarId(ud);
        }
        return us.encontrarPorId(idBusca, true);
    }

    @GetMapping(value = "apresentar", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioApreDTO apresentarUsuarioLogado(@AuthenticationPrincipal UserDetails ud) {
        if(ud == null) {
            throw new NullResourceException("Usuário não logado");
        }
        return um.apresentarUsuario(us.encontrarPorId(us.buscarId(ud), false));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioPDTO encontrarUsuarioPeloId(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails ud) {
        Long idLogado = null;
        if(ud != null) {
            idLogado = us.buscarId(ud);
        }
        return um.usuarioPagina(us.encontrarPorId(id, false), idLogado);
    }

    @PutMapping(value = "/atualizarUsuario", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Usuario atualizarUsuario(@RequestBody UsuarioADTO u, @AuthenticationPrincipal UserDetails user) {
        Long idRequisicao = us.buscarId(user);
        return us.atualizarUsuario(u, idRequisicao);
    }

    @PreAuthorize("hasRole('PADRAO')")
    @PutMapping(value = "/atualizar_imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Usuario atualizarImagem(@RequestPart("meta") CriarImagemMetaRequest meta, @RequestPart(value = "imagem") MultipartFile imagem, @AuthenticationPrincipal UserDetails ud) {
        return us.atualizarFoto(us.buscarId(ud), imagem, meta);
    }

    @PreAuthorize("hasRole('MODER')")
    @DeleteMapping(value = "/{id}")
    public void deletarUsuario(@PathVariable Long id, @AuthenticationPrincipal UserDetails ud) {
        us.delecaoPorAdministrador(id, us.buscarId(ud));
    }

    @PreAuthorize("hasRole('MODER')")
    @PutMapping(value ="/aplicar_cargo/{id}")
    public void atualizarCargo(@AuthenticationPrincipal UserDetails ud, @PathVariable("id") Long id, @RequestBody AdicionarCargoRequest adr) {
        us.alterarTitulacao(id, us.buscarId(ud), adr);
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
        cookie.setMaxAge(pl.lembrar() ? 3628800 : 86400);
        response.addCookie(cookie);

        return ResponseEntity.ok("Logado com sucesso!");
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> register(@RequestBody Usuario u, HttpServletResponse response) {
        String senhaPrevia = u.getSenha();
        us.saveUsuario(u);

        var usernamePassword = new UsernamePasswordAuthenticationToken(u.getEmail(), senhaPrevia);
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = ts.gerarToken((Usuario) auth.getPrincipal());

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(1209600);
        response.addCookie(cookie);

        return ResponseEntity.ok("Registrado com sucesso!");
    }

    @DeleteMapping("/requisitar_exclusao")
    public PedidoExclusaoConta requisitarExclusao(@AuthenticationPrincipal UserDetails ud, @RequestBody ExcluirUsuarioDTO eudto) {
        Usuario u = us.encontrarPorId(us.buscarId(ud),true);
        var usernamePassword = new UsernamePasswordAuthenticationToken(u.getEmail(), eudto.senha());
        this.authenticationManager.authenticate(usernamePassword);

        return us.requisitarExclusao(u, eudto);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetails ud, HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Deslogado com sucesso!");
    }

    @GetMapping("/config")
    public UsuarioADTO getConfigs(@AuthenticationPrincipal UserDetails ud) {
        return us.getConfigs(us.encontrarPorId(us.buscarId(ud),true));
    }



}
