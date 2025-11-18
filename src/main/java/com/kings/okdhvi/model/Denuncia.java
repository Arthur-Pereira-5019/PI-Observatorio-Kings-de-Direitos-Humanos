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

    @Column(length = 512)
    private String motivacao;

    @Column(length = 512)
    private String anexoDenuncia;

    @Column
    private Date dataDenuncia;

    @Column
    private Long idDonoPagina;

    @Column
    private Character tipoDonoPagina;

    @Column
    private String nomeModerado;

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

    public String getAnexoDenuncia() {
        return anexoDenuncia;
    }

    public void setAnexoDenuncia(String anexoDenuncia) {
        this.anexoDenuncia = anexoDenuncia;
    }

    public Long getIdDonoPagina() {
        return idDonoPagina;
    }

    public void setIdDonoPagina(Long idDonoPagina) {
        this.idDonoPagina = idDonoPagina;
    }

    public Character getTipoDonoPagina() {
        return tipoDonoPagina;
    }

    public void setTipoDonoPagina(Character tipoDonoPagina) {
        this.tipoDonoPagina = tipoDonoPagina;
    }

    public String getNomeModerado() {
        return nomeModerado;
    }

    public void setNomeModerado(String nomeModerado) {
        this.nomeModerado = nomeModerado;
    }
}
