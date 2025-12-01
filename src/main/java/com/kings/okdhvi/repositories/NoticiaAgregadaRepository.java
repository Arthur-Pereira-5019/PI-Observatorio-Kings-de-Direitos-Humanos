package com.kings.okdhvi.repositories;

import com.kings.okdhvi.model.NoticiaAgregada;
import io.netty.util.concurrent.Promise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticiaAgregadaRepository extends JpaRepository<NoticiaAgregada, Long> {
    public Promise<NoticiaAgregada> findByLink(String link);
}
