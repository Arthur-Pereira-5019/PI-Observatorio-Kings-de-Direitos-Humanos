package com.kings.okdhvi.repositories;

import com.kings.okdhvi.model.Forum;
import com.kings.okdhvi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    List<Forum> findByAutor(Usuario u);
    List<Forum> findByAutorOrderByDataDeCriacaoDesc(Usuario u);
}
