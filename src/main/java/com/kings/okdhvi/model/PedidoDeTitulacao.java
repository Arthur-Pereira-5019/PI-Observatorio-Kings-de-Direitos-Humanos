package com.kings.okdhvi.model;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class PedidoDeTitulacao {

    @Column
    private EstadoDaContaEnum cargoRequisitado;

    @Column
    private String motivacao;

    @OneToMany
    private ArrayList<Imagem> anexos = new ArrayList<>();

    @ManyToOne
    private Usuario requisitor;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
