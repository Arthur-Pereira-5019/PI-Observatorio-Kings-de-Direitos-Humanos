package com.kings.okdhvi;

import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class MainPageController {

    String emailRegex = ".{3,64}\\@.{4,255}";
    String passwordRegex = ".{8,64}";
    @Autowired
    UsuarioRepository ur;

        @RequestMapping("/")
        public String mainScreen() {
            return "mainscreen.html";
        }

    @RequestMapping("/login_screen/login")
    //Recebe os parâmetros de login e os passa para variáveis String de mesmo nome
    public String login(@RequestParam("email") String email, @RequestParam("passw") String passw) {
        if(verifyEmail(email) && verifyPassword(passw)) {
            return"Logado";
        }
        return "Banido";
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


    public boolean verifyEmail(String email) {
        return email.matches(emailRegex);
    }

    public boolean verifyPassword(String passw) {
        return passw.matches(passwordRegex);
    }


}
