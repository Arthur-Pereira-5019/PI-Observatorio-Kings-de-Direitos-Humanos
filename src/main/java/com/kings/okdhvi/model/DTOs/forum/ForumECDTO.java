package com.kings.okdhvi.model.DTOs.forum;

import com.kings.okdhvi.model.DTOs.UsuarioForDTO;

import java.time.ZonedDateTime;
import java.util.Date;

public class ForumECDTO extends ForumESDTO{
    private String textoForum;

    private boolean arquivado;

    public boolean dono;

    public String getTextoForum() {
        return textoForum;
    }

    public void setTextoForum(String textoForum) {
        this.textoForum = textoForum;
    }



    public ForumECDTO() {
    }

    public ForumECDTO(Long idForum, String titulo, long respostas, ZonedDateTime ultimaAtualizacao, ZonedDateTime dataCriacao, UsuarioForDTO autor, boolean oculto, String textoForum, boolean dono, boolean arquivado) {
        super(idForum, titulo, respostas, ultimaAtualizacao, dataCriacao, autor, oculto);
        this.textoForum = textoForum;
        this.dono = dono;
        this.arquivado = arquivado;
    }

    public boolean isArquivado() {
        return arquivado;
    }

    public void setArquivado(boolean arquivado) {
        this.arquivado = arquivado;
    }
}
