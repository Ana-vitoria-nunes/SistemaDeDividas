package org.example.service;

import org.example.model.PagamentoModel;
import org.example.model.Validacoes;

import java.math.BigDecimal;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

import static org.example.connection.Connect.fazerConexao;

public class CartaoService {
    private Statement statement;
    private final Validacoes validacoes=new Validacoes();
    public CartaoService() {
        try {
            statement = Objects.requireNonNull(fazerConexao()).createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void adicionarCartao(Long idCliente, String nomeRemetente, String numeroCartao, String cvvCartao, Date dataDevalidade, BigDecimal limiteCartao) {

        if (!validacoes.validarCamposObrigatorios(nomeRemetente,numeroCartao,cvvCartao,dataDevalidade,limiteCartao)){
            System.out.println("Todos os campos do cartão deve estar preenchidoa!");
            return;
        }

        if (!validacoes.validarUser(idCliente)){
            System.out.println("Esse cliente não existe no banco!");
            return;
        }

        if (validacoes.validarNumeroCartao(numeroCartao)) {
            System.out.println("Número de cartão inválido!");
            return;
        }

        if (validacoes.validarDataValidade(dataDevalidade)){
            System.out.println("Data do cartão é inválida!");
            return;

        }

        String sql = "INSERT INTO cartao (idcliente, nomeRemetente, NumeroCartao, cvv, dataValidade, limiteCartao) " +
                "VALUES (" + idCliente+ ", '" + nomeRemetente + "', '" + numeroCartao + "', '" + cvvCartao + "', '" + dataDevalidade + "', " + limiteCartao + ")";
        try {
            statement.executeUpdate(sql);
            System.out.println("Cartão adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deletarCartao(Long cartaoId) {
        String sql = "DELETE FROM cartao WHERE id = " + cartaoId;
        try {
            int rowsAffected = statement.executeUpdate(sql);
            if (rowsAffected > 0) {
                System.out.println("Cartão excluído com sucesso!");
            } else {
                System.out.println("Nenhum cartão encontrado com o ID especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
