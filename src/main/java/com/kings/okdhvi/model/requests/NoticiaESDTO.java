package com.kings.okdhvi.model.requests;

import com.kings.okdhvi.model.Imagem;

public class NoticiaESDTO extends PostagemESDTO{
    String nomeAutor;

    public NoticiaESDTO(Long idPostagem, String titulo, Imagem capa, String nomeAutor) {
        super(idPostagem, titulo, capa);
        this.nomeAutor = nomeAutor;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }
}
