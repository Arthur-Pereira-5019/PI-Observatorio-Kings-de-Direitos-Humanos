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

    @GetMapping("/popupRegistro")
    public String popupRegistro() {
        return "popupRegistro";
    }

    @GetMapping("/popupLogin")
    public String popupLogin() {
        return "popupLogin";
    }

    @GetMapping("/imagem/{id}")
    public String imageView() {
        return "imagem";
    }

};
