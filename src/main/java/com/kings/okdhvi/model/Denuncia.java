package com.kings.okdhvi.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Usuario requisitor;

    @Column
    private Long idDenunciado;

    @Column
    private String tipoDenunciado;

    @Column
    private String motivacao;

    @Column
    private Date dataDenuncia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getRequisitor() {
        return requisitor;
    }

    public void setRequisitor(Usuario requisitor) {
        this.requisitor = requisitor;
    }

    public Long getIdDenunciado() {
        return idDenunciado;
    }

    public void setIdDenunciado(Long idDenunciado) {
        this.idDenunciado = idDenunciado;
    }

    public String getTipoDenunciado() {
        return tipoDenunciado;
    }

    public void setTipoDenunciado(String tipoDenunciado) {
        this.tipoDenunciado = tipoDenunciado;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public Date getDataDenuncia() {
        return dataDenuncia;
    }

    public void setDataDenuncia(Date date) {
        this.dataDenuncia = date;
    }
}
