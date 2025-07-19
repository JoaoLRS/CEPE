# Projeto CEPE - Consulta Eleitoral de Pernambuco

Este projeto é uma aplicação web desenvolvida para consultar informações sobre Zonas Eleitorais, Municípios, Polos e Seções de votação do estado de Pernambuco. A aplicação permite a importação de dados a partir de arquivos CSV e a realização de diversas consultas através de uma interface gráfica.

## 🛠️ Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias:

* **Backend:** Spring Boot 
* **Acesso a Dados:** Spring Data JPA 
* **Servidor Web:** Spring Web 
* **Template Engine:** Thymeleaf 
* **Estilização:** Bootstrap 
* **Banco de Dados:** PostgreSQL  

## 🗃️ Estrutura de Dados

A base de dados foi modelada para armazenar de forma normalizada as informações eleitorais, que são importadas de arquivos CSV.

### Entidades JPA 

As seguintes entidades JPA foram criadas para representar os dados:

* **`Zona`** 
    * `Número` (Chave Primária) 
    * `Município Sede`
    * Relacionamento com `Municipio` 

* **`Municipio`** 
    * `CodTse` (Chave Primária)
    * `Nome` 
    * Relacionamento com `Zona` 
    * Relacionamento com `Polo` 

* **`Polo`** 
    * `Número` (Chave Primária)
    * `Município Sede` 

* **`Secao`** 
    * `Id` (Chave Primária, autoincremento) 
    * `Número` 
    * Relacionamento com `Zona` 
    * Relacionamento com `Municipio` 
    * Relacionamento com `Polo` 
* **`Usuario`** 
    * `CPF` (Chave Primária) 
    * `Nome` 
    * `Email` 

### Relacionamentos

* **Zonas e Municípios:** Relação Muitos-para-Muitos (`n-para-n`).
* **Zonas e Seções:** Relação Um-para-Muitos (`1-para-n`).
* **Polos e Municípios:** Relação Um-para-Muitos (`1-para-n`).

## ✨ Funcionalidades

A aplicação web oferece as seguintes funcionalidades de consulta e cadastro:

### 1. Consultas de Zonas Eleitorais 
* Buscar Zona por número.
* Listar o município sede de uma zona.
* Listar todos os municípios que compõem uma zona.
* Listar todas as seções de uma zona.

### 2. Consultas de Municípios 
* Buscar Município pelo código.
* Listar as zonas de um município.
* Listar as seções de um município.

### 3. Consultas de Polos 
* Listar os municípios de um polo.
* Listar as zonas de um polo.
* Exibir o número do polo a partir de um município.
* Exibir o número do polo a partir de uma zona.

### 4. Cadastro de Usuários 
* Inclusão de novos usuários no sistema.
* Consulta de usuários já cadastrados.

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
      git clone https://github.com/JoaoLRS/CEPE.git
    ```

2.  **Configure o Banco de Dados:**
    * Crie um novo banco de dados no PostgreSQL chamado CEPE.
    * Abra o arquivo `src/main/resources/application.properties`.
    * Atualize as propriedades de conexão com o banco (`spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`).
    * Recomendado criar uma variável de ambiente chamada  `SENHA_DB` nela conterá a senha utilizada no `spring.datasource.password`.

3.  **Importe os Dados:**
    * O projeto requer a importação de dados a partir dos arquivos `secoes.csv` e dos outros arquivos que definem Polos e Zonas.
    * Execute o processo de importação para popular o banco de dados, lidando com as repetições e estabelecendo os relacionamentos conforme o modelo de entidades, para isso basta apenas iniciar a aplicação.

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
