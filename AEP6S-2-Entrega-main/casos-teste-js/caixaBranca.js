const { test } = require('node:test');
const assert = require('node:assert/strict');
const fs = require('node:fs');
const path = require('node:path');

const pastaJava = process.env.AGROPEC_SOURCE_DIR || path.join(
  process.cwd(),
  'agroPec',
  'src',
  'main',
  'java',
  'com',
  'agroPec',
  'agropec'
);

function lerArquivo(caminho) {
  const arquivo = path.join(pastaJava, caminho);

  assert.ok(
    fs.existsSync(arquivo),
    `Arquivo não encontrado: ${caminho}`
  );

  return fs.readFileSync(arquivo, 'utf8');
}

function conferir(codigo, expressao, mensagem) {
  assert.match(codigo, expressao, mensagem);
}

const arquivosDoProjeto = [
  ['AgroPecApplication.java', 'AgroPecApplication'],

  ['controller/AnimalController.java', 'AnimalController'],
  ['controller/LoteController.java', 'LoteController'],
  ['controller/VendaController.java', 'VendaController'],

  ['dto/AnimalLoteDTO.java', 'AnimalLoteDTO'],
  ['dto/AnimalPesagemDTO.java', 'AnimalPesagemDTO'],
  ['dto/AnimalRequestDTO.java', 'AnimalRequestDTO'],
  ['dto/LoteRequestDTO.java', 'LoteRequestDTO'],
  ['dto/VendaLoteDTO.java', 'VendaLoteDTO'],

  ['model/AnimalLoteModel.java', 'AnimalLoteModel'],
  ['model/AnimalModel.java', 'AnimalModel'],
  ['model/AnimalVenda.java', 'AnimalVenda'],
  ['model/LoteModel.java', 'LoteModel'],
  ['model/PesagemModel.java', 'PesagemModel'],
  ['model/VacinacaoModel.java', 'VacinacaoModel'],
  ['model/VendaModel.java', 'VendaModel'],
  ['model/enums/EStatusAnimal.java', 'EStatusAnimal'],

  ['repository/IAnimalRepository.java', 'IAnimalRepository'],
  ['repository/ILoteRepository.java', 'ILoteRepository'],
  ['repository/IVendaRepository.java', 'IVendaRepository'],

  ['service/AnimalService.java', 'AnimalService'],
  ['service/LoteService.java', 'LoteService'],
  ['service/VendaService.java', 'VendaService']
];

for (const [arquivo, nome] of arquivosDoProjeto) {
  test(`${nome} está presente no projeto`, () => {
    const codigo = lerArquivo(arquivo);

    assert.match(
      codigo,
      new RegExp(`\\b(class|interface|enum)\\s+${nome}\\b`)
    );
  });
}

test('AnimalService possui os métodos de cadastro e consulta', () => {
  const codigo = lerArquivo('service/AnimalService.java');

  const metodos = [
    'createAnimal',
    'findAllAnimals',
    'findByIdAnimal',
    'buscarPorBrinco',
    'deleteByIdAnimal',
    'updateAnimal',
    'pesarAnimal'
  ];

  for (const metodo of metodos) {
    assert.match(
      codigo,
      new RegExp(`\\b${metodo}\\s*\\(`),
      `Método ${metodo} não encontrado.`
    );
  }
});

test('AnimalService trata a busca de animais inexistentes', () => {
  const codigo = lerArquivo('service/AnimalService.java');

  conferir(
    codigo,
    /findById\(id\)\.orElseThrow/,
    'A busca por ID precisa tratar animais não encontrados.'
  );

  conferir(
    codigo,
    /findByBrinco\(brincoAnimal\)\s*\.orElseThrow/,
    'A busca por brinco precisa tratar animais não encontrados.'
  );
});

test('AnimalService prepara o histórico antes de registrar uma pesagem', () => {
  const codigo = lerArquivo('service/AnimalService.java');

  conferir(
    codigo,
    /getPesagens\(\)\s*==\s*null/,
    'É necessário verificar se a lista de pesagens está vazia ou nula.'
  );

  conferir(
    codigo,
    /setPesagens\(new ArrayList<.*>\(\)\)/,
    'A lista de pesagens deve ser inicializada.'
  );

  conferir(
    codigo,
    /getPesagens\(\)\.add\(novaPesagem\)/,
    'A nova pesagem deve ser adicionada ao histórico.'
  );
});

