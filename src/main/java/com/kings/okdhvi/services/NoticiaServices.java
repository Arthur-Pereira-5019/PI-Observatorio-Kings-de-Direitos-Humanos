package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.infra.config.ClienteDeNoticias;
import com.kings.okdhvi.mapper.NoticiaMapper;
import com.kings.okdhvi.mapper.PostagemMapper;
import com.kings.okdhvi.model.DTOs.*;
import com.kings.okdhvi.model.NoticiaAgregada;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.repositories.NoticiaAgregadaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PersistenceContext
    private EntityManager em;

    @Value("${api.news}")
    private String apiKey;

    private final String[] BUSCASD = {
            "(negros OR pretos OR racismo OR negra) AND (Blumenau OR Brusque OR Gaspar OR Ilhota OR Indaial)",
            "(negro OR negra) AND (Blumenau OR Brusque OR Gaspar OR Ilhota OR Indaial OR Pomerode OR SC)",
            "(negros OR pretos OR racismo OR negra OR Nazismo) AND (Catarina OR SC OR Pomerode)",
            "(neonazismo OR neonazista) AND (Catarina OR SC OR Pomerode OR Blumenau OR Timbo or Indaial)",
            "(Umbanda OR Candomblé OR Africana) AND (Blumenau OR Brusque OR Gaspar OR Ilhota OR Indaial)",
            "(xenofobia OR nortistas OR nordestinos OR xenofobia) AND (Catarina OR SC OR Timbo)",
            "(homofobia OR LGBT OR LGBTQIAPN+ OR gay) AND (Blumenau OR Brusque OR Gaspar OR Ilhota OR Indaial)",
            "(homofobia OR transfobia OR lgtbfobia OR trans OR gay OR homossexual) AND (Catarina OR SC)",
            "(Mulher, feminicídio, estupro, abuso) AND (Blumenau OR Brusque OR Gaspar OR Ilhota OR Indaial)",
            "(Mulher, feminicídio, estupro, abuso) AND (Catarina OR SC OR Timbo OR Pomerode)",
            "(Mulheres OR lilás AND violencia) AND (Catarina OR Blumenau OR Ilhota OR Gaspar OR Pomerode)",
            "(travesti OR trans) AND (Catarina OR SC OR Blumenau OR Ilhota OR Gaspar OR Pomerode)",
            "(mulher AND (agredida OR humilhada)) AND (Catarina OR Blumenau OR Ilhota OR Gaspar OR Indaial)",
            "(mulher AND xingada) AND (Catarina OR Blumenau OR Ilhota OR Gaspar OR Pomerode OR Timbo)",
            "(violência AND mulher) AND (Catarina OR Blumenau OR Ilhota OR Gaspar OR Pomerode OR Timbo)",
            "(Orgulho AND (Parada OR Marcha)) AND (Catarina OR Blumenau OR Ilhota OR Gaspar OR Pomerode)",
            "(LGBTI+ AND (Parada OR Marcha)) AND (Catarina OR Blumenau OR Ilhota OR Gaspar OR Pomerode)",
            "(LGBTQIAPN+ AND (Parada)) AND (Catarina OR Blumenau OR Ilhota OR Gaspar OR Pomerode)",
            "(Mulheres) AND (Catarina OR Blumenau OR Ilhota OR Gaspar OR Pomerode OR SC OR Indaial)",
            "(Intolerancia) AND (Catarina OR Blumenau OR Ilhota OR Gaspar OR Pomerode OR SC OR Indaial)",
            "(machista OR machistas) AND (Catarina OR Blumenau OR Ilhota OR Gaspar OR Pomerode OR SC)"
    };


    private final String[] BUSCAST = {"Brasil"};

    private final String[] BUSCAS = BUSCASD;

    public NoticiaESDTO parsePostagemToNoticiaESDTO(Postagem p) {
        String prefixoOculto = p.isOculto() ? "[OCULTO] " : "";
        String nomeAutor = p.getAutor() == null ? "Externo" : p.getAutor().getNome();

        return new NoticiaESDTO(p.getId(), prefixoOculto + p.getTituloPostagem(), p.getCapa(), nomeAutor, p.isExterna());
    }

    public Postagem indexarNoticia(Long id) {
        NoticiaAgregada na = encontrarNoticia(id);
        if(!na.getTratada()) {
            return null;
        }
        Postagem r = ps.salvarPostagem(pm.parseNoticiaToPostagem(na));
        tratarNoticia(id);
        return r;
    }

    public void tratarNoticia(Long id) {
        NoticiaAgregada na = encontrarNoticia(id);
        na.setTratada(true);
        atualizarNoticia(na);
    }

    public NoticiaAgregada salvarNoticia(NoticiaAgregada na) {
        NoticiaAgregada teste = procurarPeloLink(na.getLink());
        if(teste != null) {
            return na;
        }
        return nr.save(na);
    }

    public NoticiaAgregada atualizarNoticia(NoticiaAgregada na) {
        return nr.save(na);
    }

    public NoticiaAgregada procurarPeloLink(String link) {
        return nr.findByLink(link).isSuccess() ? nr.findByLink(link).getNow() : null;
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

    public List<NewsResponseDTO> fetchNoticia(String query, String queryType) {
        return c.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/1/latest")
                        .queryParam("apikey", apiKey)
                        .queryParam(queryType, query)
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
                .block();
    }

    public List<NoticiaAgregadaDTO> agregarNoticias() {
        ArrayList<NoticiaAgregadaDTO> e = new ArrayList<>();
        ArrayList<NewsResponseDTO> n = new ArrayList<>();
        try {
            for(int i = 0; i < BUSCAS.length; i++) {
                n.addAll(fetchNoticia(BUSCAS[i],"qInTitle"));
            }
            if(!n.isEmpty()) {
                n.forEach(a -> {
                    e.addAll(a.getResults());
                });
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }

        return e;
    }

    public BuscaPaginadaResultado<NoticiaAgregada> buscaFiltrada(BuscaPaginada bp, String texto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<NoticiaAgregada> cq = cb.createQuery(NoticiaAgregada.class);
        Root<NoticiaAgregada> p = cq.from(NoticiaAgregada.class);

        List<Predicate> predicates = new ArrayList<>();

        if (texto != null && !texto.isBlank()) {
            Predicate predicatesAutor = construirTextoPredicado(cb, p, texto, "autor");

            Predicate predicatesTitulo = construirTextoPredicado(cb, p, texto, "titulo");

            predicates.add(cb.or(predicatesAutor, predicatesTitulo));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(p.get("date")));

        Predicate naoTratado = cb.equal(p.get("tratada"), false);
        predicates.add(naoTratado);

        TypedQuery<NoticiaAgregada> busca = em.createQuery(cq);
        BuscaPaginadaResultado<NoticiaAgregada> bpr = new BuscaPaginadaResultado<>();

        busca.setFirstResult(bp.numeroPagina() * bp.numeroResultados());
        busca.setMaxResults(bp.numeroResultados() * 5);

        List<NoticiaAgregada> resultadosDaBusca = busca.getResultList();
        int tamanhoTotal = resultadosDaBusca.size();
        if(resultadosDaBusca.isEmpty()) {
            bpr.setResultado(resultadosDaBusca);
        } else {
            bpr.setResultado(resultadosDaBusca.subList(0,Math.min(bp.numeroResultados(), resultadosDaBusca.size())));
        }

        bpr.setProximosIndexes(tamanhoTotal-bpr.getResultado().size());
        return bpr;
    }

    public Predicate construirTextoPredicado(CriteriaBuilder cb, Root<NoticiaAgregada> p, String texto, String campo) {

        String[] t = texto.split(" ");
        List<Predicate> retorno = new ArrayList<>();

        for(int i = 0; i < t.length; i++) {
            retorno.add(cb.like(cb.lower(p.get(campo)), "%" + t[i] + "%"));
        }

        return cb.or(retorno.toArray(new Predicate[0]));
    }

    public void salvarNoticias() {
        List<NoticiaAgregadaDTO> encontradas = agregarNoticias();
        List<NoticiaAgregada> noticias = new ArrayList<>();
        encontradas.forEach(e -> {noticias.add(nm.parseNoticiaDTOtoNoticiaAgregada(e));});
        System.out.println("Agregadas " + noticias.size() + " notícias ao sistema");
        noticias.forEach(this::salvarNoticia);
        nr.saveAll(noticias);
    }
}
