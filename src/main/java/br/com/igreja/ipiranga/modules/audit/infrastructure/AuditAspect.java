package br.com.igreja.ipiranga.modules.audit.infrastructure;

import br.com.igreja.ipiranga.infrastructure.security.CustomUserDetails;
import br.com.igreja.ipiranga.modules.audit.domain.model.LogCorrecao;
import br.com.igreja.ipiranga.modules.audit.domain.repository.LogCorrecaoRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aspecto AOP (Aspect Oriented Programming) para automação de logs de
 * auditoria.
 * <p>
 * Este componente intercepta chamadas de métodos nos Services de Aplicação,
 * especificamente
 * operações de escrita (save*, delete*), e gera registros na tabela de
 * LogCorrecao automaticamente.
 * </p>
 * <p>
 * Benefícios:
 * <ul>
 * <li>Desacoplamento: O código de negócio não precisa saber que está sendo
 * auditado.</li>
 * <li>Consistência: Garante que todas as operações mapeadas sejam auditadas sem
 * esquecimento.</li>
 * <li>Manutenibilidade: Lógica de auditoria centralizada em um único
 * lugar.</li>
 * </ul>
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final LogCorrecaoRepository logRepository;

    /**
     * Define o Pointcut (Ponto de Corte) para interceptação.
     * <p>
     * Alvo: Métodos que começam com 'save' ou 'delete'
     * Localização: Qualquer classe terminada em 'Service' dentro de subpacotes
     * 'application' do projeto base.
     * </p>
     */
    @Pointcut("execution(* br.com.igreja.ipiranga.modules..application.*Service.save*(..)) || " +
            "execution(* br.com.igreja.ipiranga.modules..application.*Service.delete*(..))")
    public void serviceWriteOperations() {
    }

    /**
     * Advice (Conselho) executado após o retorno bem-sucedido do método
     * interceptado.
     * <p>
     * Captura o resultado da operação e o usuário autenticado para criar o registro
     * de log.
     * </p>
     *
     * @param joinPoint Metadados do ponto de junção (método interceptado).
     * @param result    O objeto retornado pelo método (entidade salva, por
     *                  exemplo).
     */
    @AfterReturning(pointcut = "serviceWriteOperations()", returning = "result")
    public void logAfterWrite(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String acao = methodName.startsWith("save") ? "INSERT/UPDATE" : "DELETE";

        UUID usuarioId = getCurrentUserId();

        LogCorrecao log = LogCorrecao.builder()
                .entidadeTipo(result != null ? result.getClass().getSimpleName() : "Unknown")
                .acao(acao)
                .usuarioId(usuarioId)
                .timestamp(LocalDateTime.now())
                .valorNovo(result != null ? result.toString() : null)
                .build();

        logRepository.save(log);
    }

    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getUsuario().getId();
        }
        return null;
    }
}
