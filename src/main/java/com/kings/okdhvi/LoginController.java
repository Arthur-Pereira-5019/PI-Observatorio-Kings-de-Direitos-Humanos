package com.kings.okdhvi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    String emailRegex = ".{3,64}\\@.{4,255}";
    String passwordRegex = ".{8,64}";


    @RequestMapping("/login_screen/login")
    //Recebe os parâmetros de login e os passa para variáveis String de mesmo nome
    public String login(@RequestParam("email") String email, @RequestParam("passw") String passw) {
        if(verifyEmail(email) && verifyPassword(passw)) {
            return"Logado";
        }
        return "Banido";
    }

    //[a-zA-Z0-9]{3,64}\@[a-zA-Z0-9]{4,64}\.com
    //.{3,64}\@.{4,255}
    //.{3,64}\@.{4,255}\.com
    public boolean verifyEmail(String email) {
        return email.matches(emailRegex);
    }

    public boolean verifyPassword(String passw) {
        return passw.matches(passwordRegex);
    }



}
