package com.kings.okdhvi.model.requests;

import com.kings.okdhvi.model.Usuario;

public record OcultarRecursoRequest(Usuario moderador, Usuario moderado, String motivacao, Long idPostagem) {
}
