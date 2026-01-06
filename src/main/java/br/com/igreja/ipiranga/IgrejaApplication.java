package br.com.igreja.ipiranga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Classe principal de inicialização do Spring Boot.
 * Sistema de Gestão de Igrejas (Ipiranga)
 * 
 * Habilita o suporte a Kafka e Cache (Redis) em toda a aplicação.
 */
@SpringBootApplication
@EnableKafka
@EnableCaching
public class IgrejaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IgrejaApplication.class, args);
    }

}
