package com.kings.okdhvi.model.DTOs;

import com.kings.okdhvi.model.EstadoDaConta;
import com.kings.okdhvi.model.Imagem;

public class UsuarioForDTO extends UsuarioComDTO{
    private EstadoDaConta estadoDaConta;

    public EstadoDaConta getEstadoDaConta() {
        return estadoDaConta;
    }

    public void setEstadoDaConta(EstadoDaConta estadoDaConta) {
        this.estadoDaConta = estadoDaConta;
    }

    public UsuarioForDTO(Long id, String nome, Imagem foto, EstadoDaConta estadoDaConta) {
        super(id, nome, foto);
        this.estadoDaConta = estadoDaConta;
    }

    public UsuarioForDTO() {
    }
}