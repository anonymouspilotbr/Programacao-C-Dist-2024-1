package bancariothreads;

public class Main {
	public static int NUM_CLIENTES = 5;
	public static int NUM_FUNCIONARIOS = 4;
	public static void main(String[] args) {
		
        Banco banco = new Banco();
        Loja loja1 = new Loja(1, banco, 2);
        Loja loja2 = new Loja(2, banco, 2);

        banco.adicionarLoja(loja1);
        banco.adicionarLoja(loja2);

        Cliente[] clientes = new Cliente[5];
        for (int i = 0; i < clientes.length; i++) {
            clientes[i] = new Cliente(banco);
            clientes[i].start();
        }

        banco.iniciarLojas();

        for (Cliente cliente : clientes) {
            try {
                cliente.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Loja loja : new Loja[]{loja1, loja2}) {
            for (Funcionario funcionario : loja.getFuncionarios()) {
                funcionario.interrupt();
            }
        }
        
        System.out.println("Saldo final de cada conta:");
        for (Cliente cliente : clientes) {
            System.out.println("Cliente " + cliente.getId() + ": R$" + cliente.getConta().getSaldo());
        }
        for (Loja loja : new Loja[]{loja1, loja2}) {
            System.out.println("Saldo da loja " + loja.getId() + ": R$" + loja.getConta().getSaldo());
            for (Funcionario funcionario : loja.getFuncionarios()) {
                System.out.println("Saldo do funcionário da Loja " + loja.getId() + ": R$" + funcionario.getSalarioConta().getSaldo());
            }
        }
	}
}