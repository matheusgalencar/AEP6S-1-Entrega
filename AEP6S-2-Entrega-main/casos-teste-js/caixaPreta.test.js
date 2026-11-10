
const { test } = require('node:test');
const assert = require('node:assert/strict');
const { randomUUID } = require('node:crypto');

const BASE_URL = (process.env.API_BASE_URL || 'http://localhost:8080').replace(/\/$/, '');
const API_DISPONIVEL = process.env.EXECUTAR_API_TESTS === '1';

async function requisicao(caminho, opcoes = {}) {
  const resposta = await fetch(`${BASE_URL}${caminho}`, {
    ...opcoes,
    headers: {
      ...(opcoes.body ? { 'Content-Type': 'application/json' } : {}),
      ...(opcoes.headers || {}),
    },
  });

  const texto = await resposta.text();

  let corpo = null;

  if (texto) {
    try {
      corpo = JSON.parse(texto);
    } catch {
      corpo = texto;
    }
  }

  return {
    status: resposta.status,
    corpo,
  };
}

function animalValido(brinco = `CT-${randomUUID()}`) {
  return {
    brinco,
    pesoEntrada: 450.5,
    raca: 'Nelore',
    pesagens: [],
    vacinacoes: [],
  };
}

async function cadastrarAnimal(dados = animalValido()) {
  const resultado = await requisicao('/animais/create', {
    method: 'POST',
    body: JSON.stringify(dados),
  });

  assert.equal(
    resultado.status,
    201,
    `Cadastro deveria retornar HTTP 201; retornou ${resultado.status}: ${JSON.stringify(resultado.corpo)}`
  );

  const id = resultado.corpo?.id;

  assert.ok(id, 'A resposta do cadastro deveria conter o identificador do animal.');

  return {
    id,
    brinco: dados.brinco,
    resposta: resultado,
  };
}
async function excluirAnimal(id) {
  if (!id) return;

  await requisicao(`/animais/delete/${encodeURIComponent(id)}`, {
    method: 'DELETE',
  });
}
test(
  'CT-PP-001 - Listar animais: deve retornar HTTP 200 e uma lista',
  { skip: !API_DISPONIVEL },
  async () => {
    const r = await requisicao('/animais/list');

    assert.equal(r.status, 200);
    assert.ok(Array.isArray(r.corpo), 'O retorno deve ser uma lista.');
  }
);

test(
  'CT-PP-002 - Cadastrar animal válido: deve retornar HTTP 201',
  { skip: !API_DISPONIVEL },
  async () => {
    let animal;

    try {
      animal = await cadastrarAnimal();

      assert.equal(animal.resposta.corpo.brinco, animal.brinco);
      assert.equal(animal.resposta.corpo.raca, 'Nelore');
    } finally {
      if (animal) {
        await excluirAnimal(animal.id);
      }
    }
  }
);
test(
  'CT-PP-003 - Cadastrar animal sem brinco: deve rejeitar os dados (HTTP 400)',
  { skip: !API_DISPONIVEL },
  async () => {
    const dados = animalValido();

    delete dados.brinco;

    const r = await requisicao('/animais/create', {
      method: 'POST',
      body: JSON.stringify(dados),
    });

    assert.equal(
      r.status,
      400,
      `Esperado HTTP 400; recebido ${r.status}: ${JSON.stringify(r.corpo)}`
    );
  }
);
test(
  'CT-PP-004 - Buscar animal por ID: deve retornar o animal cadastrado',
  { skip: !API_DISPONIVEL },
  async () => {
    let animal;

    try {
      animal = await cadastrarAnimal();

      const r = await requisicao(
        `/animais/list/${encodeURIComponent(animal.id)}`
      );

      assert.equal(
        r.status,
        200,
        `A busca por ID falhou: ${JSON.stringify(r.corpo)}`
      );

      assert.equal(r.corpo.id, animal.id);
    } finally {
      if (animal) {
        await excluirAnimal(animal.id);
      }
    }
  }
);
test(
  'CT-PP-005 - Atualizar animal: deve persistir os novos dados',
  { skip: !API_DISPONIVEL },
  async () => {
    let animal;

    try {
      animal = await cadastrarAnimal();

      const atualizado = {
        ...animalValido(animal.brinco),
        raca: 'Angus',
        pesoEntrada: 480,
      };

      const r = await requisicao(
        `/animais/update/${encodeURIComponent(animal.id)}`,
        {
          method: 'PUT',
          body: JSON.stringify(atualizado),
        }
      );

      assert.equal(
        r.status,
        200,
        `Atualização falhou: ${JSON.stringify(r.corpo)}`
      );

      assert.equal(r.corpo.raca, 'Angus');
      assert.equal(r.corpo.pesoEntrada, 480);
    } finally {
      if (animal) {
        await excluirAnimal(animal.id);
      }
    }
  }
);
test(
  'CT-PP-006 - Excluir animal: deve retornar HTTP 204',
  { skip: !API_DISPONIVEL },
  async () => {
    const animal = await cadastrarAnimal();

    const r = await requisicao(
      `/animais/delete/${encodeURIComponent(animal.id)}`,
      {
        method: 'DELETE',
      }
    );

    assert.equal(r.status, 204);
  }
);
test(
  'CT-PP-007 - Registrar pesagem: deve adicionar uma pesagem ao animal',
  { skip: !API_DISPONIVEL },
  async () => {
    let animal;

    try {
      animal = await cadastrarAnimal();

      const r = await requisicao('/animais/pesarAnimal', {
        method: 'POST',
        body: JSON.stringify({
          novoPeso: 470,
          brincoAnimal: animal.brinco,
        }),
      });

      assert.equal(
        r.status,
        201,
        `Pesagem falhou: ${JSON.stringify(r.corpo)}`
      );

      assert.ok(
        Array.isArray(r.corpo.pesagens),
        'O animal deveria possuir uma lista de pesagens.'
      );

      assert.equal(r.corpo.pesagens.at(-1).peso, 470);
    } finally {
      if (animal) {
        await excluirAnimal(animal.id);
      }
    }
  }
);
test(
  'CT-PP-008 - Listar lotes: deve retornar HTTP 200 e uma lista',
  { skip: !API_DISPONIVEL },
  async () => {
    const r = await requisicao('/api/lotes');

    assert.equal(
      r.status,
      200,
      `Listagem de lotes falhou: ${JSON.stringify(r.corpo)}`
    );

    assert.ok(Array.isArray(r.corpo));
  }
);
test(
  'CT-PP-009 - Listar vendas: deve retornar HTTP 200 e uma lista',
  { skip: !API_DISPONIVEL },
  async () => {
    const r = await requisicao('/api/vendas');

    assert.equal(
      r.status,
      200,
      `Listagem de vendas falhou: ${JSON.stringify(r.corpo)}`
    );

    assert.ok(Array.isArray(r.corpo));
  }
);
