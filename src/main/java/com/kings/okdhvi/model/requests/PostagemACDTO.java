package com.kings.okdhvi.model.requests;

import com.kings.okdhvi.model.Imagem;

public record PostagemACDTO(Long idPostagem, String titulo, Imagem capa, String texto) {
}
