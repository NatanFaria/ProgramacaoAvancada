import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Produto {
    private int codigo;
    private String nome;
    private double preco;

    public Produto(int codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + ", Nome: " + nome + ", Preço: R$ " + preco;
    }
}

public class CadastroProdutos {
    private static Map<Integer, Produto> produtos = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\nMenu:");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Buscar produto por código");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    buscarProduto();
                    break;
                case 3:
                    System.out.println("Saindo.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 3);

        scanner.close();
    }

    private static void cadastrarProduto() {
        System.out.print("Digite o código do produto: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha

        if (produtos.containsKey(codigo)) {
            System.out.println("Código já cadastrado. Utilize outro código.");
            return;
        }

        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o preço: ");
        double preco = scanner.nextDouble();

        Produto produto = new Produto(codigo, nome, preco);
        produtos.put(codigo, produto);
        System.out.println("Produto cadastrado.");
    }

    private static void buscarProduto() {
        System.out.print("Digite o código do produto: ");
        int codigo = scanner.nextInt();

        if (produtos.containsKey(codigo)) {
            System.out.println(produtos.get(codigo));
        } else {
            System.out.println("Produto não encontrado.");
        }
    }
}
