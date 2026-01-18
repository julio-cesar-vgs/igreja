package br.com.igreja.ipiranga;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Requer ambiente completo (MySQL, Kafka, Redis)")
class IgrejaApplicationTests {

    @Test
    void contextLoads() {
    }

}
