package org.example.service;

import org.example.model.PagamentoModel;
import org.example.model.Validacoes;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import static org.example.connection.Connect.fazerConexao;

public class PagamentoService {
    private Statement statement;
    private Validacoes validacoes=new Validacoes();

    public PagamentoService() {
        try {
            statement = fazerConexao().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarPagamento(Long id_cartao, int parcelas, BigDecimal valorTotalParcela,BigDecimal valorTotalEmprestimo,String status) {
        if (!validacoes.validarCamposObrigatoriosPagamento(id_cartao,parcelas,valorTotalParcela,valorTotalEmprestimo,status)){
            System.out.println("Todos os campos de pagamento devem ser preenchidos!");
            return;
        }
        if (!validacoes.validarCartao(id_cartao)){
            System.out.println("Id do cartão inválido!");
            return;
        }
        String sql = "INSERT INTO pagamento (idcartao, parcelas, valorTotalParcela, valorTotalEmprestimo, status) " +
                "VALUES (" + id_cartao + ", " + parcelas + ", " +
                valorTotalParcela + ", " + valorTotalEmprestimo + ", '" +
                status + "')";
        try {
            statement.executeUpdate(sql);
            System.out.println("Pagamento adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
