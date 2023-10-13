package org.example.view;

import org.example.model.*;
import org.example.service.CartaoService;
import org.example.service.PagamentoService;
import org.example.service.UserService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class MenuView {
    private final Scanner scanner=new Scanner(System.in);
    private final InputUser inputUser=new InputUser();
    private final UserModel userModel=new UserModel();
    private final UserService userService=new UserService();
    private final Validacoes validacoes=new Validacoes();

    private final CartaoModel cartaoModel=new CartaoModel();
    private final CartaoService cartaoService=new CartaoService();

    private final PagamentoModel pagamentoModel=new PagamentoModel();
    private final PagamentoService pagamentoService=new PagamentoService();

    public void MenuPrincipal() {
        System.out.println("\nBem-vindo(a) ao menu Principal");
        System.out.println("0 - Sair.");
        System.out.println("1 - Logar.");
        System.out.println("2 - Cadastrar.");
    }
    public void casePrincipal() {
        int option;
        do {
            MenuPrincipal();
            option = inputUser.readIntFromUser("Qual opção você deseja: ");

            switch (option) {
                case 0 -> System.out.println("Saindo do sistema...");
                case 1 -> logar();
                case 2 -> cadastrar();
                default -> System.out.println("Opção inválida, tente novamente!");
            }
        } while (option != 0);
    }

    public void menuParcelas() {
        System.out.println("Escolha o número de parcelas:");
        System.out.println("1 - Dividir em 2x");
        System.out.println("2 - Dividir em 4x");
        System.out.println("3 - Dividir em 6x");
        System.out.println("4 - Dividir em 8x");
        System.out.println("5 - Dividir em 10x");
        System.out.println("6 - Dividir em 12x");
        System.out.println("0 - Sair");

    }
    public void menuLogar() {
        System.out.println("0 - Sair.");
        System.out.println("1 - Cadastra seu cartão.");
        System.out.println("2 - Efetuar um emprestimo.");
    }
    public void logar() {
        String email = inputUser.readStringFromUser("Digite seu e-mail:");
        String senha = inputUser.readStringFromUser("Digite sua senha:");

        if (validacoes.validarUserCredenciais(email, senha)) {
            System.out.println("========== Bem-Vindo(a) ao Deixa que eu pago ==========");
            System.out.println(validacoes.userInfoByAlias(email));
            Long id=validacoes.getClienteId(email);
            int option;
            do {
                menuLogar();
                option = inputUser.readIntFromUser("Qual opção você deseja: ");

                switch (option) {
                    case 0 -> System.out.println("Saindo do sistema...");
                    case 1 -> cadastrarCartao();
                    case 2 -> {
                        System.out.println("Qual o valor da sua divida:");
                        BigDecimal valorDivida =scanner.nextBigDecimal();
                        menuParcelas();
                        double juros = 0;
                        int parcela=0;

                        int opc = inputUser.readIntFromUser("Qual o número de Parcelas");

                        switch (opc) {
                            case 1 -> {
                                juros = 0.05;
                                parcela=2;
                            }
                            case 2 -> {
                                juros = 0.07; // Juros de 7% para 4 parcelas
                                parcela=4;
                            }
                            case 3 ->{
                                juros = 0.09; // Juros de 9% para 6 parcelas
                                parcela=6;
                            }
                            case 4 -> {
                                juros = 0.11; // Juros de 11% para 8 parcelas
                                parcela=8;
                            }
                            case 5 -> {
                                juros = 0.13; // Juros de 13% para 10 parcelas
                                parcela=10;
                            }
                            case 6 -> {
                                juros = 0.15; // Juros de 15% para 12 parcelas
                                parcela=12;
                            }
                            default -> System.out.println("Opção inválida. Escolha uma opção válida.");
                        }
                        System.out.println("Você escolheu a opção " + opc + " e tera um acrecimo do juros de " + (juros * 100) + "%.");

                        System.out.println("Fazer Pagamento");
                        System.out.println(validacoes.cartaoInfoByEmail(id));
                        int idCartao =inputUser.readIntFromUser("ID do Cartão: ");
                        BigDecimal valorTotalEmprestimo = valorDivida.multiply(BigDecimal.ONE.add(BigDecimal.valueOf(juros)));

                        // Calcula o valor total da parcela
                        BigDecimal valorTotalParcela = valorTotalEmprestimo.divide(BigDecimal.valueOf(parcela), 2, BigDecimal.ROUND_HALF_UP);

                        // Chama o método para adicionar o pagamento
                        pagamentoService.adicionarPagamento((long) idCartao, parcela, valorTotalParcela, valorTotalEmprestimo, "Pago");
                    }

                    default -> System.out.println("Opção inválida, tente novamente!");
                }
            } while (option != 0);

        } else {
            System.out.println("E-mail ou senha inválidos!");
        }

    }

    public void cadastrarCartao(){
        System.out.println("Cadastrar Cartão");


        int idCliente =inputUser.readIntFromUser("ID do Cliente:");
        String nomeRemetente = inputUser.readStringFromUser("Nome do Remetente: ");
        String numeroCartao = inputUser.readStringFromUser("Número do Cartão: ");
        String cvvCartao = inputUser.readStringFromUser("CVV do Cartão: ");

        System.out.print("Data de Validade (MM/yyyy): ");
        String dataValidadeStr = scanner.next();
        Date dataDevalidade;

        try {
            dataDevalidade = new SimpleDateFormat("MM/yyyy").parse(dataValidadeStr);
        } catch (ParseException e) {
            System.out.println("Formato de data de validade inválido. Use MM/yyyy.");
            return;
        }

        System.out.print("Limite do Cartão: ");
        BigDecimal limiteCartao = scanner.nextBigDecimal();
        cartaoService.adicionarCartao((long) idCliente, nomeRemetente, numeroCartao, cvvCartao, dataDevalidade, limiteCartao);
    }
    public void cadastrar() {
        String nome = inputUser.readStringFromUser("Qual seu nome completo:");
        String email = inputUser.readStringFromUser("Qual seu e-mail:");
        String senha = inputUser.readStringFromUser("Qual sua senha:");
        String cpf = inputUser.readStringFromUser("Qual seu CPF:");
        String endereco = inputUser.readStringFromUser("Qual seu endereço:");
        String telefone = inputUser.readStringFromUser("Qual seu telefone para contato:");

        // Defina um loop para garantir que o usuário forneça uma entrada válida
        while (true) {
            if (validacoes.validarEmail(email)) {
                userModel.setNomeCompleto(nome);
                userModel.setEmail(email);
                userModel.setSenha(senha);
                userModel.setCpf(cpf);
                userModel.setEndereco(endereco);
                userModel.setTelefone(telefone);

                userService.adicionarUsuario(userModel.getNomeCompleto(), userModel.getEmail(),
                        userModel.getSenha(), userModel.getCpf(), userModel.getEndereco(), userModel.getTelefone());

                // Saia do loop quando a entrada for válida
                break;
            } else {
                System.out.println("E-mail inválido. Digite um e-mail válido.");
                // Solicite novamente a entrada do usuário
                email = inputUser.readStringFromUser("Qual seu e-mail:");
            }
        }
    }

}
