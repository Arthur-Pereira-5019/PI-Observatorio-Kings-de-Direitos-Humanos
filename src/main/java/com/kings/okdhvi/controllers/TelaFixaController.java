package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.TelaFixa;
import com.kings.okdhvi.services.TelaFixaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController("/api/tf")
public class TelaFixaController {

    @Autowired
    TelaFixaServices tfs;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/criar")
    public TelaFixa salvarTelaFixa(@RequestBody TelaFixa tf, @AuthenticationPrincipal UserDetails ud) {
        return tfs.saveTela(tf);
    }

    @GetMapping("/{id}")
    public TelaFixa lerTelaFixa(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails ud) {
        return tfs.lerTela(id);
    }
}
