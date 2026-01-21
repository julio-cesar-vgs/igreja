package br.com.igreja.ipiranga.modules.identity.domain.model;

import br.com.igreja.ipiranga.modules.igreja.domain.model.Igreja;
import br.com.igreja.ipiranga.shared.domain.TenantEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import java.util.UUID;

/**
 * Entidade que representa um Usuário do sistema.
 * <p>
 * O Usuário é a raiz de identidade dentro do sistema, utilizado para controle de acesso (login)
 * e auditoria. Implementa {@link UserDetails} para integração nativa com o Spring Security.
 * </p>
 * <p>
 * A classe estende {@link TenantEntity}, o que significa que cada usuário pertence estritamente
 * a uma única igreja (IgrejaId), garantindo o isolamento multilocatário.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Entity
@Table(name = "usuarios")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Usuario extends TenantEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igreja_id", insertable = false, updatable = false)
    private Igreja igreja;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Role {
        ROLE_ADMIN, ROLE_USER, ROLE_TESOUREIRO
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
