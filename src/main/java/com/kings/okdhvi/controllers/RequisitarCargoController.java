package com.kings.okdhvi.controllers;

import com.kings.okdhvi.mapper.UsuarioRequestsMapper;
import com.kings.okdhvi.model.DTOs.*;
import com.kings.okdhvi.model.PedidoDeTitulacao;
import com.kings.okdhvi.model.PedidoDeTitulacaoDTO;
import com.kings.okdhvi.model.PedidoExclusaoConta;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.services.PedidoDeTitulacaoServices;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reqcar")
public class RequisitarCargoController {

    @Autowired
    UsuarioService us;

    @Autowired
    PedidoDeTitulacaoServices pdts;

    @Autowired
    UsuarioRequestsMapper urm;

    @PreAuthorize("hasRole('PADRAO')")
    @PostMapping(value="/requisitar_cargo")
    public void requisitarCargo(@AuthenticationPrincipal UserDetails ud, @RequestBody PedidoDeTitulacaoDTO pdtDTO) {
        us.gerarPedidoDeTitulacao(us.buscarId(ud), pdtDTO);
    }

    @PreAuthorize("hasRole('PADRAO')")
    @GetMapping(value="")
    public PedidoDeTitulacao encontrar(@AuthenticationPrincipal UserDetails ud) {
        return pdts.encontrarPedidoPeloUsuario(us.encontrarPorId(us.buscarId(ud), false));
    }

    @PostMapping(value="/listar_requisicoes/{texto}/{pagina}")
    public BuscaPaginadaResultado<RequestUsuarioDTO> listarPostagens(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @PathVariable("pagina") Integer pagina, @AuthenticationPrincipal UserDetails ud) {
        List<PedidoDeTitulacao> pedidos;
        List<RequestUsuarioDTO> resultadosDTO = new ArrayList<>();
        BuscaPaginada bp = new BuscaPaginada(pagina, 20, bpt.parametro(), bpt.ascending());
        BuscaPaginadaResultado<PedidoDeTitulacao> bpr = pdts.buscaFiltrada(bp, texto, ud);
        pedidos = bpr.getResultado();

        pedidos.forEach(b -> resultadosDTO.add(urm.apresentar(b)));

        BuscaPaginadaResultado<RequestUsuarioDTO> retorno = new BuscaPaginadaResultado<>();
        retorno.setResultado(resultadosDTO);
        retorno.setProximosIndexes(bpr.getProximosIndexes());
        return retorno;
    }
}
