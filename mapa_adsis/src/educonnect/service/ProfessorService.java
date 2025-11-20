package educonnect.service;

import educonnect.model.Professor;
import educonnect.repository.ProfessorRepository;

import java.util.List;
import java.util.Optional;

public class ProfessorService {
    private final ProfessorRepository repo;

    public ProfessorService(ProfessorRepository repo) { this.repo = repo; }

    public Professor cadastrar(String nome, String login, String senha, String esp, String registro) {
        Professor p = new Professor(nome, login, senha, esp, registro);
        repo.save(p);
        return p;
    }

    public Optional<Professor> buscarPorRegistro(String r) { return repo.findByRegistro(r); }
    public List<Professor> listar() { return repo.findAll(); }
}
