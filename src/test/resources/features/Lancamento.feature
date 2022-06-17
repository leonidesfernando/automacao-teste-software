Feature: Validar o CRUD de lancamentos de gastos e receitas
  Cenarios a validar:
  1. criar um novo lancamento de saida e de entrada
  2. buscar o lancamento
  4. editar o lancamento
  5. remover o lancamento

  Scenario: Criando um lancamentos de entrada e saida
    Given Registrar lancamento com dados fornecidos
      | descricao     | valor       | dataLancamento    | tipoLancamento | categoria   |
      | Almoco        | @MoneyValue | @AnyDate          | SAIDA          | ALIMENTACAO |
      | Mercado       | 123,22      | @DateCurrentMonth | SAIDA          | ALIMENTACAO |
      | Mercado       | 256,00      | @DateCurrentMonth | SAIDA          | LAZER       |
      | Cinema        | R$ 56,00    | @AnyDate          | SAIDA          | LAZER       |
      | Salario       | @MoneyValue | @DateCurrentMonth | ENTRADA        | SALARIO     |
      | Bonus Salario | 2342,02     | @AnyDate          | ENTRADA        | SALARIO     |


  Scenario: Buscando um lancamento existente por categoria
    Given Buscar um lancamento por categoria "ENTRADA"
    And Buscar um lancamento por categoria "SAIDA"

  Scenario: Editar primeiro lancamento encontrado
    Given Buscar um lancamento por categoria "ENTRADA"
    And Editar o primeiro lancamento encontrado

  Scenario: Remover o primeiro elemento encotnrado
    Given Buscar um lancamento por categoria "SAIDA"
    And Remover o primeiro lancamento encontrado