package com.kings.okdhvi.controllers;

import com.kings.okdhvi.model.DecisaoModeradora;
import com.kings.okdhvi.services.DecisaoModeradoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @RequestMapping("/nova_decisao")
    public String returnPopupNovaDecisao() {return "popupNovaDecisao";}

    @RequestMapping("/decisao")
    public String returnDecisao() {return "popupDecisaoModeradora";}

    @RequestMapping("/publicacao/{id}")
    public String returnPublicacao() {
        return "publicacao";
    }

    @RequestMapping({"/publicacoes/**"})
    public String returnPublicacoesN() {
        return "publicacoes";
    }

    @RequestMapping({"/noticias/**"})
    public String returnNoticias() {
        return "noticias";
    }

    @RequestMapping({"/aplicar_cargo"})
    public String returnAplicarCargo() {return "aplicarPopup";}

    @RequestMapping({"/rte"})
    public String returnRichText() {return "richTextEditor";}

    @RequestMapping("/sobre")
    public String returnSobre() {return "sobre";}

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/sobre/edit")
    public String returnSobreEdit() {return "sobreEdit";}

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/log_com")
    public String returnPopupLogCom() {return "popupLogCom";}

}
