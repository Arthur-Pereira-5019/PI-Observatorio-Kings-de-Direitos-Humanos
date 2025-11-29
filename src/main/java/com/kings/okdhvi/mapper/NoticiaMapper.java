package com.kings.okdhvi.mapper;

import com.kings.okdhvi.model.DTOs.NoticiaAgregadaDTO;
import com.kings.okdhvi.model.DTOs.NoticiaESDTO;
import com.kings.okdhvi.model.NoticiaAgregada;
import com.kings.okdhvi.model.Postagem;
import org.springframework.stereotype.Service;

@Service
public class NoticiaMapper {

    public NoticiaESDTO parsePostagemToNoticiaESDTO(Postagem p) {
        String prefixoOculto = p.isOculto() ? "[OCULTO] " : "";
        String nomeAutor = p.getAutor() == null ? "Externo" : p.getAutor().getNome();

        return new NoticiaESDTO(p.getId(), prefixoOculto + p.getTituloPostagem(), p.getCapa(), nomeAutor, p.isExterna());
    }

    public NoticiaAgregada parseNoticiaDTOtoNoticiaAgregada(NoticiaAgregadaDTO nd) {
        NoticiaAgregada na = new NoticiaAgregada();
        na.setTitulo(nd.getTitle());
        na.setAutor(nd.getCreator() == null ? nd.getSource_name() : nd.getCreator());
        na.setDate(nd.getPubDate());
        na.setLinkCapa(nd.getImage_url());
        na.setIcon(nd.getSource_icon());
        return na;
    }
}
