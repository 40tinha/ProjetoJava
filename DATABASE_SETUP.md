# 📊 Configuração do Banco de Dados MySQL

## 📋 Índice
1. [Pré-requisitos](#pré-requisitos)
2. [Criar o Banco de Dados](#criar-o-banco-de-dados)
3. [Configuração de Usuário](#configuração-de-usuário-opcional)
4. [Configuração da Aplicação](#configuração-da-aplicação)
5. [Estrutura do Banco de Dados](#estrutura-do-banco-de-dados)
6. [Scripts SQL](#scripts-sql)
7. [Executar a Aplicação](#executar-a-aplicação)
8. [Verificar as Tabelas Criadas](#verificar-as-tabelas-criadas)
9. [Repositórios Disponíveis](#repositórios-disponíveis)
10. [Solucionar Problemas](#solucionar-problemas)

---

## 🔧 Pré-requisitos

- **MySQL Server** instalado (versão 5.7 ou superior, recomenda-se 8.0+)
- **MySQL Workbench** ou outra ferramenta de gerenciamento (opcional)
- **Java 17** ou superior
- **Maven 3.6+**
- **Spring Boot 3.2+**

### ✅ Verificar Versões

```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version

# Verificar MySQL
mysql --version
```

---

## 🗄️ Criar o Banco de Dados

Abra seu terminal MySQL e execute:

```sql
-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS futebolcamisas 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Selecionar o banco
USE futebolcamisas;

-- Verificar se foi criado
SHOW DATABASES;
SHOW CREATE DATABASE futebolcamisas;
```

---

## 👤 Configuração de Usuário (Opcional)

Se quiser usar um usuário diferente de **root** (recomendado para produção):

```sql
-- Criar usuário com senha segura
CREATE USER IF NOT EXISTS 'futebolcamisas'@'localhost' IDENTIFIED BY 'SenhaForte123!@#';

-- Conceder permissões completas
GRANT ALL PRIVILEGES ON futebolcamisas.* TO 'futebolcamisas'@'localhost';

-- Recarregar privilégios
FLUSH PRIVILEGES;

-- Verificar usuário criado
SELECT User, Host FROM mysql.user WHERE User='futebolcamisas';
```

---

## ⚙️ Configuração da Aplicação

### 📝 Arquivo `application.properties`

O arquivo está localizado em: `src/main/resources/application.properties`

```properties
# ===================================
# MySQL Configuration
# ===================================
spring.datasource.url=jdbc:mysql://localhost:3306/futebolcamisas?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ===================================
# JPA/Hibernate Configuration
# ===================================
# Estratégia de criação de tabelas
# create: cria do zero (cuidado! deleta dados)
# update: atualiza estrutura existente (RECOMENDADO para desenvolvimento)
# validate: apenas valida (usar em produção)
spring.jpa.hibernate.ddl-auto=update

# Mostrar SQL executado no console
spring.jpa.show-sql=true

# Formatar SQL de forma legível
spring.jpa.properties.hibernate.format_sql=true

# Mostrar bind parameters
spring.jpa.properties.hibernate.use_sql_comments=true

# Dialect MySQL
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

# ===================================
# Connection Pool
# ===================================
# HikariCP - Pool de conexões (recomendado)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.max-lifetime=1200000

# ===================================
# Server Configuration
# ===================================
server.port=8080
server.servlet.context-path=/

# ===================================
# Logging
# ===================================
logging.level.root=INFO
logging.level.com.futebolcamisas=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### 🔐 Se usou usuário diferente

Atualize apenas essas linhas:

```properties
spring.datasource.username=futebolcamisas
spring.datasource.password=SenhaForte123!@#
```

### 📋 Arquivo `application.yml` (Alternativa)

Se preferir usar YAML ao invés de properties:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/futebolcamisas?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 20000
      idle-timeout: 300000
      max-lifetime: 1200000

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL8Dialect
    properties:
      hibernate:
        format_sql: true
        use_sql_comments: true

server:
  port: 8080
  servlet:
    context-path: /

logging:
  level:
    root: INFO
    com.futebolcamisas: DEBUG
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
```

---

## 🏗️ Estrutura do Banco de Dados

O Hibernate irá criar automaticamente as seguintes tabelas:

### 📊 Diagrama de Relacionamentos

```
usuario (1) ────── (N) carrinho
usuario (1) ────── (N) avaliacao

produto (1) ────── (N) item_carrinho
produto (1) ────── (N) avaliacao

carrinho (1) ────── (N) item_carrinho
```

### 📑 Tabelas Criadas

| Tabela | Descrição | Relacionamentos |
|--------|-----------|-----------------|
| `usuario` | Usuários da aplicação (clientes e admins) | 1:N com carrinho, 1:N com avaliacao |
| `produto` | ✅ Produtos disponíveis (migrado de anuncio) | 1:N com item_carrinho, 1:N com avaliacao |
| `carrinho` | Carrinhos de compra dos usuários | 1:N com item_carrinho, N:1 com usuario |
| `item_carrinho` | Itens dentro de cada carrinho | N:1 com carrinho, N:1 com produto |
| `avaliacao` | Avaliações de produtos | N:1 com produto, N:1 com usuario |

---

## 📝 Scripts SQL

### ✅ Script de Criação Completa (sem Hibernate)

Se quiser criar manualmente sem depender do Hibernate:

```sql
-- Usar banco
USE futebolcamisas;

-- ========== TABELA USUARIO ==========
CREATE TABLE usuario (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'USER', -- USER ou ADMIN
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========== TABELA PRODUTO ==========
CREATE TABLE produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    descricao LONGTEXT,
    preco DECIMAL(10, 2) NOT NULL,
    marca VARCHAR(100),
    modelo VARCHAR(100),
    tamanho VARCHAR(50),
    cor VARCHAR(100),
    time VARCHAR(100),
    temporada VARCHAR(50),
    estoque INT DEFAULT 0,
    url_imagem VARCHAR(500),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_time (time),
    INDEX idx_marca (marca),
    FULLTEXT idx_search (titulo, descricao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========== TABELA CARRINHO ==========
CREATE TABLE carrinho (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    UNIQUE KEY uk_usuario_carrinho (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========== TABELA ITEM_CARRINHO ==========
CREATE TABLE item_carrinho (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    carrinho_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    data_adicao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (carrinho_id) REFERENCES carrinho(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE RESTRICT,
    UNIQUE KEY uk_carrinho_produto (carrinho_id, produto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========== TABELA AVALIACAO ==========
CREATE TABLE avaliacao (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    produto_id BIGINT NOT NULL,
    autor VARCHAR(255) NOT NULL,
    texto LONGTEXT,
    estrelas INT NOT NULL CHECK (estrelas >= 1 AND estrelas <= 5),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE,
    INDEX idx_produto_avaliacao (produto_id),
    INDEX idx_data_criacao (data_criacao DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========== CRIAR ÍNDICES ==========
CREATE INDEX idx_usuario_id ON carrinho(usuario_id);
CREATE INDEX idx_carrinho_id ON item_carrinho(carrinho_id);
CREATE INDEX idx_produto_id ON item_carrinho(produto_id);
CREATE INDEX idx_avaliacao_produto ON avaliacao(produto_id);

-- ========== DADOS DE TESTE ==========
-- Usuário Admin
INSERT INTO usuario (nome, email, senha, role) 
VALUES ('Admin', 'admin@futebolcamisas.com', 'admin123', 'ADMIN');

-- Usuário Cliente
INSERT INTO usuario (nome, email, senha, role) 
VALUES ('João Silva', 'joao@email.com', 'senha123', 'USER');

-- Produtos
INSERT INTO produto (titulo, descricao, preco, marca, modelo, tamanho, cor, time, temporada, estoque, url_imagem) 
VALUES 
('Camisa Corinthians 2024/25', 'Camisa oficial do Corinthians temporada 2024/25', 250.00, 'Nike', 'Oficial', 'M', 'Preto e Branco', 'Corinthians', '2024/25', 50, 'https://via.placeholder.com/500x500?text=Corinthians'),
('Camisa Flamengo 2024/25', 'Camisa oficial do Flamengo temporada 2024/25', 280.00, 'Adidas', 'Oficial', 'G', 'Vermelho e Preto', 'Flamengo', '2024/25', 30, 'https://via.placeholder.com/500x500?text=Flamengo'),
('Camisa Palmeiras 2024/25', 'Camisa oficial do Palmeiras temporada 2024/25', 270.00, 'Puma', 'Oficial', 'P', 'Verde', 'Palmeiras', '2024/25', 25, 'https://via.placeholder.com/500x500?text=Palmeiras');
```

### 🔄 Script de Migração (Anúncio → Produto)

Se você tinha dados na tabela `anuncio` antiga:

```sql
-- Backup da tabela antiga
CREATE TABLE anuncio_backup AS SELECT * FROM anuncio;

-- Renomear coluna (se ainda existir)
ALTER TABLE avaliacao 
DROP FOREIGN KEY avaliacao_ibfk_1;

ALTER TABLE avaliacao 
DROP COLUMN anuncio_id;

ALTER TABLE avaliacao 
ADD COLUMN produto_id BIGINT NOT NULL;

ALTER TABLE avaliacao 
ADD FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE;

-- Adicionar coluna data_criacao se não existir
ALTER TABLE avaliacao 
ADD COLUMN data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Deletar tabela anuncio antiga
DROP TABLE IF EXISTS anuncio;
```

---

## ▶️ Executar a Aplicação

### Via Maven

```bash
# Compilar e instalar dependências
mvn clean install

# Executar a aplicação
mvn spring-boot:run
```

### Via IDE (IntelliJ IDEA)

1. Abra o projeto
2. Vá em `Run` → `Run 'FutebolcamisasApplication'`
3. Ou clique em ▶️ ao lado da classe main

### Via IDE (VS Code)

1. Instale "Extension Pack for Java"
2. Abra o arquivo `FutebolcamisasApplication.java`
3. Clique em "Run" acima do método `main`

### Via JAR

```bash
# Empacotar como JAR
mvn clean package

# Executar
java -jar target/futebolcamisas-1.0.0.jar
```

---

## 🔍 Verificar as Tabelas Criadas

Depois de rodar a aplicação pela primeira vez:

```sql
-- Usar o banco
USE futebolcamisas;

-- Listar todas as tabelas
SHOW TABLES;

-- Ver estrutura de cada tabela
DESC usuario;
DESC produto;
DESC carrinho;
DESC item_carrinho;
DESC avaliacao;

-- Contar registros
SELECT COUNT(*) FROM usuario;
SELECT COUNT(*) FROM produto;
SELECT COUNT(*) FROM avaliacao;

-- Ver estrutura completa (criar table)
SHOW CREATE TABLE produto\G

-- Consultar dados
SELECT * FROM usuario;
SELECT * FROM produto;
SELECT * FROM avaliacao;
```

---

## 📚 Repositórios Disponíveis

O projeto possui os seguintes repositórios Spring Data JPA:

### `ProdutoRepository`
```java
// Gerenciar produtos
List<Produto> findAll();
List<Produto> findByTime(String time);
List<Produto> findByMarca(String marca);
Optional<Produto> findById(Long id);
```

### `UsuarioRepository`
```java
// Gerenciar usuários
Optional<Usuario> findByEmail(String email);
Optional<Usuario> findById(Long id);
boolean existsByEmail(String email);
```

### `CarrinhoRepository`
```java
// Gerenciar carrinhos
Optional<Carrinho> findByUsuarioId(Long usuarioId);
Optional<Carrinho> findById(Long id);
```

### `ItemCarrinhoRepository`
```java
// Gerenciar itens do carrinho
List<ItemCarrinho> findByCarrinhoId(Long carrinhoId);
Optional<ItemCarrinho> findByCarrinhoIdAndProdutoId(Long carrinhoId, Long produtoId);
```

### `AvaliacaoRepository`
```java
// Gerenciar avaliações
List<Avaliacao> findByProdutoId(Long produtoId);
List<Avaliacao> findByProdutoIdOrderByDataCriacaoDesc(Long produtoId);
```

---

## 🔧 Classe DatabaseConfig

A classe `DatabaseConfig.java` está localizada em:
```
src/main/java/com/futebolcamisas/config/DatabaseConfig.java
```

Ela ativa:
- ✅ Escan automático de repositórios JPA
- ✅ Escan de entidades no pacote `model`
- ✅ Suporte a transações
- ✅ Auditoria (se configurado)

```java
@Configuration
@EnableJpaRepositories(basePackages = "com.futebolcamisas.repository")
@EntityScan(basePackages = "com.futebolcamisas.model")
@EnableTransactionManagement
public class DatabaseConfig {
    // Configurações adicionais
}
```

---

## ⚠️ Solucionar Problemas

### ❌ Erro: "Access denied for user 'root'@'localhost'"

**Causa**: Credenciais incorretas ou MySQL não está rodando

**Solução**:
```bash
# Verificar se MySQL está rodando
sudo service mysql status

# Iniciar MySQL
sudo service mysql start

# Testar conexão
mysql -u root -p
```

### ❌ Erro: "Unknown database 'futebolcamisas'"

**Causa**: Banco de dados não foi criado

**Solução**:
```sql
CREATE DATABASE futebolcamisas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### ❌ Erro: "Driver not found"

**Causa**: Dependência do MySQL Connector não instalada

**Solução** - Verificar `pom.xml`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

### ❌ Erro: "Hibernate: connect the first time"

**Causa**: Problemas de conexão no boot

**Solução**:
```bash
# Limpar e recompilar
mvn clean install -DskipTests

# Executar novamente
mvn spring-boot:run
```

### ❌ Erro: "Port 8080 already in use"

**Causa**: Outra aplicação usando a porta 8080

**Solução** - Mudar em `application.properties`:
```properties
server.port=8081
```

### ❌ Erro: "Anuncio table not found"

**Causa**: Tabela antiga ainda está sendo referenciada

**Solução**:
```sql
-- Verificar entidades
SHOW TABLES;

-- Deletar tabela anuncio se existir
DROP TABLE IF EXISTS anuncio;

-- Reiniciar aplicação
```

---

## 🚀 Próximos Passos

Agora que o banco está configurado:

1. ✅ Verifique as entidades com anotações JPA (`@Entity`, `@Table`, `@Id`, etc)
2. ✅ Injete os Repositories nos Services
3. ✅ Implemente a lógica de negócio
4. ✅ Use os Controllers para expor os endpoints REST
5. ✅ Configure segurança (Spring Security, JWT, etc)
6. ✅ Implemente cache (Redis, etc)
7. ✅ Configure testes unitários

---

## 📋 Checklist de Configuração

- [ ] MySQL Server instalado e rodando
- [ ] Banco `futebolcamisas` criado
- [ ] `application.properties` atualizado com credenciais
- [ ] Dependências Maven baixadas (`mvn clean install`)
- [ ] Aplicação iniciada sem erros (`mvn spring-boot:run`)
- [ ] Tabelas criadas automaticamente pelo Hibernate
- [ ] Dados de teste inseridos
- [ ] Consultas funcionando normalmente
- [ ] Repositórios injetados nos Services
- [ ] Controllers testados com Postman/Insomnia

---

## 📞 Suporte

Se encontrar problemas:

1. Verifique os logs: `mvn spring-boot:run` (console output)
2. Ative debug em `application.properties`: `logging.level.root=DEBUG`
3. Verifique conexão MySQL: `mysql -u root -p futebolcamisas`
4. Consulte documentação: [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

---

**Versão**: 2.0  
**Data**: Novembro 2025  
**Status**: ✅ Migrado de Anúncio para Produto  
**Projeto**: Futebol Camisas - Loja Online
