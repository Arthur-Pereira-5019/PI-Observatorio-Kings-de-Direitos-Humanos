package com.kings.okdhvi.model.DTOs;

public class ComentarioDTO {

    UsuarioComDTO autor;

    String texto;

    Long id;

    boolean proprio;

    public UsuarioComDTO getAutor() {
        return autor;
    }

    public void setAutor(UsuarioComDTO autor) {
        this.autor = autor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isProprio() {
        return proprio;
    }

    public void setProprio(boolean proprio) {
        this.proprio = proprio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
