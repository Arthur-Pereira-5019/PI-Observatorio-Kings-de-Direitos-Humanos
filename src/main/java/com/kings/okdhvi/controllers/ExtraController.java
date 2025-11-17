package com.kings.okdhvi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExtraController {
    @GetMapping("/.well-known/appspecific/com.chrome.devtools.json")
    public ResponseEntity<Void> ignoreChromeRequest() {
        return ResponseEntity.noContent().build();
    }
}
