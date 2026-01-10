package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Imagem {
    @Column(length = 128)
    String tituloImagem;

    @Column(length = 512)
    String descricaoImagem;

    @Column(length = 128)
    String donoImagem;

    @Column
    Date dataImagem;

    @Column
    String tipoImagem;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idImagem;

    @Lob
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    byte[] imagem;

    public String getTituloImagem() {
        return tituloImagem;
    }

    public void setTituloImagem(String tituloImagem) {
        this.tituloImagem = tituloImagem;
    }

    public String getDescricaoImagem() {
        return descricaoImagem;
    }

    public void setDescricaoImagem(String descricaoImagem) {
        this.descricaoImagem = descricaoImagem;
    }

    public String getDonoImagem() {
        return donoImagem;
    }

    public void setDonoImagem(String donoImagem) {
        this.donoImagem = donoImagem;
    }

    public Date getDataImagem() {
        return dataImagem;
    }

    public void setDataImagem(Date dataImagem) {
        this.dataImagem = dataImagem;
    }

    public Long getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(Long idImagem) {
        this.idImagem = idImagem;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getTipoImagem() {
        return tipoImagem;
    }

    public void setTipoImagem(String tipoImagem) {
        this.tipoImagem = tipoImagem;
    }
}
