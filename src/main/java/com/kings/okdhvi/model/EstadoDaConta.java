package com.kings.okdhvi.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class EstadoDaConta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idEstado;

    @Column
    String nomeEstado;

    @Column
    boolean podeModerar;
    @Column
    boolean podePostar;
    @Column
    boolean podeConcederTitulacoes;
    @Column
    boolean podeComentar;
    @Column
    boolean estadoNocivo;
    @Column
    boolean aplicavelPorModerador;
    @Column
    boolean requisitavel;

    @OneToMany(mappedBy = "estadoDaConta")
    List<Usuario> pertencentes;

    public boolean isRequisitavel() {
        return requisitavel;
    }

    public void setRequisitavel(boolean requisitavel) {
        this.requisitavel = requisitavel;
    }

    public boolean isAplicavelPorModerador() {
        return aplicavelPorModerador;
    }

    public void setAplicavelPorModerador(boolean aplicavelPorModerador) {
        this.aplicavelPorModerador = aplicavelPorModerador;
    }

    public boolean isEstadoNocivo() {
        return estadoNocivo;
    }

    public void setEstadoNocivo(boolean estadoNocivo) {
        this.estadoNocivo = estadoNocivo;
    }

    public boolean isPodeComentar() {
        return podeComentar;
    }

    public void setPodeComentar(boolean podeComentar) {
        this.podeComentar = podeComentar;
    }

    public boolean isPodeConcederTitulacoes() {
        return podeConcederTitulacoes;
    }

    public void setPodeConcederTitulacoes(boolean podeConcederTitulacoes) {
        this.podeConcederTitulacoes = podeConcederTitulacoes;
    }

    public boolean isPodePostar() {
        return podePostar;
    }

    public void setPodePostar(boolean podePostar) {
        this.podePostar = podePostar;
    }

    public boolean isPodeModerar() {
        return podeModerar;
    }

    public void setPodeModerar(boolean podeModerar) {
        this.podeModerar = podeModerar;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }
}
