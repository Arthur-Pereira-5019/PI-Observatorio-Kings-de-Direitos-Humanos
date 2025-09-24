package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.DecisaoModeradora;
import com.kings.okdhvi.services.DecisaoModeradoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


//Controler secundário apenas para gerir testes quando o controller principal estiver em uso em outra branch
@Controller
public class TestController {
    @RequestMapping("/pagina_banida/{id}")
    public String decisaoModeradora(@PathVariable("id") String id) {
        return "popupDecisaoModeradora";
    }

    @RequestMapping("/telaInexistente")
    public String returnTelaInexistente() {
        return "telaInexistente";
    }

    @RequestMapping("/nova_imagem")
    public String returnPopupNovaImagem() {
        return "popupNovaImagem";
    }

    @RequestMapping("/nova_publicacao")
    public String returnNovaPublicacao() {
        return "criacaoPublicacao";
    }

}
