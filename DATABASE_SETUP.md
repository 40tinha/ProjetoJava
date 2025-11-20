# Configuração do Banco de Dados MySQL

## 1. Pré-requisitos

- MySQL Server instalado (versão 5.7 ou superior)
- MySQL Workbench ou outra ferramenta de gerenciamento (opcional)
- Java 17 ou superior
- Maven 3.6+

## 2. Criar o Banco de Dados

Abra seu terminal MySQL e execute:

```sql
-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS futebolcamisas;

-- Verificar se foi criado
SHOW DATABASES;
```

## 3. Configuração de Usuário (Opcional)

Se quiser usar um usuário diferente de root:

```sql
-- Criar usuário
CREATE USER 'futebolcamisas'@'localhost' IDENTIFIED BY 'sua_senha';

-- Conceder permissões
GRANT ALL PRIVILEGES ON futebolcamisas.* TO 'futebolcamisas'@'localhost';
FLUSH PRIVILEGES;
```

## 4. Configuração da Aplicação

O arquivo `application.properties` já está configurado com:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/futebolcamisas?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

**Se usou um usuário diferente**, atualize as linhas:
```properties
spring.datasource.username=futebolcamisas
spring.datasource.password=sua_senha
```

## 5. Estrutura do Banco de Dados

O Hibernate irá criar automaticamente as seguintes tabelas:

- `usuario` - Dados dos usuários
- `produto` - Produtos disponíveis
- `anuncio` - Anúncios de tênis
- `carrinho` - Carrinhos de compra dos usuários
- `item_carrinho` - Itens dentro de cada carrinho

## 6. Executar a Aplicação

```bash
# No diretório do projeto
mvn clean install
mvn spring-boot:run
```

Ou pelo VS Code:
1. Abra a pasta do projeto
2. Execute `mvn spring-boot:run` no terminal

## 7. Verificar as Tabelas Criadas

Depois de rodar a aplicação pela primeira vez, verifique se as tabelas foram criadas:

```sql
USE futebolcamisas;
SHOW TABLES;

-- Ver estrutura de uma tabela
DESC usuario;
```

## 8. Repositórios Disponíveis

O projeto possui os seguintes repositórios Spring Data JPA:

- `ProdutoRepository` - Gerenciar produtos
- `UsuarioRepository` - Gerenciar usuários (com busca por email)
- `AnuncioRepository` - Gerenciar anúncios
- `CarrinhoRepository` - Gerenciar carrinhos (com busca por usuário)
- `ItemCarrinhoRepository` - Gerenciar itens do carrinho

## 9. Configuração da Classe DatabaseConfig

A classe `DatabaseConfig.java` está localizada em:
```
src/main/java/com/futebolcamisas/config/DatabaseConfig.java
```

Ela ativa:
- Escan automático de repositórios JPA
- Escan de entidades no pacote model
- Suporte a transações

## 10. Solucionar Problemas

### Erro: "Access denied for user"
- Verifique se o MySQL está rodando
- Verifique as credenciais em `application.properties`

### Erro: "Unknown database"
- Crie o banco de dados manualmente (ver seção 2)

### Erro: "Driver not found"
- Certifique-se de que o `pom.xml` tem a dependência do MySQL Connector

## 11. Próximos Passos

Agora que o banco está configurado:

1. Atualize as entidades com anotações JPA (@Entity, @Table, @Id, etc)
2. Injete os Repositories nos Services
3. Implemente a lógica de negócio
4. Use os Controllers para expor os endpoints REST

---

**Versão**: 1.0  
**Data**: Novembro 2025  
**Projeto**: Futebol Camisas - Loja Online
