package br.com.igreja.ipiranga.infrastructure.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Resolutor de Tenant para o Hibernate.
 * Camada: Infrastructure
 * 
 * Integra o contexto de multi-tenancy do sistema com o mecanismo nativo do Hibernate 6,
 * fornecendo o ID da igreja atual para filtrar automaticamente as consultas ao banco.
 */
@Component
public class TenantResolver implements CurrentTenantIdentifierResolver<Long>, HibernatePropertiesCustomizer {

    @Override
    public Long resolveCurrentTenantIdentifier() {
        Long tenantId = TenantContext.getCurrentTenant();
        return tenantId != null ? tenantId : 0L; // 0L or null depending on how you want to handle it
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.tenant_identifier_resolver", this);
    }
}
