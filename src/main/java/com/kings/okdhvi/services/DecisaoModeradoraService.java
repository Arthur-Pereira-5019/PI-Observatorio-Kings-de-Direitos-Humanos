package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.NullResourceException;
import com.kings.okdhvi.model.DTOs.BuscaPaginada;
import com.kings.okdhvi.model.DTOs.BuscaPaginadaResultado;
import com.kings.okdhvi.model.DTOs.DecisaoModeradoraOPDTO;
import com.kings.okdhvi.model.DecisaoModeradora;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.repositories.DecisaoModeradoraRepository;
import com.kings.okdhvi.repositories.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DecisaoModeradoraService {

    @Autowired
    DecisaoModeradoraRepository dmr;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PersistenceContext
    private EntityManager em;

    public DecisaoModeradora criarDecisaoModeradora(DecisaoModeradoraOPDTO dm, String tipo, Usuario moderador, Usuario moderado, Long idModerado, String acaoInFixo) {
        if(dm == null) {
            throw new NullResourceException("Decisão Moderadora nula submetido");
        }
        String nomeM = moderado == null ? "" : moderado.gerarNome();
        String nomeR = moderador == null ? "" : moderador.gerarNome();
        DecisaoModeradora d = new DecisaoModeradora();
        d.setData(Date.from(Instant.now()));
        d.setMotivacao(dm.motivacao());
        d.setTipo(tipo);
        d.setResponsavel(moderador);
        d.setUsuarioModerado(moderado);
        d.setNomeModerado(nomeM);
        d.setNomeModerador(nomeR);
        d.setIdModerado(idModerado);
        d.setAcao(nomeR + " " + acaoInFixo + " " + nomeM);
        return dmr.save(d);
    }

    public DecisaoModeradora criarDecisaoModeradoraExc(String m, String nomeM, String nomeR, Long idModerado, String acaoInFixo) {
        DecisaoModeradora d = new DecisaoModeradora();
        d.setData(Date.from(Instant.now()));
        d.setMotivacao(m);
        d.setTipo("Usuario");
        d.setResponsavel(null);
        d.setUsuarioModerado(null);
        d.setNomeModerado(nomeM);
        d.setNomeModerador(nomeR);
        d.setIdModerado(idModerado);
        d.setAcao(nomeR + " " + acaoInFixo + " " + nomeM);
        return dmr.save(d);
    }

    public DecisaoModeradora encontrarDecisaoPeloId(Long id, String c) {
        List<DecisaoModeradora> todas = dmr.findByIdModeradoAndTipoOrderByDataDesc(id, c);
        return todas.get(0);
    }


    public List<DecisaoModeradora> encontrarTodasDecisoes() {
        return dmr.findAll();
    }

    public BuscaPaginadaResultado<DecisaoModeradora> buscaFiltrada(BuscaPaginada bp, String texto, UserDetails ud) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DecisaoModeradora> cq = cb.createQuery(DecisaoModeradora.class);
        Root<DecisaoModeradora> d = cq.from(DecisaoModeradora.class);

        List<Predicate> predicates = new ArrayList<>();

        if (texto != null && !texto.isBlank()) {
            //Ação
            //Motivação
            //Nome Moderado
            //Moderador

            Predicate predicatesAcao =  construirTextoPredicado(cb, d, texto, "acao");

            Predicate predicatesNomeModerado =  construirTextoPredicado(cb, d, texto, "nomeModerado");

            Predicate predicatesNomeModerador =  construirTextoPredicado(cb, d, texto, "nomeModerador");

            Predicate predicatesMotivacao =  construirTextoPredicado(cb, d, texto, "motivacao");

            predicates.add(cb.or(predicatesAcao, predicatesNomeModerado, predicatesMotivacao, predicatesNomeModerador));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(d.get("data")));

        TypedQuery<DecisaoModeradora> busca = em.createQuery(cq);
        BuscaPaginadaResultado<DecisaoModeradora> bpr = new BuscaPaginadaResultado<>();

        busca.setFirstResult(bp.numeroPagina() * bp.numeroResultados());
        busca.setMaxResults(bp.numeroResultados() * 5);

        List<DecisaoModeradora> resultadosDaBusca = busca.getResultList();
        int tamanhoTotal = resultadosDaBusca.size();

        if(resultadosDaBusca.isEmpty()) {
            bpr.setResultado(resultadosDaBusca);
        } else {
            bpr.setResultado(resultadosDaBusca.subList(0,Math.min(bp.numeroResultados(), resultadosDaBusca.size())));
        }

        bpr.setProximosIndexes(tamanhoTotal-bpr.getResultado().size());
        return bpr;
    }

    public Predicate construirTextoPredicado(CriteriaBuilder cb, Root<DecisaoModeradora> p, String texto, String campo) {

        String[] t = texto.split(" ");
        List<Predicate> retorno = new ArrayList<>();

        for(int i = 0; i < t.length; i++) {
            retorno.add(cb.like(cb.lower(p.get(campo)), "%" + t[i] + "%"));
        }

        return cb.or(retorno.toArray(new Predicate[0]));
    }

}
