package com.kings.okdhvi.model.DTOs;

import com.kings.okdhvi.model.Local;

public record UsuarioADTO(String telefone, String nome, boolean notificacoesPorEmail, String senha, Local local) {
}
