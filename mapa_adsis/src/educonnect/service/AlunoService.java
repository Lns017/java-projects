package educonnect.service;

import educonnect.model.Aluno;
import educonnect.model.Curso;
import educonnect.repository.AlunoRepository;

import java.util.List;
import java.util.Optional;

public class AlunoService {
    private final AlunoRepository repo;

    public AlunoService(AlunoRepository repo) { this.repo = repo; }

    public Aluno cadastrar(String nome, String login, String senha, String matricula, Curso curso) {
        Aluno a = new Aluno(nome, login, senha, matricula, curso);
        repo.save(a);
        return a;
    }

    public Optional<Aluno> buscarPorMatricula(String m) { return repo.findByMatricula(m); }
    public List<Aluno> listar() { return repo.findAll(); }
}
