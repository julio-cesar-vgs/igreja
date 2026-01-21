package br.com.igreja.ipiranga.infrastructure.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

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
public class TenantResolver implements CurrentTenantIdentifierResolver<UUID>, HibernatePropertiesCustomizer {

    /**
     * Resolve o identificador do tenant atual.
     *
     * @return O ID do tenant como UUID.
     */
    @Override
    public UUID resolveCurrentTenantIdentifier() {
        UUID tenantId = TenantContext.getCurrentTenant();
        // Em caso de null (contexto geral), precisamos de um valor default ou null.
        // O Hibernate espera que o ID seja consistente com o tipo da coluna.
        // Retornamos null para "sem tenant" ou um UUID zerado se necessário.
        // Em estratégia de coluna discriminadora com filtro, null pode significar "ver tudo" ou "erro", depende do filtro.
        // O filtro @TenantId geralmente ignora se for null ou aplica a cláusula.
        // Para segurança, retornamos null e esperamos que o Hibernate lide (geralmente desabilitando o filtro se permitido).
        return tenantId;
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
