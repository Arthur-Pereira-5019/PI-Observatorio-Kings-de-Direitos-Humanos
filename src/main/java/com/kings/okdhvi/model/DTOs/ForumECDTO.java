package com.kings.okdhvi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kings.okdhvi.model.Imagem;

import java.util.Date;

public class ForumECDTO extends ForumESDTO{
    private String textoForum;

    public String getTextoForum() {
        return textoForum;
    }

    public void setTextoForum(String textoForum) {
        this.textoForum = textoForum;
    }

    public ForumECDTO() {
    }

    public ForumECDTO(Long idForum, String titulo, long respostas, Date ultimaAtualizacao, Date dataCriacao, UsuarioForDTO autor, boolean oculto, String textoForum) {
        super(idForum, titulo, respostas, ultimaAtualizacao, dataCriacao, autor, oculto);
        this.textoForum = textoForum;
    }
}
