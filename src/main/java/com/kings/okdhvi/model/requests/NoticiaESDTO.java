package com.kings.okdhvi.model.requests;

import com.kings.okdhvi.model.Imagem;

public class NoticiaESDTO extends PostagemESDTO{
    String nomeAutor;
    boolean externa;

    public NoticiaESDTO(Long idPostagem, String titulo, Imagem capa, String nomeAutor, boolean externa) {
        super(idPostagem, titulo, capa);
        this.nomeAutor = nomeAutor;
        this.externa = externa;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public boolean isExterna() {
        return externa;
    }

    public void setExterna(boolean externa) {
        this.externa = externa;
    }
}
