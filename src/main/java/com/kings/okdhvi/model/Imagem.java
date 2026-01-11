package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Imagem {
    @Column(length = 128)
    private String tituloImagem;

    @Column(length = 512)
    private String descricaoImagem;

    @Column(length = 128)
    private String donoImagem;

    @Column
    private Date dataImagem;

    @Column
    private String tipoImagem;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImagem;

    @Column
    private String caminho;

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

    public String getTipoImagem() {
        return tipoImagem;
    }

    public void setTipoImagem(String tipoImagem) {
        this.tipoImagem = tipoImagem;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
