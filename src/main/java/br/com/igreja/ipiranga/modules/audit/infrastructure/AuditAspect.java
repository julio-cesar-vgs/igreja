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

/**
 * Aspecto de Auditoria: AuditAspect
 * Camada: Infrastructure
 * 
 * Implementa a preocupação transversal (cross-cutting concern) de auditoria.
 * Utiliza AOP (Aspect Oriented Programming) para interceptar operações de escrita (save, delete)
 * nos serviços de aplicação e registrar logs automaticamente, sem poluir o código de negócio.
 * 
 * Como funciona:
 * 1. Define um Pointcut que identifica métodos 'save*' e 'delete*' nos ApplicationServices.
 * 2. Usa @AfterReturning para executar a lógica de log após o sucesso do método interceptado.
 * 3. Captura o usuário logado do SecurityContextHolder.
 * 4. Persiste um registro em LogCorrecaoRepository.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final LogCorrecaoRepository logRepository;

    // Pointcut atualizado para os novos pacotes de módulos
    @Pointcut("execution(* br.com.igreja.ipiranga.modules..application.*Service.save*(..)) || " +
              "execution(* br.com.igreja.ipiranga.modules..application.*Service.delete*(..))")
    public void serviceWriteOperations() {}

    @AfterReturning(pointcut = "serviceWriteOperations()", returning = "result")
    public void logAfterWrite(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String acao = methodName.startsWith("save") ? "INSERT/UPDATE" : "DELETE";
        
        Long usuarioId = getCurrentUserId();
        
        LogCorrecao log = LogCorrecao.builder()
                .entidadeTipo(result != null ? result.getClass().getSimpleName() : "Unknown")
                .acao(acao)
                .usuarioId(usuarioId)
                .timestamp(LocalDateTime.now())
                .valorNovo(result != null ? result.toString() : null)
                .build();
        
        logRepository.save(log);
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getUsuario().getId();
        }
        return null;
    }
}
