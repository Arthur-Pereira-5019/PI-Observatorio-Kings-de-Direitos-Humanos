package com.kings.okdhvi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ComentarioLogDTO {
    private String texto;

    private Long id;

    private String tituloDono;

    private Long idDono;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date dataComentario;

    private Character tipo;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloDono() {
        return tituloDono;
    }

    public void setTituloDono(String tituloDono) {
        this.tituloDono = tituloDono;
    }

    public Date getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(Date dataComentario) {
        this.dataComentario = dataComentario;
    }

    public Long getIdDono() {
        return idDono;
    }

    public void setIdDono(Long idDono) {
        this.idDono = idDono;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }
}
