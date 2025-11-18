package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.model.Comentario;
import com.kings.okdhvi.model.DTOs.BuscaPaginada;
import com.kings.okdhvi.model.DTOs.BuscaPaginadaResultado;
import com.kings.okdhvi.model.DTOs.DenunciaCDTO;
import com.kings.okdhvi.model.Denuncia;
import com.kings.okdhvi.model.PedidoDeTitulacao;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.repositories.DenunciaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DenunciaServices {

    @Autowired
    UsuarioService us;

    @Autowired
    DenunciaRepository dr;

    @Autowired
    ComentarioServices cs;

    @Autowired
    ForumServices fs;

    @Autowired
    PostagemServices ps;

    @PersistenceContext
    private EntityManager em;

    public Denuncia criarDenuncia(DenunciaCDTO dto, Long idUsuario) {
        Denuncia d = new Denuncia();
        Usuario u = us.encontrarPorId(idUsuario, false);
        if(d.getTipoDenunciado().equals("Comentario")) {
            Comentario c = cs.encontrarComentario(dto.getIdDenunciado());
            d.setAnexoDenuncia(c.getTextComentario());
            d.setIdDonoPagina(c.getIdDono());
            d.setTipoDonoPagina(c.getTipo());
            d.setNomeModerado(getNome(c.getTipo().toString(),c.getIdDono()));
        } else {
            d.setNomeModerado(getNome(d.getTipoDenunciado(), d.getIdDenunciado()));
        }
        d.setDataDenuncia(Date.from(Instant.now()));
        d.setMotivacao(dto.getMotivacao());
        d.setRequisitor(u);
        d.setTipoDenunciado(dto.getTipoDenunciado());
        return dr.save(d);
    }

    public String getNome(String tipo, Long id) {
        if(tipo.equals("P") || tipo.equals("Postagem")) {
            return ps.encontrarPostagemPeloId(id).getTituloPostagem();
        } else if (tipo.equals("F") || tipo.equals("Forum")) {
            return fs.encontrarForumPeloId(id).getTituloForum();
        } else if(tipo.equals("Usuario")) {
            return us.encontrarPorId(id, false).getNome();
        }
        return "";
    }

    public Denuncia encontrarPorId(Long id) {
        return dr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Denúncia não encontrada"));
    }

    public List<Denuncia> encontrarEspecifica(Long idDenunciado, String tipoDenunciado) {
        Usuario u = us.encontrarPorId(idDenunciado, false);
        return dr.findByIdDenunciadoAndTipoDenunciadoOrderByDataDenuncia(idDenunciado, tipoDenunciado);
    }

    public BuscaPaginadaResultado<Denuncia> buscaFiltrada(BuscaPaginada bp, String texto, UserDetails ud) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Denuncia> cq = cb.createQuery(Denuncia.class);
        Root<Denuncia> p = cq.from(Denuncia.class);

        List<Predicate> predicates = new ArrayList<>();

        if (texto != null && !texto.isBlank()) {
            Predicate predicatesMotivacao =  construirTextoPredicado(cb, p, texto, "motivacao");

            Predicate predicatesContato =  construirTextoPredicado(cb, p, texto, "tipoDenunciado");

            Predicate predicateAutor = predicadoJoin(cb, p, texto, "requisitor", "nome");

            predicates.add(cb.or(predicatesMotivacao, predicatesContato, predicateAutor));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(p.get("dataDenuncia")));

        TypedQuery<Denuncia> busca = em.createQuery(cq);
        BuscaPaginadaResultado<Denuncia> bpr = new BuscaPaginadaResultado<>();

        busca.setFirstResult(bp.numeroPagina() * bp.numeroResultados());
        busca.setMaxResults(bp.numeroResultados() * 5);

        List<Denuncia> resultadosDaBusca = busca.getResultList();
        int tamanhoTotal = resultadosDaBusca.size();

        if(resultadosDaBusca.isEmpty()) {
            bpr.setResultado(resultadosDaBusca);
        } else {
            bpr.setResultado(resultadosDaBusca.subList(0,Math.min(bp.numeroResultados(), resultadosDaBusca.size())));
        }

        bpr.setProximosIndexes(tamanhoTotal-bpr.getResultado().size());
        return bpr;
    }

    public Predicate construirTextoPredicado(CriteriaBuilder cb, Root<Denuncia> p, String texto, String campo) {

        String[] t = texto.split(" ");
        List<Predicate> retorno = new ArrayList<>();

        for(int i = 0; i < t.length; i++) {
            retorno.add(cb.like(cb.lower(p.get(campo)), "%" + t[i] + "%"));
        }

        return cb.or(retorno.toArray(new Predicate[0]));
    }

    public Predicate predicadoJoin(CriteriaBuilder cb, Root<Denuncia> p, String texto, String campo1, String campo2) {
        Join<Denuncia, Usuario> join = p.join(campo1);

        String[] t = texto.split(" ");
        List<Predicate> retorno = new ArrayList<>();

        for(int i = 0; i < t.length; i++) {
            retorno.add(cb.like(cb.lower(join.get(campo2)), "%" + t[i] + "%"));
        }

        return cb.or(retorno.toArray(new Predicate[0]));
    }
}
