package com.kings.okdhvi.controllers;

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

    @PreAuthorize("hasRole('MODER')")
    @GetMapping("/mock")
    public DecisaoModeradora mock() {
        return dms.mock();
    }

    @PreAuthorize("hasRole('MODER')")
    @GetMapping("{tipo}"+"/{id}")
    public DecisaoModeradora buscarDecisaoModeradoraPeloId(@PathVariable("id") Long id, @PathVariable("tipo") String c) {
        return dms.encontrarDecisaoPeloId(id, c);
    }
}
