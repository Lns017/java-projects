package educonnect.model;

// Classe base para perfis do sistema (Fase 5)
public abstract class Usuario implements Autenticavel {
    private String nome;
    private String login;
    private String senha;

    protected Usuario(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    @Override
    public boolean autenticar(String login, String senha) {
        return this.login != null && this.senha != null &&
                this.login.equals(login) && this.senha.equals(senha);
    }

    public String getNome() { return nome; }
    public String getLogin() { return login; }
    public void setSenha(String senha) { this.senha = senha; }
}
