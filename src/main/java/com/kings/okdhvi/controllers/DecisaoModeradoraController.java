package com.kings.okdhvi.controllers;

import com.kings.okdhvi.mapper.DecisaoModeradoraMapper;
import com.kings.okdhvi.model.DTOs.*;
import com.kings.okdhvi.model.DecisaoModeradora;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.repositories.DecisaoModeradoraRepository;
import com.kings.okdhvi.services.DecisaoModeradoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins="")
@RestController()
@RequestMapping("/api/decmod")
public class DecisaoModeradoraController {
    @Autowired
    DecisaoModeradoraService dms;

    @Autowired
    DecisaoModeradoraMapper dmm;


    @PreAuthorize("hasRole('MODER')")
    @GetMapping("{tipo}"+"/{id}")
    public DecisaoModeradoraPADTO buscarDecisaoModeradoraPeloId(@PathVariable("id") Long id, @PathVariable("tipo") String c) {
        return dmm.paginaAtual(dms.encontrarDecisaoPeloId(id, c));
    }

    @PreAuthorize("hasRole('MODER')")
    @PostMapping(value="/listar/{texto}/{pagina}")
    public BuscaPaginadaResultado<DecisaoModeradora> listarPostagens(@RequestBody BuscaPaginadaTexto bpt, @PathVariable("texto") String texto, @PathVariable("pagina") Integer pagina, @AuthenticationPrincipal UserDetails ud) {
        BuscaPaginada bp = new BuscaPaginada(pagina, 25, bpt.parametro(), bpt.ascending());
        return dms.buscaFiltrada(bp, texto, ud);
    }
}
