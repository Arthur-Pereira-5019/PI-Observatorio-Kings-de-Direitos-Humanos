package com.kings.okdhvi.repositories;

import com.kings.okdhvi.model.Comentario;
import com.kings.okdhvi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByAutor(Usuario u);
    long countByTipoAndIdDono(Character tipo, Long idDono);
}
