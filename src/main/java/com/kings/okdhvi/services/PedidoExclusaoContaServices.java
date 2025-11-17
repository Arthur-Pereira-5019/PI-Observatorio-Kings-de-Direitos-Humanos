package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.DTOs.BuscaPaginada;
import com.kings.okdhvi.model.DTOs.BuscaPaginadaResultado;
import com.kings.okdhvi.model.PedidoDeTitulacao;
import com.kings.okdhvi.model.PedidoExclusaoConta;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.repositories.PedidoExclusaoContaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PedidoExclusaoContaServices {
    @Autowired
    PedidoExclusaoContaRepository pecr;

    @PersistenceContext
    private EntityManager em;

    Logger logger = LoggerFactory.getLogger(PedidoExclusaoContaServices.class);

    public PedidoExclusaoConta salvarPedidoExclusao(PedidoExclusaoConta pec) {
        return pecr.save(pec);
    }

    public PedidoExclusaoConta encontrarPedidoDeExclusaoPeloId(Long id) {
        return pecr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido de exclusão não encontrado!"));
    }

    public List<PedidoExclusaoConta> encontrarTodosPedidosDeExclusao() {
        return pecr.findAll();
    }

    public void deletarPedidoDeExclusaoPeloId(Long id) {
        pecr.delete(encontrarPedidoDeExclusaoPeloId(id));
    }

    public BuscaPaginadaResultado<PedidoExclusaoConta> buscaFiltrada(BuscaPaginada bp, String texto, UserDetails ud) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PedidoExclusaoConta> cq = cb.createQuery(PedidoExclusaoConta.class);
        Root<PedidoExclusaoConta> p = cq.from(PedidoExclusaoConta.class);

        List<Predicate> predicates = new ArrayList<>();

        if (texto != null && !texto.isBlank()) {
            Predicate predicateAutor = predicadoJoin(cb, p, texto, "usuarioPedido", "nome");

            predicates.add(cb.or(predicateAutor));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(p.get("dataPedido")));

        TypedQuery<PedidoExclusaoConta> busca = em.createQuery(cq);
        BuscaPaginadaResultado<PedidoExclusaoConta> bpr = new BuscaPaginadaResultado<>();

        busca.setFirstResult(bp.numeroPagina() * bp.numeroResultados());
        busca.setMaxResults(bp.numeroResultados() * 5);

        List<PedidoExclusaoConta> resultadosDaBusca = busca.getResultList();
        int tamanhoTotal = resultadosDaBusca.size();

        if(resultadosDaBusca.isEmpty()) {
            bpr.setResultado(resultadosDaBusca);
        } else {
            bpr.setResultado(resultadosDaBusca.subList(0,Math.min(bp.numeroResultados(), resultadosDaBusca.size())));
        }

        bpr.setProximosIndexes(tamanhoTotal-bpr.getResultado().size());
        return bpr;
    }

    public Predicate construirTextoPredicado(CriteriaBuilder cb, Root<PedidoExclusaoConta> p, String texto, String campo) {

        String[] t = texto.split(" ");
        List<Predicate> retorno = new ArrayList<>();

        for(int i = 0; i < t.length; i++) {
            retorno.add(cb.like(cb.lower(p.get(campo)), "%" + t[i] + "%"));
        }

        return cb.or(retorno.toArray(new Predicate[0]));
    }

    public Predicate predicadoJoin(CriteriaBuilder cb, Root<PedidoExclusaoConta> p, String texto, String campo1, String campo2) {
        Join<PedidoExclusaoConta, Usuario> join = p.join(campo1);

        String[] t = texto.split(" ");
        List<Predicate> retorno = new ArrayList<>();

        for(int i = 0; i < t.length; i++) {
            retorno.add(cb.like(cb.lower(p.get(campo2)), "%" + t[i] + "%"));
        }

        return cb.or(retorno.toArray(new Predicate[0]));
    }






}
