package com.kings.okdhvi.model.requests;

import com.kings.okdhvi.model.Imagem;

public class PostagemECDTO extends PostagemESDTO{

    String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public PostagemECDTO() {
    }

    public PostagemECDTO(Long idPostagem, String titulo, Imagem capa, String texto) {
        super(idPostagem, titulo, capa);
        this.texto = texto;
    }
}
