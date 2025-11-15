package com.kings.okdhvi.mapper;

import com.kings.okdhvi.model.DTOs.DecisaoModeradoraECDTO;
import com.kings.okdhvi.model.DTOs.DecisaoModeradoraPADTO;
import com.kings.okdhvi.model.DecisaoModeradora;
import com.kings.okdhvi.services.ForumServices;
import com.kings.okdhvi.services.PostagemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DecisaoModeradoraMapper {
    @Autowired
    UsuarioMapper um;

    @Autowired
    ForumServices fs;

    @Autowired
    PostagemServices ps;

    public DecisaoModeradoraPADTO paginaAtual(DecisaoModeradora dm) {
        DecisaoModeradoraPADTO r = new DecisaoModeradoraPADTO();
        r.setResponsavel(um.usuarioComentador(dm.getResponsavel()));
        r.setMotivacao(dm.getMotivacao());
        r.setData(dm.getData());
        return r;
    }

    public DecisaoModeradoraECDTO decisaoCompleta(DecisaoModeradora dm) {
        DecisaoModeradoraECDTO r = new DecisaoModeradoraECDTO();
        r.setAcao(dm.getAcao());
        r.setIdModerado(0L);
        r.setIdModerador(0L);
        try {
            r.setIdModerado(dm.getUsuarioModerado().getIdUsuario());
        } catch (Exception ignored) {

        }
        try {
            r.setIdModerador(dm.getResponsavel().getIdUsuario());
        } catch (Exception ignored) {

        }
        r.setNomeModerado(dm.getNomeModerado());
        r.setNomeModerador(dm.getNomeModerador());
        r.setMotivacao(dm.getMotivacao());
        r.setData(dm.getData());
        String prEs = "";
        String es = "";
        switch (dm.getTipo()) {
            case "Usuario":
                prEs = "usuario/";
                es = dm.getNomeModerado();
                break;
            case "Postagem", "Comentario P":
                prEs = "publicacao/";
                es = ps.encontrarPostagemPeloId(dm.getIdModerado()).getTituloPostagem();
                break;
            case "Forum", "Comentario F":
                prEs = "forum/";
                es = fs.encontrarForumPeloId(dm.getIdModerado()).getTituloForum();
                break;
        }
        r.setLinkEspaco(prEs + r.getIdModerado());
        r.setNomeEspaco(es);
        return r;
    }
}
