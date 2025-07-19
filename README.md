# Projeto CEPE - Consulta Eleitoral de Pernambuco

[cite_start]Este projeto é uma aplicação web desenvolvida para consultar informações sobre Zonas Eleitorais, Municípios, Polos e Seções de votação do estado de Pernambuco[cite: 3]. [cite_start]A aplicação permite a importação de dados a partir de arquivos CSV e a realização de diversas consultas através de uma interface gráfica[cite: 6, 37].

## 🛠️ Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias:

* [cite_start]**Backend:** Spring Boot [cite: 38]
* [cite_start]**Acesso a Dados:** Spring Data JPA [cite: 38]
* [cite_start]**Servidor Web:** Spring Web [cite: 38]
* [cite_start]**Template Engine:** Thymeleaf [cite: 38]
* [cite_start]**Estilização:** Bootstrap [cite: 38]
* [cite_start]**Banco de Dados:** PostgreSQL (recomendado) [cite: 38]

## 🗃️ Estrutura de Dados

[cite_start]A base de dados foi modelada para armazenar de forma normalizada as informações eleitorais, que são importadas de arquivos CSV[cite: 6, 7].

### [cite_start]Entidades JPA [cite: 5, 8]

As seguintes entidades JPA foram criadas para representar os dados:

* [cite_start]**`Zona`** [cite: 9]
    * [cite_start]`Número` (Chave Primária) [cite: 10]
    * [cite_start]`Município Sede` [cite: 11]
    * [cite_start]Relacionamento com `Municipio` [cite: 12]

* [cite_start]**`Municipio`** [cite: 13]
    * [cite_start]`CodTse` (Chave Primária) [cite: 14]
    * [cite_start]`Nome` [cite: 15]
    * [cite_start]Relacionamento com `Zona` [cite: 16]
    * [cite_start]Relacionamento com `Polo` [cite: 17]

* [cite_start]**`Polo`** [cite: 23]
    * [cite_start]`Número` (Chave Primária) [cite: 24]
    * [cite_start]`Município Sede` [cite: 25]

* [cite_start]**`Secao`** [cite: 18]
    * [cite_start]`Id` (Chave Primária, autoincremento) [cite: 19, 31]
    * [cite_start]`Número` [cite: 20]
    * [cite_start]Relacionamento com `Zona` [cite: 20]
    * [cite_start]Relacionamento com `Municipio` [cite: 21]
    * [cite_start]Relacionamento com `Polo` [cite: 22]

* [cite_start]**`Usuario`** [cite: 26]
    * [cite_start]`CPF` (Chave Primária) [cite: 27]
    * [cite_start]`Nome` [cite: 28]
    * [cite_start]`Email` [cite: 29]

### Relacionamentos

* [cite_start]**Zonas e Municípios:** Relação Muitos-para-Muitos (`n-para-n`)[cite: 34].
* [cite_start]**Zonas e Seções:** Relação Um-para-Muitos (`1-para-n`)[cite: 35].
* [cite_start]**Polos e Municípios:** Relação Um-para-Muitos (`1-para-n`)[cite: 36].

## ✨ Funcionalidades

[cite_start]A aplicação web oferece as seguintes funcionalidades de consulta e cadastro[cite: 38]:

### [cite_start]1. Consultas de Zonas Eleitorais [cite: 39]
* [cite_start]Buscar Zona por número[cite: 40].
* [cite_start]Listar o município sede de uma zona[cite: 41].
* [cite_start]Listar todos os municípios que compõem uma zona[cite: 42].
* [cite_start]Listar todas as seções de uma zona[cite: 43].

### [cite_start]2. Consultas de Municípios [cite: 44]
* [cite_start]Buscar Município pelo código[cite: 45].
* [cite_start]Listar as zonas de um município[cite: 46].
* [cite_start]Listar as seções de um município[cite: 47].

### [cite_start]3. Consultas de Polos [cite: 48]
* [cite_start]Listar os municípios de um polo[cite: 49].
* [cite_start]Listar as zonas de um polo[cite: 50].
* [cite_start]Exibir o número do polo a partir de um município[cite: 51].
* [cite_start]Exibir o número do polo a partir de uma zona[cite: 52].

### [cite_start]4. Cadastro de Usuários [cite: 53]
* [cite_start]Inclusão de novos usuários no sistema[cite: 54].
* [cite_start]Consulta de usuários já cadastrados[cite: 55].

## 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar e executar a aplicação em seu ambiente local.

### Pré-requisitos
* Java JDK
* Maven ou Gradle
* PostgreSQL
* Git

### Passos

1.  **Clone o repositório:**
    ```bash
       git clone [https://github.com/JoaoLRS/CEPE.git(https://github.com/JoaoLRS/CEPE.git)]
    ```

2.  **Configure o Banco de Dados:**
    * Crie um novo banco de dados no PostgreSQL chamado CEPE.
    * Abra o arquivo `src/main/resources/application.properties`.
    * Atualize as propriedades de conexão com o banco (`spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`).
    * Recomendado criar uma variável de ambiente chamada  `SENHA_DB` nela conterá a senha utilizada no `spring.datasource.password`.

3.  **Importe os Dados:**
    * [cite_start]O projeto requer a importação de dados a partir dos arquivos `secoes.csv` e dos outros arquivos que definem Polos e Zonas[cite: 3, 4, 6].
    * [cite_start]Execute o processo de importação para popular o banco de dados, lidando com as repetições e estabelecendo os relacionamentos conforme o modelo de entidades[cite: 7], para isso basta apenas iniciar a aplicação.

4.  **Execute a Aplicação:**
    * Utilize o Maven ou Gradle para iniciar o servidor Spring Boot.
    ```bash
    # Usando Maven
    ./mvnw spring-boot:run
    
    # Usando Gradle
    ./gradlew bootRun
    ```

5.  **Acesse a Aplicação:**
    * Abra seu navegador e acesse `http://localhost:8080/inicio`.
