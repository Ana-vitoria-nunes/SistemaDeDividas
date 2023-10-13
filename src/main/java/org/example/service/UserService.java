package org.example.service;
import org.example.model.UserModel;
import org.example.model.Validacoes;

import java.sql.Statement;
import java.sql.SQLException;

import static org.example.connection.Connect.fazerConexao;

public class UserService {
    private Statement statement;
    private Validacoes validacoes=new Validacoes();

    public UserService() {
        try {
            statement = fazerConexao().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarUsuario(String nomeCompleto,String email,String senha, String cpf, String endereco,String telefone) {

        if (!validacoes.validarCamposObrigatoriosUser(nomeCompleto,email,senha,cpf,endereco,telefone)){
            System.out.println("Todos os campos do cliente devem ser preenchidos!");
            return;
        }
        if (!validacoes.validarEmail(email)) {
            System.out.println("O e-mail precisa conter @ e o (gmail.com)");
            return;
        }
        String sql = "INSERT INTO \"user\" (nomeCompleto, email, senha, cpf, endereco, telefone) " +
                "VALUES ('" + nomeCompleto + "', '" + email + "', '" + senha +
                "', '" + cpf + "', '" + endereco + "', '" + telefone + "')";
        try {
            statement.executeUpdate(sql);
            System.out.println("Usuário adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarUsuario(Long id, String email, String senha, String endereco, String telefone) {
        if (validacoes.validarEmail(email)) {
            System.out.println("O e-mail precisa conter @ e o (gmail.com)");
        }
        String sql = "UPDATE user SET email = '" + email + "', senha = '" + senha + "', endereco = '" + endereco + "', telefone = '" + telefone + "' WHERE id = " + id;
        try {
            statement.executeUpdate(sql);
            System.out.println("Usuário atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
