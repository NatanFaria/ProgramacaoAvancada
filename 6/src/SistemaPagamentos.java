interface FormaPagamento {
    void efetuarPagamento(double quantia);
}

class PagamentoViaPix implements FormaPagamento {
    @Override
    public void efetuarPagamento(double quantia) {
        System.out.println("Pix realizado com sucesso! Valor: R$" + quantia);
    }
}

class PagamentoCartao implements FormaPagamento {
    @Override
    public void efetuarPagamento(double quantia) {
        System.out.println("Transação aprovada no cartão! Valor: R$" + quantia);
    }
}

class PagamentoBoletoBancario implements FormaPagamento {
    @Override
    public void efetuarPagamento(double quantia) {
        System.out.println("Boleto emitido com valor de R$" + quantia);
    }
}

class CriadorPagamento {
    public static FormaPagamento gerarPagamento(String modalidade) {
        switch (modalidade.toLowerCase()) {
            case "pix":
                return new PagamentoViaPix();
            case "cartao":
                return new PagamentoCartao();
            case "boleto":
                return new PagamentoBoletoBancario();
            default:
                throw new IllegalArgumentException("Forma de pagamento inválida.");
        }
    }
}

class ControladorPagamento {
    private FormaPagamento formaPagamento;
    
    public ControladorPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
    public void iniciarPagamento(double quantia) {
        formaPagamento.efetuarPagamento(quantia);
    }
}

import java.util.Scanner;
public class SistemaPagamentos {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        
        System.out.println("Selecione a forma de pagamento:");
        System.out.println("1: Pix");
        System.out.println("2: Cartão de Crédito");
        System.out.println("3: Boleto");
        System.out.print("Escolha: ");
        
        int escolha = leitor.nextInt();
        leitor.nextLine();
        
        System.out.print("Informe o valor do pagamento: ");
        double quantia = leitor.nextDouble();
        
        FormaPagamento forma = null;
        
        switch (escolha) {
            case 1:
                forma = CriadorPagamento.gerarPagamento("pix");
                break;
            case 2:
                forma = CriadorPagamento.gerarPagamento("cartao");
                break;
            case 3:
                forma = CriadorPagamento.gerarPagamento("boleto");
                break;
            default:
                System.out.println("Escolha inválida!");
                return;
        }
        
        ControladorPagamento controlador = new ControladorPagamento(forma);
        controlador.iniciarPagamento(quantia);
    }
}
