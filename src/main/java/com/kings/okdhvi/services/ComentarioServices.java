package com.kings.okdhvi.services;

import com.kings.okdhvi.exception.ResourceNotFoundException;
import com.kings.okdhvi.exception.usuario.UnauthorizedActionException;
import com.kings.okdhvi.model.Comentario;
import com.kings.okdhvi.model.DTOs.DecisaoModeradoraOPDTO;
import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.Usuario;
import com.kings.okdhvi.model.DTOs.BuscaPaginada;
import com.kings.okdhvi.model.DTOs.BuscaPaginadaResultado;
import com.kings.okdhvi.model.DTOs.ComentarioCDTO;
import com.kings.okdhvi.repositories.ComentarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ComentarioServices {
    @Autowired
    ComentarioRepository cr;

    @Autowired
    UsuarioService us;

    @Autowired
    PostagemServices ps;

    @Autowired
    DecisaoModeradoraService dms;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Comentario criarComentario(ComentarioCDTO ccdto, Long id) {
        Comentario c = new Comentario();

        c.setAutor(us.encontrarPorId(id, false));
        c.setTextComentario(ccdto.textoComentario());
        c.setDataComentario(Date.from(Instant.now()));
        c.setTipo(ccdto.tipo());
        c.setIdDono(ccdto.idComentavel());
        c = cr.save(c);
        if(ccdto.tipo() == 'P') {
            Postagem p = ps.encontrarPostagemPeloId(ccdto.idComentavel());
            if(p.isOculto()) {
                throw new UnauthorizedActionException("A postagem está oculta, não há como comentar.");
            }
            p.getComentarios().add(c);
            ps.atualizarPostagem(p, id);
        }else {
            Postagem p = ps.encontrarPostagemPeloId(ccdto.idComentavel());
        }
        return c;
    }


    public Comentario ocultarComentario(Long id) {
        Comentario c = encontrarComentario(id);
        c.setOculto(true);
        return c;
    }

    public Comentario restaurarComentario(Long id) {
        Comentario c = encontrarComentario(id);
        c.setOculto(false);
        return c;
    }

    public void deletarComentario(Long id, Usuario u, DecisaoModeradoraOPDTO dmdto) {
        Comentario c = encontrarComentario(id);
        if(dmdto != null) {
            dms.criarDecisaoModeradora(dmdto, "Comentario", u, c.getAutor(), c.getAutor().getNome());
        } else {
            if(u != c.getAutor()) {
                throw new UnauthorizedActionException("Tentativa de excluir comentário de outro usuário!");
            }
            dms.criarDecisaoModeradora(new DecisaoModeradoraOPDTO("Usuário excluiu o próprio comentário"), "Comentario", u, c.getAutor(), c.getAutor().getNome());
        }
        cr.delete(c);
    }

    public Comentario encontrarComentario(Long id) {
        return cr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado!"));
    }

    public BuscaPaginadaResultado<Comentario> buscaFiltrada(BuscaPaginada bp, Long id, Character tipo, UserDetails ud) {
        boolean moderador = false;

        if(ud != null) {
            if(ud.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODER"))) {
                moderador = true;
            }
        }

        if (!moderador) {
            if(tipo == 'P' && ps.encontrarPostagemPeloId(id).isOculto()) {
                throw new UnauthorizedActionException("Você não possui permissão para acessar esse conteúdo!");
            }
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Comentario> cq = cb.createQuery(Comentario.class);
        Root<Comentario> c = cq.from(Comentario.class);

        List<Predicate> predicates = new ArrayList<>();

        Predicate predicateId = cb.equal(c.get("idDono"), id);
        Predicate predicateTipo =  cb.equal(c.get("tipo"), tipo);
        predicates.add(predicateTipo);
        predicates.add(predicateId);

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(c.get("dataComentario")));


        TypedQuery<Comentario> busca = em.createQuery(cq);

        BuscaPaginadaResultado<Comentario> bpr = new BuscaPaginadaResultado<>();


        busca.setFirstResult(bp.numeroPagina() * bp.numeroResultados());
        busca.setMaxResults(bp.numeroResultados() * 2);


        List<Comentario> resultadosDaBusca = busca.getResultList();
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

    public List<Comentario> encontrarPeloUsuario(Long id) {
        Usuario u = us.encontrarPorId(id, false);
        return cr.findByAutor(u);
    }
}
