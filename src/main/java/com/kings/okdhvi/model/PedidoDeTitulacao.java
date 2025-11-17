package com.kings.okdhvi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class PedidoDeTitulacao {

    @Column
    private EstadoDaConta cargoRequisitado;

    @Column(length = 512)
    private String motivacao;

    @Column(length = 256)
    private String contato;

    @OneToOne
    private Imagem anexo;

    @OneToOne()
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Usuario requisitor;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}
