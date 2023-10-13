package org.example.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Scanner;

public class InputUser {
    private final Scanner scanner;
    public InputUser() {
        scanner = new Scanner(System.in);
    }
    public int readIntFromUser(String mensagem) {
        while (true) {
            System.out.println(mensagem);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro válido.");
            }
        }
    }
    public Double readDobleFromUser(String mensagem) {
        while (true) {
            System.out.println(mensagem);
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro válido.");
            }
        }
    }
    public String readStringFromUser(String mensagem) {
        while (true) {
            System.out.println(mensagem);
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Entrada inválida. Por favor, digite um valor válido.");
            }
        }
    }

}
