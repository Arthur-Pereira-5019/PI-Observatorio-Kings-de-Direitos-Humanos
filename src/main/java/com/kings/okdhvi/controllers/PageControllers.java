package com.kings.okdhvi.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageControllers {

    @GetMapping("/")
    public String mainScreen() {
        return "telaInicial";
    }

    @GetMapping("/rodape")
    public String rodape() {
        return "rodape";
    }

    @GetMapping("/cabecalho")
    public String cabecalho() {
        return "cabecalho";
    }

    @GetMapping("login")
    public String login() {
        return "telaLogin";
    }

    @GetMapping("/popupRegistro")
    public String popupRegistro() {
        return "popupRegistro";
    }

    @GetMapping("/popupLogin")
    public String popupLogin() {
        return "popupLogin";
    }

    @GetMapping("/popupEditarPerfil")
    public String popupEditarPerfil() {
        return "configuracaoUsuarioPopup";
    }

    @GetMapping("/popupDeleteUser")
    public String popupDeleteUser() {
        return "deleteUsuarioPopup";
    }

    @GetMapping("/popupRequisitar")
    public String popupRequisitar() {return "requisitarPopup"; }

    @GetMapping("/imagem/{id}")
    public String imageView() {
        return "imagem";
    }

    @GetMapping("/foruns/**")
    public String telaForuns() {return "telaForuns"; }

    @GetMapping("/novo_forum")
    public String novoForum() {return "novoForum"; }

    @GetMapping("/forum/**")
    public String forum() {return "forum"; }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usuario/{id}")
    public String telaUsuario() {
        return "telaUsuario";
    }

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

    @PreAuthorize("hasRole('ROLE_ESPEC')")
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

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/popupNovaDenuncia")
    public String returnpopupNovaDenuncia() {return "popupNovaDenuncia";}

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/sobre/edit")
    public String returnSobreEdit() {return "sobreEdit";}

    @PreAuthorize("hasRole('ROLE_MODER')")
    @RequestMapping("/log_com")
    public String returnPopupLogCom() {return "popupLogCom";}

    @PreAuthorize("hasRole('ROLE_MODER')")
    @RequestMapping("/registro/**")
    public String returnRegistro() {return "decisoesModeradoras";}

    @PreAuthorize("hasRole('ROLE_MODER')")
    @RequestMapping("/requisicoes/**")
    public String returnRequisicoes() {return "requisicoesUsuarios";}

};
