<br><h3 align="center">
  CRUD CLEAN ARCHITECTURE
</h3><br>

[![Build & test](https://github.com/RodrigoAntonioCruz/crud-clean-architecture/actions/workflows/build.yml/badge.svg)](https://github.com/RodrigoAntonioCruz/crud-clean-architecture/actions/workflows/build.yml) [![codecov](https://codecov.io/gh/RodrigoAntonioCruz/crud-clean-architecture/graph/badge.svg?token=oXUNgmqgIm)](https://codecov.io/gh/RodrigoAntonioCruz/crud-clean-architecture)

### Contexto

Uma `API REST` implementando um `CRUD` básico, seguindo o padrão Clean Architecture.

## Estrutura do Projeto

O projeto está dividido em diferentes módulos e pacotes, seguindo os princípios da arquitetura limpa. Aqui está uma visão geral da estrutura do projeto:

### Módulo "Core"

- **`core/domain`**: Este pacote contém as classes de domínio do projeto. São classes que representam as entidades e objetos de negócios do sistema.

- **`core/use-case`**: Neste pacote, você encontrará os casos de uso ou interações principais da aplicação. Os casos de uso definem as regras de negócios e a lógica da aplicação.

### Módulo "Adapter"

- **`adapter/input`**: Os adaptadores de entrada são responsáveis por receber as solicitações do mundo exterior. Isso pode incluir controladores REST, classes de serialização, entre outros.

- **`adapter/output`**: Os adaptadores de saída são responsáveis por fornecer saídas para o mundo exterior. Isso pode incluir repositórios de dados, serviços externos, armazenamento em banco de dados, entre outros.

### Módulo "App"

- **`app/spring-app`**: Este é o módulo de aplicação da sua arquitetura. Aqui, você pode encontrar a configuração e componentes específicos do Spring Boot, como a classe principal de inicialização.

<h4>Tecnologias</h4>
<ul>
  <li> Java 17
  <li> Spring Boot 
  <li> Maven
  <li> Swagger
</ul>

### Executando o projeto

1. Clone ou baixe o projeto do repositório para o seu `Computador`.

2. Navegue até a raíz do diretório do projeto, abra o `terminal e execute o comando:`
<ul>
   <li> sudo mvn clean package
</ul>

   <img align="center" src="https://raw.githubusercontent.com/RodrigoAntonioCruz/assets/main/mvn-clean-pkg.png" />

<ul>
   <li> sudo docker-compose up -d
</ul>

<img align="center" src="https://raw.githubusercontent.com/RodrigoAntonioCruz/assets/main/docker-compose.png" />


3. Após a execução dos processos anteriores, estarão disponíveis para acesso em seu browser os seguintes `endpoints` para teste:

<a href="http://localhost:8887/v1/api/swagger-ui/index.html" target="_blank" title="Clique e navegue!">
<img align="center" src="https://raw.githubusercontent.com/RodrigoAntonioCruz/assets/main/users-api.png" /></a>

