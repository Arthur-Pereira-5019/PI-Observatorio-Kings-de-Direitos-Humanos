package com.kings.okdhvi.repositories;

import com.kings.okdhvi.model.Postagem;
import com.kings.okdhvi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostagemRepository extends JpaRepository<Postagem, Long> { }
