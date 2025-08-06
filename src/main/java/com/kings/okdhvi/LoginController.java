package com.kings.okdhvi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @RequestMapping("/login_screen/login")


    //Recebe os parâmetros de login e os passa para variáveis String de mesmo nome
    public String login(@RequestParam("email") String email, @RequestParam("passw") String passw) {
        return email + " " + passw;
    }

    //[a-zA-Z0-9]{3,64}\@[a-zA-Z0-9]{4,64}\.com
    public String verifyEmail() {
        return "";
    }



}
