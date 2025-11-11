public class Ordenacao {

    // Método de ordenação Bubble Sort
    public static void bubbleSort(Jogo[] jogos, String criterio) {
        int n = jogos.length; // Armazena o tamanho do vetor de jogos
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) { // Compara elementos adjacentes

                // Se o elemento atual for "maior" que o próximo segundo o critério, troca
                if (comparar(jogos[j], jogos[j + 1], criterio) > 0) {
                    Jogo temp = jogos[j];
                    jogos[j] = jogos[j + 1]; // Move o próximo jogo para a posição atual
                    jogos[j + 1] = temp; // Coloca o jogo guardado na próxima posição
                }
            }
        }
    }

    // Método de ordenação Insertion Sort
    public static void insertionSort(Jogo[] jogos, String criterio) {
        for (int i = 1; i < jogos.length; i++) { // Começa do segundo elemento
            Jogo chave = jogos[i]; // Elemento atual que será inserido na posição correta
            int j = i - 1; // Índice do elemento anterior

            // Move os elementos maiores que a chave uma posição à frente
            while (j >= 0 && comparar(jogos[j], chave, criterio) > 0) {
                jogos[j + 1] = jogos[j];
                j--;
            }
            // Insere a chave na posição correta
            jogos[j + 1] = chave;
        }
    }

    // Método de ordenação Quick Sort (recursivo)
    public static void quickSort(Jogo[] jogos, int inicio, int fim, String criterio) {
        if (inicio < fim) { // Se ainda há mais de um elemento no intervalo
            int pivo = particionar(jogos, inicio, fim, criterio); // Encontra a posição do pivô
            quickSort(jogos, inicio, pivo - 1, criterio); // Ordena a parte esquerda
            quickSort(jogos, pivo + 1, fim, criterio); // Ordena a parte direita
        }
    }

    // Função auxiliar do Quick Sort: particiona o vetor
    private static int particionar(Jogo[] jogos, int inicio, int fim, String criterio) {
        Jogo pivo = jogos[fim]; // Define o pivô como o último elemento do intervalo
        int i = inicio - 1; // Marca a posição do menor elemento
        for (int j = inicio; j < fim; j++) { // Percorre o vetor até o elemento anterior ao pivô

            // Se o elemento atual é menor ou igual ao pivô, troca de posição
            if (comparar(jogos[j], pivo, criterio) <= 0) {
                i++;
                Jogo temp = jogos[i];
                jogos[i] = jogos[j];
                jogos[j] = temp;
            }
        }
        // Coloca o pivô na posição correta (entre os menores e maiores elementos)
        Jogo temp = jogos[i + 1];
        jogos[i + 1] = jogos[fim];
        jogos[fim] = temp;
        return i + 1; // Retorna o índice do pivô
    }

    

    // Função genérica de comparação entre dois jogos
    private static int comparar(Jogo a, Jogo b, String criterio) {
        switch (criterio.toLowerCase()) { // Converte o critério pra minúsculas
            case "titulo":
                return a.getTitulo().compareToIgnoreCase(b.getTitulo()); // Compara títulos
            case "genero":
                return a.getGenero().compareToIgnoreCase(b.getGenero()); // Compara gêneros
            case "ano":
                return Integer.compare(a.getAno(), b.getAno()); // Compara anos (numérico)
            default:
                return 0; // Caso o critério seja inválido, não altera a ordem
        }
    }

    // Exibe o vetor de jogos ordenado no console
    public static void exibir(Jogo[] jogos) {
        for (Jogo j : jogos) { // Percorre cada jogo do vetor
            System.out.println(j); // Imprime o objeto Jogo (usando o toString)
        }
    }
}
