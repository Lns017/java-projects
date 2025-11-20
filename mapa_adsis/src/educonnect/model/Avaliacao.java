package educonnect.model;

// Fase 3 (encapsulamento)
public class Avaliacao {
    private double nota;       // 0..10
    private String descricao;  // ex.: "Prova 1"

    public Avaliacao(String descricao) {
        this.descricao = descricao;
        this.nota = 0.0;
    }

    public void atribuirNota(double valor) {
        if (valor < 0 || valor > 10) {
            throw new IllegalArgumentException("Nota inválida: deve estar entre 0 e 10.");
        }
        this.nota = valor;
    }

    public double getNota() { return nota; }
    public String getDescricao() { return descricao; }
}
