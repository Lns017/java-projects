package educonnect.repository;

import educonnect.model.Turma;
import java.util.*;

public class TurmaRepository {
    private final List<Turma> data = new ArrayList<>();
    public void save(Turma t) { data.add(t); }
    public List<Turma> findAll() { return Collections.unmodifiableList(data); }
    public Optional<Turma> findByCodigo(String cod) {
        return data.stream().filter(t -> t.getCodigo().equals(cod)).findFirst();
    }
}
