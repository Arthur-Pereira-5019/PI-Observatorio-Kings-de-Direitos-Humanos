package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.*;
import com.kings.okdhvi.model.DTOs.*;
import com.kings.okdhvi.repositories.PostagemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostagemServices {
    @Autowired
    PostagemRepository pr;
    @Autowired
    UsuarioService us;
    @Autowired
    ImagemService is;

    @Autowired
    DecisaoModeradoraService dms;
    @PersistenceContext
    private EntityManager em;

    Logger logger = LoggerFactory.getLogger(PostagemServices.class);


    public BuscaPaginadaResultado<Postagem> buscaFiltrada(BuscaPaginada bp, String texto, UserDetails ud, boolean noticia) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Postagem> cq = cb.createQuery(Postagem.class);
        Root<Postagem> p = cq.from(Postagem.class);

        List<Predicate> predicates = new ArrayList<>();

        if (texto != null && !texto.isBlank()) {
            Predicate predicatesCorpo =  construirTextoPredicado(cb, p, texto, "textoPostagem");

            Predicate predicatesTitulo =  construirTextoPredicado(cb, p, texto, "tituloPostagem");

            Predicate predicatesTags =  construirTextoPredicado(cb, p, texto, "tags");

            predicates.add(cb.or(predicatesCorpo, predicatesTitulo, predicatesTags));

        }

        if(noticia) {
            predicates.add(cb.like(cb.lower(p.get("tags")), "%" + "noticia" + "%"));
        } else {
            Predicate naoExterno = cb.equal(p.get("externa"), false);
            predicates.add(naoExterno);
        }

        boolean moderador = false;
        if(ud != null) {
            if(ud.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODER"))) {
                moderador = true;
            }
        }

        if (!moderador) {
            Predicate naoOculto = cb.equal(p.get("oculto"), false);
            predicates.add(naoOculto);
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(p.get("dataDaPostagem")));

        TypedQuery<Postagem> busca = em.createQuery(cq);
        BuscaPaginadaResultado<Postagem> bpr = new BuscaPaginadaResultado<>();

        busca.setFirstResult(bp.numeroPagina() * bp.numeroResultados());
        busca.setMaxResults(bp.numeroResultados() * 5);

        List<Postagem> resultadosDaBusca = busca.getResultList();
        int tamanhoTotal = resultadosDaBusca.size();

        if(resultadosDaBusca.isEmpty()) {
            bpr.setResultado(resultadosDaBusca);
        } else {
            bpr.setResultado(resultadosDaBusca.subList(0,Math.min(bp.numeroResultados(), resultadosDaBusca.size())));
        }

        bpr.setProximosIndexes(tamanhoTotal-bpr.getResultado().size());
        return bpr;
    }

    public Predicate construirTextoPredicado(CriteriaBuilder cb, Root<Postagem> p, String texto, String campo) {

        String[] t = texto.split(" ");
        List<Predicate> retorno = new ArrayList<>();

        for(int i = 0; i < t.length; i++) {
             retorno.add(cb.like(cb.lower(p.get(campo)), "%" + t[i] + "%"));
        }

        return cb.or(retorno.toArray(new Predicate[0]));
    }

    public List<Postagem> encontrarPeloUsuario(Long id) {
        Usuario u = us.encontrarPorId(id, false);
        return pr.findByAutorOrderByDataDaPostagemDesc(u);
    }

    @Transactional
    public Postagem criarPostagem(PostagemCDTO pcdto, Long usuarioId) {
        if (pcdto == null) {
            throw new NullResourceException("Postagem nula submetida!");
        }
        if (pcdto.capaBase64() == null) {
            throw new NullResourceException("Postagem sem capa submetida!");
        }
        Postagem post = new Postagem();
        Usuario u = us.encontrarPorId(usuarioId, false);

        /*
        CriarImagemMetaRequest cir = new CriarImagemMetaRequest(pcdto.capaBase64(), pcdto.descricaoCapa(), "Capa da publicacao: " + pcdto.tituloPostagem(), pcdto.tipoCapa());
        Imagem i = is.criarImagem(cir, u);
                post.setCapa(i);

*/

        post.setTextoPostagem(pcdto.textoPostagem());
        post.setTituloPostagem(pcdto.tituloPostagem());
        post.setTags(pcdto.tags());
        post.setLocal(pcdto.local());
        post.setExterna(false);


        post.setAutor(u);
        post.setDataDaPostagem(Date.from(Instant.now()));
        return pr.save(post);
    }

    public Postagem salvarPostagem(Postagem p) {
        return pr.save(p);
    }

    public Postagem atualizarPostagem(Postagem p, Long id) {
        if (p == null) {
            throw new NullResourceException("Postagem nula submetido");
        }
        return pr.save(p);
    }

    public void deletarPeloId(Long id) {
        pr.delete(encontrarPostagemPeloId(id));
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Postagem encontrarPostagemPeloId(Long id) {
        return pr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Postagem não encontrada!"));
    }

    @Transactional
    public void ocultar(Long idModerador, Long idPost, DecisaoModeradoraOPDTO d) {
        Postagem p = encontrarPostagemPeloId(idPost);
        Usuario u = us.encontrarPorId(idModerador, false);
        p.setOculto(!p.isOculto());
        dms.criarDecisaoModeradora(d, "Postagem", u, p.getAutor(), idPost, (p.isOculto() ? "" : "des") +"ocultou o post de");
        pr.save(p);
    }


}
