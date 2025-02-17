# BenchPlanisa - Comparação de Dados de COVID-19

O **BenchPlanisa** é uma aplicação web que permite comparar dados de COVID-19 entre diferentes localidades (atualmente apenas municípios) no Brasil. A aplicação coleta dados da API da Brasil.io, processa as informações e exibe gráficos comparativos para o usuário.

---

## Tecnologias Utilizadas

### Backend

- **Java 17**: Linguagem de programação principal.
- **Spring Boot**: Framework para desenvolvimento de aplicações web.
- **Spring Data JPA**: Para acesso e persistência de dados.
- **MySQL**: Banco de dados relacional para armazenamento de dados.
- **RestTemplate**: Para consumir a API externa da Brasil.io.
- **Swagger**: Para documentação da API.
- **Lombok**: Para reduzir boilerplate code.

### Frontend

- **Angular**: Framework para desenvolvimento de interfaces web.
- **Chart.js**: Para criação de gráficos.
- **Bootstrap**: Para estilização e layout responsivo.
- **RxJS**: Para programação reativa.
- **Angular Material**: Para componentes de UI modernos.

### Ferramentas

- **Docker**: Para conteinerização da aplicação.
- **Docker Compose**: Para orquestração dos contêineres.
- **Postman**: Para testes de API.
- **Git**: Para controle de versão.

---

## Como Rodar a Aplicação

### Pré-requisitos

- Primeiramente faça o clone dessa aplicação através da url: git clone https://github.com/voidGustavoNunes/BenchPlanisa
- **Docker** e **Docker Compose** instalados na máquina.
- Um arquivo **.env** configurado com as variáveis de ambiente necessárias que deve ser salvo na pasta raiz do projeto, junto com o docker-compose.yml.
- Criar uma conta na Brasil.io nessa url: https://brasil.io/dataset/covid19/caso/ e gerar uma chave de API.

### Passo 1: Configuração do Ambiente

Crie um arquivo **.env** na raiz do projeto com as seguintes variáveis de ambiente:

```
MYSQL_ROOT_PASSWORD=ROOT QUALQUER
MYSQL_DATABASE=DATABASE QUALQUER
MYSQL_USER=USER QUALQUER
MYSQL_PASSWORD=PASSWORD QUALQUER
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/DATABASE QUALQUER?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME= USERNAME QUALQUER
SPRING_DATASOURCE_PASSWORD= PASSWORD QUALQUER
API_TOKEN= >> CHAVE API GERADA <<
API_URL=https://api.brasil.io/v1/dataset/covid19/caso/data
```

Crie um arquivo **init.sql** para inicializar o banco de dados:

```sql
CREATE USER IF NOT EXISTS 'USERNAME QUALQUER'@'%' IDENTIFIED BY 'PASSWORD QUALQUER';
GRANT ALL PRIVILEGES ON DATABASE QUALQUER.* TO 'USERNAME QUALQUER'@'%';
FLUSH PRIVILEGES;
```
### IMPORTANTE: Lembre-se de substituir o nome das variáveis antes de rodar o container.

### Passo 2: Executando a Aplicação

#### Primeira Execução:

Na raiz do projeto, execute o seguinte comando para construir e iniciar os contêineres:

```
docker-compose up --build
```

Esse comando faz o build das imagens Docker e inicia os contêineres do backend, frontend e MySQL.

#### Execuções Posteriores:

Para iniciar a aplicação novamente, use:

```
docker-compose up
```

Acesse a aplicação:

- **Backend**: [http://localhost:8080](http://localhost:8080)
- **Frontend**: [http://localhost:4200](http://localhost:4200)

---

## Estrutura do Projeto

### Backend

O backend é desenvolvido em Spring Boot e organizado da seguinte forma:

```
src/main/java/com/backend/BenchMarks/
├── controller/           # Controladores da API REST
├── model/                # Entidades do banco de dados
├── repository/           # Interfaces de repositório (Spring Data JPA)
├── service/              # Lógica de negócio
├── util/                 # Utilitários (conversores, validadores, etc.)
└── BenchMarksApplication.java # Classe principal do Spring Boot
```

### Frontend

O frontend é desenvolvido em Angular e organizado da seguinte forma:

```
src/app/
├── components/           # Componentes reutilizáveis
├── services/             # Serviços para comunicação com o backend
├── models/               # Modelos de dados
├── pages/                # Páginas da aplicação
├── app-routing.module.ts # Configuração de rotas
└── app.module.ts         # Módulo principal
```

---

## Funcionalidades

### Backend

- **Criação de Benchmarks**: Comparar dados de duas localidades em um período específico.
- **Consulta de Benchmarks**: Buscar benchmarks por ID ou listar todos.
- **Comparar Dados**: Analisar casos confirmados, mortes e taxa de letalidade.

### Frontend

- **Formulário de Criação de Benchmarks**.
- **Visualização de Gráficos Comparativos**.
- **Listagem de Benchmarks**.

---

## Endpoints da API

### Benchmark

- **POST** `/api/benchmark`: Criar um novo benchmark.
- **GET** `/api/benchmark/{id}`: Buscar um benchmark por ID.
- **GET** `/api/benchmark/all`: Listar todos os benchmarks.

### Comparacao

- **POST** `/api/comparacao`: Criar uma nova comparação.
- **GET** `/api/comparacao/{benchmarkId}`: Buscar uma comparação por ID do benchmark.
- **GET** `/api/comparacao/all`: Listar todas as comparações.

---

## Exemplos de Requisições

### Criar Benchmark

```json
POST /api/benchmark
Content-Type: application/json
{
  "nome": "Comparacao ES x BA",
  "localidade1": "Colatina",
  "estadoLocalidade1": "SP",
  "localidade2": "Salvador",
  "estadoLocalidade2": "BA",
  "dataInicial": "2021-01-01",
  "dataFinal": "2021-03-31",
  "tipoLocalidade": "MUNICIPIO"
}
```

### Buscar Benchmark por ID

```
GET /api/benchmark/1
```

### Listar Benchmarks

```
GET /api/benchmark/all
```

---

## Contribuição

1. Faça um **fork** do repositório.
2. Crie uma **branch** (`git checkout -b feature/nova-feature`).
3. **Commit** suas alterações (`git commit -m 'Adiciona nova feature'`).
4. **Push** para a branch (`git push origin feature/nova-feature`).
5. Abra um **Pull Request**.

---

## Licença

Este projeto está licenciado sob a licença GNU.

---

Obrigado a todos pela oportunidade.
