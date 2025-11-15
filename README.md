# Futebol Camisas - Loja Online

Sistema de e-commerce para venda de camisas de futebol desenvolvido em Java com Spring Boot seguindo o padrão MVC.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring MVC**
- **Thymeleaf** (Template Engine)
- **Maven** (Gerenciamento de Dependências)
- **Spring Validation**
- **Bootstrap 5** (Frontend)
- **Font Awesome** (Ícones)

## Estrutura do Projeto (MVC)

```
src/main/java/com/futebolcamisas/
├── FutebolCamisasApplication.java    # Classe principal
├── controller/                        # Controllers (Camada de Controle)
│   ├── HomeController.java
│   ├── UsuarioController.java
│   ├── ProdutoController.java
│   └── CarrinhoController.java       # ✨ NOVO
├── service/                          # Services (Lógica de Negócio)
│   ├── UsuarioService.java
│   ├── ProdutoService.java
│   └── CarrinhoService.java          # ✨ NOVO
├── dao/                              # Data Access Object (Acesso a Dados)
│   ├── UsuarioDAO.java
│   └── ProdutoDAO.java
└── model/                            # Models (Entidades)
    ├── Usuario.java
    ├── Produto.java
    ├── Carrinho.java                 # ✨ NOVO
    └── ItemCarrinho.java             # ✨ NOVO

src/main/resources/
├── templates/                        # Views (Templates Thymeleaf)
│   ├── index.html
│   ├── login.html
│   ├── cadastro.html
│   ├── produto.html
│   └── carrinho.html                 # ✨ NOVO
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
- ✅ Logout de usuários

### Produtos
- ✅ Listagem de produtos
- ✅ Filtros por time e marca
- ✅ Ordenação por preço (menor/maior)
- ✅ Detalhes do produto
- ✅ Busca de produtos
- ✅ Visualização com imagens

### Carrinho de Compras ✨ NOVO
- ✅ Adicionar produtos ao carrinho
- ✅ Remover produtos do carrinho
- ✅ Atualizar quantidade de produtos
- ✅ Visualizar total da compra
- ✅ Limpar carrinho
- ✅ Finalizar compra
- ✅ Persistência do carrinho na sessão
- ✅ Cálculo automático de subtotais

## Como Executar

### Pré-requisitos
- **Java 17 ou superior** (⚠️ OBRIGATÓRIO)
- Maven 3.6 ou superior (ou use uma IDE)

### Opções para Executar

#### Opção 1: Usando Maven (Recomendado)

1. **Navegue até a pasta do projeto**
   ```bash
   cd projetojava
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

#### Opção 2: Usando IDE (IntelliJ IDEA / Eclipse)

1. Abra o projeto na IDE
2. Configure o Java 17 como JDK do projeto
3. Execute a classe `FutebolCamisasApplication.java`

#### Opção 3: Instalar Java 17

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
- `GET /carrinho` - ✨ Página do carrinho de compras
- `GET /logout` - Logout do usuário

### APIs do Carrinho ✨ NOVO
- `POST /carrinho/adicionar/{id}` - Adicionar produto ao carrinho
- `POST /carrinho/remover/{id}` - Remover produto do carrinho
- `POST /carrinho/atualizar/{id}` - Atualizar quantidade do produto
- `POST /carrinho/limpar` - Limpar todos os itens do carrinho
- `POST /carrinho/finalizar` - Finalizar compra

### APIs de Usuário
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
- Classes: `Usuario`, `Produto`, `Carrinho`, `ItemCarrinho`

### View (Visualização)
- Templates Thymeleaf em `src/main/resources/templates/`
- Responsáveis pela apresentação dos dados
- Utiliza Bootstrap 5 para design responsivo

### Controller (Controle)
- Recebe requisições HTTP
- Processa a lógica de negócio através dos Services
- Retorna a view apropriada

## Novas Funcionalidades Implementadas ✨

### Sistema de Carrinho de Compras

O sistema agora possui um carrinho de compras totalmente funcional:

1. **Adicionar ao Carrinho**: Clique no botão "Adicionar ao Carrinho" em qualquer produto
2. **Visualizar Carrinho**: Acesse `/carrinho` para ver todos os itens
3. **Gerenciar Quantidades**: Aumente ou diminua a quantidade diretamente no carrinho
4. **Remover Itens**: Remova produtos individuais com o botão de lixeira
5. **Cálculo Automático**: O total é calculado automaticamente
6. **Finalizar Compra**: Complete sua compra (requer login)

### Melhorias de Interface

- Design moderno com gradientes e sombras
- Interface responsiva com Bootstrap 5
- Ícones do Font Awesome para melhor experiência
- Mensagens de feedback para ações do usuário
- Animações suaves nas interações

## Notas Importantes

- Os dados são armazenados em memória (não persistem após reiniciar a aplicação)
- O carrinho é mantido na sessão do usuário
- Para produção, é recomendado implementar persistência com banco de dados (JPA/Hibernate)
- As senhas não estão criptografadas (implementar BCrypt para produção)

## Roadmap - Próximos Passos

- [ ] Implementar persistência com banco de dados (JPA/Hibernate + MySQL/PostgreSQL)
- [ ] Adicionar criptografia de senhas (BCrypt)
- [x] ✅ Implementar carrinho de compras
- [ ] Adicionar sistema de pagamento (integração com gateway)
- [ ] Implementar histórico de pedidos
- [ ] Adicionar sistema de avaliações e comentários
- [ ] Implementar upload de imagens de produtos
- [ ] Adicionar painel administrativo para gerenciar produtos
- [ ] Implementar busca avançada com filtros múltiplos
- [ ] Adicionar wishlist (lista de desejos)
- [ ] Implementar sistema de cupons de desconto
- [ ] Adicionar testes unitários e de integração
- [ ] Implementar API RESTful
- [ ] Adicionar autenticação OAuth2

## Como Contribuir

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

## Licença

Este projeto é de código aberto e está disponível para fins educacionais.

## Autor

Desenvolvido como projeto educacional para demonstração do padrão MVC em Java com Spring Boot.

---

**Versão**: 2.0.0  
**Última Atualização**: Novembro 2025
