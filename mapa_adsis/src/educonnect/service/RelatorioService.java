package educonnect.service;

import educonnect.model.*;

import java.util.List;
import java.util.Map;

public class RelatorioService {

    public String relatorioAlunos(List<Aluno> alunos) {
        StringBuilder sb = new StringBuilder("=== Relatório de Alunos ===\n");
        alunos.forEach(a -> sb.append(a.gerarRelatorio()).append("\n"));
        return sb.toString();
    }

    public String relatorioProfessores(List<Professor> profs) {
        StringBuilder sb = new StringBuilder("=== Relatório de Professores ===\n");
        profs.forEach(p -> sb.append(p.gerarRelatorio()).append("\n"));
        return sb.toString();
    }

    public String relatorioCursos(List<Curso> cursos) {
        StringBuilder sb = new StringBuilder("=== Relatório de Cursos ===\n");
        cursos.forEach(c -> sb.append(c.gerarRelatorio()).append("\n"));
        return sb.toString();
    }

    public String relatorioTurmasDetalhado(List<Turma> turmas) {
        StringBuilder sb = new StringBuilder("=== Relatório de Turmas ===\n");
        for (Turma t : turmas) {
            sb.append(t.resumo()).append("\n");
            for (Aluno a : t.getListaAlunos()) {
                sb.append(" - ").append(a.getMatricula()).append(" | ").append(a.getNome()).append("\n");
                Map<String, List<Avaliacao>> map = t.getAvaliacoesPorMatricula();
                List<Avaliacao> avs = map.getOrDefault(a.getMatricula(), List.of());
                for (Avaliacao av : avs) {
                    sb.append("     * ").append(av.getDescricao())
                            .append(": ").append(av.getNota()).append("\n");
                }
            }
        }
        return sb.toString();
    }
}
