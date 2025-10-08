package com.kings.okdhvi.repositories;

import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {
    public List<Postagem> findByAutor(Usuario u);
}
