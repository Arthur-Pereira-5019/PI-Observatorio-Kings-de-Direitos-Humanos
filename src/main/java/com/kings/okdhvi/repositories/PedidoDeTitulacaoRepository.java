package com.kings.okdhvi.repositories;

import com.kings.okdhvi.model.PedidoDeTitulacao;
import com.kings.okdhvi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoDeTitulacaoRepository extends JpaRepository<PedidoDeTitulacao, Long> {
    Optional<PedidoDeTitulacao> findByRequisitor(Usuario requisitor);
}
