package bancariothreads;

import java.util.Random;

public class Cliente extends Thread {
    private static final int SALDO_INICIAL = 1000;
    
    private final Banco banco;
    private Conta conta;
    private final Random random;
    
    public Cliente(Banco banco) {
        this.banco = banco;
        this.random = new Random();
        this.conta = new Conta(SALDO_INICIAL);
    }
    
    @Override
    public void run() {
        while (conta.getSaldo() > 0) {
        	int valorCompra = (Math.random() < 0.5) ? 100 : 200;
        	int lojaIndex = (int) (Math.random() * banco.getNumeroLojas());
            Loja loja = banco.getLoja(lojaIndex);
            
            if (loja.efetuarCompra(this, valorCompra)) {
                System.out.println("Cliente " + getId() + " comprou R$" + valorCompra + " na loja " + loja.getId());
            } else {
                System.out.println("Cliente " + getId() + " não conseguiu comprar R$" + valorCompra + " na loja " + loja.getId() + " por saldo insuficiente.");
                break;
            }

            try {
                Thread.sleep((int) (Math.random() * 2000) + 1000); 
            } catch (InterruptedException e) {
                System.out.println("Thread Interrompida!");
            }
        }
        
        System.out.println("Cliente " + getId() + " não tem saldo suficiente para continuar comprando.");
    }
    
    public Conta getConta() {
        return conta;
    }
}
