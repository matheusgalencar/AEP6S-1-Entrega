# Catálogo de casos de teste - AgroPec

## A Caixa-preta 
Os casos abaixo verificam entradas e saídas sem depender da implementação interna. Os caminhos foram extraídos dos controllers do projeto.

| ID | Funcionalidade / cenário | Entrada / procedimento | Resultado esperado | Situação inicial |
|---|---|---|---|---|
| CT-PP-001 | Listar animais | `GET /animais/list` | HTTP 200 e corpo em formato de lista JSON | Automatizado |

| CT-PP-002 | Cadastrar animal válido | `POST /animais/create`, JSON com brinco, pesoEntrada positivo e raça | HTTP 201 e animal retornado com os dados enviados | Automatizado |

| CT-PP-003 | Cadastrar animal sem brinco | `POST /animais/create`, omitindo `brinco` | HTTP 400 por validação | Automatizado |

| CT-PP-004 | Buscar animal por ID | Cadastrar animal e chamar `GET /animais/list/{id}` | HTTP 200 e mesmo ID do animal criado | Automatizado |

| CT-PP-005 | Atualizar animal | Cadastrar e chamar `PUT /animais/update/{id}` com nova raça/peso | HTTP 200 e dados atualizados | Automatizado |

| CT-PP-006 | Excluir animal | Cadastrar e chamar `DELETE /animais/delete/{id}` | HTTP 204 | Automatizado |

| CT-PP-007 | Registrar pesagem | Cadastrar animal e enviar `POST /animais/pesarAnimal` com `novoPeso` positivo e `brincoAnimal` existente | HTTP 201 e nova pesagem no histórico | Automatizado |

| CT-PP-008 | Listar lotes | `GET /api/lotes` | HTTP 200 e lista JSON | Automatizado |

| CT-PP-009 | Listar vendas | `GET /api/vendas` | HTTP 200 e lista JSON | Automatizado |

| CT-PP-010 | Cadastrar lote com animais válidos | `POST /api/lotes`, preencher código, descrição, data, valor e lista `animais` | HTTP 201; lote deve retornar quantidade igual à lista de animais | Manual — requer corrigir/validar controller e DTO |

| CT-PP-011 | Cadastrar lote sem lista de animais | `POST /api/lotes` sem `animais` | Sistema deve rejeitar a entrada de forma controlada, sem erro interno 500 | Manual — validar regra desejada |

| CT-PP-012 | Consultar lote por ID | `GET /api/lotes/{id}` com ID existente | HTTP 200 e lote correspondente | Manual — há divergência no nome de `@PathVariable` |

| CT-PP-013 | Consultar lote por código | `GET /api/lotes/codigo/{codigo}` com código existente | HTTP 200 e lote correspondente | Manual — há divergência no nome de `@PathVariable` |

| CT-PP-014 | Registrar venda de animal pertencente ao lote | `POST /api/vendas` com código de lote, comprador, brinco existente no lote e tipo de negociação | HTTP 201; venda registrada e animal passa para `VENDIDO` | Manual — requer dados previamente cadastrados |

| CT-PP-015 | Vender animal inexistente | Enviar brinco que não existe | Operação rejeitada com mensagem controlada de animal não encontrado | Manual |

| CT-PP-016 | Vender animal de outro lote | Enviar animal existente, mas não associado ao lote informado | Operação rejeitada; animal não deve ser alterado | Manual |

| CT-PP-017 | Vender animal já vendido/perdido | Enviar animal cujo status não seja `ATIVO` | Operação rejeitada; não deve criar venda duplicada | Manual |

| CT-PP-018 | Consultar venda por ID | `GET /api/vendas/{id}` com ID existente | HTTP 200 e venda correspondente | Manual |

| CT-PP-019 | Consultar venda por código | `GET /api/vendas/codigo/{codigo}` com código existente | HTTP 200 e venda correspondente | Manual — há divergência no nome de `@PathVariable` |

### Exemplo de JSON para cadastro de animal

```json
{
  "brinco": "CT-ANIMAL-001",
  "pesoEntrada": 450.5,
  "raca": "Nelore",
  "pesagens": [],
  "vacinacoes": []
}
```

### Exemplo de JSON para registrar pesagem

```json
{
  "novoPeso": 470.0,
  "brincoAnimal": "CT-ANIMAL-001"
}
```

### Exemplo de JSON para cadastrar lote

```json
{
  "codigoLote": "CT-LOTE-001",
  "descricao": "Lote de teste",
  "dataEntrada": "2026-11-10",
  "valorLoteCompra": 15000.0,
  "animais": [
    {
      "brinco": "CT-ANIMAL-LOTE-001",
      "raca": "Nelore",
      "pesoEntrada": 420.0
    }
  ]
}
```

