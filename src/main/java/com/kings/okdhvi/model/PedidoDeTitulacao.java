package com.kings.okdhvi.model;

import jakarta.persistence.*;

@Entity
public class PedidoDeTitulacao {

    @Column
    private EstadoDaConta cargoRequisitado;

    @Column
    private String motivacao;

    @OneToOne
    private Imagem anexo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Usuario requisitor;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    public EstadoDaConta getCargoRequisitado() {
        return cargoRequisitado;
    }

    public void setCargoRequisitado(EstadoDaConta cargoRequisitado) {
        this.cargoRequisitado = cargoRequisitado;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public Imagem getAnexo() {
        return anexo;
    }

    public void setAnexo(Imagem anexo) {
        this.anexo = anexo;
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
