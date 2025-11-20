package educonnect.model;

// Fase 1 + Fase 5 + Fase 6
public class Professor extends Usuario implements Relatoriavel {
    private String especialidade;
    private String registro;

    public Professor(String nome, String login, String senha, String especialidade, String registro) {
        super(nome, login, senha);
        this.especialidade = especialidade;
        this.registro = registro;
    }

    public String getEspecialidade() { return especialidade; }
    public String getRegistro() { return registro; }

    @Override
    public String gerarRelatorio() {
        return String.format("Professor: %s | Esp.: %s | Reg.: %s",
                getNome(), especialidade, registro);
    }
}
