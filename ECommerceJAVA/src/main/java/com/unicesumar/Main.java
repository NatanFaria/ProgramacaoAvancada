
package com.unicesumar;

import com.unicesumar.entities.Product;
import com.unicesumar.entities.User;
import com.unicesumar.paymentMethods.PaymentMethod;
import com.unicesumar.paymentMethods.PaymentType;
import com.unicesumar.repository.ProductRepository;
import com.unicesumar.repository.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ProductRepository listaDeProdutos = null;
        UserRepository listaDeUsuarios = null;
        SaleRepository saleRepository = null;

        Connection conn = null;
        String url = "jdbc:sqlite:database.sqlite";

        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                listaDeProdutos = new ProductRepository(conn);
                listaDeUsuarios = new UserRepository(conn);
                saleRepository = new SaleRepository(conn);
            } else {
                System.out.println("Falha na conexão.");
                System.exit(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n---MENU---");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Listar Produtos");
            System.out.println("3 - Cadastrar Usuário");
            System.out.println("4 - Listar Usuários");
            System.out.println("5 - Realizar Venda");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.print("Nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.print("Preço do produto: ");
                    double preco = scanner.nextDouble();
                    scanner.nextLine();
                    listaDeProdutos.save(new Product(nome, preco));
                }
                case 2 -> listaDeProdutos.findAll().forEach(System.out::println);
                case 3 -> {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("E-mail: ");
                    String email = scanner.nextLine();
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();
                    listaDeUsuarios.save(new User(nome, email, senha));
                }
                case 4 -> listaDeUsuarios.findAll().forEach(System.out::println);
                case 5 -> {
                    System.out.print("Digite o e-mail do comprador: ");
                    String email = scanner.nextLine();
                    Optional<User> userOpt = listaDeUsuarios.findAll().stream()
                            .filter(u -> u.getEmail().equalsIgnoreCase(email))
                            .findFirst();
                    if (userOpt.isEmpty()) {
                        System.out.println("Usuário não encontrado.");
                        break;
                    }
                    User cliente = userOpt.get();

                    List<Product> allProducts = listaDeProdutos.findAll();
                    if (allProducts.isEmpty()) {
                        System.out.println("Nenhum produto disponível.");
                        break;
                    }

                    System.out.println("Produtos disponíveis:");
                    for (int i = 0; i < allProducts.size(); i++) {
                        System.out.printf("%d - %s\n", i + 1, allProducts.get(i));
                    }

                    System.out.print("Digite os números dos produtos (separados por vírgula): ");
                    String[] indices = scanner.nextLine().split(",");
                    List<Product> produtosSelecionados = new ArrayList<>();
                    for (String idxStr : indices) {
                        try {
                            int idx = Integer.parseInt(idxStr.trim()) - 1;
                            produtosSelecionados.add(allProducts.get(idx));
                        } catch (Exception e) {
                            System.out.println("Índice inválido ignorado: " + idxStr);
                        }
                    }

                    if (produtosSelecionados.isEmpty()) {
                        System.out.println("Nenhum produto válido selecionado.");
                        break;
                    }

                    System.out.println("Escolha a forma de pagamento:");
                    System.out.println("1 - PIX");
                    System.out.println("2 - BOLETO");
                    System.out.println("3 - CARTÃO");
                    int pagamentoOpcao = scanner.nextInt();
                    scanner.nextLine();

                    PaymentType tipo = switch (pagamentoOpcao) {
                        case 1 -> PaymentType.PIX;
                        case 2 -> PaymentType.BOLETO;
                        case 3 -> PaymentType.CARTAO;
                        default -> null;
                    };

                    if (tipo == null) {
                        System.out.println("Forma de pagamento inválida.");
                        break;
                    }

                    PaymentMethod metodo = PaymentMethodFactory.create(tipo);
                    PaymentManager gestor = new PaymentManager();
                    gestor.setPaymentMethod(metodo);

                    double total = produtosSelecionados.stream().mapToDouble(Product::getPrice).sum();
                    gestor.pay(total);

                    Sale venda = new Sale(cliente, tipo, produtosSelecionados);
                    saleRepository.save(venda);

                    System.out.println("\nResumo da venda:");
                    System.out.println("Cliente: " + cliente.getName());
                    produtosSelecionados.forEach(p -> System.out.println("- " + p.getName()));
                    System.out.printf("Valor total: R$ %.2f\n", total);
                    System.out.println("Pagamento: " + tipo);
                    System.out.println("Venda registrada com sucesso!");
                }
                case 6 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

        } while (option != 6);

        scanner.close();
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
