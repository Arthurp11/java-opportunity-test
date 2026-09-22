# Teste Prático de Programação — Java

Projeto desenvolvido como parte de um teste técnico, com o objetivo de manipular uma lista de funcionários usando Programação Orientada a Objetos e Collections/Streams do Java.

## Descrição

O sistema cadastra funcionários de uma indústria e executa uma série de operações sobre essa lista: remoção, formatação de dados, reajuste salarial, agrupamento, filtros, ordenação e cálculos.

## Tecnologias

- Java 21 (compatível com Java 11+)
- Maven

## Estrutura do projeto

```
src/main/java/
 ├── model/
 │    ├── Pessoa.java        # Classe base: nome e data de nascimento
 │    └── Funcionario.java   # Estende Pessoa: salário e função
 └── Principal.java          # Classe principal, executa todas as regras
```

## Funcionalidades implementadas

| # | Requisito |
|---|---|
| 3.1 | Inserção de todos os funcionários da tabela, na ordem original |
| 3.2 | Remoção do funcionário "João" da lista |
| 3.3 | Impressão de todos os dados, com data em `dd/MM/aaaa` e valores no formato brasileiro (`.` milhar, `,` decimal) |
| 3.4 | Reajuste de 10% no salário de todos os funcionários |
| 3.5 | Agrupamento dos funcionários por função em um `Map<String, List<Funcionario>>` |
| 3.6 | Impressão dos funcionários agrupados por função |
| 3.8 | Impressão dos aniversariantes dos meses 10 (outubro) e 12 (dezembro) |
| 3.9 | Impressão do funcionário com maior idade (nome e idade) |
| 3.10 | Impressão da lista em ordem alfabética |
| 3.11 | Impressão do total de salários da folha |
| 3.12 | Impressão de quantos salários mínimos cada funcionário recebe (salário mínimo: R$ 1.212,00) |

## Modelagem

- **Pessoa**: classe base com `nome` (String) e `dataNascimento` (LocalDate).
- **Funcionario**: estende `Pessoa`, adiciona `salario` (BigDecimal) e `funcao` (String). Também calcula a idade a partir da data de nascimento.

`BigDecimal` foi usado para os valores monetários para evitar erros de arredondamento comuns com `double`/`float`.

## Como executar

### Pela IDE (IntelliJ, Eclipse, etc.)
1. Importe o projeto como Maven.
2. Rode a classe `Principal.java` (botão direito → Run).

### Pela linha de comando
```bash
mvn compile
mvn exec:java -Dexec.mainClass="Principal"
```
ou, compilando manualmente:
```bash
javac -encoding UTF-8 -d target/classes src/main/java/model/*.java src/main/java/Principal.java
java -cp target/classes Principal
```

## Observações

- A saída do console é forçada para UTF-8 (`System.setOut(...)`), garantindo que acentos sejam exibidos corretamente independente do sistema operacional.
- Os cálculos de idade usam `Period.between(...)`, considerando a data atual do sistema.
