package bancariothreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banco {
    private final List<Loja> lojas;
    private final Lock lock;
    
    public Banco() {
        this.lojas = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void adicionarLoja(Loja loja) {
        lojas.add(loja);
    }

    public int getNumeroLojas() {
        return lojas.size();
    }

    public Loja getLoja(int index) {
        return lojas.get(index);
    }

    public void iniciarLojas() {
        for (Loja loja : lojas) {
            loja.iniciarFuncionarios();
        }
    }

    public void fazerPagamento(int lojaIndex, int valor) {
        lock.lock();
        try {
            Loja loja = lojas.get(lojaIndex);
            loja.receberPagamento(valor);
        } finally {
            lock.unlock();
        }
    }
}

