package com.kings.okdhvi.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable {


        /*create table usuario
(
 nome varchar(100) not null,
 senha varchar(100),
 telefone varchar(14),
 cpf varchar(11) not null,
 idUsuario char(8)  primary key,
 eMail varchar(100),
 dataDeNascimento date,
 oculto boolean
);

);*/

    private static final long serialVersionId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idUsuario;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(nullable = false, length = 14)
    private String telefone;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false, length = 100)
    private String eMail;

    @Column(nullable = false)
    private Date dataDeNascimento;

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

    private boolean oculto;


}
