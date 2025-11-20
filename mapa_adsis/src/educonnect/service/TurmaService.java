package educonnect.service;

import educonnect.model.*;
import educonnect.repository.TurmaRepository;

import java.util.List;
import java.util.Optional;

public class TurmaService {
    private final TurmaRepository repo;

    public TurmaService(TurmaRepository repo) { this.repo = repo; }

    public Turma criarTurma(String codigo, Professor prof, Curso curso) {
        Turma t = new Turma(codigo, prof, curso);
        repo.save(t);
        return t;
    }

    public boolean adicionarAluno(String codTurma, Aluno a) {
        Turma t = repo.findByCodigo(codTurma).orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));
        return t.adicionarAluno(a);
    }

    public boolean removerAluno(String codTurma, String matricula) {
        Turma t = repo.findByCodigo(codTurma).orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));
        return t.removerAluno(matricula);
    }

    public void registrarAvaliacao(String codTurma, String matricula, String descricao, double nota) {
        Turma t = repo.findByCodigo(codTurma).orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));
        t.buscarAlunoPorMatricula(matricula).orElseThrow(() -> new IllegalArgumentException("Aluno não está na turma."));
        Avaliacao av = new Avaliacao(descricao);
        av.atribuirNota(nota); // valida 0..10
        t.registrarAvaliacao(matricula, av);
    }

    public Optional<Turma> buscar(String cod) { return repo.findByCodigo(cod); }
    public List<Turma> listar() { return repo.findAll(); }
}
