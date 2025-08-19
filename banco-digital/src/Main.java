import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, Cliente> CLIENTES = new HashMap<>();
    private static final Banco BANCO = new Banco();
    private static final Scanner IN = new Scanner(System.in);

    public static void main(String[] args) {
        boolean executando = true;
        while (executando) {
            try {
                mostrarMenu();
                int opcao = lerInt("Escolha uma opção: ");
                System.out.println();
                switch (opcao) {
                    case 1 -> cadastrarCliente();
                    case 2 -> abrirContaCorrente();
                    case 3 -> abrirContaPoupanca();
                    case 4 -> depositar();
                    case 5 -> sacar();
                    case 6 -> transferir();
                    case 7 -> aplicarRendimentoPoupanca();
                    case 8 -> imprimirExtrato();
                    case 9 -> listarContas();
                    case 0 -> {
                        executando = false;
                        System.out.println("Encerrando. Até mais!");
                    }
                    default -> System.out.println("Opção inválida.");
                }
                System.out.println();
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                System.out.println();
                limparEntrada();
            }
        }
        IN.close();
    }

    private static void cadastrarCliente() {
        System.out.println("=== Cadastrar Cliente ===");
        String nome = lerTexto("Nome: ");
        String documento = lerTexto("Documento (único): ");
        if (CLIENTES.containsKey(documento)) {
            throw new IllegalArgumentException("Já existe cliente com esse documento.");
        }
        Cliente c = new Cliente(nome, documento);
        CLIENTES.put(documento, c);
        System.out.println("Cliente cadastrado: " + c);
    }

    private static void abrirContaCorrente() {
        System.out.println("=== Abrir Conta Corrente ===");
        Cliente titular = obterClienteExistente();
        String agencia = lerTexto("Agência: ");
        String numero = lerTexto("Número: ");
        ContaCorrente cc = BANCO.abrirContaCorrente(titular, agencia, numero);
        System.out.println("Conta Corrente aberta: " + cc.resumo());
    }

    private static void abrirContaPoupanca() {
        System.out.println("=== Abrir Conta Poupança ===");
        Cliente titular = obterClienteExistente();
        String agencia = lerTexto("Agência: ");
        String numero = lerTexto("Número: ");
        ContaPoupanca cp = BANCO.abrirContaPoupanca(titular, agencia, numero);
        System.out.println("Conta Poupança aberta: " + cp.resumo());
    }

    private static void depositar() {
        System.out.println("=== Depósito ===");
        Conta conta = obterContaPorAgenciaENumero();
        BigDecimal valor = lerBigDecimal("Valor do depósito: ");
        conta.depositar(valor);
        System.out.println("Depósito realizado. Saldo: " + conta.getSaldo());
    }

    private static void sacar() {
        System.out.println("=== Saque ===");
        Conta conta = obterContaPorAgenciaENumero();
        BigDecimal valor = lerBigDecimal("Valor do saque: ");
        conta.sacar(valor);
        System.out.println("Saque realizado. Saldo: " + conta.getSaldo());
    }

    private static void transferir() {
        System.out.println("=== Transferência ===");
        System.out.println("Conta ORIGEM:");
        Conta origem = obterContaPorAgenciaENumero();
        System.out.println("Conta DESTINO:");
        Conta destino = obterContaPorAgenciaENumero();
        BigDecimal valor = lerBigDecimal("Valor da transferência: ");
        origem.transferir(valor, destino);
        System.out.println("Transferência concluída.");
        System.out.println("Saldo ORIGEM:  " + origem.getSaldo());
        System.out.println("Saldo DESTINO: " + destino.getSaldo());
    }

    private static void aplicarRendimentoPoupanca() {
        System.out.println("=== Aplicar Rendimento (Poupança) ===");
        Conta conta = obterContaPorAgenciaENumero();
        if (!(conta instanceof ContaPoupanca cp)) {
            throw new IllegalArgumentException("A conta informada não é poupança.");
        }
        BigDecimal taxa = lerBigDecimal("Taxa (ex.: 0.005 para 0,5%): ");
        cp.aplicarRendimento(taxa);
        System.out.println("Rendimento aplicado. Saldo: " + cp.getSaldo());
    }

    private static void imprimirExtrato() {
        System.out.println("=== Extrato ===");
        Conta conta = obterContaPorAgenciaENumero();
        conta.imprimirExtrato();
    }

    private static void listarContas() {
        System.out.println("=== Contas Cadastradas ===");
        if (BANCO.listarContas().isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }
        BANCO.listarContas().forEach(System.out::println);
    }

    private static void mostrarMenu() {
        System.out.println("""
                ===== Banco Digital (didático) =====
                1) Cadastrar cliente
                2) Abrir conta corrente
                3) Abrir conta poupança
                4) Depósito
                5) Saque
                6) Transferência
                7) Aplicar rendimento (poupança)
                8) Imprimir extrato
                9) Listar contas
                0) Sair
                """);
    }

    private static Cliente obterClienteExistente() {
        String documento = lerTexto("Documento do titular: ");
        Cliente c = CLIENTES.get(documento);
        if (c == null) {
            throw new IllegalArgumentException("Cliente não encontrado. Cadastre-o antes.");
        }
        return c;
    }

    private static Conta obterContaPorAgenciaENumero() {
        String agencia = lerTexto("Agência: ");
        String numero = lerTexto("Número: ");
        return BANCO.buscarConta(agencia, numero);
    }

    private static String lerTexto(String prompt) {
        System.out.print(prompt);
        String s = IN.nextLine();
        while (s == null || s.trim().isEmpty()) {
            System.out.print("Informe um valor válido. " + prompt);
            s = IN.nextLine();
        }
        return s.trim();
    }

    private static int lerInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = IN.nextLine();
            try {
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.println("Número inválido. Tente novamente.");
            }
        }
    }

    private static BigDecimal lerBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = IN.nextLine().trim().replace(",", "."); // aceita vírgula
            try {
                return new BigDecimal(s);
            } catch (NumberFormatException e) {
                System.out.println("Valor monetário inválido. Tente novamente (ex.: 100.00).");
            }
        }
    }

    private static void limparEntrada() {
        if (IN.hasNextLine()) IN.nextLine();
    }
}
