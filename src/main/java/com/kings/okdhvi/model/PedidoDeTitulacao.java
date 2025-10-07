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

    @OneToOne
    private Usuario requisitor;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public EstadoDaContaEnum getCargoRequisitado() {
        return cargoRequisitado;
    }

    public void setCargoRequisitado(EstadoDaContaEnum cargoRequisitado) {
        this.cargoRequisitado = cargoRequisitado;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public ArrayList<Imagem> getAnexos() {
        return anexos;
    }

    public void setAnexos(ArrayList<Imagem> anexos) {
        this.anexos = anexos;
    }

    public Usuario getRequisitor() {
        return requisitor;
    }

    public void setRequisitor(Usuario requisitor) {
        this.requisitor = requisitor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
