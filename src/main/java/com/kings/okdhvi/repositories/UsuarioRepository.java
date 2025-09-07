package com.kings.okdhvi.repositories;

import com.kings.okdhvi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Optional<Usuario> findByemail(String email);
    public Optional<Usuario> findBycpf(String cpf);
}
