package com.kings.okdhvi.mapper;

import com.kings.okdhvi.model.DTOs.UsuarioApreDTO;
import com.kings.okdhvi.model.DTOs.UsuarioComDTO;
import com.kings.okdhvi.model.DTOs.UsuarioForDTO;
import com.kings.okdhvi.model.DTOs.UsuarioPDTO;
import com.kings.okdhvi.model.EstadoDaConta;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UsuarioMapper {
    @Autowired
    UsuarioService us;

    public UsuarioApreDTO apresentarUsuario(Usuario u) {
        UsuarioApreDTO uadto = new UsuarioApreDTO();
        uadto.setEdc(u.getEstadoDaConta());
        uadto.setFotoDePerfil(u.getFotoDePerfil());
        uadto.setNome(u.getNome());
        return uadto;
    }

    public UsuarioComDTO usuarioComentador(Usuario u) {
        if(u == null) {
            return new UsuarioComDTO(0L, "Desconhecido", null);
        }
        UsuarioComDTO ucdto = new UsuarioComDTO();
        ucdto.setFoto(u.getFotoDePerfil());
        ucdto.setNome(u.getNome());
        ucdto.setId(u.getIdUsuario());
        return ucdto;
    }

    public UsuarioForDTO usuarioForum(Usuario u) {
        if(u == null) {
            return new UsuarioForDTO(0L, "Desconhecido", null, EstadoDaConta.SUSPENSO);
        }
        UsuarioForDTO ufdto = new UsuarioForDTO();
        ufdto.setFoto(u.getFotoDePerfil());
        ufdto.setNome(u.getNome());
        ufdto.setId(u.getIdUsuario());
        ufdto.setEstadoDaConta(u.getEstadoDaConta());
        return ufdto;
    }

    public UsuarioPDTO usuarioPagina(Usuario u, Long idRequisitor) {
        Usuario requisitor = us.encontrarPorId(idRequisitor, false);
        UsuarioPDTO updto = new UsuarioPDTO();
        updto.setFotoDePerfil(u.getFotoDePerfil());
        updto.setNome(u.getNome());
        updto.setIdUsuario(u.getIdUsuario());
        updto.setEstadoDaConta(u.getEstadoDaConta());
        updto.setEmail(u.getEmail());
        if(idRequisitor.equals(u.getIdUsuario())) {
            if(requisitor.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODER"))) {
                updto.setProprio(3);
            } else {
                updto.setProprio(1);
            }
        } else {
            if(requisitor.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODER"))) {
                updto.setProprio(2);
            } else {
                updto.setProprio(0);
            }
        }
        return updto;
    }


}

