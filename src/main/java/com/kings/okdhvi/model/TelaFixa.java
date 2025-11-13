package com.kings.okdhvi.model;

import jakarta.persistence.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Table
@Entity
public class TelaFixa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nome;

    @Column(length = 32768)
    private String text;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TelaFixa() {
    }

    public TelaFixa(Long id, String nome, String text) {
        this.id = id;
        this.nome = nome;
        this.text = text;
    }
}
