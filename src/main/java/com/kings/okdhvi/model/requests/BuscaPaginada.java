package com.kings.okdhvi.model.requests;

public record BuscaPaginada(int numeroPagina, int numeroResultados, String parametro, boolean ascending) {
}
