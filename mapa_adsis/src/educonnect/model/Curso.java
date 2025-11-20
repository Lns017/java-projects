package educonnect.model;

// Fase 1 + Fase 4 (base para herança e polimorfismo)
public class Curso implements Relatoriavel {
    private String nome;
    private String codigo;
    private int cargaHoraria;

    public Curso(String nome, String codigo, int cargaHoraria) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
    }

    public String detalharCurso() {
        return String.format("Curso: %s (%s), CH: %dh", nome, codigo, cargaHoraria);
    }

    @Override
    public String gerarRelatorio() {
        return detalharCurso();
    }

    public String getNome() { return nome; }
    public String getCodigo() { return codigo; }
    public int getCargaHoraria() { return cargaHoraria; }
}
