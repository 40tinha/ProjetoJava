# ⚽ Futebol Camisas - Loja Online

Sistema de e-commerce para venda de camisas de futebol desenvolvido em **Java com Spring Boot** seguindo o padrão **MVC**.

![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)
![Java](https://img.shields.io/badge/Java-17+-green)
![Spring%20Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![License](https://img.shields.io/badge/License-MIT-blue)

---

## 📋 Índice

1. [Tecnologias](#tecnologias-utilizadas)
2. [Estrutura do Projeto](#estrutura-do-projeto-mvc)
3. [Funcionalidades](#funcionalidades)
4. [Como Executar](#como-executar)
5. [Endpoints](#endpoints)
6. [Dados de Teste](#dados-de-teste)
7. [Arquitetura MVC](#arquitetura-mvc)
8. [Novas Funcionalidades](#novas-funcionalidades-implementadas)
9. [Roadmap](#roadmap---próximos-passos)
10. [Contribuir](#como-contribuir)

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Propósito |
|-----------|--------|----------|
| **Java** | 17+ | Linguagem principal |
| **Spring Boot** | 3.2.0 | Framework web |
| **Spring MVC** | 3.2.0 | Padrão arquitetural |
| **Spring Data JPA** | 3.2.0 | ✨ Persistência de dados |
| **Thymeleaf** | 3.1.x | Template Engine |
| **Hibernate** | 6.2.x | ✨ ORM (Object-Relational Mapping) |
| **MySQL** | 8.0+ | ✨ Banco de dados |
| **Maven** | 3.6+ | Gerenciamento de dependências |
| **Bootstrap** | 5.3.0 | Framework CSS |
| **Font Awesome** | 6.4.0 | Ícones |
| **Jakarta Persistence** | 3.1.0 | JPA Spec |

---

## 📁 Estrutura do Projeto (MVC)

```
futebolcamisas/
│
├── src/main/java/com/futebolcamisas/
│   ├── FutebolcamisasApplication.java         # Classe principal
│   │
│   ├── config/                                # Configurações
│   │   └── DatabaseConfig.java                # ✨ Config JPA
│   │
│   ├── controller/                            # Controllers (Controle)
│   │   ├── HomeController.java                # Home/Produtos
│   │   ├── UsuarioController.java             # Login/Cadastro
│   │   ├── ProdutoController.java             # ✨ Produtos (antes Anuncio)
│   │   ├── CarrinhoController.java            # Carrinho
│   │   └── AvaliacaoController.java           # ✨ Avaliações (novo)
│   │
│   ├── service/                               # Services (Lógica de Negócio)
│   │   ├── UsuarioService.java                # Gerencia usuários
│   │   ├── ProdutoService.java                # ✨ Gerencia produtos
│   │   ├── CarrinhoService.java               # Gerencia carrinho
│   │   └── AvaliacaoService.java              # ✨ Gerencia avaliações
│   │
│   ├── repository/                            # ✨ Repositories (Spring Data JPA)
│   │   ├── UsuarioRepository.java
│   │   ├── ProdutoRepository.java             # ✨ (antes AnuncioRepository)
│   │   ├── CarrinhoRepository.java
│   │   ├── ItemCarrinhoRepository.java
│   │   └── AvaliacaoRepository.java           # ✨ (novo)
│   │
│   └── model/                                 # Models (Entidades)
│       ├── Usuario.java                       # @Entity Usuario
│       ├── Produto.java                       # ✨ @Entity Produto (antes Anuncio)
│       ├── Carrinho.java                      # @Entity Carrinho
│       ├── ItemCarrinho.java                  # @Entity ItemCarrinho
│       └── Avaliacao.java                     # ✨ @Entity Avaliacao (novo)
│
├── src/main/resources/
│   ├── templates/                             # Views (Thymeleaf)
│   │   ├── index.html                         # Listagem de produtos
│   │   ├── login.html                         # Página de login
│   │   ├── cadastro.html                      # Página de cadastro
│   │   ├── produtos/
│   │   │   ├── produtos-list.html             # ✨ Lista de produtos (admin)
│   │   │   ├── produtos-form.html             # ✨ Formulário criar/editar
│   │   │   ├── produto-detalhe.html           # ✨ Detalhes do produto
│   │   │   └── avaliacao-form.html            # ✨ Formulário avaliação
│   │   └── carrinho.html                      # Carrinho de compras
│   │
│   ├── static/
│   │   ├── css/
│   │   │   └── styles.css                     # Estilos customizados
│   │   ├── js/
│   │   │   └── app.js                         # Scripts customizados
│   │   └── img/                               # Imagens
│   │
│   ├── application.properties                 # ✨ Configuração principal
│   └── application.yml                        # ✨ Alternativa YAML
│
├── pom.xml                                    # Dependências Maven
├── README.md                                  # Este arquivo
├── database_setup.md                          # ✨ Guia de banco de dados
└── .gitignore
```

---

## ✨ Funcionalidades

### 👤 Usuário
- ✅ Cadastro de novos usuários com validação
- ✅ Login com sessão
- ✅ Validação de dados de entrada
- ✅ Logout
- ✅ Roles (USER, ADMIN)

### 📦 Produtos (Antes "Anúncios")
- ✅ Listagem de produtos com paginação
- ✅ Filtros por time e marca
- ✅ Ordenação por preço (menor/maior)
- ✅ Detalhes completos do produto
- ✅ Busca de produtos
- ✅ Imagens com placeholder
- ✅ Informações de estoque
- ✅ Admin CRUD (Criar, Ler, Atualizar, Deletar)

### 🛒 Carrinho de Compras
- ✅ Adicionar produtos ao carrinho
- ✅ Remover produtos
- ✅ Atualizar quantidades
- ✅ Visualizar total e subtotais
- ✅ Limpar carrinho
- ✅ Finalizar compra
- ✅ Persistência em sessão

### ⭐ Avaliações (Novo)
- ✅ Avaliar produtos (1-5 estrelas)
- ✅ Deixar comentários
- ✅ Visualizar avaliações
- ✅ Admin pode deletar avaliações
- ✅ Data de criação automática

### 🔐 Administração
- ✅ Painel admin de produtos
- ✅ Criar novos produtos
- ✅ Editar produtos existentes
- ✅ Deletar produtos
- ✅ Gerenciar avaliações

---

## ▶️ Como Executar

### 📋 Pré-requisitos

```bash
# Verificar versões instaladas
java -version              # Deve ser 17 ou superior
mvn -version              # Deve ser 3.6 ou superior
mysql --version           # Deve ser 5.7+ ou 8.0+
```

### 🗄️ Configurar Banco de Dados

1. **Criar banco de dados:**
```sql
CREATE DATABASE IF NOT EXISTS futebolcamisas 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

2. **Criar usuário (opcional):**
```sql
CREATE USER 'futebolcamisas'@'localhost' IDENTIFIED BY 'SenhaForte123!@#';
GRANT ALL PRIVILEGES ON futebolcamisas.* TO 'futebolcamisas'@'localhost';
FLUSH PRIVILEGES;
```

3. **Consulte** `database_setup.md` para detalhes completos

### ⚙️ Configurar Aplicação

1. **Atualize** `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/futebolcamisas?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

2. **Ou use** `application.yml` (alternativa)

### ▶️ Executar a Aplicação

#### Opção 1: Maven (Recomendado)

```bash
# Clonar/abrir o repositório
cd futebolcamisas

# Compilar e instalar dependências
mvn clean install

# Executar a aplicação
mvn spring-boot:run
```

#### Opção 2: IDE (IntelliJ / Eclipse)

1. Abra o projeto
2. Configure Java 17 como JDK
3. Clique em "Run" na classe `FutebolcamisasApplication`

#### Opção 3: VS Code

```bash
# Instale "Extension Pack for Java"
# Abra FutebolcamisasApplication.java
# Clique em "Run" acima do método main
```

#### Opção 4: JAR

```bash
# Empacotar como JAR
mvn clean package

# Executar
java -jar target/futebolcamisas-1.0.0.jar
```

### 🌐 Acessar a Aplicação

Abra seu navegador em:
```
http://localhost:8080
```

---

## 📡 Endpoints

### 🏠 Páginas Públicas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/` | Página inicial |
| GET | `/login` | Página de login |
| GET | `/cadastro` | Página de cadastro |
| GET | `/produtos` | Lista de produtos |
| GET | `/produtos/{id}` | Detalhes do produto |
| GET | `/carrinho` | Carrinho de compras |
| POST | `/login` | Submeter login |
| POST | `/cadastro` | Submeter cadastro |
| GET | `/logout` | Fazer logout |

### 🛒 Carrinho

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/carrinho` | Ver carrinho |
| POST | `/carrinho/adicionar/{id}` | Adicionar produto |
| POST | `/carrinho/remover/{id}` | Remover produto |
| POST | `/carrinho/atualizar/{id}` | Atualizar quantidade |
| POST | `/carrinho/limpar` | Limpar carrinho |
| POST | `/carrinho/finalizar` | Finalizar compra |

### ⭐ Avaliações

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/produtos/{id}/avaliacao/novo` | Formulário avaliação |
| POST | `/produtos/{id}/avaliacao` | Submeter avaliação |
| POST | `/produtos/{produtoId}/avaliacao/{id}/deletar` | Deletar (admin) |

### 🔐 Administração

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/produtos/admin` | Lista de produtos (admin) |
| GET | `/produtos/admin/novo` | Formulário novo produto |
| POST | `/produtos/admin` | Criar produto |
| GET | `/produtos/admin/{id}/editar` | Formulário editar |
| POST | `/produtos/admin/{id}` | Atualizar produto |
| POST | `/produtos/admin/{id}/deletar` | Deletar produto |

---

## 👤 Dados de Teste

### Usuário Admin

```
Email: admin@futebolcamisas.com
Senha: admin123
Role: ADMIN
```

### Usuário Cliente

Crie uma nova conta através da página de cadastro em `/cadastro`

### Produtos Pré-Cadastrados

| Produto | Marca | Time | Preço | Estoque |
|---------|-------|------|-------|---------|
| Camisa Corinthians 2024/25 | Nike | Corinthians | R$ 250,00 | 50 |
| Camisa Flamengo 2024/25 | Adidas | Flamengo | R$ 280,00 | 30 |
| Camisa Palmeiras 2024/25 | Puma | Palmeiras | R$ 270,00 | 25 |
| Camisa São Paulo 2024/25 | Adidas | São Paulo | R$ 260,00 | 40 |

---

## 🏗️ Arquitetura MVC

### Model (Modelo)

Representam os dados da aplicação:

```
Produto.java
├── id (Long)
├── titulo (String)
├── descricao (String)
├── preco (BigDecimal)
├── marca (String)
├── modelo (String)
├── tamanho (String)
├── cor (String)
├── time (String)
├── temporada (String)
├── estoque (Integer)
├── urlImagem (String)
└── avaliacoes (List<Avaliacao>)
```

### View (Visualização)

Templates Thymeleaf em `src/main/resources/templates/`:

- Responsáveis pela apresentação
- Utiliza Bootstrap 5 para design responsivo
- Integrado com JavaScript para interatividade
- Ícones Font Awesome

### Controller (Controle)

Recebe requisições HTTP e coordena:

```
Request HTTP
    ↓
ProdutoController
    ↓
ProdutoService (Lógica)
    ↓
ProdutoRepository (Dados)
    ↓
Banco de Dados
```

---

## ✨ Novas Funcionalidades Implementadas

### ✅ Migração de Anúncio para Produto

| Item | Antes | Depois |
|------|-------|--------|
| **Entidade** | `Anuncio.java` | `Produto.java` |
| **Repository** | `AnuncioRepository` | `ProdutoRepository` |
| **Service** | `AnuncioService` | `ProdutoService` |
| **Controller** | `AnuncioController` | `ProdutoController` |
| **URL** | `/admin/anuncios` | `/produtos/admin` |
| **Template** | `admin/anuncio-list.html` | `produtos/produtos-list.html` |

### ✅ Sistema de Avaliações

- Avaliar produtos com 1-5 estrelas
- Deixar comentários textuais
- Data de criação automática
- Admin pode gerenciar avaliações
- Visual com estrelas amarelas

### ✅ Melhorias de Interface

- Design moderno com gradientes
- Responsivo em mobile
- Ícones Font Awesome
- Animações suaves
- Mensagens de feedback
- Badges de estoque

### ✅ Persistência com Banco de Dados

- Spring Data JPA configurado
- Hibernate como ORM
- MySQL como banco de dados
- Migrations automáticas
- Relacionamentos N:1 e 1:N

---

## 🚀 Roadmap - Próximos Passos

### Core
- [x] ✅ Sistema de login/cadastro
- [x] ✅ Listagem de produtos
- [x] ✅ Carrinho de compras
- [x] ✅ Banco de dados (MySQL + JPA)
- [x] ✅ Sistema de avaliações
- [ ] Sistema de pagamento (Stripe/PayPal)
- [ ] Histórico de pedidos
- [ ] Rastreamento de pedidos

### Admin
- [x] ✅ CRUD de produtos
- [ ] CRUD de usuários
- [ ] Dashboard com estatísticas
- [ ] Gerenciar cupons
- [ ] Relatórios de vendas

### Features
- [ ] Busca avançada com filtros
- [ ] Wishlist (lista de desejos)
- [ ] Sistema de recomendações
- [ ] Notificações por email
- [ ] Cupons de desconto
- [ ] Programa de fidelização

### Técnico
- [ ] Testes unitários (JUnit)
- [ ] Testes de integração (Mockito)
- [ ] API RESTful completa
- [ ] Autenticação OAuth2/JWT
- [ ] Cache com Redis
- [ ] Docker para deploy
- [ ] CI/CD com GitHub Actions
- [ ] Documentação com Swagger

---

## 🤝 Como Contribuir

1. **Fork o projeto**
   ```bash
   git clone https://github.com/seu-usuario/futebolcamisas.git
   ```

2. **Crie uma branch para sua feature**
   ```bash
   git checkout -b feature/MinhaFeature
   ```

3. **Commit suas mudanças**
   ```bash
   git commit -m 'Adiciona MinhaFeature'
   ```

4. **Push para a branch**
   ```bash
   git push origin feature/MinhaFeature
   ```

5. **Abra um Pull Request**

---

## ⚠️ Notas Importantes

- ⚠️ As senhas não estão criptografadas (usar BCrypt em produção)
- ⚠️ Sem autenticação real (implementar Spring Security)
- ⚠️ Sem sistema de pagamento integrado
- ⚠️ Imagens usam placeholders
- ⚠️ Sem testes automatizados
- ⚠️ Sem API REST completa

---

## 📚 Documentação

- [Database Setup](./database_setup.md) - Guia completo de configuração do banco
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Thymeleaf Guide](https://www.thymeleaf.org/documentation.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Bootstrap Docs](https://getbootstrap.com/docs)

---

## 📋 Checklist de Desenvolvimento

### Backend
- [x] ✅ Models criados
- [x] ✅ Repositories configurados
- [x] ✅ Services implementados
- [x] ✅ Controllers funcionando
- [x] ✅ Banco de dados integrado
- [ ] Testes unitários
- [ ] Validações avançadas
- [ ] Tratamento de exceções

### Frontend
- [x] ✅ Templates HTML
- [x] ✅ Bootstrap integrado
- [x] ✅ Font Awesome integrado
- [x] ✅ Responsivo
- [ ] Melhorias de UX
- [ ] Formulários com validação client
- [ ] Dark mode
- [ ] Acessibilidade (WCAG)

---

## 📞 Suporte

### Problemas Comuns

**Erro: "Access denied for user 'root'@'localhost'"**
- Verifique credenciais em `application.properties`
- Verifique se MySQL está rodando

**Erro: "Unknown database 'futebolcamisas'"**
- Execute SQL: `CREATE DATABASE futebolcamisas;`
- Consulte `database_setup.md`

**Erro: "Port 8080 already in use"**
- Mude em `application.properties`: `server.port=8081`

**Erro: "Hibernate: could not locate entity"**
- Verifique se os models têm `@Entity`
- Verifique o escaneamento em `DatabaseConfig.java`

Para mais detalhes, veja `database_setup.md`

---

## 📄 Licença

Este projeto é de código aberto e está disponível sob a licença **MIT** para fins educacionais e comerciais.

---

## 👨‍💻 Autor

Desenvolvido como projeto educacional para demonstração de:
- ✅ Padrão MVC em Java
- ✅ Spring Boot 3.2
- ✅ Spring Data JPA
- ✅ Thymeleaf templating
- ✅ Bootstrap 5 frontend
- ✅ MySQL database

---

## 📊 Estatísticas do Projeto

- **Linguagem**: Java 17
- **Framework**: Spring Boot 3.2
- **Banco de Dados**: MySQL 8.0+
- **Template Engine**: Thymeleaf 3.1
- **Frontend**: Bootstrap 5.3 + Font Awesome 6.4
- **Build**: Maven 3.6+
- **Linhas de Código**: 1000+
- **Arquivos**: 20+

---

## 🔄 Histórico de Versões

| Versão | Data | Mudanças |
|--------|------|----------|
| **2.1.0** | Nov 2025 | ✨ Avaliações + Banco de dados |
| **2.0.0** | Nov 2025 | ✨ Migração Anúncio → Produto |
| **1.5.0** | Nov 2025 | ✨ Carrinho de compras |
| **1.0.0** | Nov 2025 | Versão inicial |

---

**🚀 Pronto para começar? Veja a seção [Como Executar](#como-executar)!**

Para dúvidas ou sugestões, abra uma issue no repositório.

---

**Versão**: 2.1.0  
**Última Atualização**: Novembro 2025  
**Status**: ✅ Em desenvolvimento ativo  
**Projeto**: Futebol Camisas - Loja Online
