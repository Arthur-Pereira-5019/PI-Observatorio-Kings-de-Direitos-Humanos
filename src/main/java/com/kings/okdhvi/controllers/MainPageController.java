package com.kings.okdhvi.controllers;

import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainPageController {

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

}
