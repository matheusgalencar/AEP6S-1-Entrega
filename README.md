# AEP 6S - 1ª Entrega

## AgroPec - Gestão de animais

Desenvolvemos o AgroPec como uma Prova de Conceito para a AEP de Engenharia de Software, no 6º semestre de 2026.2.
Nosso projeto é uma API para organizar informações de animais de um rebanho.
Nesta primeira entrega, trabalhamos na estrutura da aplicação, no banco de dados e nas operações de gerenciamento dos animais.

## Problema e objetivo

Nosso projeto busca ajudar produtores rurais que precisam manter os dados do rebanho organizados.
Quando essas informações ficam em cadernos ou arquivos separados, consultar o histórico de um animal pode ser mais difícil.
Isso também pode atrapalhar o acompanhamento das pesagens e dos cuidados registrados.

A ideia do AgroPec é reunir essas informações em um só lugar.
Cada animal possui identificação por brinco, raça, peso de entrada e estruturas para registrar pesagens e vacinações.
Com isso, queremos facilitar a consulta dos dados e apoiar o planejamento do manejo.
Nesta etapa, o sistema funciona como um backend, acessado por ferramentas como Postman ou pelo terminal.

## ODS escolhido

Escolhemos o **ODS 2 - Fome Zero e Agricultura Sustentável**.
Esse objetivo envolve segurança alimentar e formas sustentáveis de produzir alimentos.
Relacionamos nossa proposta principalmente às metas 2.3 e 2.4, que abordam produtividade dos pequenos produtores e produção sustentável.
As metas podem ser consultadas na [página da ONU sobre o ODS 2](https://brasil.un.org/pt-br/sdgs/2).

Queremos contribuir com a organização das informações usadas na produção pecuária.
Um acompanhamento mais organizado pode ajudar o produtor a planejar o manejo e o uso dos recursos da propriedade.
Essa é a contribuição que buscamos com o projeto; ainda não medimos seus resultados em uma propriedade real.

## Tecnologias utilizadas

- **Java 21:** linguagem utilizada no desenvolvimento orientado a objetos.
- **Spring Boot 4.1.1:** inicialização e configuração da aplicação.
- **Spring Web MVC:** criação das rotas da API.
- **Spring Data MongoDB e MongoDB:** acesso e armazenamento dos dados em um banco NoSQL.
- **Jakarta Validation:** definição de validações para os dados recebidos.
- **Lombok:** geração de métodos como getters e setters nas classes anotadas.
- **Spring Dotenv 5.1.0:** suporte ao uso de arquivos `.env`.
- **Maven 3.9.16:** gerenciamento das dependências e execução do projeto, pelo Wrapper incluído.
- **JUnit Jupiter e Spring Boot Test:** suporte aos testes automatizados.
- **SonarQube:** análise de qualidade apresentada no relatório do projeto.
- **Git e GitHub:** versionamento e compartilhamento do código.

## Organização e funcionalidades

Organizamos o código em controller, service, repository, DTO e model.
O controller recebe as requisições, o service executa as operações e o repository acessa o banco.
O DTO representa os dados enviados para a API, e os models representam os animais e seus históricos.
Essa separação ajuda a manter cada parte do código com uma responsabilidade definida.

Usamos o banco `agroPec` e a coleção `animais` no MongoDB.
As pesagens e vacinações ficam dentro do documento de cada animal, como listas de subdocumentos.
As rotas estruturadas para a primeira entrega são:

| Método | Rota | Finalidade |
| --- | --- | --- |
| GET | `/animais/list` | Listar os animais. |
| GET | `/animais/list/{id}` | Buscar um animal pelo identificador. |
| POST | `/animais/create` | Cadastrar um animal. |
| PUT | `/animais/update/{id}` | Atualizar os dados de um animal. |
| DELETE | `/animais/delete/{id}` | Excluir um animal. |

No cadastro e na atualização, enviamos os dados em JSON, com o cabeçalho `Content-Type: application/json`.
As rotas com `{id}` usam o identificador do registro no banco, e não o número do brinco.

## Como executar

Para executar o projeto, é necessário ter JDK 21, Git e MongoDB instalados.
O MongoDB deve estar em execução na porta `27017`.
O Maven Wrapper já está no repositório e baixa as dependências na primeira execução.

Primeiro, clone o repositório e entre na pasta da aplicação:

```bash
git clone https://github.com/matheusgalencar/AEP6S-1-Entrega.git
cd AEP6S-1-Entrega/agroPec
```

A conexão local já está configurada em `src/main/resources/application.properties`:

```properties
spring.mongodb.uri=mongodb://localhost:27017/agroPec
```

No Windows, execute pelo PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS, execute:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

A API fica disponível em `http://localhost:8080`.
Para conferir a listagem, acesse [localhost:8080/animais/list](http://localhost:8080/animais/list).
Com o banco disponível e sem animais cadastrados, o retorno dessa rota é `[]`.
Para encerrar a aplicação no terminal, pressione `Ctrl+C`.

## Testes e relatório

Incluímos no repositório o [relatório de testes e análise de qualidade](./Relatorio_testes%282%29.pdf).
O documento apresenta imagens do SonarQube com **Quality Gate aprovado, 94,6% de cobertura e 0,0% de duplicações** na análise registrada.
Também descrevemos cinco casos funcionais: listar, cadastrar, buscar por ID, atualizar e excluir animais.
Conforme indicado no relatório, a aprovação definitiva desses casos depende da execução do fluxo com MongoDB ativo e Postman.

Para executar o teste automatizado disponível, dentro da pasta `agroPec`, use no Windows:

```powershell
.\mvnw.cmd test
```

No Linux ou macOS, use `./mvnw test`.
