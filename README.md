# AEP 6S - 2ª Entrega

## AgroPec - Gestão de animais, lotes e vendas

Desenvolvemos o AgroPec como uma Prova de Conceito para a AEP de Engenharia de Software, no 6º semestre de 2026.2.
Nosso projeto é uma API REST para organizar informações de animais, lotes de compra e vendas.
A aplicação utiliza Java com Spring Boot e armazena os dados em MongoDB.
A interação acontece por ferramentas como Postman ou pelos testes automatizados, pois o projeto não possui interface gráfica.

## Problema e objetivo

Nosso projeto busca ajudar produtores rurais que precisam acompanhar os dados do rebanho.
Quando essas informações ficam em cadernos ou arquivos separados, consultar o histórico de um animal e relacioná-lo a uma compra ou venda pode ser mais difícil.
Isso também dificulta o acompanhamento das pesagens e a organização do manejo.

A ideia do AgroPec é reunir esses registros em um só lugar.
Cada animal possui identificação por brinco, raça, peso de entrada, status e estruturas para pesagens e vacinações.
Os lotes agrupam os animais e os dados da compra, enquanto as vendas registram os animais negociados, o comprador e os valores informados.
Com isso, queremos facilitar as consultas e apoiar o planejamento das atividades da propriedade.

## ODS escolhido

