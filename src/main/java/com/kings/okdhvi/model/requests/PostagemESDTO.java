package com.kings.okdhvi.model.requests;

import com.kings.okdhvi.model.Imagem;

public class PostagemESDTO {
    Long idPostagem;

    String titulo;

    Imagem capa;

    public Long getIdPostagem() {
        return idPostagem;
    }

    public void setIdPostagem(Long idPostagem) {
        this.idPostagem = idPostagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Imagem getCapa() {
        return capa;
    }

    public void setCapa(Imagem capa) {
        this.capa = capa;
    }

    public PostagemESDTO() {
    }

    public PostagemESDTO(Long idPostagem, String titulo, Imagem capa) {
        this.idPostagem = idPostagem;
        this.titulo = titulo;
        this.capa = capa;
    }
}
