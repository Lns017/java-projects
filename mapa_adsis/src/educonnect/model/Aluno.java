package educonnect.model;

// Fase 1 + Fase 5 + Fase 6
public class Aluno extends Usuario implements Relatoriavel {
    private String matricula;
    private Curso curso; // referência ao curso principal do aluno (opcional)

    public Aluno(String nome, String login, String senha, String matricula, Curso curso) {
        super(nome, login, senha);
        this.matricula = matricula;
        this.curso = curso;
    }

    public String getMatricula() { return matricula; }
    public Curso getCurso() { return curso; }

    @Override
    public String gerarRelatorio() {
        String cursoStr = (curso == null) ? "Sem curso" :
                curso.getNome() + " (" + curso.getCodigo() + ")";
        return String.format("Aluno: %s | Matrícula: %s | Curso principal: %s",
                getNome(), matricula, cursoStr);
    }
}
