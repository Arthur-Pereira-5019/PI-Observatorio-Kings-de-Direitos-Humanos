package com.kings.okdhvi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
public class DecisaoModeradora {

    @Column(length = 32)
    String tipo;
    @Column(nullable = false)
    Date data;
    @Column(nullable = false, length = 512)
    String motivacao;
    @Column(length = 100)
    String nomeModerado;

    @ManyToOne
    Usuario usuarioModerado;

    @ManyToOne
    Usuario responsavel;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

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

    public Usuario getUsuarioModerado() {
        return usuarioModerado;
    }

    public void setUsuarioModerado(Usuario usuarioModerado) {
        this.usuarioModerado = usuarioModerado;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }
}
