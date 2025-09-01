package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.DecisaoModeradora;
import com.kings.okdhvi.model.EstadoDaConta;
import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.model.requests.PedidoLogin;
import com.kings.okdhvi.model.requests.RetornoLogin;
import com.kings.okdhvi.repositories.DecisaoModeradoraRepository;
import com.kings.okdhvi.services.DecisaoModeradoraService;
import com.kings.okdhvi.services.EstadoDaContaServices;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

    @CrossOrigin(origins="")
    @RestController
    @RequestMapping("/api/estdacon")
    public class EstadoDaContaController {

        @Autowired
        EstadoDaContaServices edcs;
        @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        public EstadoDaConta criarEstadoDaConta(@RequestBody EstadoDaConta edc) {
            return edcs.criarEstadoDaConta(edc);
        }

        @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public EstadoDaConta encontrarEstadoDaContaPeloId(@PathVariable("id") Long id) {
            return edcs.encontrarEstadoDaContaPeloId(id);
        }

        @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
        public EstadoDaConta atualizarEstadoDaConta(@RequestBody EstadoDaConta edc) {
            return edcs.atualizarEstadoDaConta(edc);
        }

        @DeleteMapping(value = "/{id}")
        public void deletarEstadoDaConta(@PathVariable Long id) {
            edcs.deletarEstadoDaContaPeloId(id);
        }

}
