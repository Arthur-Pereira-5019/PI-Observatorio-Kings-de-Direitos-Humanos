package com.kings.okdhvi.repositories;

import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {
    public Optional<Postagem> findByUsuario(Usuario u);
}
