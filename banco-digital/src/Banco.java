import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Banco {
    private final List<Conta> contas = new ArrayList<>();

    public ContaCorrente abrirContaCorrente(Cliente titular, String agencia, String numero) {
        validarNovaConta(agencia, numero);
        ContaCorrente cc = new ContaCorrente(agencia, numero, titular);
        contas.add(cc);
        return cc;
    }

    public ContaPoupanca abrirContaPoupanca(Cliente titular, String agencia, String numero) {
        validarNovaConta(agencia, numero);
        ContaPoupanca cp = new ContaPoupanca(agencia, numero, titular);
        contas.add(cp);
        return cp;
    }

    public Conta buscarConta(String agencia, String numero) {
        Objects.requireNonNull(agencia, "agência obrigatória");
        Objects.requireNonNull(numero, "número obrigatório");
        return contas.stream()
                .filter(c -> c.getAgencia().equals(agencia) && c.getNumero().equals(numero))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada: " + agencia + "/" + numero));
    }

    public List<Conta> listarContas() {
        return List.copyOf(contas);
    }

    private void validarNovaConta(String agencia, String numero) {
        Objects.requireNonNull(agencia, "agência obrigatória");
        Objects.requireNonNull(numero, "número obrigatório");
        boolean existe = contas.stream()
                .anyMatch(c -> c.getAgencia().equals(agencia) && c.getNumero().equals(numero));
        if (existe) {
            throw new IllegalArgumentException("Já existe conta com agência/número informados");
        }
    }
}
