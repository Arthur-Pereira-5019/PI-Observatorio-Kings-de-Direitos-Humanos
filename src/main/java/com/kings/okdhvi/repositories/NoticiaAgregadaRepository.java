package com.kings.okdhvi.repositories;

import com.kings.okdhvi.model.NoticiaAgregada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticiaAgregadaRepository extends JpaRepository<NoticiaAgregada, Long> {
    //public List<NoticiaAgregada>
}
