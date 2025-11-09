package com.kings.okdhvi.mapper;

import com.kings.okdhvi.model.DTOs.UsuarioApreDTO;
import com.kings.okdhvi.model.DTOs.UsuarioComDTO;
import com.kings.okdhvi.model.DTOs.UsuarioForDTO;
import com.kings.okdhvi.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class UsuarioMapper {
    public UsuarioApreDTO apresentarUsuario(Usuario u) {
        UsuarioApreDTO uadto = new UsuarioApreDTO();
        uadto.setEdc(u.getEstadoDaConta());
        uadto.setFotoDePerfil(u.getFotoDePerfil());
        uadto.setNome(u.getNome());
        return uadto;
    }

    public UsuarioComDTO usuarioComentador(Usuario u) {
        UsuarioComDTO ucdto = new UsuarioComDTO();
        ucdto.setFoto(u.getFotoDePerfil());
        ucdto.setNome(u.getNome());
        ucdto.setId(u.getIdUsuario());
        return ucdto;
    }

    public UsuarioForDTO usuarioForum(Usuario u) {
        UsuarioForDTO ufdto = new UsuarioForDTO();
        ufdto.setFoto(u.getFotoDePerfil());
        ufdto.setNome(u.getNome());
        ufdto.setId(u.getIdUsuario());
        ufdto.setEstadoDaConta(u.getEstadoDaConta());
        return ufdto;
    }}
