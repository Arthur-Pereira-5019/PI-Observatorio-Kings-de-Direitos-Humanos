package com.kings.okdhvi.model;

public record BuscaPaginada(int numeroPagina, int numeroResultados, String parametro, boolean ascending) {
}
