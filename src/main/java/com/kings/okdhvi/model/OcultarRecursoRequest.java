package com.kings.okdhvi.model;

public record OcultarRecursoRequest(Usuario moderador, Usuario moderado, String motivacao, Long idPostagem) {
}
