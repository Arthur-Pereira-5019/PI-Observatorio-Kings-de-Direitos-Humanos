package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    Date dataDeCriacao;

    @Column(nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    Date dataDeAtualizacao;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="idUsuario")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    Usuario autor;
    @Column(nullable = false, length = 32768)
    String textoForum;

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

    public Date getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
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

    public Date getDataDeAtualizacao() {
        return dataDeAtualizacao;
    }

    public void setDataDeAtualizacao(Date dataDeAtualizacao) {
        this.dataDeAtualizacao = dataDeAtualizacao;
    }
}
