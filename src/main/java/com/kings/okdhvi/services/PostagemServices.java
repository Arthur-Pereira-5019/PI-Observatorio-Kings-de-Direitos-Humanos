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
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @PersistenceContext
    private EntityManager em;

    public List<PostagemESDTO> encontrarPostagens(BuscaPaginada bp) {
        Pageable pageable = PageRequest.of(bp.numeroPagina(), bp.numeroResultados(), Sort.by(bp.parametro()).descending());
        if(bp.ascending()) {
            pageable = PageRequest.of(bp.numeroPagina(), bp.numeroResultados(), Sort.by(bp.parametro()).ascending());
        }
        Page<Postagem> buscaPaginada = pr.findAll(pageable);

        ArrayList<PostagemESDTO> retorno = new ArrayList<>();
        List<Postagem> postagens = buscaPaginada.getContent();
        postagens.forEach(p -> {retorno.add(parsePostagemToESDTO(p));});
        return retorno;
    }

    public List<PostagemACDTO> filteredSearch(BuscaPaginada bp, String texto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Postagem> cq = cb.createQuery(Postagem.class);
        Root<Postagem> p = cq.from(Postagem.class);

        List<Predicate> predicates = new ArrayList<>();

        if(texto != null) {
            Predicate corpo = cb.like(cb.lower(p.get("textoPostagem")), "%" + texto.toLowerCase() + "%");
            Predicate titulo = cb.like(cb.lower(p.get("tituloPostagem")), "%" + texto.toLowerCase() + "%");
            Predicate autor = cb.like(cb.lower(p.get("autor").get("nome")), "%" + texto.toLowerCase() + "%");
            predicates.add(cb.or(corpo, titulo, autor));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(p.get("dataDaPostagem")));

        TypedQuery<Postagem> busca = em.createQuery(cq);

        busca.setFirstResult(bp.numeroPagina() * bp.numeroResultados());
        busca.setMaxResults(bp.numeroResultados());
        ArrayList<PostagemACDTO> retorno = new ArrayList<>();
        busca.getResultList().forEach(b -> retorno.add(parsePostagemToACDTO(b)));
        return retorno;
    }

    public List<Postagem> encontrarPeloUsuario(Long id) {
        Usuario u = us.encontrarPorId(id, false);
        return pr.findByAutor(u);
    }

    @Transactional
    public Postagem criarPostagem(PostagemCDTO pcdto, Long usuarioId) {
        if(pcdto == null) {
            throw new NullResourceException("Postagem nula submetida!");
        }

        if(pcdto.capaBase64() == null) {
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


        post.setAutor(u);
        post.setDataDaPostagem(Date.from(Instant.now()));
        post.setRevisor(null);
        return pr.save(post);
    }

    public Postagem atualizarPostagem (Postagem p, Long id) {
        if(p == null) {
            throw new NullResourceException("Postagem nula submetido");
        }
        Postagem original = encontrarPostagemPeloId(id);
        original = p;
        p.setIdPostagem(id);
        return pr.save(original);
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

        if(p.getRevisor().contains(u)) {
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

    public PostagemESDTO parsePostagemToESDTO(Postagem p) {
       return new PostagemESDTO(p.getIdPostagem(), p.getTituloPostagem(), p.getCapa());
    }

    public PostagemACDTO parsePostagemToACDTO(Postagem p) {
        return new PostagemACDTO(p.getIdPostagem(), p.getTituloPostagem(), p.getCapa(), p.getTextoPostagem().substring(0, 255));
    }
}
