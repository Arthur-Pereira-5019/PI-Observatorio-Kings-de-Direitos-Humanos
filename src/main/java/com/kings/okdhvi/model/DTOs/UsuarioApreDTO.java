package com.kings.okdhvi.model.DTOs;

import com.kings.okdhvi.model.EstadoDaConta;
import com.kings.okdhvi.model.Imagem;

public class UsuarioApreDTO {
    private String nome;
    private Imagem fotoDePerfil;
    private EstadoDaConta edc;

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

    public EstadoDaConta getEdc() {
        return edc;
    }

    public void setEdc(EstadoDaConta edc) {
        this.edc = edc;
    }
}
