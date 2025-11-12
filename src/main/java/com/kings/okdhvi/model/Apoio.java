package com.kings.okdhvi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Apoio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long idApoio;

    @Column
    String nomeInstituicao;

    @Column(length = 512)
    String sobreInstituicao;

    @Column
    String twitter;

    @Column
    String telefone;

    @Column
    String localizacao;

    @Column
    String site;

    @Column
    String instagram;

    @Column
    String linkedin;

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
}
