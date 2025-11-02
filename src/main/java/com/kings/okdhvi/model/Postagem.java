package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="postagens")
public class Postagem{

    @Column
    boolean oculto;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Comentario> comentarios = new ArrayList<>();

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 512)
    String tituloPostagem;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    Date dataDaPostagem;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="idUsuario")
    Usuario autor;
    @Column(nullable = false, length = 32768)
    String textoPostagem;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "revisores",
            joinColumns = @JoinColumn(name = "idPostagem"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario")
    )
    List<Usuario> revisor = new ArrayList<>();

    @Column(nullable = true, length = 256)
    String tags;

    @OneToOne
    @JoinColumn(name="id_capa")
    private Imagem capa;

    @Column
    boolean externa;

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



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Imagem getCapa() {
        return capa;
    }

    public void setCapa(Imagem capa) {
        this.capa = capa;
    }

    public boolean isExterna() {
        return externa;
    }

    public void setExterna(boolean externa) {
        this.externa = externa;
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }
}
