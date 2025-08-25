package com.kings.okdhvi.model.requests;

import com.kings.okdhvi.model.Usuario;

public record CriarImagemRequest (String imageBase64, Long usuarioId, String descricao, String titulo) {}
