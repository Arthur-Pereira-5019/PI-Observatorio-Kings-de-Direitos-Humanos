package com.kings.okdhvi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kings.okdhvi.model.Imagem;
import com.kings.okdhvi.model.Usuario;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PostagemPaginaDTO{
    Long id;

    String tituloPostagem;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    Date dataDaPostagem;

    UsuarioComDTO autor;

    String textoPostagem;

    private Imagem capa;

    public String getTituloPostagem() {
        return tituloPostagem;
    }

    public void setTituloPostagem(String tituloPostagem) {
        this.tituloPostagem = tituloPostagem;
    }

    public Date getDataDaPostagem() {
        return dataDaPostagem;
    }

    public void setDataDaPostagem(Date dataDaPostagem) {
        this.dataDaPostagem = dataDaPostagem;
    }

    public String getTextoPostagem() {
        return textoPostagem;
    }

    public void setTextoPostagem(String textoPostagem) {
        this.textoPostagem = textoPostagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Imagem getCapa() {
        return capa;
    }

    public void setCapa(Imagem capa) {
        this.capa = capa;
    }

    public UsuarioComDTO getAutor() {
        return autor;
    }

    public void setAutor(UsuarioComDTO autor) {
        this.autor = autor;
    }

    public PostagemPaginaDTO() {
    }

    public PostagemPaginaDTO(Long id, String tituloPostagem, Date dataDaPostagem, UsuarioComDTO autor, String textoPostagem, Imagem capa) {
        this.id = id;
        this.tituloPostagem = tituloPostagem;
        this.dataDaPostagem = dataDaPostagem;
        this.autor = autor;
        this.textoPostagem = textoPostagem;
        this.capa = capa;
    }
}
