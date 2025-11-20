package educonnect.service;

import educonnect.model.*;
import educonnect.repository.CursoRepository;

import java.util.List;
import java.util.Optional;

public class CursoService {
    private final CursoRepository repo;

    public CursoService(CursoRepository repo) { this.repo = repo; }

    public Curso cadastrarPresencial(String nome, String codigo, int ch, String sala) {
        Curso c = new CursoPresencial(nome, codigo, ch, sala);
        repo.save(c);
        return c;
    }

    public Curso cadastrarEAD(String nome, String codigo, int ch, String plataforma) {
        Curso c = new CursoEAD(nome, codigo, ch, plataforma);
        repo.save(c);
        return c;
    }

    public Optional<Curso> buscarPorCodigo(String cod) { return repo.findByCodigo(cod); }
    public List<Curso> listar() { return repo.findAll(); }
}
