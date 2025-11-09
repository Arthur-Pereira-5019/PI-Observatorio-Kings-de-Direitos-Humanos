package com.kings.okdhvi.controllers;

import com.kings.okdhvi.mapper.DecisaoModeradoraMapper;
import com.kings.okdhvi.model.DTOs.DecisaoModeradoraPADTO;
import com.kings.okdhvi.model.DecisaoModeradora;
import com.kings.okdhvi.repositories.DecisaoModeradoraRepository;
import com.kings.okdhvi.services.DecisaoModeradoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
