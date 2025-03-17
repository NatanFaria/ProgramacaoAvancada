interface MetodoPagamento {
    void realizarPagamento(double quantia);
}

import java.util.UUID;
class PagamentoViaPix implements MetodoPagamento {
    @Override
    public void realizarPagamento(double quantia) {
        String codigoGerado = UUID.randomUUID().toString();
        System.out.println("Pagamento realizado via Pix. Código: " + codigoGerado);
        System.out.println("Valor: R$" + quantia);
    }
}

import java.util.Scanner;
class PagamentoCartao implements MetodoPagamento {
    @Override
    public void realizarPagamento(double quantia) {
        Scanner entrada = new Scanner(System.in);
        System.out.print("Informe o número do cartão de crédito: ");
        String numeroCartao = entrada.nextLine();
        System.out.println("Pagamento aprovado para o cartão: " + numeroCartao);
        System.out.println("Valor: R$" + quantia);
    }
}

class PagamentoBoletoBancario implements MetodoPagamento {
    @Override
    public void realizarPagamento(double quantia) {
        String codigoBoleto = "00190.00009 12345.678901 23456.789012 3 12345678901234";
        System.out.println("Pagamento realizado via Boleto. Código: " + codigoBoleto);
        System.out.println("Valor: R$" + quantia);
    }
}

class ProcessadorDePagamentos {
    private MetodoPagamento metodo;
    
    public ProcessadorDePagamentos(MetodoPagamento metodo) {
        this.metodo = metodo;
    }
    
    public void processarPagamento(double quantia) {
        metodo.realizarPagamento(quantia);
    }
}

import java.util.Scanner;
public class SistemaPagamento {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        
        System.out.println("Selecione o método de pagamento:");
        System.out.println("1: Pix");
        System.out.println("2: Cartão de Crédito");
        System.out.println("3: Boleto");
        System.out.print("Escolha: ");
        
        int escolha = entrada.nextInt();
        
        System.out.print("Informe o valor da transação: ");
        double quantia = entrada.nextDouble();
        
        MetodoPagamento metodo = null;
        
        switch (escolha) {
            case 1:
                metodo = new PagamentoViaPix();
                break;
            case 2:
                metodo = new PagamentoCartao();
                break;
            case 3:
                metodo = new PagamentoBoletoBancario();
                break;
            default:
                System.out.println("Escolha inválida!");
                return;
        }
        
        ProcessadorDePagamentos processador = new ProcessadorDePagamentos(metodo);
        processador.processarPagamento(quantia);
    }
}
