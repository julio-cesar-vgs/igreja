package br.com.igreja.ipiranga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Ponto de entrada (Main Class) da aplicação Spring Boot "Igreja Ipiranga".
 *
 * <p>
 * Este sistema é uma plataforma multi-tenant para gestão eclesiástica, abrangendo:
 * <ul>
 *     <li>Gestão de Membros e Identidade (Módulo Identity)</li>
 *     <li>Gestão de Cultos e Liturgia (Módulo Culto)</li>
 *     <li>Gestão Financeira e Tesouraria (Módulo Financeiro)</li>
 *     <li>Auditoria e Segurança (Módulos Audit e Infrastructure)</li>
 * </ul>
 * </p>
 * <p>
 * A anotação {@code @SpringBootApplication} habilita o component scan e autoconfiguração.
 * Também ativamos explicitamente:
 * <ul>
 *     <li>{@code @EnableKafka}: Para o sistema de mensageria assíncrona.</li>
 *     <li>{@code @EnableCaching}: Para cache distribuído (ex: Redis).</li>
 * </ul>
 * </p>
 *
 * @author Equipe de Desenvolvimento Ipiranga
 * @version 1.0.0
 */
@SpringBootApplication
@EnableKafka
@EnableCaching
public class IgrejaApplication {

    /**
     * Método principal que inicializa o contexto do Spring Boot.
     *
     * @param args Argumentos de linha de comando.
     */
    public static void main(String[] args) {
        SpringApplication.run(IgrejaApplication.class, args);
    }

}
