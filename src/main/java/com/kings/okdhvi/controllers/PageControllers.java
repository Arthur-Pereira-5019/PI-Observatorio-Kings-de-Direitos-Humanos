package com.kings.okdhvi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageControllers {

    @GetMapping("/")
    public String mainScreen() {
        return "telaInicial";
    }

    @GetMapping("/rodape")
    public String rodape() {
        return "rodape";
    }

    @GetMapping("/cabecalho")
    public String cabecalho() {
        return "cabecalho";
    }

    @GetMapping("login")
    public String login() {
        return "telaLogin";
    }

    @GetMapping("/**")
    public String telaInexistente() {
        return "telaInexistente";
    }


}
