package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.Local;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UtilityController {

    @PreAuthorize("isAuthenticated()")
    public List<String> getLocais() {
        Local[] l = Local.values();
        ArrayList<String> retorno = new ArrayList<>();
        Arrays.asList(l).forEach(a -> {retorno.add(a.name());});
        return retorno;
    }
}
