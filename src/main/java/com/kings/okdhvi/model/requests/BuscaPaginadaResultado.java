package com.kings.okdhvi.model.requests;

import java.util.ArrayList;
import java.util.List;

public class BuscaPaginadaResultado<T> {
    private List<T> resultado;
    private int proximosIndexes;

    public List<T> getResultado() {
        return resultado;
    }

    public void setResultado(List<T> resultado) {
        this.resultado = resultado;
    }

    public int getProximosIndexes() {
        return proximosIndexes;
    }

    public void setProximosIndexes(int proximosIndexes) {
        this.proximosIndexes = proximosIndexes;
    }

    public BuscaPaginadaResultado() {
    }

    public BuscaPaginadaResultado(ArrayList<T> resultado, int proximosIndexes) {
        this.resultado = resultado;
        this.proximosIndexes = proximosIndexes;
    }
}
