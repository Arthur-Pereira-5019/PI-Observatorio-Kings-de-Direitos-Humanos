package com.kings.okdhvi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

public class DecisaoModeradoraECDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date data;

    private String motivacao;
    private String nomeModerado;
    private String nomeModerador;
    private Long idModerado;
    private Long idModerador;
    private String linkEspaco;
    private String nomeEspaco;
    private String acao;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public String getNomeModerado() {
        return nomeModerado;
    }

    public void setNomeModerado(String nomeModerado) {
        this.nomeModerado = nomeModerado;
    }

    public String getNomeModerador() {
        return nomeModerador;
    }

    public void setNomeModerador(String nomeModerador) {
        this.nomeModerador = nomeModerador;
    }

    public Long getIdModerado() {
        return idModerado;
    }

    public void setIdModerado(Long idModerado) {
        this.idModerado = idModerado;
    }

    public Long getIdModerador() {
        return idModerador;
    }

    public void setIdModerador(Long idModerador) {
        this.idModerador = idModerador;
    }

    public String getLinkEspaco() {
        return linkEspaco;
    }

    public void setLinkEspaco(String linkEspaco) {
        this.linkEspaco = linkEspaco;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getNomeEspaco() {
        return nomeEspaco;
    }

    public void setNomeEspaco(String nomeEspaco) {
        this.nomeEspaco = nomeEspaco;
    }
}
