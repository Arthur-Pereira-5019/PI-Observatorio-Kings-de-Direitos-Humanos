package com.kings.okdhvi.services;

import com.kings.okdhvi.model.DTOs.DenunciaCDTO;
import com.kings.okdhvi.model.Denuncia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class DenunciaServices {

    @Autowired
    UsuarioService us;

    private Denuncia criarDenuncia(DenunciaCDTO dto) {
        Denuncia d = new Denuncia();
        d.setDataDenuncia(Date.from(Instant.now()));
        d.setMotivacao(dto.getMotivacao());
        d.setRequisitor();
    }
}
