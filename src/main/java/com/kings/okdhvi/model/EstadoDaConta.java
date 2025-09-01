package com.kings.okdhvi.model;

import jakarta.persistence.*;

public class EstadoDaConta {

    @Column
    Long idEstado;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
