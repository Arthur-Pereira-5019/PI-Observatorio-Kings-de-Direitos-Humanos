package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.infra.config.ClienteDeNoticias;
import com.kings.okdhvi.mapper.NoticiaMapper;
import com.kings.okdhvi.mapper.PostagemMapper;
import com.kings.okdhvi.model.DTOs.NewsResponseDTO;
import com.kings.okdhvi.model.DTOs.NoticiaAgregadaDTO;
import com.kings.okdhvi.model.NoticiaAgregada;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.DTOs.NoticiaESDTO;
import com.kings.okdhvi.repositories.NoticiaAgregadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticiaServices {

    @Autowired
    PostagemServices ps;

    @Autowired
    NoticiaAgregadaRepository nr;

    @Autowired
    PostagemMapper pm;

    @Autowired
    WebClient c;

    @Autowired
    NoticiaMapper nm;

    @Value("${api.news}")
    private String apiKey;



    private final String[] BUSCASD = {
            "(negros OR pretos OR racismo OR negra) AND (Blumenau OR Brusque OR Gaspar OR Ilhota OR Itajaí)",
    "(negros OR pretos OR racismo OR negra OR Nazismo) AND (Santa Catarina OR SC OR Pomerode)",
            "(Umbanda OR Candomblé OR Africana) AND (Blumenau OR Brusque OR Gaspar OR Ilhota OR Indaial)",
            "(xenofobia OR nortistas OR nordestinos OR xenofobia) AND (Santa Catarina OR SC)",
            "(homofobia OR LGBT OR LGBTQIAPN+ OR gay) AND (Blumenau OR Brusque OR Gaspar OR Ilhota OR Indaial)",
            "(homofobia OR transfobia OR lgtbfobia OR trans OR gay OR homossexual) AND (Santa Catarina OR SC)",
            "(Mulher, feminicidio, estupro, abuso) AND (Blumenau OR Brusque OR Gaspar OR Ilhota OR Indaial)",
            "(Mulheres OR lilás AND violencia) AND (Santa Catarina OR Blumenau OR Ilhota OR Gaspar OR Pomerode)",
            "(Mulheres OR lilás AND violencia) AND (Santa Catarina OR Blumenau OR Ilhota OR Gaspar OR Pomerode)"
    };

    private final String[] BUSCAST = {"Brasil"};

    private final String[] BUSCAS = BUSCAST;

    public NoticiaESDTO parsePostagemToNoticiaESDTO(Postagem p) {
        String prefixoOculto = p.isOculto() ? "[OCULTO] " : "";
        String nomeAutor = p.getAutor() == null ? "Externo" : p.getAutor().getNome();

        return new NoticiaESDTO(p.getId(), prefixoOculto + p.getTituloPostagem(), p.getCapa(), nomeAutor, p.isExterna());
    }

    public Postagem indexarNoticia(Long id) {
        NoticiaAgregada na = encontrarNoticia(id);
        return ps.salvarPostagem(pm.parseNoticiaToPostagem(na));
    }

    public List<NoticiaAgregada> encontrarNoticias() {
        return nr.findAll();
    }

    public NoticiaAgregada encontrarNoticia(Long id) {
        return nr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notícia não encontrada!"));
    }

    public void deletarNoticia(Long id) {
        nr.delete(encontrarNoticia(id));
    }

    public List<NoticiaAgregadaDTO> agregarNoticias() {
        ArrayList<NoticiaAgregadaDTO> e = new ArrayList<>();
        for(int i = 0; i < BUSCAS.length; i++) {
            final int z = i;
            c.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/1/latest")
                            .queryParam("apikey", apiKey)
                            .queryParam("qInTitle", BUSCAS[z])
                            .queryParam("country", "br")
                            .queryParam("language", "pt")
                            .queryParam("prioritydomain", "low")
                            .queryParam("removeduplicate", "1")
                            .queryParam("sort", "pubdateasc")
                            .queryParam("excludefield", "ai_summary,ai_org,ai_region")
                            .build()
                    )
                    .retrieve()
                    .bodyToFlux(NewsResponseDTO.class)
                    .collectList()
                    .block()
                    .forEach(n -> {
                        e.addAll(n.getResults());
                    });
        }
        return e;
    }

    public void salvarNoticias() {
        List<NoticiaAgregadaDTO> encontradas = agregarNoticias();
        List<NoticiaAgregada> noticias = new ArrayList<>();
        encontradas.forEach(e -> {noticias.add(nm.parseNoticiaDTOtoNoticiaAgregada(e));});
        System.out.println("Agregadas " + noticias.size() + " notícias ao sistema");
        nr.saveAll(noticias);
    }
}
