package com.kings.okdhvi.model.DTOs;

import com.kings.okdhvi.model.Imagem;

public class UsuarioComDTO {
    private Long id;
    private String nome;
    private Imagem foto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Imagem getFoto() {
        return foto;
    }

    public void setFoto(Imagem foto) {
        this.foto = foto;
    }

    public UsuarioComDTO() {
    }

    public UsuarioComDTO(Long id, String nome, Imagem foto) {
        this.id = id;
        this.nome = nome;
        this.foto = foto;
    }
}
