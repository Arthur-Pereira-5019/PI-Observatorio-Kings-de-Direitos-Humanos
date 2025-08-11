package com.kings.okdhvi;

import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class MainPageController {

    @Autowired
    UsuarioRepository ur;

        @RequestMapping("/")
        public String mainScreen() {

            return "mainscreen.html";

        }

        @RequestMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE)
        public Usuario login() {
            Usuario u = mockUsuario();
            return ur.save(u);
        }

        public Usuario mockUsuario() {
            Usuario u = new Usuario();
            u.setCpf("13178339965");
            u.setNome("A");
            u.setDataDeNascimento(new Date(31/03/2008));
            u.seteMail("Blabla@");
            u.setOculto(false);
            u.setSenha("AAAAAAAAAAAA");
            u.setTelefone("9243112");
            return u;
        }


}
