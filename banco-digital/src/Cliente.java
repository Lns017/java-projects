import java.util.Objects;
import java.util.UUID;

public class Cliente {
    private final String id;
    private String nome;
    private String documento;

    public Cliente(String nome, String documento) {
        this.id = UUID.randomUUID().toString();
        this.nome = Objects.requireNonNull(nome, "nome obrigatório");
        this.documento = Objects.requireNonNull(documento, "documento obrigatório");
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDocumento() { return documento; }

    public void setNome(String nome) { this.nome = Objects.requireNonNull(nome); }
    public void setDocumento(String documento) { this.documento = Objects.requireNonNull(documento); }

    @Override
    public String toString() {
        return "Cliente{id='%s', nome='%s', documento='%s'}".formatted(id, nome, documento);
    }
}
