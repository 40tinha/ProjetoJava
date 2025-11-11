# Futebol Camisas - Loja Online

Sistema de e-commerce para venda de camisas de futebol desenvolvido em Java com Spring Boot seguindo o padrão MVC.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring MVC**
- **Thymeleaf** (Template Engine)
- **Maven** (Gerenciamento de Dependências)
- **Spring Validation**

## Estrutura do Projeto (MVC)

```
src/main/java/com/futebolcamisas/
├── FutebolCamisasApplication.java    # Classe principal
├── controller/                        # Controllers (Camada de Controle)
│   ├── HomeController.java
│   ├── UsuarioController.java
│   └── ProdutoController.java
├── service/                          # Services (Lógica de Negócio)
│   ├── UsuarioService.java
│   └── ProdutoService.java
├── dao/                              # Data Access Object (Acesso a Dados)
│   ├── UsuarioDAO.java
│   └── ProdutoDAO.java
└── model/                            # Models (Entidades)
    ├── Usuario.java
    └── Produto.java

src/main/resources/
├── templates/                        # Views (Templates Thymeleaf)
│   ├── index.html
│   ├── login.html
│   ├── cadastro.html
│   └── produto.html
├── static/                           # Arquivos estáticos (CSS, JS, imagens)
│   └── css/
│       └── styles.css
└── application.properties            # Configurações da aplicação
```

## Funcionalidades

### Usuário
- ✅ Cadastro de novos usuários
- ✅ Login de usuários
- ✅ Validação de dados de entrada
- ✅ Gerenciamento de sessão

### Produtos
- ✅ Listagem de produtos
- ✅ Filtros por time e marca
- ✅ Ordenação por preço (menor/maior)
- ✅ Detalhes do produto
- ✅ Busca de produtos

## Como Executar

### Pré-requisitos
- **Java 17 ou superior** (⚠️ OBRIGATÓRIO)
- Maven 3.6 ou superior (ou use uma IDE)

### Verificação de Requisitos

Execute o script de verificação:
```bash
verificar-requisitos.bat
```

### Opções para Executar

#### Opção 1: Usando Maven (Recomendado)

1. **Navegue até a pasta do projeto**
   ```bash
   cd "Projeto Java"
   ```

2. **Compile o projeto**
   ```bash
   mvn clean install
   ```

3. **Execute a aplicação**
   ```bash
   mvn spring-boot:run
   ```

4. **Acesse a aplicação**
   - Abra seu navegador em: `http://localhost:8080`

#### Opção 2: Usando Scripts

- **Windows (PowerShell)**: Execute `start.ps1`
- **Windows (CMD)**: Execute `start.bat`

#### Opção 3: Usando IDE (IntelliJ IDEA / Eclipse)

1. Abra o projeto na IDE
2. Configure o Java 17 como JDK do projeto
3. Execute a classe `FutebolCamisasApplication.java`

#### Opção 4: Instalar Java 17

Se você não tem Java 17:

1. **Baixe o Java 17:**
   - Oracle JDK: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
   - OpenJDK: https://adoptium.net/temurin/releases/?version=17

2. **Instale e configure o JAVA_HOME:**
   - Adicione a variável de ambiente `JAVA_HOME` apontando para a instalação do Java 17
   - Adicione `%JAVA_HOME%\bin` ao PATH

3. **Verifique a instalação:**
   ```bash
   java -version
   ```
   Deve mostrar: `java version "17.x.x"`

## Endpoints

### Páginas
- `GET /` - Página inicial com lista de produtos
- `GET /login` - Página de login
- `GET /cadastro` - Página de cadastro
- `GET /produto/{id}` - Página de detalhes do produto
- `GET /logout` - Logout do usuário

### APIs
- `POST /login` - Realizar login
- `POST /cadastro` - Cadastrar novo usuário

## Usuário de Teste

Para testar o sistema, você pode usar:
- **Email**: admin@futebolcamisas.com
- **Senha**: admin123

Ou criar uma nova conta através da página de cadastro.

## Dados de Exemplo

O sistema vem com produtos de exemplo pré-cadastrados:
- Camisa Corinthians I 2024/25 (Nike)
- Camisa Flamengo I 2024/25 (Adidas)
- Camisa Palmeiras I 2024/25 (Puma)
- Camisa São Paulo I 2024/25 (Adidas)

## Estrutura MVC

### Model (Modelo)
- Representa os dados da aplicação
- Classes: `Usuario`, `Produto`

### View (Visualização)
- Templates Thymeleaf em `src/main/resources/templates/`
- Responsáveis pela apresentação dos dados

### Controller (Controle)
- Recebe requisições HTTP
- Processa a lógica de negócio através dos Services
- Retorna a view apropriada

## Notas Importantes

- Os dados são armazenados em memória (não persistem após reiniciar a aplicação)
- Para produção, é recomendado implementar persistência com banco de dados (JPA/Hibernate)
- As senhas não estão criptografadas (implementar BCrypt para produção)
- O sistema de carrinho de compras ainda não foi implementado

## Próximos Passos

- [ ] Implementar persistência com banco de dados
- [ ] Adicionar criptografia de senhas
- [ ] Implementar carrinho de compras
- [ ] Adicionar sistema de pagamento
- [ ] Implementar busca de produtos
- [ ] Adicionar sistema de avaliações
- [ ] Implementar upload de imagens
- [ ] Adicionar testes unitários e de integração

## Autor

Desenvolvido como projeto educacional para demonstração do padrão MVC em Java.

