import entities.User;
import repository.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db")) {
            UserRepository repo = new UserRepository(conn);
            Scanner input = new Scanner(System.in);

            while (true) {
                System.out.println("\n=== MENU USUÁRIOS ===");
                System.out.println("1. Novo usuário");
                System.out.println("2. Ver todos");
                System.out.println("3. Sair");
                System.out.print("Opção: ");
                int choice = input.nextInt();
                input.nextLine();

                if (choice == 1) {
                    System.out.print("Nome: ");
                    String nome = input.nextLine();
                    System.out.print("Email: ");
                    String email = input.nextLine();
                    System.out.print("Senha: ");
                    String senha = input.nextLine();
                    User user = new User(UUID.randomUUID(), nome, email, senha);
                    repo.save(user);
                    System.out.println("Usuário salvo.");
                } else if (choice == 2) {
                    repo.findAll().forEach(u -> System.out.println(
                        u.getUuid() + " - " + u.getName() + " - " + u.getEmail()));
                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println("Escolha inválida.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
        }
    }
}