## Caixa-branca 
### Verificações automatizadas no `whitebox.test.js`

| IDs |
|---|---|---|
| CT-CB-001 a CT-CB-023 | `AgroPecApplication`; três Controllers; cinco DTOs; sete Models; enum `EStatusAnimal`; três Repositories; três Services | Existência de cada arquivo e declaração da classe/interface/enum correspondente |

| CT-CB-024 | `AnimalService` | Métodos de criação, listagem, busca, exclusão, atualização e pesagem; tratamento de animal não encontrado; verificação de lista de pesagens nula |

| CT-CB-025 | `LoteService` | Métodos principais, laço sobre animais e cálculo da quantidade |

| CT-CB-026 | `VendaService` | Busca com exceção, pertencimento ao lote, status ativo, status vendido, histórico de pesagem e verificação de venda total |

| CT-CB-027 | `AnimalController` | Rotas principais e validação do corpo da requisição |

| CT-CB-028 | `LoteController` e `VendaController` | Prefixos e mapeamentos POST |

| CT-CB-029 | `EStatusAnimal` | Valores `ATIVO`, `VENDIDO` e `PERDIDO` |

| CT-CB-030 | `AnimalPesagemDTO` e `AnimalRequestDTO` | Detecção do uso inadequado de `@NotBlank` em campos `Double` |

| CT-CB-031 | `AnimalController` | Detecção de dois mapeamentos GET com o mesmo padrão `/list/{variável}` |

| CT-CB-032 | `LoteController` e `VendaController` | Detecção de divergências entre nomes nas rotas e parâmetros `@PathVariable` |

| CT-CB-033 | Três Repositories | Consultas personalizadas por brinco, código do lote e código da venda |

| CT-CB-034 | `AnimalModel`, `LoteModel` e `VendaModel` | Presença dos principais atributos das entidades |

| CT-CB-035 | `AnimalLoteDTO` e `VendaLoteDTO` | Campos obrigatórios e validações de peso/lista |

| CT-CB-036 | `AgroPecApplication` | `@SpringBootApplication` e `SpringApplication.run` |


### Casos de caminho lógico

Os cenários abaixo são casos de caixa-branca definidos pela leitura dos métodos. Eles exigem execução dos métodos Java com dependências simuladas (Mockito) para comprovar o comportamento.

| ID | Método | Caminho / entrada | Resultado esperado |
|---|---|---|---|
| CT-CB-M01 | `AnimalService.findByIdAnimal` | ID existente | Retornar o animal do repositório |

| CT-CB-M02 | `AnimalService.findByIdAnimal` | ID inexistente | Lançar `RuntimeException` com mensagem de animal não encontrado |

| CT-CB-M03 | `AnimalService.buscarPorBrinco` | Brinco existente e inexistente | Retornar animal no primeiro caso; lançar exceção no segundo |

| CT-CB-M04 | `AnimalService.pesarAnimal` | Animal com `pesagens == null` | Inicializar lista, adicionar pesagem e salvar 

| CT-CB-M05 | `AnimalService.pesarAnimal` | Animal com lista de pesagens existente | Não substituir a lista; adicionar novo item e salvar |

| CT-CB-M06 | `LoteService.cadastrarLote` | DTO com um ou mais animais | Criar animais, associá-los ao lote e definir quantidade correta |

| CT-CB-M07 | `LoteService.cadastrarLote` | DTO com lista de animais nula | Atualmente pode lançar `NullPointerException`; definir validação esperada |

| CT-CB-M08 | `VendaService.venderLote` | Lote inexistente | Lançar exceção de lote não encontrado |

| CT-CB-M09 | `VendaService.venderLote` | Animal inexistente | Lançar exceção de animal não encontrado |

| CT-CB-M10 | `VendaService.venderLote` | Animal não pertence ao lote | Rejeitar venda antes de marcar animal como vendido |

| CT-CB-M11 | `VendaService.venderLote` | Animal com status diferente de `ATIVO` | Rejeitar venda repetida |

| CT-CB-M12 | `VendaService.venderLote` | Animal com pesagens e sem pesagens | Usar última pesagem quando existir; caso contrário, usar peso de entrada |

| CT-CB-M13 | `VendaService.venderLote` | Todos os animais vendidos | Alterar status do lote para `VENDIDO` |

| CT-CB-M14 | `VendaService.venderLote` | Apenas parte dos animais vendidos | Não marcar o lote inteiro como vendido |

