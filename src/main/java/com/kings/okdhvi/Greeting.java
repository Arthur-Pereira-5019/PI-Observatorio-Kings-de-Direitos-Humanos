package com.kings.okdhvi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Greeting {

    @GetMapping("/login_screen")
    public String mainScreen() {
        return "login.html";
    }


}
