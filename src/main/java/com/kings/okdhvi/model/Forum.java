package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "foruns")
public class Forum {
    @Column
    private boolean oculto;

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
    private List<Comentario> comentarios = new ArrayList<>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 512)
    private String tituloForum;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private ZonedDateTime dataDeCriacao;

    @Column(nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private ZonedDateTime dataDeAtualizacao;

    @ManyToOne
    @JoinColumn(name="idUsuario")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Usuario autor;

    @Column(nullable = false, length = 8196)
    private String textoForum;

    @Column
    private Local local;

    @Column
    private boolean arquivado = false;

    public Forum() {
    }

    public Forum(String tituloForum, Usuario autor, String textoForum, Local local) {
        this.dataDeCriacao = ZonedDateTime.now();
        this.dataDeAtualizacao = ZonedDateTime.now();
        this.tituloForum = tituloForum;
        this.autor = autor;
        this.textoForum = textoForum;
        this.local = local;
    }

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void comentar(Comentario c) {
        comentarios.add(c);
        dataDeAtualizacao = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTituloForum() {
        return tituloForum;
    }

    public void setTituloForum(String tituloForum) {
        this.tituloForum = tituloForum;
    }

    public ZonedDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public Usuario getAutor() {
        return autor;
    }

    public String getTextoForum() {
        return textoForum;
    }

    public void setTextoForum(String textoForum) {
        this.textoForum = textoForum;
    }

    public ZonedDateTime getDataDeAtualizacao() {
        return dataDeAtualizacao;
    }

    public void setDataDeAtualizacao(ZonedDateTime dataDeAtualizacao) {
        this.dataDeAtualizacao = dataDeAtualizacao;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public void arquivar() {
        this.arquivado = true;
    }
}
