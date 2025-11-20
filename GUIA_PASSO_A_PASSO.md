# Guia Passo a Passo - Integração MySQL com Spring Data JPA

## 🚀 Introdução

Este guia vai te mostrar exatamente como conectar seu projeto Java Spring Boot com MySQL usando Spring Data JPA.

---

## PASSO 1: Instalar MySQL Server

### Windows:
1. Acesse: https://dev.mysql.com/downloads/mysql/
2. Baixe a versão 8.0 Community Server
3. Execute o instalador
4. Escolha "Server only" (ou instale tudo se preferir)
5. Configure a porta como 3306 (padrão)
6. Defina uma senha para o usuário root (ex: `root`)

### macOS:
```bash
brew install mysql
brew services start mysql
mysql_secure_installation
```

### Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

---

## PASSO 2: Verificar a Instalação do MySQL

### Abra um terminal/cmd e execute:
```bash
mysql -u root -p
```

### Digite sua senha (ex: `root`)

### Você deve ver:
```
mysql>
```

### Para sair, digite:
```sql
EXIT;
```

---

## PASSO 3: Clonar e Abrir o Projeto no VS Code

### 1. Abra o terminal/cmd

### 2. Clone o repositório:
```bash
git clone https://github.com/40tinha/ProjetoJava.git
cd ProjetoJava
```

### 3. Abra no VS Code:
```bash
code .
```

### 4. O VS Code vai sugerir instalar extenões Java. Clique em "Install All" (ou instale:
- Extension Pack for Java
- Spring Boot Extension Pack

---

## PASSO 4: Criar o Banco de Dados MySQL

### 1. Abra um novo terminal no VS Code (Ctrl + `)

### 2. Conecte ao MySQL:
```bash
mysql -u root -p
```

### 3. Digite sua senha

### 4. Execute estes comandos SQL (um de cada vez):

```sql
CREATE DATABASE futebolcamisas;
```

```sql
SHOW DATABASES;
```

### Vocé deve ver `futebolcamisas` na lista.

### 5. Digite `EXIT;` para sair

---

## PASSO 5: Verificar o Arquivo application.properties

### 1. No VS Code, abra o arquivo:
```
src/main/resources/application.properties
```

### 2. Verifique se tem estas linhas:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/futebolcamisas?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

### ⚠️ Se sua senha do MySQL é diferente:

Altere apenas esta linha:
```properties
spring.datasource.password=SUA_SENHA_AQUI
```

### Se criou um usuário diferente de root:

```properties
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

---

## PASSO 6: Fazer Build do Projeto

### 1. Abra um terminal no VS Code (Ctrl + `)

### 2. Execute:
```bash
mvn clean install
```

### 3. Aguarde (pode levar 2-5 minutos na primeira vez)

### ✅ Sucesso se ver:
```
[INFO] BUILD SUCCESS
```

### ❌ Se ver erros de Maven:
- Instale Maven: https://maven.apache.org/download.cgi
- Ou use: `mvn -v` para verificar se tem Maven instalado

---

## PASSO 7: Executar a Aplicação

### Opção A: Pelo Terminal

```bash
mvn spring-boot:run
```

Você deve ver:
```
Tomcat started on port(s): 8080 (http)
```

### Opção B: Pelo VS Code

1. Abra a classe `FutebolCamisasApplication.java`
2. Clique no "Run" acima do `main` method
3. A aplicação vai iniciar

---

## PASSO 8: Verificar se as Tabelas Foram Criadas

### 1. Abra um novo terminal

### 2. Conecte ao MySQL:
```bash
mysql -u root -p
```

### 3. Selecione o banco:
```sql
USE futebolcamisas;
```

### 4. Liste as tabelas:
```sql
SHOW TABLES;
```

### Vocé deve ver algo como:
```
+----------------------------+
| Tables_in_futebolcamisas   |
+----------------------------+
| usuario                    |
| produto                    |
| anuncio                    |
| carrinho                   |
| item_carrinho              |
+----------------------------+
```

### ✅ Se aparecer, a conexão funcionou!

### Ver estrutura de uma tabela:
```sql
DESC usuario;
```

---

## PASSO 9: Testar os Endpoints da API

### 1. Abra o Postman ou Insomnia (ou use o navegador para GET)

### 2. Teste um GET simples:
```
GET http://localhost:8080/
```

### 3. Se configurou controllers REST, teste:
```
GET http://localhost:8080/api/produtos
```

---

## PASSO 10: Usar os Repositories nos Services

### Exemplo de como usar o ProdutoRepository em um Service:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.futebolcamisas.repository.ProdutoRepository;
import com.futebolcamisas.model.Produto;
import java.util.List;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    // Listar todos
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
    
    // Buscar por ID
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }
    
    // Salvar
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
    
    // Deletar
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }
}
```

---

## PASSO 11: Adicionar Anotações JPA nas Entidades

### Exemplo de como anotar a classe Produto:

```java
import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome", length = 100)
    private String nome;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "preco")
    private Double preco;
    
    // ... getters e setters
}
```

---

## PASSO 12: Solucionar Problemas

### " Access denied for user 'root'@'localhost' "
- Verifique a senha em `application.properties`
- Resetar senha: `mysql -u root` (sem -p)

### "Unknown database 'futebolcamisas'"
- Crie o banco: `CREATE DATABASE futebolcamisas;`

### "Driver not found"
- Execute: `mvn clean install`
- Verifique se tem conexão com internet

### "Port 8080 already in use"
- Mude em `application.properties`:
  ```properties
  server.port=8081
  ```

### MySQL não inicia
- Windows: Services > MySQL80 > Start
- macOS: `brew services restart mysql`
- Linux: `sudo systemctl restart mysql`

---

## ✔️ Checklist de Verificação

- [ ] MySQL instalado e rodando
- [ ] Banco `futebolcamisas` criado
- [ ] `application.properties` configurado
- [ ] Projeto clonado no VS Code
- [ ] `mvn clean install` executado com sucesso
- [ ] Aplicação rodando em http://localhost:8080
- [ ] Tabelas criadas no MySQL
- [ ] Repositories injetados nos Services
- [ ] Entidades com anotações JPA
- [ ] Endpoints testados

---

## 🚀 Próximos Passos

1. Implemente os Métodos dos Repositories
2. Crie os Services com lógica de negócio
3. Implemente os Controllers REST
4. Crie os templates HTML com Thymeleaf
5. Teste toda a aplicação

---

**Dúvidas?** Confira:
- DATABASE_SETUP.md
- README.md
- Spring Data JPA Docs: https://spring.io/projects/spring-data-jpa

**Versão**: 2.0  
**Data**: Novembro 2025  
**Autor**: Your Team
