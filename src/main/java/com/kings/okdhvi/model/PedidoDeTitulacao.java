package com.kings.okdhvi.model;

import jakarta.persistence.*;

import java.util.ArrayList;

public class PedidoDeTitulacao {

    @Column
    private EstadoDaContaEnum cargoRequisitado;

    @Column
    private String motivacao;

    @OneToMany
    private ArrayList<Imagem> anexos = new ArrayList<>();

    @OneToMany
    private Usuario requisitor;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
