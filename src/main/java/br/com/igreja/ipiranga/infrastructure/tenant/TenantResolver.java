package br.com.igreja.ipiranga.infrastructure.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Componente que ensina ao Hibernate como descobrir qual é o Tenant atual.
 * <p>
 * Implementa {@link CurrentTenantIdentifierResolver}, sendo invocado automaticamente pelo Hibernate
 * toda vez que uma sessão precisa ser aberta ou uma query executada.
 * Ele delega a responsabilidade da descoberta para o {@link TenantContext}.
 * </p>
 *
 * @author Sistema Igreja
 * @version 1.0
 */
@Component
public class TenantResolver implements CurrentTenantIdentifierResolver<Long>, HibernatePropertiesCustomizer {

    /**
     * Resolve o identificador do tenant atual.
     *
     * @return O ID do tenant como Long (embora a interface force o retorno genérico compatível com String, ajustamos na implementação).
     */
    @Override
    public Long resolveCurrentTenantIdentifier() {
        Long tenantId = TenantContext.getCurrentTenant();
        return tenantId != null ? tenantId : 0L;
    }

    /**
     * Define se o Hibernate deve validar sessões existentes. (Geralmente true em cenários padrão).
     */
    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    /**
     * Personaliza as propriedades do Hibernate para registrar este resolver.
     *
     * @param hibernateProperties Mapa de propriedades de configuração do Hibernate.
     */
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.tenant_identifier_resolver", this);
    }
}
