package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.PedidoDeTitulacao;
import com.kings.okdhvi.model.PedidoDeTitulacaoDTO;
import com.kings.okdhvi.services.DecisaoModeradoraService;
import com.kings.okdhvi.services.PedidoDeTitulacaoServices;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reqcar")
public class RequisitarCargoController {

    @Autowired
    UsuarioService us;

    @Autowired
    PedidoDeTitulacaoServices pdts;

    @PostMapping(value="/requisitar_cargo")
    public void requisitarCargo(@AuthenticationPrincipal UserDetails ud, @RequestBody PedidoDeTitulacaoDTO pdtDTO) {
        us.gerarPedidoDeTitulacao(us.buscarId(ud), pdtDTO);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value="")
    public PedidoDeTitulacao encontrar(@AuthenticationPrincipal UserDetails ud) {
        return pdts.encontrarPedidoPeloUsuario(us.encontrarPorId(us.buscarId(ud), false));
    }
}
