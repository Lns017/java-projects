package educonnect.repository;

import educonnect.model.Aluno;
import java.util.*;

public class AlunoRepository {
    private final List<Aluno> data = new ArrayList<>();

    public void save(Aluno a) { data.add(a); }
    public List<Aluno> findAll() { return Collections.unmodifiableList(data); }
    public Optional<Aluno> findByMatricula(String m) {
        return data.stream().filter(a -> a.getMatricula().equals(m)).findFirst();
    }
}
