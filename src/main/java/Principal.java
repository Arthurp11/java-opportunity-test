import model.Funcionario;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Locale LOCALE_BR = new Locale("pt", "BR");
    private static final BigDecimal PERCENTUAL_AUMENTO = new BigDecimal("1.10");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private static final int MES_OUTUBRO = 10;
    private static final int MES_DEZEMBRO = 12;

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        List<Funcionario> funcionarios = criarFuncionarios();
        System.out.println("Funcionários cadastrados");
        imprimirFuncionarios(funcionarios);

        removerFuncionarioPorNome(funcionarios, "João");

        System.out.println("\nFuncionários cadastrados após remover João:");
        imprimirFuncionarios(funcionarios);

        aplicarAumento(funcionarios, PERCENTUAL_AUMENTO);
        System.out.println("\nApós aumento de 10%:");
        imprimirFuncionarios(funcionarios);

        Map<String, List<Funcionario>> porFuncao = agruparPorFuncao(funcionarios);
        System.out.println("\nAgrupados por função:");
        imprimirAgrupadoPorFuncao(porFuncao);

        System.out.println("\nAniversariantes de outubro e dezembro:");
        imprimirAniversariantes(funcionarios, MES_OUTUBRO, MES_DEZEMBRO);

        System.out.println("\nFuncionário mais velho:");
        imprimirMaisVelho(funcionarios);

        System.out.println("\nOrdem alfabética:");
        imprimirOrdemAlfabetica(funcionarios);

        System.out.println("\nTotal da folha: " + formatarValor(somarSalarios(funcionarios)));

        System.out.println("\nSalários mínimos por funcionário:");
        imprimirSalariosMinimos(funcionarios, SALARIO_MINIMO);
    }

    private static List<Funcionario> criarFuncionarios() {
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
        return funcionarios;
    }

    private static void removerFuncionarioPorNome(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(f -> f.getNome().equals(nome));
    }

    private static void aplicarAumento(List<Funcionario> funcionarios, BigDecimal percentual) {
        for (Funcionario f : funcionarios) {
            BigDecimal novoSalario = f.getSalario().multiply(percentual).setScale(2, RoundingMode.HALF_UP);
            f.setSalario(novoSalario);
        }
    }

    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    private static void imprimirAgrupadoPorFuncao(Map<String, List<Funcionario>> porFuncao) {
        for (Map.Entry<String, List<Funcionario>> entrada : porFuncao.entrySet()) {
            System.out.println("\n" + entrada.getKey() + ":");
            for (Funcionario f : entrada.getValue()) {
                System.out.println("  - " + f.getNome());
            }
        }
    }

    private static void imprimirAniversariantes(List<Funcionario> funcionarios, int... meses) {
        List<Integer> mesesFiltro = List.of(meses[0], meses[1]);
        boolean encontrou = false;
        for (Funcionario f : funcionarios) {
            if (mesesFiltro.contains(f.getDataNascimento().getMonthValue())) {
                System.out.println(f.getNome() + " - " + f.getDataNascimento().format(FORMATO_DATA));
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum funcionário faz aniversário nesses meses.");
        }
    }

    private static void imprimirMaisVelho(List<Funcionario> funcionarios) {
        funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .ifPresent(f -> System.out.println(f.getNome() + " - " + f.getIdade() + " anos"));
    }

    private static void imprimirOrdemAlfabetica(List<Funcionario> funcionarios) {
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(f.getNome()));
    }

    private static BigDecimal somarSalarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios, BigDecimal salarioMinimo) {
        for (Funcionario f : funcionarios) {
            BigDecimal qtd = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " - " + formatarValor(qtd) + " salários mínimos");
        }
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario f : funcionarios) {
            System.out.println(f.getNome() + " | " + f.getDataNascimento().format(FORMATO_DATA)
                    + " | " + formatarValor(f.getSalario()) + " | " + f.getFuncao());
        }
    }

    private static String formatarValor(BigDecimal valor) {
        NumberFormat nf = NumberFormat.getNumberInstance(LOCALE_BR);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(valor);
    }
}