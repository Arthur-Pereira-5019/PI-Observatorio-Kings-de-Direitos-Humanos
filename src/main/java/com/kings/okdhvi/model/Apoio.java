package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kings.okdhvi.services.ImagemService;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Apoio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idApoio;

    @Column
    private String nomeInstituicao;

    @Column(length = 2048)
    private String sobreInstituicao;

    @Column
    private String twitter;

    @Column
    private String telefone;

    @Column
    private String localizacao;

    @Column
    private String site;

    @Column
    private String instagram;

    @Column
    private String linkedin;

    @OneToOne
    private Imagem foto;

    public Long getIdApoio() {
        return idApoio;
    }

    public void setIdApoio(Long idApoio) {
        this.idApoio = idApoio;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public String getSobreInstituicao() {
        return sobreInstituicao;
    }

    public void setSobreInstituicao(String sobreInstituicao) {
        this.sobreInstituicao = sobreInstituicao;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public Imagem getFoto() {
        return foto;
    }

    public void setFoto(Imagem foto) {
        this.foto = foto;
    }
}
