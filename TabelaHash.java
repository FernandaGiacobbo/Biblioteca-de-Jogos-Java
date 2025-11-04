import java.util.LinkedList;

public class TabelaHash {
    private LinkedList<Jogo>[] tabela;
    private int tamanho;

    @SuppressWarnings("unchecked")
    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        tabela = new LinkedList[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int hash(String chave) {
        int soma = 0;
        for (char c : chave.toCharArray()) {
            soma += c;
        }
        return soma % tamanho;
    }

    public void inserir(Jogo jogo) {
        int indice = hash(jogo.getTitulo());
        tabela[indice].add(jogo);
    }

    public boolean remover(String titulo) {
        int indice = hash(titulo);
        for (Jogo jogo : tabela[indice]) {
            if (jogo.getTitulo().equalsIgnoreCase(titulo)) {
                tabela[indice].remove(jogo);
                return true;
            }
        }
        return false;
    }

    public Jogo buscar(String titulo) {
        int indice = hash(titulo);
        for (Jogo jogo : tabela[indice]) {
            if (jogo.getTitulo().equalsIgnoreCase(titulo)) {
                return jogo;
            }
        }
        return null;
    }

    public Jogo[] exportar() {
        LinkedList<Jogo> lista = new LinkedList<>();
        for (LinkedList<Jogo> bucket : tabela) {
            lista.addAll(bucket);
        }
        return lista.toArray(new Jogo[0]);
    }

    public void exibirTodos() {
        for (LinkedList<Jogo> bucket : tabela) {
            for (Jogo j : bucket) {
                System.out.println(j);
            }
        }
    }
}
