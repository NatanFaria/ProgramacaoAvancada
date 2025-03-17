interface MensagemNotificacao {
    void disparar(String conteudo);
}

class EmailMensagem implements MensagemNotificacao {
    @Override
    public void disparar(String conteudo) {
        System.out.println("E-mail enviado: " + conteudo);
    }
}

class SMSMensagem implements MensagemNotificacao {
    @Override
    public void disparar(String conteudo) {
        System.out.println("SMS enviado: " + conteudo);
    }
}

class PushMensagem implements MensagemNotificacao {
    @Override
    public void disparar(String conteudo) {
        System.out.println("Push Notification enviado: " + conteudo);
    }
}

class GeradorNotificacao {
    public static MensagemNotificacao criarMensagem(String tipo) {
        switch (tipo.toLowerCase()) {
            case "email":
                return new EmailMensagem();
            case "sms":
                return new SMSMensagem();
            case "push":
                return new PushMensagem();
            default:
                throw new IllegalArgumentException("Tipo de notificação inválido.");
        }
    }
}

import java.util.Scanner;
public class SistemaDeNotificacao {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        
        System.out.println("Escolha um tipo de notificação:");
        System.out.println("1: Email");
        System.out.println("2: SMS");
        System.out.println("3: Push Notification");
        System.out.print("Escolha: ");
        
        int tipoEscolhido = leitor.nextInt();
        leitor.nextLine(); // Consumir quebra de linha
        
        System.out.print("Escreva a mensagem: ");
        String conteudo = leitor.nextLine();
        
        MensagemNotificacao mensagem = null;
        
        switch (tipoEscolhido) {
            case 1:
                mensagem = GeradorNotificacao.criarMensagem("email");
                break;
            case 2:
                mensagem = GeradorNotificacao.criarMensagem("sms");
                break;
            case 3:
                mensagem = GeradorNotificacao.criarMensagem("push");
                break;
            default:
                System.out.println("Escolha inválida!");
                return;
        }
        
        mensagem.disparar(conteudo);
    }
}
