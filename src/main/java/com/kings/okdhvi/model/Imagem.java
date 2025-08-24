package com.kings.okdhvi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

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

    @Column
    Long idImagem;

    @Lob
    Byte[] image;

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

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }
}
