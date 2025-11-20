package com.futebolcamisas.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuração do banco de dados MySQL com JPA/Hibernate
 * 
 * Esta classe configura:
 * - Scan de entidades JPA no pacote model
 * - Ativação de repositórios Spring Data JPA
 * - Suporte a transações
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.futebolcamisas.repository")
@EntityScan(basePackages = "com.futebolcamisas.model")
@EnableTransactionManagement
public class DatabaseConfig {
    
    /**
     * Configuração automática do Spring Boot:
     * 
     * O Spring Boot detecta automaticamente:
     * - DataSource: a partir de spring.datasource.* no application.properties
     * - JdbcTemplate e NamedParameterJdbcTemplate
     * - Transaction Manager
     * - EntityManagerFactory
     * - JpaTransactionManager
     * 
     * Por isso não precisamos configurá-los manualmente nesta classe.
     * 
     * A configuração está em: src/main/resources/application.properties
     * 
     * Propriedades essenciais:
     * - spring.datasource.url
     * - spring.datasource.username
     * - spring.datasource.password
     * - spring.jpa.hibernate.ddl-auto
     * - spring.jpa.show-sql
     */
}
