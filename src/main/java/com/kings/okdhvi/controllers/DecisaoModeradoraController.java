package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.DecisaoModeradora;
import com.kings.okdhvi.repositories.DecisaoModeradoraRepository;
import com.kings.okdhvi.services.DecisaoModeradoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/decmod")
public class DecisaoModeradoraController {
    @Autowired
    DecisaoModeradoraService dms;

    @GetMapping("/mock")
    public DecisaoModeradora mock() {
        return dms.mock();
    }

    @PostMapping("/")
    public DecisaoModeradora criarDecisaoModeradora(@RequestBody DecisaoModeradora dm) {
        return dms.criarDecisaoModeradora(dm);
    }

    @GetMapping("/{id}")
    public DecisaoModeradora buscarDecisaoModeradoraPeloId(@PathVariable("id") Long id) {
        return dms.encontrarDecisaoPeloId(id);
    }
}
