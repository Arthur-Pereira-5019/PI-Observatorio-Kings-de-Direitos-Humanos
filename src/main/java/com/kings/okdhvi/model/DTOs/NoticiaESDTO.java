package com.kings.okdhvi.model.DTOs;

import com.kings.okdhvi.model.Imagem;

public class NoticiaESDTO extends PostagemESDTO{
    private String nomeAutor;
    private boolean externa;
    private String nomeAutorNoticiaExterna;
    private String linkIconDonoExterno;
    private String linkNoticiaExterna;
    private String linkCapaExterna;

    public NoticiaESDTO(Long idPostagem, String titulo, Imagem capa, String nomeAutor, boolean externa) {
        super(idPostagem, titulo, capa);
        this.nomeAutor = nomeAutor;
        this.externa = externa;
    }

    public NoticiaESDTO(String titulo, String nomeAutorNoticiaExterna, String linkIconDonoExterno, String linkNoticiaExterna, String linkCapaExterna) {
        super(null, titulo, null);
        this.externa = true;
        this.nomeAutorNoticiaExterna = nomeAutorNoticiaExterna;
        this.linkIconDonoExterno = linkIconDonoExterno;
        this.linkNoticiaExterna = linkNoticiaExterna;
        this.linkCapaExterna = linkCapaExterna;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public boolean isExterna() {
        return externa;
    }

    public void setExterna(boolean externa) {
        this.externa = externa;
    }

    public String getLinkCapaExterna() {
        return linkCapaExterna;
    }

    public void setLinkCapaExterna(String linkCapaExterna) {
        this.linkCapaExterna = linkCapaExterna;
    }

    public String getLinkNoticiaExterna() {
        return linkNoticiaExterna;
    }

    public void setLinkNoticiaExterna(String linkNoticiaExterna) {
        this.linkNoticiaExterna = linkNoticiaExterna;
    }

    public String getLinkIconDonoExterno() {
        return linkIconDonoExterno;
    }

    public void setLinkIconDonoExterno(String linkIconDonoExterno) {
        this.linkIconDonoExterno = linkIconDonoExterno;
    }

    public String getNomeAutorNoticiaExterna() {
        return nomeAutorNoticiaExterna;
    }

    public void setNomeAutorNoticiaExterna(String nomeAutorNoticiaExterna) {
        this.nomeAutorNoticiaExterna = nomeAutorNoticiaExterna;
    }
}
