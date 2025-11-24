package com.kings.okdhvi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kings.okdhvi.model.Imagem;

import java.util.Date;

public class PostagemECDTO extends PostagemESDTO{

    private String texto;
    private String autor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    Date data;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public PostagemECDTO() {
    }

    public PostagemECDTO(Long idPostagem, String titulo, Imagem capa, String texto, String autor, Date data) {
        super(idPostagem, titulo, capa);
        this.texto = texto;
        this.autor = autor;
        this.data = data;
    }
}
