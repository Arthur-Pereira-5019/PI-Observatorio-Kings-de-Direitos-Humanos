package com.kings.okdhvi.model.requests;

public record PostagemCDTO(
        String tituloPostagem,
        String textoPostagem,
        String tags,
        boolean publicada,
        String capaBase64,
        String tipoCapa)
{

}

