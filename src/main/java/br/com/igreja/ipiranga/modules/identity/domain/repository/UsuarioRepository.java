package br.com.igreja.ipiranga.modules.identity.domain.repository;

import br.com.igreja.ipiranga.modules.identity.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para o Agregado Usuario.
 * Camada: Domain
 * 
 * Permite buscar usuários pelo email, utilizado principalmente no processo de autenticação.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
