package bancariothreads;

public class Funcionario extends Thread {
    public static final int SALARIO_BASE = 1400;
    private static final double TAXA_INVESTIMENTO = 0.20; 
    
    private final Loja loja;
    private final Conta salarioConta;
    private final Conta investimentoConta;
    
    public Funcionario(Loja loja) {
        this.loja = loja;
        this.salarioConta = new Conta(0); 
        this.investimentoConta = new Conta(0); 
    }
    
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            
            int salario = loja.pagarSalario(this);
            salarioConta.creditar(salario);
            System.out.println("Funcionário da Loja " + loja.getId() + " recebeu o salário de R$" + salario);
            
          
            int valorInvestimento = (int) (salario * TAXA_INVESTIMENTO);
            investimentoConta.creditar(valorInvestimento);
            System.out.println("Funcionário da Loja " + loja.getId() + " investiu R$" + valorInvestimento +
                    " em sua conta de investimentos");
            
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
            	System.out.println("Thread Interrompida!");
                return;
            }
        }
    }
    
    public Conta getSalarioConta() {
        return salarioConta;
    }
    
    public Conta getInvestimentoConta() {
        return investimentoConta;
    }
}
