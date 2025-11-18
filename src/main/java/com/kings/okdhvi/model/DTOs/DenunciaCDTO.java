package com.kings.okdhvi.model.DTOs;

import com.kings.okdhvi.model.Usuario;
import jakarta.persistence.*;

import java.util.Date;

public class DenunciaCDTO {
    private Long idUsuarioRequisitor;

    private Long idDenunciado;

    private String tipoDenunciado;

    private String motivacao;

    public Long getIdUsuarioRequisitor() {
        return idUsuarioRequisitor;
    }

    public void setIdUsuarioRequisitor(Long idUsuarioRequisitor) {
        this.idUsuarioRequisitor = idUsuarioRequisitor;
    }

    public Long getIdDenunciado() {
        return idDenunciado;
    }

    public void setIdDenunciado(Long idDenunciado) {
        this.idDenunciado = idDenunciado;
    }

    public String getTipoDenunciado() {
        return tipoDenunciado;
    }

    public void setTipoDenunciado(String tipoDenunciado) {
        this.tipoDenunciado = tipoDenunciado;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }
}