Escolhemos o **ODS 2 - Fome Zero e Agricultura Sustentável**.
Esse objetivo envolve segurança alimentar e formas sustentáveis de produzir alimentos.
Relacionamos nossa proposta às metas 2.3 e 2.4, que abordam a produtividade dos pequenos produtores e a produção sustentável.
As metas podem ser consultadas na [página da ONU sobre o ODS 2](https://brasil.un.org/pt-br/sdgs/2).

Queremos contribuir com a organização das informações usadas na produção pecuária.
Um acompanhamento mais organizado pode ajudar o produtor a planejar o manejo e o uso dos recursos da propriedade.
Essa é a contribuição que buscamos; ainda não medimos os resultados em uma propriedade real.

## Tecnologias utilizadas

- **Java 21:** desenvolvimento orientado a objetos e regras da aplicação.
- **Spring Boot 4.1.1 e Spring Web MVC:** configuração do backend e criação dos endpoints.
- **Spring Data MongoDB e MongoDB:** acesso e armazenamento dos dados em banco NoSQL.
- **Jakarta Validation:** definição de restrições para os dados recebidos.
- **Lombok:** geração de métodos e construtores nas classes anotadas.
- **Spring Dotenv 5.1.0:** suporte ao uso de arquivos `.env`.
- **Maven 3.9.16:** gerenciamento das dependências, compilação e execução pelo Wrapper incluído.
- **JUnit Jupiter e Spring Boot Test:** teste de inicialização da aplicação.
- **JavaScript e Node.js:** inspeção dos arquivos Java e testes HTTP da API.
- **SonarQube:** análise de qualidade apresentada no relatório do projeto.
- **Git e GitHub:** versionamento e compartilhamento do código.

## Organização e funcionalidades

Organizamos o backend em controller, service, repository, DTO e model.
O controller recebe as requisições, o service coordena as operações e o repository acessa o banco.
Os DTOs representam os dados enviados para a API, e os models representam os documentos e seus históricos.
Essa separação ajuda a manter cada parte do código com uma responsabilidade definida.

No cadastro individual, o animal recebe o status `ATIVO`.
A pesagem localiza o animal pelo brinco e acrescenta o peso ao histórico com a data atual.
O enum também define os estados `VENDIDO` e `PERDIDO`, embora não exista uma operação específica para registrar perda.

O serviço de lotes cria os animais recebidos na requisição e guarda seus identificadores e dados de compra.
A quantidade de animais é calculada a partir da lista enviada.
O serviço de vendas verifica se os animais pertencem ao lote e se estão ativos antes de registrar a negociação.
O peso de venda utiliza o último registro de pesagem ou o peso de entrada, quando não há histórico.
O código atualiza o status dos animais vendidos e marca o lote como vendido quando todos os seus animais estiverem nessa situação.
Os valores financeiros são recebidos na requisição; o serviço não calcula automaticamente o valor total da venda.

### Arquivos do backend

Os arquivos abaixo ficam em `src/main/java/com/agroPec/agropec/` dentro do módulo `agroPec`.
Para execução e testes, utilizamos o módulo em `AEP6S-2-Entrega-main/agroPec`.

| Arquivo | Responsabilidade |
| --- | --- |
| `AgroPecApplication.java` | Carrega o `.env`, quando disponível, e inicia o Spring Boot. |
| `controller/AnimalController.java` | Rotas de cadastro, consulta, atualização, exclusão e pesagem de animais. |
| `controller/LoteController.java` | Rotas de cadastro e consulta de lotes. |
| `controller/VendaController.java` | Rotas de registro e consulta de vendas. |
| `service/AnimalService.java` | Operações sobre animais e histórico de pesagens. |
| `service/LoteService.java` | Cadastro dos animais do lote e organização dos dados da compra. |
| `service/VendaService.java` | Verificações e registro da negociação dos animais. |
| `repository/IAnimalRepository.java` | Persistência e busca de animais por brinco. |
| `repository/ILoteRepository.java` | Persistência e busca de lotes por código. |
| `repository/IVendaRepository.java` | Persistência e busca de vendas por código. |
| `dto/AnimalRequestDTO.java` | Dados para cadastro e atualização de animais. |
| `dto/AnimalLoteDTO.java` | Brinco, raça e peso dos animais enviados com o lote. |
| `dto/AnimalPesagemDTO.java` | Brinco do animal e novo peso. |
| `dto/LoteRequestDTO.java` | Dados de compra do lote e lista de animais. |
| `dto/VendaLoteDTO.java` | Lote, comprador, animais, tipo de negociação, valores e observação. |
| `model/AnimalModel.java` | Documento do animal. |
| `model/LoteModel.java` | Documento do lote. |
| `model/VendaModel.java` | Documento da venda. |
| `model/AnimalLoteModel.java` | Identificação do animal e dados registrados na compra. |
| `model/AnimalVenda.java` | Identificação do animal e peso registrado na venda. |
| `model/PesagemModel.java` | Data e peso de uma pesagem. |
| `model/VacinacaoModel.java` | Estrutura de data e descrição da vacina. |
| `model/enums/EStatusAnimal.java` | Estados `ATIVO`, `VENDIDO` e `PERDIDO`. |

## Banco de dados

Utilizamos o banco `agroPec`, com três coleções.
Os documentos possuem listas de subdocumentos e referências para relacionar animais, lotes e vendas.

| Coleção | Informações |
| --- | --- |
| `animais` | Identificador, brinco, peso de entrada, raça, pesagens, vacinações e status. |
| `lotes` | Identificador, código, descrição, data de entrada, quantidade e lista de animais, valor de compra e status. |
| `vendas` | Identificador, códigos da venda e do lote, data, comprador, quantidade e lista de animais, peso total, tipo de negociação, valores, status e observação. |

As listas de pesagens e vacinações ficam no documento do animal.
Cada item de `lotes.animais` guarda o ID, o brinco, a raça e o peso de compra do animal.
Cada item de `vendas.animais` guarda o ID, o brinco e o peso registrado na venda.
O campo `animalId` relaciona esses itens à coleção `animais`, e `codigoLote` relaciona a venda ao lote.
O arquivo `mongoDBConfig.txt` descreve a criação das coleções e seus validadores, mas não é executado automaticamente pela aplicação.

## Endpoints da API

A URL base padrão é `http://localhost:8080`.
No cadastro, na atualização e na pesagem, enviamos os dados em JSON com `Content-Type: application/json`.
As rotas documentadas correspondem ao módulo `AEP6S-2-Entrega-main/agroPec`.

| Método | Rota | Finalidade |
| --- | --- | --- |
| GET | `/animais/list` | Listar os animais. |
| GET | `/animais/list/{id}` | Buscar um animal pelo ID. |
| GET | `/animais/list/brinco/{brincoAnimal}` | Buscar um animal pelo brinco. |
| POST | `/animais/create` | Cadastrar um animal. |
| POST | `/animais/pesarAnimal` | Registrar uma pesagem. |
| PUT | `/animais/update/{id}` | Atualizar os dados básicos e as vacinações do animal. |
| DELETE | `/animais/delete/{id}` | Excluir um animal. |
| POST | `/api/lotes` | Cadastrar um lote com seus animais. |
| GET | `/api/lotes` | Listar os lotes. |
| GET | `/api/lotes/{id}` | Buscar um lote pelo ID numérico. |
| GET | `/api/lotes/codigo/{codigo}` | Buscar um lote pelo código. |
| POST | `/api/vendas` | Registrar a venda dos animais informados. |
| GET | `/api/vendas` | Listar as vendas. |
| GET | `/api/vendas/{id}` | Buscar uma venda pelo ID. |
| GET | `/api/vendas/codigo/{codigo}` | Buscar uma venda pelo código. |

O cadastro de animal recebe `brinco`, `pesoEntrada`, `raca`, `pesagens` e `vacinacoes`.
A pesagem recebe `brincoAnimal` e `novoPeso`.
O cadastro de lote recebe `codigoLote`, `descricao`, `dataEntrada`, `valorLoteCompra` e a lista `animais`.
A venda recebe `codigoLote`, `comprador`, `brincosAnimais`, `tipoNegociacao`, `valorPorArroba`, `valorTotalVenda` e `observacao`.
Exemplos de requisições estão no [catálogo de testes](./AEP6S-2-Entrega-main/casos-teste-js/CASOS_DE_TESTE.md).

## Como executar

Para executar o backend, precisamos de JDK 21, Git e MongoDB em execução na porta `27017`.
Para os testes em JavaScript, também precisamos de Node.js; utilizamos a versão 24.14.0 na validação.
O Maven Wrapper baixa as dependências na primeira execução.

Clone o repositório e entre na sua pasta principal:

```bash
git clone https://github.com/matheusgalencar/AEP6S-2-Entrega.git
cd AEP6S-2-Entrega
```

A conexão está configurada em `AEP6S-2-Entrega-main/agroPec/src/main/resources/application.properties`:

```properties
spring.mongodb.uri=mongodb://localhost:27017/agroPec
```

O `.env` é opcional para essa configuração local.
Para outro banco, altere a URI ou configure a variável de ambiente `SPRING_MONGODB_URI`.
Os comandos abaixo usam o Wrapper disponível em `agroPec` na raiz e apontam para o módulo que acompanha os testes.
Isso é necessário porque a pasta interna não contém a configuração `.mvn/wrapper`.

No Windows, pelo PowerShell:

```powershell
.\agroPec\mvnw.cmd -f AEP6S-2-Entrega-main/agroPec/pom.xml spring-boot:run
```

No Linux ou macOS:

```bash
sh agroPec/mvnw -f AEP6S-2-Entrega-main/agroPec/pom.xml spring-boot:run
```

A API fica disponível em `http://localhost:8080`.
Para conferir a conexão, acesse [a listagem de animais](http://localhost:8080/animais/list).
Com o banco disponível e sem animais cadastrados, essa rota retorna `[]`.
Para encerrar a aplicação no terminal, pressione `Ctrl+C`.

## Testes realizados em JavaScript

Os testes estão em `AEP6S-2-Entrega-main/casos-teste-js` e utilizam os recursos nativos do Node.js, sem necessidade de `npm install`.
Organizamos as verificações em caixa branca e caixa preta.

| Arquivo | O que verifica |
| --- | --- |
| `caixaBranca.js` | Executa 54 inspeções nos arquivos Java, conferindo classes, métodos, campos, rotas, validações e trechos das regras dos services. |
| `caixaPreta.test.js` | Executa 9 testes HTTP de cadastro, listagem, consulta, atualização, exclusão, validação do brinco, pesagem e listagens de lotes e vendas. |
| `CASOS_DE_TESTE.md` | Descreve 19 cenários funcionais, sendo 9 automatizados e 10 manuais, além de 14 caminhos internos previstos para testes Java. |

Os testes de caixa branca leem o código como texto; eles não executam os métodos Java nem medem sua cobertura.
Os testes de caixa preta usam `fetch` para conferir respostas reais da API.
Como esses testes criam, alteram e excluem animais, devemos executá-los com um banco separado para testes.

### Executar os testes de caixa branca

Em outro terminal, a partir da raiz do repositório:

```bash
cd AEP6S-2-Entrega-main
node --test casos-teste-js/caixaBranca.js
```

Execute nessa pasta para que o script encontre o código Java corretamente.
A variável `AGROPEC_SOURCE_DIR` permite informar outro diretório de fontes, se necessário.

### Executar os testes de caixa preta

Com a API e o MongoDB de testes em execução, permaneça em `AEP6S-2-Entrega-main`.
No PowerShell:

```powershell
$env:EXECUTAR_API_TESTS = '1'
$env:API_BASE_URL = 'http://localhost:8080'
node --test casos-teste-js/caixaPreta.test.js
```

No Linux ou macOS:

```bash
EXECUTAR_API_TESTS=1 API_BASE_URL=http://localhost:8080 node --test casos-teste-js/caixaPreta.test.js
```

Sem `EXECUTAR_API_TESTS=1`, os testes HTTP ficam ignorados (`skipped`).
Na execução de 10/11/2026, os **54 testes de inspeção e os 9 testes HTTP passaram**, sem falhas ou casos ignorados.
Os cenários automatizados não incluem o cadastro de lotes nem o fluxo completo de venda.

## Teste Java e relatório

O arquivo `AgroPecApplicationTests.java`, em `src/test/java/com/agroPec/agropec/`, contém o teste `contextLoads()`, que verifica a inicialização do Spring.
Para executá-lo, volte à raiz do repositório e use no Windows:

```powershell
.\agroPec\mvnw.cmd -f AEP6S-2-Entrega-main/agroPec/pom.xml test
```

No Linux ou macOS, use `sh agroPec/mvnw -f AEP6S-2-Entrega-main/agroPec/pom.xml test`.
A compilação e esse teste também passaram na verificação realizada.
O [relatório em PDF](./Relatorio_testes%282%29.pdf) reúne evidências de análise no SonarQube.
Os comandos apresentados não geram uma medição de cobertura Java; ainda precisamos disponibilizar uma configuração reproduzível para comprovar a cobertura exigida pela atividade.

## Outros arquivos do projeto

O repositório contém o módulo `agroPec` na raiz e o módulo utilizado nesta documentação em `AEP6S-2-Entrega-main/agroPec`.
Os arquivos de apoio completam a configuração, a documentação e a compilação da aplicação.

| Arquivo ou pasta | Finalidade |
| --- | --- |
| `README.md` | Apresentação do projeto e instruções de execução. Há também uma cópia em `AEP6S-2-Entrega-main`. |
| `Relatorio_testes(2).pdf` | Relatório de qualidade disponível na raiz e na pasta interna. |
| `mongoDBConfig.txt` | Script com a estrutura e os validadores das coleções, presente nas duas pastas. |
| `pom.xml` | Dependências, versão Java e plugins de cada módulo. |
| `mvnw` e `mvnw.cmd` | Scripts de execução do Maven para Linux/macOS e Windows. |
| `agroPec/.mvn/wrapper/maven-wrapper.properties` | Configuração do Maven Wrapper disponível na raiz. |
| `application.properties` | Nome da aplicação e conexão com o banco; fica em `src/main/resources` de cada módulo. |
| `agroPec/.gitignore` e `.gitattributes` | Regras de versionamento e finais de linha dos arquivos da pasta da raiz. |
| `.idea/` | Arquivos de configuração do IntelliJ: módulo `.iml`, `.gitignore`, `compiler.xml`, `encodings.xml`, `jarRepositories.xml`, `misc.xml`, `modules.xml` e `vcs.xml`. |
| `AEP6S-2-Entrega-main/agroPec/target/classes/` | Classes de produção compiladas e cópia de `application.properties`. |
| `AEP6S-2-Entrega-main/agroPec/target/test-classes/` | Classe compilada do teste Java. |
| `AEP6S-2-Entrega-main/agroPec/target/maven-status/` | Listas `createdFiles.lst` e `inputFiles.lst` produzidas na compilação principal e dos testes. |

Os arquivos em `target` são gerados na compilação e não representam resultados de cobertura.
Os modelos, controllers, services, repositories, DTOs e testes estão descritos nas seções acima.
