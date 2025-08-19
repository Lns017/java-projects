import java.math.BigDecimal;

public class ContaCorrente extends Conta {
    private static final BigDecimal TARIFA_FIXA = money(1.50);

    public ContaCorrente(String agencia, String numero, Cliente titular) {
        super(agencia, numero, titular);
    }

    @Override
    protected BigDecimal tarifaTransferencia(BigDecimal valor) {
        return TARIFA_FIXA;
    }
}
