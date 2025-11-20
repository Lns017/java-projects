package educonnect.repository;

import educonnect.model.Curso;
import java.util.*;

public class CursoRepository {
    private final List<Curso> data = new ArrayList<>();
    public void save(Curso c) { data.add(c); }
    public List<Curso> findAll() { return Collections.unmodifiableList(data); }
    public Optional<Curso> findByCodigo(String cod) {
        return data.stream().filter(c -> c.getCodigo().equals(cod)).findFirst();
    }
}
