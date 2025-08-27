package com.kings.okdhvi.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Imagem {
    @Column
    String tituloImagem;

    @Column(length = 256)
    String decricaoImagem;

    @Column(length = 128)
    String donoImagem;


    @Column
    Date dataImagem;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idImagem;

    @Lob
    byte[] image;

    public String getTituloImagem() {
        return tituloImagem;
    }

    public void setTituloImagem(String tituloImagem) {
        this.tituloImagem = tituloImagem;
    }

    public String getDecricaoImagem() {
        return decricaoImagem;
    }

    public void setDecricaoImagem(String decricaoImagem) {
        this.decricaoImagem = decricaoImagem;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
