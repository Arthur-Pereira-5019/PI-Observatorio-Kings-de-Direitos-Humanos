package com.kings.okdhvi.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="postagens")
public class Postagem {

    @Column(nullable = false, length = 512)
    String tituloPostagem;
    @Column(nullable = false)
    Date dataDaPostagem;

    @OneToOne
    @JoinColumn(name="idUsuario")
    @PrimaryKeyJoinColumn
    Usuario autor;
    @Column(nullable = false, length = 32768)
    String textoPostagem;

    @ManyToMany
    @JoinTable(
            name = "revisores",
            joinColumns = @JoinColumn(name = "idPostagem"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario")
    )
    List<Usuario> revisor = new ArrayList<>();

    @Column(nullable = true, length = 256)
    String tags;
    @Column
    boolean oculto;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPostagem;

    public String getTituloPostagem() {
        return tituloPostagem;
    }

    public void setTituloPostagem(String tituloPostagem) {
        this.tituloPostagem = tituloPostagem;
    }

    public Date getDataDaPostagem() {
        return dataDaPostagem;
    }

    public void setDataDaPostagem(Date dataDaPostagem) {
        this.dataDaPostagem = dataDaPostagem;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getTextoPostagem() {
        return textoPostagem;
    }

    public void setTextoPostagem(String textoPostagem) {
        this.textoPostagem = textoPostagem;
    }

    public List<Usuario> getRevisor() {
        return revisor;
    }

    public void setRevisor(List<Usuario> revisor) {
        this.revisor = revisor;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

    public Long getIdPostagem() {
        return idPostagem;
    }

    public void setIdPostagem(Long idPostagem) {
        this.idPostagem = idPostagem;
    }
}
