import java.math.BigDecimal;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(String agencia, String numero, Cliente titular) {
        super(agencia, numero, titular);
    }

    public void aplicarRendimento(BigDecimal taxa) {
        if (taxa == null || taxa.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa inválida");
        }
        BigDecimal rendimento = getSaldo().multiply(taxa).setScale(2, java.math.RoundingMode.HALF_EVEN);
        if (rendimento.signum() > 0) {
            creditoInterno(rendimento, "RENDIMENTO", "Taxa " + taxa);
        }
    }

    @Override
    protected BigDecimal tarifaTransferencia(BigDecimal valor) {
        return money(0);
    }
}