test('LoteService possui as operações de cadastro e consulta', () => {
  const codigo = lerArquivo('service/LoteService.java');

  for (const metodo of [
    'cadastrarLote',
    'listarLotes',
    'listarLoteId',
    'buscarPorCodigo'
  ]) {
    assert.match(
      codigo,
      new RegExp(`\\b${metodo}\\s*\\(`),
      `Método ${metodo} não encontrado.`
    );
  }
});

test('LoteService percorre os animais informados no cadastro', () => {
  const codigo = lerArquivo('service/LoteService.java');

  conferir(
    codigo,
    /for\s*\(\s*AnimalLoteDTO\s+\w+\s*:/,
    'Os animais enviados precisam ser percorridos.'
  );

  conferir(
    codigo,
    /animalService\.createAnimal\(/,
    'Os animais precisam ser cadastrados.'
  );

  conferir(
    codigo,
    /setQuantidadeAnimais\(/,
    'A quantidade de animais precisa ser registrada.'
  );

  conferir(
    codigo,
    /loteRepository\.save\(lote\)/,
    'O lote precisa ser salvo.'
  );
});

test('VendaService verifica se o lote e os animais existem', () => {
  const codigo = lerArquivo('service/VendaService.java');

  conferir(
    codigo,
    /findByCodigoLote[\s\S]{0,120}orElseThrow/,
    'O lote precisa ser localizado antes da venda.'
  );

  conferir(
    codigo,
    /findByBrinco[\s\S]{0,120}orElseThrow/,
    'O animal precisa ser localizado antes da venda.'
  );
});

test('VendaService verifica se o animal pertence ao lote', () => {
  const codigo = lerArquivo('service/VendaService.java');

  conferir(
    codigo,
    /anyMatch\(/,
    'Deve existir uma verificação de vínculo entre animal e lote.'
  );

  conferir(
    codigo,
    /if\s*\(!pertenceAoLote\)/,
    'A venda deve ser interrompida se o animal não pertencer ao lote.'
  );
});

test('VendaService verifica o status do animal antes da venda', () => {
  const codigo = lerArquivo('service/VendaService.java');

  conferir(
    codigo,
    /animal\.getStatus\(\)\s*!=\s*EStatusAnimal\.ATIVO/,
    'O animal precisa estar ativo para ser vendido.'
  );

  conferir(
    codigo,
    /setStatus\(EStatusAnimal\.VENDIDO\)/,
    'Após a venda, o status deve ser atualizado.'
  );
});

test('VendaService utiliza o peso mais recente do animal', () => {
  const codigo = lerArquivo('service/VendaService.java');

  conferir(
    codigo,
    /getPesagens\(\)\s*!=\s*null/,
    'O histórico de pesagens precisa ser verificado.'
  );

  conferir(
    codigo,
    /getLast\(\)\.getPeso\(\)/,
    'O sistema deve considerar a última pesagem.'
  );

  conferir(
    codigo,
    /getPesoEntrada\(\)/,
    'O peso de entrada deve estar disponível como referência.'
  );
});

test('VendaService verifica se todos os animais foram vendidos', () => {
  const codigo = lerArquivo('service/VendaService.java');

  conferir(
    codigo,
    /allMatch\(/,
    'Deve verificar a situação de todos os animais do lote.'
  );

  conferir(
    codigo,
    /loteAtual\.setStatus\("VENDIDO"\)/,
    'O lote deve ser marcado como vendido.'
  );
});

test('AnimalController possui as rotas principais', () => {
  const codigo = lerArquivo('controller/AnimalController.java');

  const rotas = [
    /@GetMapping\("\/list"\)/,
    /@PostMapping\("\/create"\)/,
    /@PostMapping\("\/pesarAnimal"\)/,
    /@PutMapping\("\/update\/\{id\}"\)/,
    /@DeleteMapping\("\/delete\/\{id\}"\)/
  ];

  for (const rota of rotas) {
    assert.match(codigo, rota, `Rota não encontrada: ${rota}`);
  }

  conferir(
    codigo,
    /@RequestMapping\("\/animais"\)/,
    'O caminho principal de animais não foi encontrado.'
  );
});

test('LoteController possui rotas para cadastrar e consultar lotes', () => {
  const codigo = lerArquivo('controller/LoteController.java');

  conferir(codigo, /@RequestMapping\("\/api\/lotes"\)/, 'Caminho principal de lotes não encontrado.');
  conferir(codigo, /@PostMapping/, 'Rota de cadastro não encontrada.');
  conferir(codigo, /@GetMapping/, 'Rotas de consulta não encontradas.');
});

test('VendaController possui rotas para registrar e consultar vendas', () => {
  const codigo = lerArquivo('controller/VendaController.java');

  conferir(codigo, /@RequestMapping\("\/api\/vendas"\)/, 'Caminho principal de vendas não encontrado.');
  conferir(codigo, /@PostMapping/, 'Rota de venda não encontrada.');
  conferir(codigo, /@GetMapping/, 'Rotas de consulta não encontradas.');
});

test('AnimalLoteDTO verifica os dados obrigatórios do animal', () => {
  const codigo = lerArquivo('dto/AnimalLoteDTO.java');

  conferir(codigo, /@NotBlank[\s\S]*?private String brinco/, 'O brinco deve ser obrigatório.');
  conferir(codigo, /@NotBlank[\s\S]*?private String raca/, 'A raça deve ser obrigatória.');
  conferir(codigo, /@NotNull[\s\S]*?@Positive[\s\S]*?private Double pesoEntrada/, 'O peso deve ser informado e positivo.');
});

test('AnimalPesagemDTO recebe o brinco e o novo peso', () => {
  const codigo = lerArquivo('dto/AnimalPesagemDTO.java');

  conferir(codigo, /private Double novoPeso/, 'Campo de novo peso não encontrado.');
  conferir(codigo, /@Positive/, 'O novo peso precisa ser positivo.');
  conferir(codigo, /private String brincoAnimal/, 'Campo de identificação do animal não encontrado.');
});

test('AnimalRequestDTO possui os campos utilizados no cadastro', () => {
  const codigo = lerArquivo('dto/AnimalRequestDTO.java');

  for (const campo of [
    'brinco',
    'pesoEntrada',
    'raca',
    'vacinacoes',
    'pesagens'
  ]) {
    conferir(
      codigo,
      new RegExp(`\\b${campo}\\b`),
      `Campo ${campo} não encontrado.`
    );
  }
});

test('LoteRequestDTO recebe as informações do lote', () => {
  const codigo = lerArquivo('dto/LoteRequestDTO.java');

  for (const campo of [
    'codigoLote',
    'descricao',
    'dataEntrada',
    'valorLoteCompra',
    'animais'
  ]) {
    conferir(
      codigo,
      new RegExp(`\\b${campo}\\b`),
      `Campo ${campo} não encontrado.`
    );
  }
});

test('VendaLoteDTO verifica as informações necessárias para uma venda', () => {
  const codigo = lerArquivo('dto/VendaLoteDTO.java');

  conferir(codigo, /@NotBlank[\s\S]*?private String codigoLote/, 'Código do lote obrigatório não encontrado.');
  conferir(codigo, /@NotBlank[\s\S]*?private String comprador/, 'Comprador obrigatório não encontrado.');
  conferir(codigo, /@NotEmpty[\s\S]*?private List<String> brincosAnimais/, 'A lista de animais não pode ficar vazia.');
});
test('AnimalModel possui os dados principais do animal', () => {
  const codigo = lerArquivo('model/AnimalModel.java');

  for (const campo of [
    'id',
    'brinco',
    'pesoEntrada',
    'raca',
    'pesagens',
    'vacinacoes',
    'status'
  ]) {
    conferir(codigo, new RegExp(`\\b${campo}\\b`), `Campo ${campo} não encontrado.`);
  }
});

test('LoteModel possui os dados necessários para representar um lote', () => {
  const codigo = lerArquivo('model/LoteModel.java');

  for (const campo of [
    'id',
    'codigoLote',
    'descricao',
    'dataEntrada',
    'quantidadeAnimais',
    'animais',
    'valorLoteCompra',
    'status'
  ]) {
    conferir(codigo, new RegExp(`\\b${campo}\\b`), `Campo ${campo} não encontrado.`);
  }
});

test('VendaModel possui os dados da negociação', () => {
  const codigo = lerArquivo('model/VendaModel.java');

  for (const campo of [
    'codigoVenda',
    'codigoLote',
    'dataVenda',
    'comprador',
    'quantidadeAnimais',
    'animais',
    'tipoNegociacao',
    'valorPorArroba',
    'valorTotalVenda'
  ]) {
    conferir(codigo, new RegExp(`\\b${campo}\\b`), `Campo ${campo} não encontrado.`);
  }
});

test('Os modelos auxiliares guardam os dados de pesagem, vacinação e venda', () => {
  const animalLote = lerArquivo('model/AnimalLoteModel.java');
  const animalVenda = lerArquivo('model/AnimalVenda.java');
  const pesagem = lerArquivo('model/PesagemModel.java');
  const vacinacao = lerArquivo('model/VacinacaoModel.java');

  conferir(animalLote, /animalId|brincoAnimal/, 'Identificação do animal no lote não encontrada.');
  conferir(animalVenda, /animalId|brincoAnimal/, 'Identificação do animal na venda não encontrada.');
  conferir(pesagem, /LocalDate[\s\S]*Double/, 'Data e peso da pesagem não encontrados.');
  conferir(vacinacao, /LocalDate[\s\S]*descricaoVacina/, 'Data e descrição da vacina não encontradas.');
});

test('EStatusAnimal possui os estados utilizados pelo sistema', () => {
  const codigo = lerArquivo('model/enums/EStatusAnimal.java');

  for (const status of ['ATIVO', 'VENDIDO', 'PERDIDO']) {
    assert.ok(codigo.includes(status), `Status ${status} não encontrado.`);
  }
});

test('IAnimalRepository permite buscar animais pelo brinco', () => {
  const codigo = lerArquivo('repository/IAnimalRepository.java');

  conferir(
    codigo,
   /Optional<AnimalModel>\s+findByBrinco\(String\s+brinco\)/,
    'Método de busca por brinco não encontrado.'
  );
});

test('ILoteRepository permite buscar lotes pelo código', () => {
  const codigo = lerArquivo('repository/ILoteRepository.java');

  conferir(
    codigo,
    /Optional<LoteModel>\s+findByCodigoLote\(String\s+codigoLote\)/,
    'Método de busca por código do lote não encontrado.'
  );
});

test('IVendaRepository permite buscar vendas pelo código', () => {
  const codigo = lerArquivo('repository/IVendaRepository.java');

  conferir(
    codigo,
   /Optional<VendaModel>\s+findByCodigoVenda\(String\s+codigoVenda\)/,
    'Método de busca por código da venda não encontrado.'
  );
});
test('Os DTOs não devem usar @NotBlank em campos numéricos', () => {
  const arquivos = [
    'dto/AnimalPesagemDTO.java',
    'dto/AnimalRequestDTO.java'
  ];

  for (const arquivo of arquivos) {
    const codigo = lerArquivo(arquivo);

    assert.doesNotMatch(
      codigo,
      /@NotBlank[\s\S]{0,100}private\s+Double\b/,
      `${arquivo}: use @NotNull para campos numéricos.`
    );
  }
});

test('As rotas GET de animais não devem ter caminhos repetidos', () => {
  const codigo = lerArquivo('controller/AnimalController.java');

  const rotas = [
    ...codigo.matchAll(/@GetMapping\("([^"]+)"\)/g)
  ].map(item => item[1]);

  const rotasOrganizadas = rotas.map(
    rota => rota.replace(/\{[^}]+\}/g, '{}')
  );

  const repetidas = rotasOrganizadas.filter(
    (rota, indice) => rotasOrganizadas.indexOf(rota) !== indice
  );

  assert.deepEqual(
    repetidas,
    [],
    `Foram encontradas rotas repetidas: ${repetidas.join(', ')}`
  );
});

test('Os parâmetros das rotas de lote devem corresponder aos métodos', () => {
  const codigo = lerArquivo('controller/LoteController.java');

  assert.ok(
    codigo.includes('@GetMapping("/{id}")') &&
    codigo.includes('@PathVariable("id")'),
    'O parâmetro ID da rota de lote precisa estar declarado corretamente.'
  );

  assert.ok(
    codigo.includes('@GetMapping("/codigo/{codigo}")') &&
    codigo.includes('@PathVariable("codigo")'),
    'O parâmetro código da rota de lote precisa estar declarado corretamente.'
  );
});

test('O parâmetro da rota de consulta de venda deve estar correto', () => {
  const codigo = lerArquivo('controller/VendaController.java');

  assert.ok(
    codigo.includes('@GetMapping("/codigo/{codigo}")') &&
    codigo.includes('@PathVariable("codigo")'),
    'O parâmetro código da rota de venda precisa estar declarado corretamente.'
  );
});
test('AgroPecApplication possui a inicialização do Spring Boot', () => {
  const codigo = lerArquivo('AgroPecApplication.java');

  conferir(
    codigo,
    /@SpringBootApplication/,
    'Anotação de inicialização do Spring Boot não encontrada.'
  );

  conferir(
    codigo,
    /SpringApplication\.run\(/,
    'Chamada de inicialização da aplicação não encontrada.'
  );
});
