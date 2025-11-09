package com.kings.okdhvi.mapper;

import com.kings.okdhvi.model.DTOs.DecisaoModeradoraPADTO;
import com.kings.okdhvi.model.DecisaoModeradora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DecisaoModeradoraMapper {
    @Autowired
    UsuarioMapper um;



    public DecisaoModeradoraPADTO paginaAtual(DecisaoModeradora dm) {
        DecisaoModeradoraPADTO r = new DecisaoModeradoraPADTO();
        r.setResponsavel(um.usuarioComentador(dm.getResponsavel()));
        r.setMotivacao(dm.getMotivacao());
        r.setData(dm.getData());
        return r;
    }
}
