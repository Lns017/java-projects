package educonnect.repository;

import educonnect.model.Professor;
import java.util.*;

public class ProfessorRepository {
    private final List<Professor> data = new ArrayList<>();
    public void save(Professor p) { data.add(p); }
    public List<Professor> findAll() { return Collections.unmodifiableList(data); }
    public Optional<Professor> findByRegistro(String r) {
        return data.stream().filter(p -> p.getRegistro().equals(r)).findFirst();
    }
}
