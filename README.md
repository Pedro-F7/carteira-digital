# 💳 Carteira Digital API

Um sistema de back-end para uma carteira digital corporativa, focado em transações seguras, integridade de dados e proteção de rotas. O projeto simula o núcleo de um aplicativo financeiro, garantindo que apenas usuários autenticados possam acessar seus saldos e realizar movimentações.

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 21+
* **Framework:** Spring Boot 4.1.1
* **Segurança:** Spring Security, JWT (JSON Web Tokens), BCrypt Password Encoder
* **Persistência de Dados:** Spring Data JPA / Hibernate
* **Banco de Dados:** PostgreSQL (via Docker)
* **Gerenciamento de Dependências:** Maven

## ⚙️ Funcionalidades

* **Gestão de Usuários:** Cadastro de novos usuários com validação de dados e impedimento de e-mails duplicados.
* **Segurança e Autenticação:** Login seguro devolvendo token JWT com tempo de expiração. Senhas são salvas no banco com hash irreversível (BCrypt).
* **Proteção de Rotas:** Filtro de segurança customizado que intercepta todas as requisições financeiras, validando a assinatura e a validade do JWT.
* **Operações Financeiras:** 
  * Geração automática de conta bancária.
  * Depósitos.
  * Consultas de saldo estritamente restritas ao dono da conta.
  * Transferências entre contas (com validação de saldo insuficiente).

## 🛠️ Como Executar o Projeto Localmente

**Pré-requisitos:** Java 21 ou superior, Maven e Docker instalados na máquina.

1. **Clone o repositório**
   ```bash
   git clone [https://github.com/Pedro-F7/carteira-digital.git](https://github.com/Pedro-F7/carteira-digital.git)
   cd carteira-digital

   Suba o Banco de Dados com Docker
Certifique-se de ter um container do PostgreSQL rodando na porta 5432 com as credenciais configuradas no seu application.properties.

Execute a Aplicação

Bash
./mvnw clean spring-boot:run
A API estará disponível em http://localhost:8081.

Teste as Requisições
O projeto inclui um arquivo requests.http na raiz com o mapeamento completo e seguro de todas as requisições prontas para serem executadas via extensões como o REST Client do VS Code.

📍 Endpoints Principais
POST /usuarios — Cadastra um novo usuário (Público)

POST /login — Autentica o usuário e retorna o Token JWT (Público)

GET /contas/{id}/saldo — Retorna o saldo da conta informada (Requer Token)

POST /contas/{id}/deposito — Adiciona fundos à conta informada (Requer Token)

POST /contas/{id}/transferencia — Transfere fundos entre contas diferentes (Requer Token)
