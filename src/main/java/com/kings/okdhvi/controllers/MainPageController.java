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

    @GetMapping("login")
    public String login() {
        return "telaLogin";
    }

}
