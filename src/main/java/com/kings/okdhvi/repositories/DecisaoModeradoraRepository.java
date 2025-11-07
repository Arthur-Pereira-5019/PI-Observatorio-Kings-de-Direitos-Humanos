package com.kings.okdhvi.repositories;

import com.kings.okdhvi.model.DecisaoModeradora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecisaoModeradoraRepository extends JpaRepository<DecisaoModeradora, Long> {
    List<DecisaoModeradora> findByIdModeradoAndTipoOrderByDataDesc(Long idModerado, String tipo);
}
