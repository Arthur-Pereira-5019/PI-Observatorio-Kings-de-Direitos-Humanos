package com.kings.okdhvi.repositories;

import com.kings.okdhvi.model.Comentario;
import com.kings.okdhvi.model.Denuncia;
import com.kings.okdhvi.model.Forum;
import com.kings.okdhvi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
    List<Denuncia> findByIdDenunciadoAndTipoDenunciadoOrderByDataDenuncia(Long idDenunciado, String tipoDenunciado);

}
