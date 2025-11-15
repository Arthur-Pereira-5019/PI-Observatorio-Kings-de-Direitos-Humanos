package com.kings.okdhvi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

public class DecisaoModeradoraPADTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date data;

    private String motivacao;

    private String nomeModerador;

    private Long idModerador;


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

    public DecisaoModeradoraPADTO() {
    }

    public String getNomeModerador() {
        return nomeModerador;
    }

    public void setNomeModerador(String nomeModerador) {
        this.nomeModerador = nomeModerador;
    }

    public Long getIdModerador() {
        return idModerador;
    }

    public void setIdModerador(Long idModerador) {
        this.idModerador = idModerador;
    }
}
