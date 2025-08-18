package com.kings.okdhvi;

import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.repositories.UsuarioRepository;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class MainPageController {

    @Autowired
    UsuarioService us;

    @RequestMapping("/")
    public String mainScreen() {
        return "mainscreen.html";
    }

    /*
    User ModelAndView("nome","mensagem")
    Aprender as tags
    View resolver

     */


}
