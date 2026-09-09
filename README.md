# AEP 6S - 1° Entrega

AgroPec - Gestão de animais

Prova de Conceito (PoC) da primeira entrega da AEP de Engenharia de Software, 6º semestre, 2026.2.
O projeto está alinhado ao **ODS 2 - Fome Zero e Agricultura Sustentável**.

## Sobre o projeto e o problema

O AgroPec é uma API REST voltada à organização das informações de animais de um rebanho.
A proposta atende produtores rurais e pessoas responsáveis pelo manejo pecuário.
O problema abordado é a dificuldade de consultar e manter registros individuais quando os dados estão dispersos em cadernos ou arquivos separados.
Essa fragmentação pode dificultar o acompanhamento dos animais e a recuperação de seu histórico.

A aplicação propõe centralizar identificação por brinco, raça, peso de entrada e estruturas de pesagens e vacinações.
O brinco representa a identificação utilizada no manejo, enquanto o sistema utiliza um identificador próprio para acessar cada registro.
As informações ficam armazenadas em MongoDB e são acessadas por requisições HTTP com dados em JSON.
Nesta etapa, a interação acontece por ferramentas como Postman, Insomnia ou terminal, pois o projeto ainda não possui interface gráfica.

Com os dados organizados, espera-se facilitar consultas e apoiar decisões humanas relacionadas ao manejo.
A primeira entrega estabelece a base cadastral da solução e permite avaliar sua viabilidade técnica.
Os benefícios produtivos e ambientais são objetivos da proposta e ainda não foram medidos em uma propriedade real.

## Relação com o ODS 2

O **ODS 2 - Fome Zero e Agricultura Sustentável** envolve segurança alimentar e formas sustentáveis de produzir alimentos.
A meta 2.3 contempla a produtividade e a renda de pequenos produtores, enquanto a meta 2.4 trata de sistemas sustentáveis de produção.
Essas metas estão descritas na [página oficial do ODS 2 das Nações Unidas no Brasil](https://brasil.un.org/pt-br/sdgs/2).

O AgroPec busca contribuir por meio da organização das informações que apoiam a produção pecuária.
O acompanhamento individual pode auxiliar o responsável pelo rebanho no planejamento de ações de manejo.
A centralização dos registros também busca apoiar uma gestão mais cuidadosa dos recursos empregados na produção.
Essa contribuição é potencial e indireta; a versão atual não calcula produtividade, desperdício ou impacto ambiental.

## Tecnologias utilizadas

As versões indicadas foram obtidas do `pom.xml` e da configuração do Maven Wrapper.

| Tecnologia | Finalidade |
| --- | --- |
| Java 21 | Desenvolvimento orientado a objetos. |
| Spring Boot 4.1.1 | Inicialização e configuração da aplicação. |
| Spring Web MVC | Implementação dos endpoints REST. |
| Spring Data MongoDB e MongoDB | Mapeamento dos objetos e persistência em banco NoSQL. |
| Jakarta Validation | Declaração de restrições para os dados recebidos. |
| Lombok | Geração de métodos nas classes que utilizam suas anotações. |
| Spring Dotenv 5.1.0 | Suporte a arquivos `.env`, além do carregamento manual presente na classe principal. |
| Maven 3.9.16, via Wrapper 3.3.4 | Gerenciamento de dependências, compilação e execução. |
| JUnit Jupiter e Spring Boot Test | Execução do teste automatizado existente. |
| Git e GitHub | Versionamento e disponibilização do projeto.
