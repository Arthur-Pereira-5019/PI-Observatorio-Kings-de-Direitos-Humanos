package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.Apoio;
import com.kings.okdhvi.model.DTOs.ApoioCDTO;
import com.kings.okdhvi.services.ApoioService;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apoio")
public class ApoioController {
    @Autowired
    ApoioService as;

    @Autowired
    UsuarioService us;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public Apoio criarApoio(@RequestBody ApoioCDTO a, @AuthenticationPrincipal UserDetails ud) {
        return as.criarNovoApoio(a, us.encontrarPorId(us.buscarId(ud), false));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public Apoio atualizarApoio(@RequestBody ApoioCDTO a, @PathVariable("id") Long id, @AuthenticationPrincipal UserDetails ud) {
        return as.atualizarApoio(a,id,us.encontrarPorId(us.buscarId(ud), false));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public Apoio encontrarApoio(Long id) {
        return as.encontrarApoio(id);
    }

    @GetMapping("/")
    public List<Apoio> todosApoios() {
        return as.encontrarTodosApoios();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void excluirApoio(@PathVariable("id") Long id) {
        as.deletarApoio(id);
    }
}
