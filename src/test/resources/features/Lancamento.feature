Feature: Validar o CRUD de lancamentos de gastos e receitas
  Cenarios a validar:
  1. logar no sistema
  2. criar um novo lancamento de DESPESA e de RENDA
  3. buscar o lancamento
  4. editar o lancamento
  5. remover o lancamento

  Scenario: Logar no sistema com usuario pre-definido via configuracoes
    Given Usuario e senha existente nas configuracoes
    Then Deve logar e acessar a home

  Scenario: Criando um lancamentos de RENDA e DESPESA
    Given Registrar lancamento com dados fornecidos
      | descricao     | valor       | dataLancamento    | tipoLancamento | categoria     |
      | Almoco        | @MoneyValue | @AnyDate          | DESPESA        | ALIMENTACAO   |
      | Mercado       | 123,22      | @DateCurrentMonth | DESPESA        | ALIMENTACAO   |
      | Mercado       | 256,00      | @DateCurrentMonth | DESPESA        | LAZER         |
      | Cinema        | R$ 56,00    | @AnyDate          | DESPESA        | LAZER         |
      | Salario       | @MoneyValue | @DateCurrentMonth | RENDA          | SALARIO       |
      | Bonus Salario | 2342,02     | @AnyDate          | RENDA          | SALARIO       |
      | Bonus Salario | 2342,02     | @AnyDate          | TRANSF         | INVESTIMENTOS |


  Scenario: Buscando um lancamento existente por categoria
    Given Buscar um lancamento por categoria "RENDA"
    And Buscar um lancamento por categoria "DESPESA"
    And Buscar um lancamento por categoria "TRANSF"

  Scenario: Editar primeiro lancamento encontrado
    Given Buscar um lancamento por categoria "RENDA"
    And Editar o primeiro lancamento encontrado

  Scenario: Remover o primeiro elemento encotnrado
    Given Registrar lancamento com dados fornecidos
      | descricao | valor       | dataLancamento | tipoLancamento | categoria |
      | Roupa     | @MoneyValue | @AnyDate       | DESPESA        | VESTUARIO |
    Given Buscar um lancamento por categoria "DESPESA"
    And Remover o primeiro lancamento encontrado