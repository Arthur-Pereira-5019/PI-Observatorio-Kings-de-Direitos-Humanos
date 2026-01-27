package com.kings.okdhvi.model.DTOs;

import com.kings.okdhvi.model.*;

public class UsuarioPDTO {
        private Long idUsuario;

        private String nome;

        private Imagem fotoDePerfil;

        private EstadoDaConta estadoDaConta;

        private Integer proprio = 0;

        private String email;

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

    public void setProprio(Integer proprio) {
        this.proprio = proprio;
    }

    public Integer getProprio() {
        return proprio;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

}
