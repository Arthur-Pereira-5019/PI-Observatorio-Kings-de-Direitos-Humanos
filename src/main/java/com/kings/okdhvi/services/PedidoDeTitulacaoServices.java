package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.*;
import com.kings.okdhvi.model.DTOs.BuscaPaginada;
import com.kings.okdhvi.model.DTOs.BuscaPaginadaResultado;
import com.kings.okdhvi.repositories.PedidoDeTitulacaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoDeTitulacaoServices {
    @Autowired
    PedidoDeTitulacaoRepository petr;

    @PersistenceContext
    private EntityManager em;

    public PedidoDeTitulacao criarPedidoDeTitulacao(PedidoDeTitulacao pet) {
        return petr.save(pet);
    }

    public PedidoDeTitulacao desserializarPedido(PedidoDeTitulacaoDTO dto, Usuario u) {
        PedidoDeTitulacao p = new PedidoDeTitulacao();
        p.setMotivacao(dto.motivacao());
        p.setDataPedido(Date.from(Instant.now()));
        p.setContato(dto.contato());
        p.setRequisitor(u);
        EstadoDaConta edce = EstadoDaConta.MODERADOR;

        if(dto.cargoRequisitado() == 1) {
            edce = EstadoDaConta.ESPECIALISTA;
        }
        p.setCargoRequisitado(edce);
        return p;
    }

    public PedidoDeTitulacao adicionarId(PedidoDeTitulacao p, Long id) {
        p.setId(id);
        return p;
    }

    public PedidoDeTitulacao encontrarPedidoDeTitulacao(Long id) {
        return petr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido de titulação não encontrado!"));
    }

    public List<PedidoDeTitulacao> encontrarTodosPedidosDeTitulacao() {
        return petr.findAll();
    }

    @Transactional
    public void deletarPedidoDeTitulacaoPeloId(Long id) {
        petr.delete(encontrarPedidoDeTitulacao(id));
    }

    public PedidoDeTitulacao atualizarPedidoDeTitulacao(PedidoDeTitulacao pet) {
        return petr.save(pet);
    }

    public PedidoDeTitulacao encontrarPedidoPeloUsuario(Usuario u) {
        Optional<PedidoDeTitulacao> p = petr.findByRequisitor(u);
        return p.orElse(null);
    }

    public BuscaPaginadaResultado<PedidoDeTitulacao> buscaFiltrada(BuscaPaginada bp, String texto, UserDetails ud) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PedidoDeTitulacao> cq = cb.createQuery(PedidoDeTitulacao.class);
        Root<PedidoDeTitulacao> p = cq.from(PedidoDeTitulacao.class);

        List<Predicate> predicates = new ArrayList<>();

        if (texto != null && !texto.isBlank()) {
            Predicate predicatesMotivacao =  construirTextoPredicado(cb, p, texto, "motivacao");

            Predicate predicatesContato =  construirTextoPredicado(cb, p, texto, "contato");

            Predicate predicateAutor = predicadoJoin(cb, p, texto, "requisitor", "nome");

            predicates.add(cb.or(predicatesMotivacao, predicatesContato, predicateAutor));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(p.get("dataPedido")));

        TypedQuery<PedidoDeTitulacao> busca = em.createQuery(cq);
        BuscaPaginadaResultado<PedidoDeTitulacao> bpr = new BuscaPaginadaResultado<>();

        busca.setFirstResult(bp.numeroPagina() * bp.numeroResultados());
        busca.setMaxResults(bp.numeroResultados() * 5);

        List<PedidoDeTitulacao> resultadosDaBusca = busca.getResultList();
        int tamanhoTotal = resultadosDaBusca.size();

        if(resultadosDaBusca.isEmpty()) {
            bpr.setResultado(resultadosDaBusca);
        } else {
            bpr.setResultado(resultadosDaBusca.subList(0,Math.min(bp.numeroResultados(), resultadosDaBusca.size())));
        }

        bpr.setProximosIndexes(tamanhoTotal-bpr.getResultado().size());
        return bpr;
    }

    public Predicate construirTextoPredicado(CriteriaBuilder cb, Root<PedidoDeTitulacao> p, String texto, String campo) {

        String[] t = texto.split(" ");
        List<Predicate> retorno = new ArrayList<>();

        for(int i = 0; i < t.length; i++) {
            retorno.add(cb.like(cb.lower(p.get(campo)), "%" + t[i] + "%"));
        }

        return cb.or(retorno.toArray(new Predicate[0]));
    }

    public Predicate predicadoJoin(CriteriaBuilder cb, Root<PedidoDeTitulacao> p, String texto, String campo1, String campo2) {
        Join<PedidoDeTitulacao, Usuario> join = p.join(campo1);

        String[] t = texto.split(" ");
        List<Predicate> retorno = new ArrayList<>();

        for(int i = 0; i < t.length; i++) {
            retorno.add(cb.like(cb.lower(join.get(campo2)), "%" + t[i] + "%"));
        }

        return cb.or(retorno.toArray(new Predicate[0]));
    }
}
