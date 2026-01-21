package br.com.igreja.ipiranga.shared.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TenantEntityTest {

    @Entity
    private static class TestTenantEntity extends TenantEntity {
        @Id
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    @Test
    void setIgrejaId_ShouldStoreValue() {
        // Arrange
        TestTenantEntity entity = new TestTenantEntity();

        // Act
        entity.setIgrejaId(10L);

        // Assert
        assertThat(entity.getIgrejaId()).isEqualTo(10L);
    }

    @Test
    void getIgrejaId_WithoutSetting_ShouldReturnNull() {
        // Arrange
        TestTenantEntity entity = new TestTenantEntity();

        // Act
        Long igrejaId = entity.getIgrejaId();

        // Assert
        assertThat(igrejaId).isNull();
    }

    @Test
    void setIgrejaId_WithNull_ShouldStoreNull() {
        // Arrange
        TestTenantEntity entity = new TestTenantEntity();
        entity.setIgrejaId(5L);

        // Act
        entity.setIgrejaId(null);

        // Assert
        assertThat(entity.getIgrejaId()).isNull();
    }

    @Test
    void setIgrejaId_WithDifferentValues_ShouldUpdateValue() {
        // Arrange
        TestTenantEntity entity = new TestTenantEntity();

        // Act
        entity.setIgrejaId(1L);
        assertThat(entity.getIgrejaId()).isEqualTo(1L);

        entity.setIgrejaId(2L);
        assertThat(entity.getIgrejaId()).isEqualTo(2L);

        entity.setIgrejaId(100L);

        // Assert
        assertThat(entity.getIgrejaId()).isEqualTo(100L);
    }

    @Test
    void equals_WithSameIgrejaId_ShouldBeEqual() {
        // Arrange
        TestTenantEntity entity1 = new TestTenantEntity();
        entity1.setIgrejaId(1L);

        TestTenantEntity entity2 = new TestTenantEntity();
        entity2.setIgrejaId(1L);

        // Act & Assert
        assertThat(entity1).isEqualTo(entity2);
    }

    @Test
    void hashCode_WithSameIgrejaId_ShouldBeSame() {
        // Arrange
        TestTenantEntity entity1 = new TestTenantEntity();
        entity1.setIgrejaId(1L);

        TestTenantEntity entity2 = new TestTenantEntity();
        entity2.setIgrejaId(1L);

        // Act & Assert
        assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
    }

    @Test
    void toString_ShouldContainIgrejaId() {
        // Arrange
        TestTenantEntity entity = new TestTenantEntity();
        entity.setIgrejaId(42L);

        // Act
        String result = entity.toString();

        // Assert
        assertThat(result).contains("42");
        assertThat(result).contains("igrejaId");
    }

    @Test
    void multipleEntities_ShouldHaveIndependentIgrejaIds() {
        // Arrange & Act
        TestTenantEntity entity1 = new TestTenantEntity();
        entity1.setIgrejaId(1L);

        TestTenantEntity entity2 = new TestTenantEntity();
        entity2.setIgrejaId(2L);

        TestTenantEntity entity3 = new TestTenantEntity();
        entity3.setIgrejaId(3L);

        // Assert
        assertThat(entity1.getIgrejaId()).isEqualTo(1L);
        assertThat(entity2.getIgrejaId()).isEqualTo(2L);
        assertThat(entity3.getIgrejaId()).isEqualTo(3L);
    }
}
