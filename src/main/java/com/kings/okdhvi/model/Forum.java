package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "foruns")
public class Forum {
    @Column
    boolean oculto;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
            name = "foruns_comentarios",
            joinColumns = @JoinColumn(name = "forum_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "comentarios_id_comentario",
                    foreignKey = @ForeignKey(
                            foreignKeyDefinition = "FOREIGN KEY (comentarios_id_comentario) REFERENCES comentario(idComentario) ON DELETE CASCADE"
                    )
            )
    )
    @JsonIgnore
    List<Comentario> comentarios = new ArrayList<>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 512)
    String tituloForum;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    Date dataDoForum;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="idUsuario")
    Usuario autor;
    @Column(nullable = false, length = 32768)
    String textoForum;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "revisores",
            joinColumns = @JoinColumn(name = "idPostagem"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario")
    )
    List<Usuario> revisor = new ArrayList<>();

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloForum() {
        return tituloForum;
    }

    public void setTituloForum(String tituloForum) {
        this.tituloForum = tituloForum;
    }

    public Date getDataDoForum() {
        return dataDoForum;
    }

    public void setDataDoForum(Date dataDoForum) {
        this.dataDoForum = dataDoForum;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getTextoForum() {
        return textoForum;
    }

    public void setTextoForum(String textoForum) {
        this.textoForum = textoForum;
    }

    public List<Usuario> getRevisor() {
        return revisor;
    }

    public void setRevisor(List<Usuario> revisor) {
        this.revisor = revisor;
    }
}
