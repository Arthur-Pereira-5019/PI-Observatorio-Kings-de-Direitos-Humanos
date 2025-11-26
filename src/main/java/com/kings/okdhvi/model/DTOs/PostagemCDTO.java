package com.kings.okdhvi.model.DTOs;

import com.kings.okdhvi.model.Local;

public record PostagemCDTO(
        String tituloPostagem,
        String textoPostagem,
        String tags,
        boolean publicada,
        String capaBase64,
        String tipoCapa,
        Local local)
{

}

