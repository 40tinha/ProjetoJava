# Guia de Integração com phpMyAdmin

## O que é o phpMyAdmin?

O **phpMyAdmin** é uma interface web gratuita para gerenciar bancos de dados MySQL/MariaDB. Ele não se "integra" diretamente ao código Java, mas permite que você administre visualmente o mesmo banco de dados que sua aplicação Java utiliza.

## Pré-requisitos

- XAMPP, WAMP, LAMP ou MAMP instalado (inclui Apache, MySQL e phpMyAdmin)
- Seu ProjetoJava configurado e funcionando
- Navegador web

## Passo 1: Acessar o phpMyAdmin

1. Certifique-se de que o **MySQL/MariaDB está rodando**
   - Se usar XAMPP: Abra o painel de controle e inicie "Apache" e "MySQL"
   - Se usar WAMP: Clique no ícone e certifique-se de que está verde

2. Abra seu navegador e acesse:
   ```
   http://localhost/phpmyadmin
   ```

3. Faça login com as credenciais:
   - **Usuário**: `root`
   - **Senha**: `root` (ou deixe em branco se não houver senha)

## Passo 2: Criar o Banco de Dados

1. No phpMyAdmin, clique na aba **"Bases de dados"** ou **"Databases"**

2. No campo **"Criar base de dados"**:
   - Digite: `futebolcamisas`
   - Selecione: `utf8mb4_unicode_ci` (charset)
   - Clique em **"Criar"**

3. O banco `futebolcamisas` agora aparece na lista à esquerda

## Passo 3: Verificar as Configurações do Projeto

Seu arquivo `application.properties` já está configurado corretamente:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/futebolcamisas?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

**Importante**: O usuário e senha do phpMyAdmin devem ser **os mesmos** do `application.properties`:
- Se no phpMyAdmin você usa `root` sem senha, altere para:
  ```properties
  spring.datasource.password=
  ```
- Se você definiu uma senha no MySQL, use a mesma senha nos dois lugares

## Passo 4: Executar o Projeto e Criar as Tabelas Automaticamente

Graças ao Spring Boot e Hibernate, as tabelas são criadas automaticamente!

1. No VS Code, abra o terminal do projeto

2. Execute:
   ```bash
   mvn spring-boot:run
   ```

3. O Hibernate irá criar automaticamente as seguintes tabelas:
   - `usuario`
   - `produto`
   - `anuncio`
   - `carrinho`
   - `item_carrinho`

4. Volte ao phpMyAdmin e atualize a página. Você verá as tabelas na lista à esquerda!

## Passo 5: Usar o phpMyAdmin para Gerenciar Dados

### Visualizar Dados
1. Clique em uma tabela (ex: `usuario`)
2. Clique na aba **"Examinar"** ou **"Browse"**
3. Você verá todos os registros da tabela

### Inserir Dados Manualmente
1. Clique na tabela desejada
2. Clique na aba **"Inserir"** ou **"Insert"**
3. Preencha os campos e clique em **"Executar"**

### Editar Dados
1. Na aba "Examinar", clique no ícone de lápis (✏️) ao lado do registro
2. Altere os valores e salve

### Executar Queries SQL
1. Clique na aba **"SQL"**
2. Digite sua query, por exemplo:
   ```sql
   SELECT * FROM usuario WHERE email = 'joao@example.com';
   ```
3. Clique em **"Executar"**

### Exportar Banco de Dados (Backup)
1. Selecione o banco `futebolcamisas`
2. Clique na aba **"Exportar"** ou **"Export"**
3. Escolha o formato (SQL é recomendado)
4. Clique em **"Executar"**

### Importar Banco de Dados
1. Clique na aba **"Importar"** ou **"Import"**
2. Escolha o arquivo `.sql`
3. Clique em **"Executar"**

## Passo 6: Popular o Banco com Dados de Teste

Você pode inserir dados de exemplo diretamente no phpMyAdmin:

### Exemplo: Inserir um Usuário
```sql
INSERT INTO usuario (nome, email, senha, telefone, endereco) 
VALUES ('João Victor', 'joao@example.com', 'senha123', '11999999999', 'Rua Exemplo, 123');
```

### Exemplo: Inserir um Produto
```sql
INSERT INTO produto (nome, descricao, preco, estoque, imagem_url) 
VALUES ('Camisa Brasil 2025', 'Camisa oficial da seleção', 299.90, 50, '/images/brasil.jpg');
```

## Workflow Completo

```
┌─────────────────┐          ┌──────────────┐          ┌─────────────────┐
│   phpMyAdmin    │          │    MySQL     │          │  ProjetoJava    │
│  (Interface)    │  ◄────►  │  (Servidor)  │  ◄────►  │  (Aplicação)    │
└─────────────────┘          └──────────────┘          └─────────────────┘
     Gerencia                   Armazena                  Usa dados via
  visualmente os                 os dados                    JDBC
      dados
```

- **phpMyAdmin**: Ferramenta visual para criar/modificar estruturas, inserir dados manualmente, fazer backups
- **MySQL Server**: O banco de dados propriamente dito
- **ProjetoJava**: Conecta-se ao MySQL e manipula dados através do código Java

## Diferenças entre phpMyAdmin e MySQL Workbench

| Recurso | phpMyAdmin | MySQL Workbench |
|---------|------------|-----------------|
| Tipo | Interface web | Aplicativo desktop |
| Instalação | Vem com XAMPP/WAMP | Download separado |
| Facilidade | Mais simples | Mais recursos avançados |
| Modelagem | Básica | Diagramas ER completos |
| Acesso | Navegador | Software nativo |

Use o phpMyAdmin para tarefas rápidas e MySQL Workbench para modelagem avançada.

## Solução de Problemas

### ❌ "Não consigo acessar http://localhost/phpmyadmin"
- Verifique se o Apache está rodando no XAMPP/WAMP
- Tente `http://127.0.0.1/phpmyadmin`

### ❌ "Access denied for user 'root'@'localhost'"
- Verifique se a senha em `application.properties` corresponde à senha do MySQL
- No phpMyAdmin, vá em **Contas de utilizador** para verificar/alterar senhas

### ❌ "Unknown database 'futebolcamisas'"
- Crie o banco manualmente no phpMyAdmin (Passo 2)

### ❌ "As tabelas não aparecem no phpMyAdmin"
- Execute o projeto Java pelo menos uma vez para o Hibernate criar as tabelas
- Atualize a página do phpMyAdmin (F5)

### ❌ "The connection was refused"
- O MySQL não está rodando. Inicie-o pelo XAMPP/WAMP

## Comandos Úteis SQL

### Ver todas as tabelas
```sql
SHOW TABLES;
```

### Ver estrutura de uma tabela
```sql
DESC usuario;
```

### Deletar todos os dados de uma tabela (cuidado!)
```sql
TRUNCATE TABLE usuario;
```

### Ver quantos registros existem
```sql
SELECT COUNT(*) FROM usuario;
```

## Próximos Passos

Agora que você já sabe usar o phpMyAdmin:

1. ✅ Crie o banco de dados `futebolcamisas`
2. ✅ Execute o projeto Java para criar as tabelas automaticamente
3. ✅ Insira dados de teste pelo phpMyAdmin
4. ✅ Acesse a aplicação e veja os dados sendo exibidos
5. ✅ Use o phpMyAdmin para monitorar os dados enquanto desenvolve

---

**Dica Final**: Mantenha o phpMyAdmin aberto em uma aba enquanto desenvolve. Assim você pode ver em tempo real os dados sendo criados/modificados pela sua aplicação Java!
