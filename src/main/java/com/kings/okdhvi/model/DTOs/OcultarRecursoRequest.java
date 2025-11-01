package com.kings.okdhvi.model.DTOs;

import com.kings.okdhvi.model.Usuario;

public record OcultarRecursoRequest(Usuario moderador, Usuario moderado, String motivacao, Long idPostagem) {
}
