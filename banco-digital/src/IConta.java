import java.math.BigDecimal;

public interface IConta {
    void depositar(BigDecimal valor);
    void sacar(BigDecimal valor);
    void transferir(BigDecimal valor, Conta contaDestino);
    void imprimirExtrato();
}
