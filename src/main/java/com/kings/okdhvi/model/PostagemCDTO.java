package com.kings.okdhvi.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public record PostagemCDTO(
        String tituloPostagem,
        String textoPostagem,
        String tags,
        boolean publicada,
        String capaBase64) {

}

