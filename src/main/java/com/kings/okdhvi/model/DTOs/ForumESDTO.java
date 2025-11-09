package com.kings.okdhvi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kings.okdhvi.model.Imagem;

import java.util.Date;

public class ForumESDTO {
    private Long idForum;
    private String titulo;
    private long respostas;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date ultimaAtualizacao;
    private Date dataCriacao;
    private UsuarioForDTO autor;
    private boolean oculto;

    public Long getIdForum() {
        return idForum;
    }

    public void setIdForum(Long idForum) {
        this.idForum = idForum;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getRespostas() {
        return respostas;
    }

    public void setRespostas(long respostas) {
        this.respostas = respostas;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public UsuarioForDTO getAutor() {
        return autor;
    }

    public void setAutor(UsuarioForDTO autor) {
        this.autor = autor;
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

    public ForumESDTO() {
    }

    public ForumESDTO(Long idForum, String titulo, long respostas, Date ultimaAtualizacao, Date dataCriacao, UsuarioForDTO autor, boolean oculto) {
        this.idForum = idForum;
        this.titulo = titulo;
        this.respostas = respostas;
        this.ultimaAtualizacao = ultimaAtualizacao;
        this.dataCriacao = dataCriacao;
        this.autor = autor;
        this.oculto = oculto;
    }
}
