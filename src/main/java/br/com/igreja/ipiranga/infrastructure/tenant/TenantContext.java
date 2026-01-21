package br.com.igreja.ipiranga.infrastructure.tenant;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe utilitária responsável por manter o contexto do Tenant (Igreja) atual.
 * <p>
 * Implementa o padrão <strong>Context Object</strong> usando {@link ThreadLocal}.
 * Isso permite que o ID da igreja seja armazenado de forma isolada para cada thread (requisição web),
 * tornando-o acessível em qualquer ponto do código daquela thread sem a necessidade de passar parâmetros explicítos.
 * </p>
 * <p>
 * <strong>Cuidado:</strong> Sempre deve ser limpo (clear) ao final da execução da thread (via try-finally ou filtros)
 * para evitar vazamento de memória ou contaminação de contexto em pools de threads.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Slf4j
public class TenantContext {

    private static final ThreadLocal<Long> CURRENT_TENANT = new ThreadLocal<>();

    /**
     * Define o ID do tenant atual para a thread em execução.
     * @param tenantId Long representando o ID da igreja.
     */
    public static void setCurrentTenant(Long tenantId) {
        log.debug("Setting current tenant to {}", tenantId);
        CURRENT_TENANT.set(tenantId);
    }

    /**
     * Recupera o ID do tenant associado à thread atual.
     * @return O ID da igreja ou null se não estiver definido.
     */
    public static Long getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    /**
     * Limpa o contexto do tenant atual. Essencial para prevenção de memória leaks.
     */
    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
