package com.kings.okdhvi.model.DTOs;

import com.kings.okdhvi.model.*;

public class UsuarioPDTO {
        private Long idUsuario;

        private String nome;

        private Imagem fotoDePerfil;

        private EstadoDaConta estadoDaConta;

        private boolean proprio = false;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Imagem getFotoDePerfil() {
        return fotoDePerfil;
    }

    public void setFotoDePerfil(Imagem fotoDePerfil) {
        this.fotoDePerfil = fotoDePerfil;
    }

    public EstadoDaConta getEstadoDaConta() {
        return estadoDaConta;
    }

    public void setEstadoDaConta(EstadoDaConta estadoDaConta) {
        this.estadoDaConta = estadoDaConta;
    }

    public boolean isProprio() {
        return proprio;
    }

    public void setProprio(boolean proprio) {
        this.proprio = proprio;
    }
}
