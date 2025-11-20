package educonnect.service;

import educonnect.model.Autenticavel;

public class AutenticacaoService {
    public boolean login(Autenticavel usuario, String login, String senha) {
        return usuario != null && usuario.autenticar(login, senha);
    }
}
