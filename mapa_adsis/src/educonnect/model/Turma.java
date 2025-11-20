package educonnect.model;

import java.util.*;

// Fase 2 + associação Avaliações/Alunos (Fase 3)
public class Turma {
    private String codigo;
    private Professor professor;
    private Curso curso;
    private List<Aluno> listaAlunos = new ArrayList<>();
    // Avaliações por aluno na turma
    private Map<String, List<Avaliacao>> avaliacoesPorMatricula = new HashMap<>();

    public Turma(String codigo, Professor professor, Curso curso) {
        this.codigo = codigo;
        this.professor = professor;
        this.curso = curso;
    }

    public boolean adicionarAluno(Aluno a) {
        if (a == null || listaAlunos.stream().anyMatch(x -> x.getMatricula().equals(a.getMatricula())))
            return false;
        listaAlunos.add(a);
        avaliacoesPorMatricula.putIfAbsent(a.getMatricula(), new ArrayList<>());
        return true;
    }

    public boolean removerAluno(String matricula) {
        boolean removed = listaAlunos.removeIf(a -> a.getMatricula().equals(matricula));
        if (removed) avaliacoesPorMatricula.remove(matricula);
        return removed;
    }

    public void registrarAvaliacao(String matricula, Avaliacao av) {
        List<Avaliacao> lista = avaliacoesPorMatricula.get(matricula);
        if (lista == null) throw new IllegalArgumentException("Aluno não encontrado na turma.");
        lista.add(av);
    }

    public String resumo() {
        String prof = (professor == null) ? "Sem professor" : professor.getNome();
        String cur = (curso == null) ? "Sem curso" : curso.getNome();
        return String.format("Turma %s | Professor: %s | Curso: %s | Alunos: %d",
                codigo, prof, cur, listaAlunos.size());
    }

    public Optional<Aluno> buscarAlunoPorMatricula(String m) {
        return listaAlunos.stream().filter(a -> a.getMatricula().equals(m)).findFirst();
    }

    public String getCodigo() { return codigo; }
    public Professor getProfessor() { return professor; }
    public Curso getCurso() { return curso; }
    public List<Aluno> getListaAlunos() { return Collections.unmodifiableList(listaAlunos); }
    public Map<String, List<Avaliacao>> getAvaliacoesPorMatricula() { return avaliacoesPorMatricula; }
}
