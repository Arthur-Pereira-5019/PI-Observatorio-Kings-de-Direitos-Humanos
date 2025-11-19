package com.kings.okdhvi.mapper;

import com.kings.okdhvi.model.DTOs.RequestUsuarioDTO;
import com.kings.okdhvi.model.Denuncia;
import com.kings.okdhvi.model.PedidoDeTitulacao;
import com.kings.okdhvi.model.PedidoExclusaoConta;
import com.kings.okdhvi.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioRequestsMapper {
    public RequestUsuarioDTO apresentar(PedidoDeTitulacao pdt) {
        RequestUsuarioDTO r = new RequestUsuarioDTO();
        r.setData(pdt.getDataPedido());
        r.setInfo("Contato: " + pdt.getContato());
        r.setTexto("Motivação: " + pdt.getMotivacao());
        r.setIdRequisitor(getIdUsuario(pdt.getRequisitor()));
        r.setNomeRequisitor("[" + r.getIdRequisitor() + "] " + getNomeUsuario(pdt.getRequisitor()) + " quer virar " + pdt.getCargoRequisitado());
        return r;
    }

    public RequestUsuarioDTO apresentar(PedidoExclusaoConta pec) {
        RequestUsuarioDTO r = new RequestUsuarioDTO();
        r.setData(pec.getDataPedido());
        r.setInfo("");
        r.setTexto("");
        r.setIdRequisitor(getIdUsuario(pec.getUsuarioPedido()));
        r.setNomeRequisitor("[" + r.getIdRequisitor() + "] " + getNomeUsuario(pec.getUsuarioPedido()));
        return r;
    }

    public RequestUsuarioDTO apresentar(Denuncia d) {
        RequestUsuarioDTO r = new RequestUsuarioDTO();
        r.setData(d.getDataDenuncia());
        r.setInfo(d.getNomeModerado());
        r.setIdExtra(d.getIdDenunciado());
        r.setBaseExtra(d.getTipoDenunciado());
        r.setIdRequisitor(getIdUsuario(d.getRequisitor()));
        r.setNomeRequisitor("[" + r.getIdRequisitor() + "] " + getNomeUsuario(d.getRequisitor()));
        r.setTexto("Motivação: " + d.getMotivacao());
        if(d.getTipoDenunciado().equals("Usuario")) {
            r.setBaseExtra("usuario/");
        } else if (d.getTipoDenunciado().equals("Postagem")) {
            r.setBaseExtra("publicacao/");
        } else if (d.getTipoDenunciado().equals("Forum")) {
            r.setBaseExtra("forum/");
        } else if(d.getTipoDenunciado().equals("Comentario")) {
            r.setTexto(r.getTexto() + "\nComentário Denunciado: "+d.getAnexoDenuncia());
            r.setBaseExtra(d.getTipoDonoPagina() == 'P' ? "publicacao/" : "forum/");
            r.setIdExtra(d.getIdDonoPagina());
        }
        return r;
    }

    public Long getIdUsuario(Usuario u) {
        if(u == null) {
            return 0L;
        }
        return u.getIdUsuario();
    }

    public String getNomeUsuario(Usuario u) {
        if(u == null) {
            return "Desconhecido";
        }
        return u.getNome();
    }

}
