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
@Table(name="postagens")
public class Postagem{

    @Column
    boolean oculto;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
            name = "postagens_comentarios",
            joinColumns = @JoinColumn(name = "postagem_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "comentarios_id_comentario",
                    foreignKey = @ForeignKey(
                            foreignKeyDefinition = "FOREIGN KEY (comentarios_id_comentario) REFERENCES comentario(idComentario) ON DELETE CASCADE"
                    )
            )
    )
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
    private Long id;

    @Column(nullable = false, length = 512)
    private String tituloPostagem;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date dataDaPostagem;

    @ManyToOne
    @JoinColumn(name="idUsuario")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Usuario autor;
    @Column(nullable = false, length = 32768)
    private String textoPostagem;

    @Column(nullable = true, length = 256)
    private String tags;

    @OneToOne
    @JoinColumn(name="id_capa")
    private Imagem capa;

    @Column
    boolean externa;

    @Column(length = 512)
    private String nomeAutorNoticiaExterna;

    @Column(length = 512)
    private String linkIconDonoExterno;

    @Column(length = 2048)
    private String linkNoticiaExterna;

    @Column(length = 1024)
    private String linkCapaExterna;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Local local;

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

    public String getNomeAutorNoticiaExterna() {
        return nomeAutorNoticiaExterna;
    }

    public void setNomeAutorNoticiaExterna(String nomeAutorNoticiaExterna) {
        this.nomeAutorNoticiaExterna = nomeAutorNoticiaExterna;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public String getLinkIconDonoExterno() {
        return linkIconDonoExterno;
    }

    public void setLinkIconDonoExterno(String linkIconDonoExterno) {
        this.linkIconDonoExterno = linkIconDonoExterno;
    }

    public String getLinkNoticiaExterna() {
        return linkNoticiaExterna;
    }

    public void setLinkNoticiaExterna(String linkNoticiaExterna) {
        this.linkNoticiaExterna = linkNoticiaExterna;
    }

    public String getLinkCapaExterna() {
        return linkCapaExterna;
    }

    public void setLinkCapaExterna(String linkCapaExterna) {
        this.linkCapaExterna = linkCapaExterna;
    }
}
