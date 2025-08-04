package com.kings.okdhvi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Greeting {

    @RequestMapping("/A")
    private String open() {
        return "Hello World";
    }

}
