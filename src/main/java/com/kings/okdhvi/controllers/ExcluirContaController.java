package com.kings.okdhvi.controllers;

import com.kings.okdhvi.mapper.UsuarioRequestsMapper;
import com.kings.okdhvi.model.DTOs.BuscaPaginada;
import com.kings.okdhvi.model.DTOs.BuscaPaginadaResultado;
import com.kings.okdhvi.model.DTOs.BuscaPaginadaTexto;
import com.kings.okdhvi.model.DTOs.RequestUsuarioDTO;
import com.kings.okdhvi.model.PedidoDeTitulacao;
import com.kings.okdhvi.model.PedidoExclusaoConta;
import com.kings.okdhvi.services.PedidoExclusaoContaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/exccon")
public class ExcluirContaController {

    @Autowired
    PedidoExclusaoContaServices pecs;

    @Autowired
    UsuarioRequestsMapper urm;

    @PostMapping(value="/listar_requisicoes/{texto}/{pagina}")
    public BuscaPaginadaResultado<RequestUsuarioDTO> listarPostagens(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @PathVariable("pagina") Integer pagina, @AuthenticationPrincipal UserDetails ud) {
        List<PedidoExclusaoConta> pedidos;
        List<RequestUsuarioDTO> resultadosDTO = new ArrayList<>();
        BuscaPaginada bp = new BuscaPaginada(pagina, 20, bpt.parametro(), bpt.ascending());
        BuscaPaginadaResultado<PedidoExclusaoConta> bpr = pecs.buscaFiltrada(bp, texto, ud);
        pedidos = bpr.getResultado();

        pedidos.forEach(b -> resultadosDTO.add(urm.apresentar(b)));

        BuscaPaginadaResultado<RequestUsuarioDTO> retorno = new BuscaPaginadaResultado<>();
        retorno.setResultado(resultadosDTO);
        retorno.setProximosIndexes(bpr.getProximosIndexes());
        return retorno;
    }
}
