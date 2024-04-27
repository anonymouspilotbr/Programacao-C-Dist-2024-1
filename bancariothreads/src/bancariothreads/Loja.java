package bancariothreads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Loja {
    private final int id;
    private final Banco banco;
    private final Funcionario[] funcionarios;
    private final Conta conta;
    private final Lock lock;
    
    public Loja(int id, Banco banco, int numFuncionarios) {
        this.id = id;
        this.banco = banco;
        this.funcionarios = new Funcionario[numFuncionarios];
        for (int i = 0; i < numFuncionarios; i++) {
            this.funcionarios[i] = new Funcionario(this);
        }
        this.conta = new Conta(0); 
        this.lock = new ReentrantLock();
    }
    
    public void iniciarFuncionarios() {
        for (Funcionario funcionario : funcionarios) {
            funcionario.start();
        }
    }
    
    public int pagarSalario(Funcionario funcionario) {
        lock.lock();
        try {
            int valorSalario = Funcionario.SALARIO_BASE;
            funcionario.getSalarioConta().creditar(valorSalario);
            conta.debitar(valorSalario);
            return valorSalario;
        } finally {
            lock.unlock();
        }
    }
    
    public void receberPagamento(int valor) {
        conta.creditar(valor);
        System.out.println("Loja " + id + " recebeu um pagamento de R$" + valor);
        pagarFuncionarios();
    }
    
    private void pagarFuncionarios() {
        int totalSalarios = funcionarios.length * Funcionario.SALARIO_BASE;
        if (conta.getSaldo() >= totalSalarios) {
            lock.lock();
            try {
                for (Funcionario funcionario : funcionarios) {
                    Conta salarioConta = funcionario.getSalarioConta();
                    salarioConta.creditar(Funcionario.SALARIO_BASE);
                }
                conta.debitar(totalSalarios); 
                System.out.println("Loja " + id + " pagou salários para todos os funcionários.");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Loja " + id + " não possui saldo suficiente para pagar os funcionários.");
        }
    }

    
    public boolean efetuarCompra(Cliente cliente, int valor) {
        lock.lock();
        try {
            if (conta.getSaldo() >= valor) {
            	cliente.getConta().debitar(valor); 
                conta.creditar(valor);
                return true;
            } else {
                return false; 
            }
        } finally {
            lock.unlock();
        }
    }
    
    public Funcionario[] getFuncionarios() {
        return funcionarios;
    }
    
    public int getId() {
        return id;
    }
    
    public Conta getConta() {
    	return conta;
    }
}

