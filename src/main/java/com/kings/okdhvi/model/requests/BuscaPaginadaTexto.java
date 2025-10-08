package com.kings.okdhvi.model.requests;

public record BuscaPaginadaTexto(int numeroPagina, int numeroResultados, String parametro, boolean ascending, String texto) {
}
