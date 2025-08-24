package com.kings.okdhvi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

import java.util.Date;

@Entity
public class Imagem {
    @Column
    String tituloImagem;

    @Column(length = 256)
    String decricaoImagem;

    @Column(length = 128)
    String donoImagem;

    @Column
    Date dataImagem;

    @Column
    Long idImagem;

    @Lob
    Byte[] image;
}
