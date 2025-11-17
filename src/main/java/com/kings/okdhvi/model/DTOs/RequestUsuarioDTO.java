package com.kings.okdhvi.model.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RequestUsuarioDTO {
    private String nomeRequisitor;
    private Long idRequisitor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date data;
    private String texto;
    private String info;

    public String getNomeRequisitor() {
        return nomeRequisitor;
    }

    public void setNomeRequisitor(String nomeRequisitor) {
        this.nomeRequisitor = nomeRequisitor;
    }

    public Long getIdRequisitor() {
        return idRequisitor;
    }

    public void setIdRequisitor(Long idRequisitor) {
        this.idRequisitor = idRequisitor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
