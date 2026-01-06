package br.com.igreja.ipiranga.infrastructure.tenant;

import lombok.extern.slf4j.Slf4j;

/**
 * Contexto de Tenant.
 * Camada: Infrastructure
 * 
 * Utiliza ThreadLocal para armazenar e fornecer o identificador da igreja (tenant)
 * durante todo o ciclo de vida da requisição no thread atual.
 */
@Slf4j
public class TenantContext {

    private static final ThreadLocal<Long> CURRENT_TENANT = new ThreadLocal<>();

    public static void setCurrentTenant(Long tenantId) {
        log.debug("Setting current tenant to {}", tenantId);
        CURRENT_TENANT.set(tenantId);
    }

    public static Long getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
