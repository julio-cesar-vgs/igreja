package br.com.igreja.ipiranga.infrastructure.tenant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TenantResolverTest {

    private TenantResolver tenantResolver;

    @BeforeEach
    void setUp() {
        tenantResolver = new TenantResolver();
        TenantContext.clear();
    }

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void resolveCurrentTenantIdentifier_WithTenantSet_ShouldReturnTenantId() {
        // Arrange
        TenantContext.setCurrentTenant(42L);

        // Act
        Long tenantId = tenantResolver.resolveCurrentTenantIdentifier();

        // Assert
        assertThat(tenantId).isEqualTo(42L);
    }

    @Test
    void resolveCurrentTenantIdentifier_WithoutTenantSet_ShouldReturnZero() {
        // Act
        Long tenantId = tenantResolver.resolveCurrentTenantIdentifier();

        // Assert
        assertThat(tenantId).isEqualTo(0L);
    }

    @Test
    void resolveCurrentTenantIdentifier_WithNullTenant_ShouldReturnZero() {
        // Arrange
        TenantContext.setCurrentTenant(null);

        // Act
        Long tenantId = tenantResolver.resolveCurrentTenantIdentifier();

        // Assert
        assertThat(tenantId).isEqualTo(0L);
    }

    @Test
    void validateExistingCurrentSessions_ShouldReturnTrue() {
        // Act
        boolean result = tenantResolver.validateExistingCurrentSessions();

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void customize_ShouldAddTenantResolverToHibernateProperties() {
        // Arrange
        Map<String, Object> hibernateProperties = new HashMap<>();

        // Act
        tenantResolver.customize(hibernateProperties);

        // Assert
        assertThat(hibernateProperties).containsKey("hibernate.tenant_identifier_resolver");
        assertThat(hibernateProperties.get("hibernate.tenant_identifier_resolver")).isEqualTo(tenantResolver);
    }

    @Test
    void customize_WithExistingProperties_ShouldAddTenantResolver() {
        // Arrange
        Map<String, Object> hibernateProperties = new HashMap<>();
        hibernateProperties.put("existing.property", "value");

        // Act
        tenantResolver.customize(hibernateProperties);

        // Assert
        assertThat(hibernateProperties).hasSize(2);
        assertThat(hibernateProperties).containsKey("existing.property");
        assertThat(hibernateProperties).containsKey("hibernate.tenant_identifier_resolver");
    }

    @Test
    void resolveCurrentTenantIdentifier_WithDifferentTenants_ShouldReturnCorrectValues() {
        // Test 1
        TenantContext.setCurrentTenant(1L);
        assertThat(tenantResolver.resolveCurrentTenantIdentifier()).isEqualTo(1L);

        // Test 2
        TenantContext.setCurrentTenant(100L);
        assertThat(tenantResolver.resolveCurrentTenantIdentifier()).isEqualTo(100L);

        // Test 3
        TenantContext.clear();
        assertThat(tenantResolver.resolveCurrentTenantIdentifier()).isEqualTo(0L);
    }
}
