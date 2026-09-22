import model.Funcionario;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final Locale LOCALE_BR = new Locale.Builder().setLanguage("pt").setRegion("BR").build();

    public static void main(String[] args) {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        System.out.println("3.1 - Funcionários inseridos");
        System.out.println("Total inserido: " + funcionarios.size());

        funcionarios.removeIf(f -> f.getNome().equals("João"));

        System.out.println("\n3.2 - Após remover 'João'");
        System.out.println("Total restante: " + funcionarios.size());

        System.out.println("\n3.3 - Lista de funcionários");
        imprimirFuncionarios(funcionarios);

        funcionarios.forEach(f -> {
            BigDecimal novoSalario = f.getSalario()
                    .multiply(new BigDecimal("1.10"))
                    .setScale(2, RoundingMode.HALF_UP);
            f.setSalario(novoSalario);
        });

        System.out.println("\n3.4 - Funcionários após aumento de 10%");
        imprimirFuncionarios(funcionarios);

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\n3.6 - Funcionários agrupados por função");
        for (Map.Entry<String, List<Funcionario>> entrada : funcionariosPorFuncao.entrySet()) {
            System.out.println("\nFunção: " + entrada.getKey());
            for (Funcionario f : entrada.getValue()) {
                System.out.println("  - " + f.getNome());
            }
        }

        System.out.println("\n3.8 - Aniversariantes de outubro e dezembro");
        List<Funcionario> aniversariantes = funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10
                        || f.getDataNascimento().getMonthValue() == 12)
                .collect(Collectors.toList());

        if (aniversariantes.isEmpty()) {
            System.out.println("Nenhum funcionário faz aniversário nesses meses.");
        } else {
            for (Funcionario f : aniversariantes) {
                System.out.println(f.getNome() + " - " + f.getDataNascimento().format(FORMATO_DATA));
            }
        }

        System.out.println("\n3.9 - Funcionário com a maior idade");
        Funcionario maisVelho = funcionarios.stream()
                .max(Comparator.comparing(f -> Period.between(f.getDataNascimento(), LocalDate.now()).getYears()))
                .orElse(null);

        if (maisVelho != null) {
            int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("Nome: " + maisVelho.getNome());
            System.out.println("Idade: " + idade + " anos");
        }

        System.out.println("\n3.10 - Funcionários em ordem alfabética");
        List<Funcionario> ordenadosPorNome = funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());

        for (Funcionario f : ordenadosPorNome) {
            System.out.println(f.getNome());
        }

        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("\n3.11 - Total dos salários");
        System.out.println("Total: " + formatarValor(totalSalarios));

        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        System.out.println("\n3.12 - Quantidade de salários mínimos por funcionário");
        for (Funcionario f : funcionarios) {
            BigDecimal qtdSalariosMinimos = f.getSalario()
                    .divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " - " + formatarValor(qtdSalariosMinimos) + " salários mínimos");
        }
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario f : funcionarios) {
            System.out.println(
                    "Nome: " + f.getNome() +
                    " | Data Nascimento: " + f.getDataNascimento().format(FORMATO_DATA) +
                    " | Salário: " + formatarValor(f.getSalario()) +
                    " | Função: " + f.getFuncao()
            );
        }
    }

    private static String formatarValor(BigDecimal valor) {
        java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance(LOCALE_BR);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(valor);
    }
}