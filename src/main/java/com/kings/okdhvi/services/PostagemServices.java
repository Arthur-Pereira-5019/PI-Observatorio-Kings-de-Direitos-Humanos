package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.exception.postagem.RevisaoPostagemException;
import com.kings.okdhvi.model.*;
import com.kings.okdhvi.model.requests.*;
import com.kings.okdhvi.repositories.PostagemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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

    @PersistenceContext
    private EntityManager em;

    Logger logger = LoggerFactory.getLogger(PostagemServices.class);

    public List<Postagem> buscaFiltrada(BuscaPaginada bp, String texto, UserDetails ud) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Postagem> cq = cb.createQuery(Postagem.class);
        Root<Postagem> p = cq.from(Postagem.class);

        List<Predicate> predicates = new ArrayList<>();

        if (texto != null && !texto.isBlank()) {
            Predicate predicatesCorpo =  construirTextoPredicado(cb, p, texto, "textoPostagem");

            Predicate predicatesTitulo =  construirTextoPredicado(cb, p, texto, "tituloPostagem");

            Predicate predicatesTags =  construirTextoPredicado(cb, p, texto, "tags");


            predicates.add(cb.or(predicatesCorpo, predicatesTitulo, predicatesTags));
            if(texto.contains("noticia")) {
                predicates.add(cb.like(cb.lower(p.get("tags")), "%" + "noticia" + "%"));
            }
        }

        boolean moderador = false;
        if(ud != null) {
            if(ud.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODER"))) {
                System.out.println("Moderador");
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

        busca.setFirstResult(bp.numeroPagina() * bp.numeroResultados());
        busca.setMaxResults(bp.numeroResultados());
        return busca.getResultList();
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
        return pr.findByAutor(u);
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
        CriarImagemRequest cir = new CriarImagemRequest(pcdto.capaBase64(), "Capa", "Capa da publicacao" + pcdto.tituloPostagem(), pcdto.tipoCapa());
        Usuario u = us.encontrarPorId(usuarioId, false);
        Imagem i = is.criarImagem(cir, u);

        post.setCapa(i);

        post.setTextoPostagem(pcdto.textoPostagem());
        post.setTituloPostagem(pcdto.tituloPostagem());
        post.setTags(pcdto.tags());
        post.setExterna(false);


        post.setAutor(u);
        post.setDataDaPostagem(Date.from(Instant.now()));
        post.setRevisor(null);
        return pr.save(post);
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

    public Postagem encontrarPostagemPeloId(Long id) {

        return pr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Postagem não encontrada!"));
    }

    @Transactional
    public Postagem revisarPostagem(RevisorPostagemRequest rpr) {
        Postagem p = encontrarPostagemPeloId(rpr.idPostagem());
        Usuario u = us.encontrarPorId(rpr.idUsuario(), false);

        if (p.getRevisor().contains(u)) {
            throw new RevisaoPostagemException("Postagem já revisada pelo usuário fornecido!");
        }

        p.getRevisor().add(u);
        return pr.save(p);
    }

    public void ocultar(OcultarRecursoRequest r) {
        Postagem p = encontrarPostagemPeloId(r.idPostagem());
        p.setOculto(false);
        DecisaoModeradora dm = new DecisaoModeradora();
        dm.setData(Date.from(Instant.now()));
        dm.setMotivacao(r.motivacao());
        dm.setResponsavel(r.moderador());
        dm.setTipo("Postagem");
        dm.setUsuarioModerado(r.moderado());
        dm.setNomeModerado(p.getTituloPostagem());
    }


}
