package br.com.igreja.ipiranga.infrastructure.tenant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TenantContextTest {

    @AfterEach
    void tearDown() {
        TenantContext.clear();
    }

    @Test
    void setCurrentTenant_ShouldStoreTenantId() {
        // Arrange
        Long tenantId = 1L;

        // Act
        TenantContext.setCurrentTenant(tenantId);

        // Assert
        assertThat(TenantContext.getCurrentTenant()).isEqualTo(tenantId);
    }

    @Test
    void getCurrentTenant_WithoutSettingTenant_ShouldReturnNull() {
        // Act
        Long tenantId = TenantContext.getCurrentTenant();

        // Assert
        assertThat(tenantId).isNull();
    }

    @Test
    void clear_ShouldRemoveTenantId() {
        // Arrange
        TenantContext.setCurrentTenant(5L);

        // Act
        TenantContext.clear();

        // Assert
        assertThat(TenantContext.getCurrentTenant()).isNull();
    }

    @Test
    void setCurrentTenant_WithDifferentValues_ShouldUpdateValue() {
        // Arrange
        TenantContext.setCurrentTenant(1L);

        // Act
        TenantContext.setCurrentTenant(2L);

        // Assert
        assertThat(TenantContext.getCurrentTenant()).isEqualTo(2L);
    }

    @Test
    void setCurrentTenant_WithNull_ShouldStoreNull() {
        // Arrange
        TenantContext.setCurrentTenant(1L);

        // Act
        TenantContext.setCurrentTenant(null);

        // Assert
        assertThat(TenantContext.getCurrentTenant()).isNull();
    }

    @Test
    void tenantContext_ShouldBeThreadLocal() throws InterruptedException {
        // Arrange
        TenantContext.setCurrentTenant(1L);

        // Act
        Thread thread = new Thread(() -> {
            TenantContext.setCurrentTenant(2L);
            assertThat(TenantContext.getCurrentTenant()).isEqualTo(2L);
        });
        thread.start();
        thread.join();

        // Assert
        assertThat(TenantContext.getCurrentTenant()).isEqualTo(1L);
    }

    @Test
    void clear_WhenNoTenantSet_ShouldNotThrowException() {
        // Act & Assert
        TenantContext.clear();
        assertThat(TenantContext.getCurrentTenant()).isNull();
    }
}
