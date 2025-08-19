import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Conta implements IConta {
    private final String agencia;
    private final String numero;
    private final Cliente titular;

    protected BigDecimal saldo = money(0);
    private final List<String> historico = new ArrayList<>();

    protected Conta(String agencia, String numero, Cliente titular) {
        this.agencia = Objects.requireNonNull(agencia, "agência obrigatória");
        this.numero = Objects.requireNonNull(numero, "número obrigatório");
        this.titular = Objects.requireNonNull(titular, "titular obrigatório");
        registrar("ABERTURA", money(0), "Conta criada");
    }

    // ----------------- Regras públicas (IConta) -----------------

    @Override
    public void depositar(BigDecimal valor) {
        validarValorPositivo(valor);
        saldo = saldo.add(money(valor));
        registrar("DEPÓSITO", money(valor), "Depósito em conta");
    }

    @Override
    public void sacar(BigDecimal valor) {
        validarValorPositivo(valor);
        BigDecimal v = money(valor);
        if (saldo.compareTo(v) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para saque");
        }
        saldo = saldo.subtract(v);
        registrar("SAQUE", v.negate(), "Saque em conta");
    }

    @Override
    public void transferir(BigDecimal valor, Conta contaDestino) {
        Objects.requireNonNull(contaDestino, "conta de destino obrigatória");
        validarValorPositivo(valor);
        BigDecimal v = money(valor);

        if (saldo.compareTo(v) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para transferência");
        }
        saldo = saldo.subtract(v);
        registrar("TRANSF. SAÍDA", v.negate(), "Para " + contaDestino.resumo());

        BigDecimal tarifa = tarifaTransferencia(v);
        if (tarifa.signum() > 0) {
            if (saldo.compareTo(tarifa) < 0) {
                saldo = saldo.add(v);
                registrar("ESTORNO", v, "Estorno por tarifa insuficiente");
                throw new IllegalArgumentException("Saldo insuficiente para tarifa de transferência");
            }
            saldo = saldo.subtract(tarifa);
            registrar("TARIFA", tarifa.negate(), "Tarifa de transferência");
        }

        contaDestino.creditoInterno(v, "TRANSF. ENTRADA", "De " + this.resumo());
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("=== Extrato " + resumo() + " ===");
        for (String linha : historico) {
            System.out.println(linha);
        }
        System.out.println("SALDO ATUAL: " + saldo);
        System.out.println();
    }

    // ----------------- Hooks e utilidades -----------------

    protected BigDecimal tarifaTransferencia(BigDecimal valor) {
        return money(0);
    }

    protected void creditoInterno(BigDecimal valor, String tipo, String detalhe) {
        saldo = saldo.add(money(valor));
        registrar(tipo, money(valor), detalhe);
    }

    protected static BigDecimal money(double v) {
        return BigDecimal.valueOf(v).setScale(2, RoundingMode.HALF_EVEN);
    }

    protected static BigDecimal money(BigDecimal v) {
        return v.setScale(2, RoundingMode.HALF_EVEN);
    }

    protected void validarValorPositivo(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
    }

    protected void registrar(String tipo, BigDecimal valor, String detalhe) {
        String linha = "[%s] %-16s %-12s Saldo=%s %s"
                .formatted(LocalDateTime.now(), tipo, valor, saldo, detalhe == null ? "" : "(" + detalhe + ")");
        historico.add(linha);
    }

    public String getAgencia() { return agencia; }
    public String getNumero() { return numero; }
    public Cliente getTitular() { return titular; }
    public BigDecimal getSaldo() { return saldo; }

    public String resumo() {
        return "%s/%s - %s".formatted(agencia, numero, titular.getNome());
    }

    @Override
    public String toString() {
        return "Conta{agencia='%s', numero='%s', titular=%s, saldo=%s}"
                .formatted(agencia, numero, titular.getNome(), saldo);
    }
}
