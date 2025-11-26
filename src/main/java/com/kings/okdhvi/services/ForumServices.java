package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.*;
import com.kings.okdhvi.model.DTOs.*;
import com.kings.okdhvi.repositories.ForumRepository;
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
public class ForumServices {
    @Autowired
    ForumRepository fr;
    @Autowired
    UsuarioService us;

    @Autowired
    DecisaoModeradoraService dms;
    @PersistenceContext
    private EntityManager em;

    public BuscaPaginadaResultado<Forum> buscaFiltrada(BuscaPaginada bp, String texto, UserDetails ud) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Forum> cq = cb.createQuery(Forum.class);
        Root<Forum> f = cq.from(Forum.class);

        List<Predicate> predicates = new ArrayList<>();

        if (texto != null && !texto.isBlank()) {
            Predicate predicatesCorpo =  construirTextoPredicado(cb, f, texto, "textoForum");

            Predicate predicatesTitulo =  construirTextoPredicado(cb, f, texto, "tituloForum");

            predicates.add(cb.or(predicatesCorpo, predicatesTitulo));
        }

        boolean moderador = false;
        if(ud != null) {
            if(ud.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODER"))) {
                moderador = true;
            }
        }

        if (!moderador) {
            Predicate naoOculto = cb.equal(f.get("oculto"), false);
            predicates.add(naoOculto);
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(f.get("dataDeCriacao")));

        TypedQuery<Forum> busca = em.createQuery(cq);
        BuscaPaginadaResultado<Forum> bpr = new BuscaPaginadaResultado<>();

        busca.setFirstResult(bp.numeroPagina() * bp.numeroResultados());
        busca.setMaxResults(bp.numeroResultados() * 5);

        List<Forum> resultadosDaBusca = busca.getResultList();
        int tamanhoTotal = resultadosDaBusca.size();

        if(resultadosDaBusca.isEmpty()) {
            bpr.setResultado(resultadosDaBusca);
        } else {
            bpr.setResultado(resultadosDaBusca.subList(0,Math.min(bp.numeroResultados(), resultadosDaBusca.size())));
        }

        bpr.setProximosIndexes(tamanhoTotal-bpr.getResultado().size());
        return bpr;
    }

    public Predicate construirTextoPredicado(CriteriaBuilder cb, Root<Forum> p, String texto, String campo) {

        String[] t = texto.split(" ");
        List<Predicate> retorno = new ArrayList<>();

        for(int i = 0; i < t.length; i++) {
            retorno.add(cb.like(cb.lower(p.get(campo)), "%" + t[i] + "%"));
        }

        return cb.or(retorno.toArray(new Predicate[0]));
    }

    public List<Forum> encontrarPeloUsuario(Long id) {
        Usuario u = us.encontrarPorId(id, false);
        return fr.findByAutorOrderByDataDeCriacaoDesc(u);
    }

    @Transactional
    public Forum criarForum(ForumCDTO fcdto, Long usuarioId) {
        Forum forum = new Forum();
        Usuario u = us.encontrarPorId(usuarioId, false);

        forum.setTextoForum(fcdto.textoForum());
        forum.setTituloForum(fcdto.tituloForum());

        forum.setAutor(u);
        forum.setLocal(fcdto.local());
        forum.setDataDeCriacao(Date.from(Instant.now()));
        forum.setDataDeAtualizacao(forum.getDataDeCriacao());
        return fr.save(forum);
    }

    public Forum atualizarForum(Forum f) {
        if (f == null) {
            throw new NullResourceException("Forum nulo submetido");
        }
        return fr.save(f);
    }

    public void deletarPeloId(Long id) {
        fr.delete(encontrarForumPeloId(id));
    }

    public Forum encontrarForumPeloId(Long id) {
        return fr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Forum não encontrado!"));
    }


    @Transactional
    public void ocultar(Long idModerador, Long idForum, DecisaoModeradoraOPDTO d) {
        Forum f = encontrarForumPeloId(idForum);
        Usuario u = us.encontrarPorId(idModerador, false);
        boolean resultado = !f.isOculto();
        f.setOculto(resultado);
        dms.criarDecisaoModeradora(d, "Forum", u, f.getAutor(), idForum, (f.isOculto() ? "" : "des") +"ocultou o fórum de");
        fr.save(f);
    }


}
