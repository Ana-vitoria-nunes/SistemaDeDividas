package org.example.model;

import org.example.connection.Connect;

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validacoes {
    private final Connection connection = Connect.fazerConexao();


    //Validações do cartão
    public boolean validarNumeroCartao(String numeroCartao) {
        // Remover espaços em branco e traços (caso presentes) do número do cartão.
        numeroCartao = numeroCartao.replaceAll("[ -]", "");

        if (numeroCartao.length() != 16) {
            return false;  // O número do cartão deve ter exatamente 16 dígitos.
        }

        int sum = 0;
        boolean doubleDigit = false;

        for (int i = numeroCartao.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(numeroCartao.charAt(i));

            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            doubleDigit = !doubleDigit;
        }

        return sum % 10 == 0;
    }

    public boolean validarDataValidade(Date dataValidade) {
        // Defina o formato esperado para a data de validade (MM/AA ou MM/AAAA).
        SimpleDateFormat formato = new SimpleDateFormat("MM/yy"); // "MM/AA"
        // Ou, se a data de validade estiver no formato "MM/AAAA":
        // SimpleDateFormat formato = new SimpleDateFormat("MM/yyyy");

        // Certifique-se de que a data de validade esteja no formato correto.
        formato.setLenient(false); // Isso impedirá datas inválidas (por exemplo, 13/20) de serem aceitas.

        try {
            Date data = formato.parse(String.valueOf(dataValidade));

            // Verifique se a data de validade não está expirada.
            Date dataAtual = new Date();
            if (data.after(dataAtual)) {
                return true; // A data é válida e não expirada.
            } else {
                return false; // A data expirou.
            }
        } catch (ParseException e) {
            return false; // A data não está no formato correto.
        }
    }

    public boolean validarLimitesValores(BigDecimal limiteCartao, BigDecimal valorTotalParcela) {
        if (limiteCartao != null && valorTotalParcela != null) {
            return limiteCartao.compareTo(valorTotalParcela) >= 0;
        } else {
            return false;
        }
    }

    public boolean validarCamposObrigatorios(String nomeRemetente, String numeroCartao, String cvv, Date dataValidade, BigDecimal limiteCartao) {
        return nomeRemetente != null && !nomeRemetente.isEmpty() &&
                numeroCartao != null && !numeroCartao.isEmpty() &&
                cvv != null && !cvv.isEmpty() &&
                dataValidade != null && limiteCartao != null;
    }


    //Validações Cliente
    public boolean validarCamposObrigatoriosUser(String nomeCompleto, String email, String senha, String cpf, String endereco, String telefone) {
        return nomeCompleto != null && !nomeCompleto.isEmpty() &&
                email != null && !email.isEmpty() &&
                senha != null && !senha.isEmpty() &&
                cpf != null && !cpf.isEmpty() &&
                endereco != null && !endereco.isEmpty() &&
                telefone != null && !telefone.isEmpty();
    }

    public boolean validarUser(Long id) {
        String sql = "SELECT COUNT(*) FROM \"user\" WHERE id=?";

        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            resultSet.close();
            preparedStatement.close();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.endsWith("gmail.com"); // O email atende aos critérios.
    }

    public boolean validarCartao(Long id) {
        String sql = "SELECT COUNT(*) FROM cartao WHERE id=?";

        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            resultSet.close();
            preparedStatement.close();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validarCamposObrigatoriosPagamento(Long idCartao, Integer parcelas, BigDecimal valorTotalParcela, BigDecimal valorTotalEmprestimo, String status) {
        return idCartao != null && parcelas != null && valorTotalParcela != null && valorTotalEmprestimo != null && status != null;
    }

    public boolean validarUserCredenciais(String email, String senha) {
        String sql = "SELECT COUNT(*) FROM \"user\" WHERE email = ? AND senha = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            resultSet.close();
            preparedStatement.close();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String userInfoByAlias(String email) {
        String sql = "SELECT id, nomecompleto, cpf FROM \"user\" WHERE email=?";

        String nome = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                nome = resultSet.getString("nomecompleto");
                String cpf = resultSet.getString("cpf");
                System.out.println("Informações da Conta:\n ID: " + id + " | CPF: " + cpf + " | Nome: " + nome + " | Email: " + email);
            } else {
                System.out.println("Usuário com o nome " + nome + " não encontrado.");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Long getClienteId(String email) {
        try {
            String sql = "SELECT id FROM \"user\" WHERE email =?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Long.valueOf("Erro ao buscar id do cliente");
        }
    }

    public String cartaoInfoByEmail(Long idCliente) {
        String sql = "SELECT id, nomeremetente, numerocartao, cvv, dataValidade, limitecartao FROM cartao WHERE idcliente = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idCliente);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String nome = resultSet.getString("nomeremetente");
                String numero = resultSet.getString("numerocartao");
                String cvv = resultSet.getString("cvv");
                Date data = resultSet.getDate("datavalidade");
                BigDecimal limite = resultSet.getBigDecimal("limitecartao");
                System.out.println("\nSeus cartões:\n ID: " + id + " |Nome Remetente: " + nome + " |Nº cartão: " + numero + " |CVV: " + cvv + " |Limite: " + limite);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }


}
