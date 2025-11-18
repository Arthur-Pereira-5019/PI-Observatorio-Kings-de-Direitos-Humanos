package com.kings.okdhvi.controllers;

import com.kings.okdhvi.mapper.UsuarioRequestsMapper;
import com.kings.okdhvi.model.DTOs.*;
import com.kings.okdhvi.model.Denuncia;
import com.kings.okdhvi.model.PedidoDeTitulacao;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.services.DenunciaServices;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/denuncia")
public class DenunciaController {

    @Autowired
    DenunciaServices ds;

    @Autowired
    UsuarioRequestsMapper urm;

    @Autowired
    UsuarioService us;

    @PutMapping(value = "/denunciar")
    @PreAuthorize("hasRole('ROLE_PADRAO')")
    public Denuncia criarDenuncia(@RequestBody DenunciaCDTO dto, @AuthenticationPrincipal UserDetails ud) {
        return ds.criarDenuncia(dto, us.buscarId(ud));
    }

    @PostMapping(value="/listar_requisicoes/{texto}/{pagina}")
    public BuscaPaginadaResultado<RequestUsuarioDTO> listarPostagens(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @PathVariable("pagina") Integer pagina, @AuthenticationPrincipal UserDetails ud) {
        List<Denuncia> pedidos;
        List<RequestUsuarioDTO> resultadosDTO = new ArrayList<>();
        BuscaPaginada bp = new BuscaPaginada(pagina, 20, bpt.parametro(), bpt.ascending());
        BuscaPaginadaResultado<Denuncia> bpr = ds.buscaFiltrada(bp, texto, ud);
        pedidos = bpr.getResultado();

        pedidos.forEach(b -> resultadosDTO.add(urm.apresentar(b)));

        BuscaPaginadaResultado<RequestUsuarioDTO> retorno = new BuscaPaginadaResultado<>();
        retorno.setResultado(resultadosDTO);
        retorno.setProximosIndexes(bpr.getProximosIndexes());
        return retorno;
    }

}
