package com.kings.okdhvi.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="postagens")
public class Postagem {

    String tituloPostagem;
    Date dataDaPostagem;
    @OneToOne
    @JoinColumn(name="idUsuario")
    Usuario autor;
    String textoPostagem;
    @ManyToMany
    @JoinColumn(name="idUsuario")
    List<Usuario> revisor;
    

}
