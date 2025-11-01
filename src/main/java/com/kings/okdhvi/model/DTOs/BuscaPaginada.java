package com.kings.okdhvi.model.DTOs;

public record BuscaPaginada(int numeroPagina, int numeroResultados, String parametro, boolean ascending) {
}
