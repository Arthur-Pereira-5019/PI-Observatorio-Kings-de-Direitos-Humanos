package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class DecisaoModeradora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idDecisaoModeradora;

    @Column(length = 32)
    String tipo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(nullable = false)
    Date data;
    @Column(nullable = false, length = 1024)
    String motivacao;
    @Column(length = 128)
    String nomeModerado;
    @Column(length = 128)
    String nomeModerador;

    @ManyToOne(cascade = CascadeType.MERGE)
    Usuario usuarioModerado;

    @ManyToOne
    Usuario responsavel;

    @Column
    Long idModerado;

    @Column(length = 512)
    String acao;

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

    public Long getIdModerado() {
        return idModerado;
    }

    public void setIdModerado(Long idModerado) {
        this.idModerado = idModerado;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getNomeModerador() {
        return nomeModerador;
    }

    public void setNomeModerador(String nomeModerador) {
        this.nomeModerador = nomeModerador;
    }
}
