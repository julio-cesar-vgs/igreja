package br.com.igreja.ipiranga.infrastructure.security;

import br.com.igreja.ipiranga.modules.identity.domain.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Implementação personalizada da interface {@link UserDetails} do Spring Security.
 * <p>
 * Esta classe atua como um <strong>Adapter</strong> (Padrão de Projeto), adaptando a entidade de domínio
 * {@link Usuario} para a interface que o Spring Security sabe consumir.
 * </p>
 * <p>
 * Isso permite que o framework realize a autenticação e autorização usando os dados persistidos da aplicação
 * sem acoplar diretamente o domínio ao framework de segurança.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
public class CustomUserDetails implements UserDetails {

    @Getter
    private final Usuario usuario;

    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna as permissões concedidas ao usuário.
     * <p>
     * Converte o Enum de Role da entidade usuário (ex: ROLE_ADMIN) para um objeto {@link SimpleGrantedAuthority},
     * que é entendido pelo Spring Security para tomada de decisões de acesso (PreAuthorize).
     * </p>
     *
     * @return Uma coleção de autoridades, geralmente contendo apenas o role do usuário.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(usuario.getRole().name()));
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
