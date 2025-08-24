package com.kings.okdhvi.model.requests;

import com.kings.okdhvi.model.Usuario;

public record CriarImagemRequest (Long usuarioId, String descricao, String titulo, Byte[] imagem) {}
