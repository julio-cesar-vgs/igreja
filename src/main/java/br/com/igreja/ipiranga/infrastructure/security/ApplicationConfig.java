package br.com.igreja.ipiranga.infrastructure.security;

import br.com.igreja.ipiranga.modules.identity.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe de configuração para Beans fundamentais da aplicação relacionados à segurança.
 * <p>
 * Define e expõe componentes do Spring Bean que serão reutilizados em diferentes partes da infraestrutura,
 * como o gerenciador de autenticação e o codificador de senhas.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UsuarioRepository repository;

    /**
     * Define o serviço de busca de usuários para autenticação.
     * <p>
     * Este bean informa ao Spring Security como buscar os dados do usuário no banco de dados.
     * Utiliza o {@link UsuarioRepository} para encontrar um usuário por e-mail e o adapta
     * para um objeto {@link CustomUserDetails}.
     * </p>
     *
     * @return Uma implementação funcional de UserDetailsService.
     * @throws UsernameNotFoundException Se o usuário não for encontrado no banco.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Define o codificador de senhas da aplicação.
     * <p>
     * Utiliza o BCrypt, um algoritmo de hash robusto e padrão da indústria para armazenamento seguro de senhas.
     * </p>
     *
     * @return Instância de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
