public class Ordenacao {

    // Bubble Sort
    public static void bubbleSort(Jogo[] jogos, String criterio) {
        int n = jogos.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparar(jogos[j], jogos[j + 1], criterio) > 0) {
                    Jogo temp = jogos[j];
                    jogos[j] = jogos[j + 1];
                    jogos[j + 1] = temp;
                }
            }
        }
    }

    // Insertion Sort
    public static void insertionSort(Jogo[] jogos, String criterio) {
        for (int i = 1; i < jogos.length; i++) {
            Jogo chave = jogos[i];
            int j = i - 1;
            while (j >= 0 && comparar(jogos[j], chave, criterio) > 0) {
                jogos[j + 1] = jogos[j];
                j--;
            }
            jogos[j + 1] = chave;
        }
    }

    // QuickSort (Recursivo)
    public static void quickSort(Jogo[] jogos, int inicio, int fim, String criterio) {
        if (inicio < fim) {
            int pivo = particionar(jogos, inicio, fim, criterio);
            quickSort(jogos, inicio, pivo - 1, criterio);
            quickSort(jogos, pivo + 1, fim, criterio);
        }
    }

    private static int particionar(Jogo[] jogos, int inicio, int fim, String criterio) {
        Jogo pivo = jogos[fim];
        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            if (comparar(jogos[j], pivo, criterio) <= 0) {
                i++;
                Jogo temp = jogos[i];
                jogos[i] = jogos[j];
                jogos[j] = temp;
            }
        }
        Jogo temp = jogos[i + 1];
        jogos[i + 1] = jogos[fim];
        jogos[fim] = temp;
        return i + 1;
    }

    // Função de comparação genérica
    private static int comparar(Jogo a, Jogo b, String criterio) {
        switch (criterio.toLowerCase()) {
            case "titulo":
                return a.getTitulo().compareToIgnoreCase(b.getTitulo());
            case "genero":
                return a.getGenero().compareToIgnoreCase(b.getGenero());
            case "ano":
                return Integer.compare(a.getAno(), b.getAno());
            default:
                return 0;
        }
    }

    // Exibir vetor ordenado
    public static void exibir(Jogo[] jogos) {
        for (Jogo j : jogos) {
            System.out.println(j);
        }
    }
}
