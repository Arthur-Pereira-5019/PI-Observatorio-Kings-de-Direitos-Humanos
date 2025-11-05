package com.kings.okdhvi.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class DecisaoModeradora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idDecisaoModeradora;

    @Column(length = 32)
    String tipo;
    @Column(nullable = false)
    Date data;
    @Column(nullable = false, length = 1024)
    String motivacao;
    @Column(length = 100)
    String nomeModerado;

    @ManyToOne(cascade = CascadeType.MERGE)
    Usuario usuarioModerado;

    @ManyToOne
    Usuario responsavel;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public String getNomeModerado() {
        return nomeModerado;
    }

    public void setNomeModerado(String nomeModerado) {
        this.nomeModerado = nomeModerado;
    }

    public Usuario getUsuarioModerado() {
        return usuarioModerado;
    }

    public void setUsuarioModerado(Usuario usuarioModerado) {
        this.usuarioModerado = usuarioModerado;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }


    public Long getIdDecisaoModeradora() {
        return idDecisaoModeradora;
    }

    public void setIdDecisaoModeradora(Long idDecisaoModeradora) {
        this.idDecisaoModeradora = idDecisaoModeradora;
    }

}
