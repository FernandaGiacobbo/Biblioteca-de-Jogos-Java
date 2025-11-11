import java.util.LinkedList; // Importa a classe LinkedList

public class TabelaHash {
    private LinkedList<Jogo>[] tabela; // Vetor de listas (cada posição é uma lista de jogos)
    private int tamanho;

    @SuppressWarnings("unchecked") // Evita o aviso do compilador sobre conversões genéricas
    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        tabela = new LinkedList[tamanho]; // Cria o vetor de listas com o tamanho indicado
        for (int i = 0; i < tamanho; i++) { // Inicializa cada posição do vetor com uma nova lista vazia
            tabela[i] = new LinkedList<>();
        }
    }

    // Função hash
    private int hash(String chave) {
        int soma = 0; // Soma acumulada dos códigos dos caracteres
        for (char c : chave.toCharArray()) {
            soma += c; // Soma o valor numérico de cada caractere
        }
        return soma % tamanho; // Retorna o resto da divisão da soma pelo tamanho da tabela
    }

    // Insere um novo jogo na tabela
    public void inserir(Jogo jogo) {
        int indice = hash(jogo.getTitulo()); // Calcula o índice usando o título do jogo
        tabela[indice].add(jogo);
    }

    // Remove um jogo da tabela, com base no título
    public boolean remover(String titulo) {
        int indice = hash(titulo); // Calcula o índice onde o jogo deve estar
        for (Jogo jogo : tabela[indice]) {
            if (jogo.getTitulo().equalsIgnoreCase(titulo)) { // Se encontrar o título
                tabela[indice].remove(jogo);
                return true; 
            }
        }
        return false;
    }

    // Busca um jogo pelo título
    public Jogo buscar(String titulo) {
        int indice = hash(titulo); // Calcula o índice da chave
        for (Jogo jogo : tabela[indice]) {
            if (jogo.getTitulo().equalsIgnoreCase(titulo)) { // Se o título for igual
                return jogo;
            }
        }
        return null;
    }

    // Exporta todos os jogos da tabela para um vetor 
    public Jogo[] exportar() {
        LinkedList<Jogo> lista = new LinkedList<>(); // Cria uma lista temporária pra juntar todos os jogos
        for (LinkedList<Jogo> bucket : tabela) { // Percorre cada lista ("bucket") da tabela
            lista.addAll(bucket); 
        }
        return lista.toArray(new Jogo[0]); // Converte a lista em um vetor e retorna
    }

    // Exibe todos os jogos da tabela no console
    public void exibirTodos() {
        for (LinkedList<Jogo> bucket : tabela) {
            for (Jogo j : bucket) { // Percorre cada jogo dentro da lista
                System.out.println(j); // Imprime o jogo (usa o método toString da classe Jogo)
            }
        }
    }
}
