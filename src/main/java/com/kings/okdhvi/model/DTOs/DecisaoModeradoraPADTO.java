package com.kings.okdhvi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

public class DecisaoModeradoraPADTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date data;

    private String motivacao;

    private UsuarioComDTO responsavel;


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

    public UsuarioComDTO getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(UsuarioComDTO responsavel) {
        this.responsavel = responsavel;
    }

    public DecisaoModeradoraPADTO() {
    }

    public DecisaoModeradoraPADTO(Date data, String motivacao, UsuarioComDTO responsavel) {
        this.data = data;
        this.motivacao = motivacao;
        this.responsavel = responsavel;
    }
}
