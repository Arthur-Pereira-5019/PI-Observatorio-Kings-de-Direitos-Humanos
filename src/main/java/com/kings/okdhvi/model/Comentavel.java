package com.kings.okdhvi.model;

import java.util.ArrayList;

public abstract class Comentavel {
    ArrayList<Comentario> comentarios = new ArrayList<>();

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
