package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Comentario {

    @ManyToOne
    Usuario autor;

    @Column(length = 512)
    String textComentario;

    @Column
    Date dataComentario;

    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    Long idComentario;

    @Column
    boolean oculto;

    @Column
    Character tipo;

    Long idDono;

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getTextComentario() {
        return textComentario;
    }

    public void setTextComentario(String textComentario) {
        this.textComentario = textComentario;
    }

    public Date getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(Date dataComentario) {
        this.dataComentario = dataComentario;
    }

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public Long getIdDono() {
        return idDono;
    }

    public void setIdDono(Long idDono) {
        this.idDono = idDono;
    }
}
