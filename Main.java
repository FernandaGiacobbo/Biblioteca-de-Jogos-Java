import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TabelaHash tabela = new TabelaHash(10);
        int opcao;

        do {
            System.out.println("\nBIBLIOTECA DE JOGOS");
            System.out.println("1. Inserir jogo");
            System.out.println("2. Remover jogo");
            System.out.println("3. Buscar jogo");
            System.out.println("4. Exibir todos");
            System.out.println("5. Ordenar jogos");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Gênero: ");
                    String genero = sc.nextLine();
                    System.out.print("Ano: ");
                    int ano = sc.nextInt();
                    tabela.inserir(new Jogo(titulo, genero, ano));
                    System.out.println("Jogo adicionado com sucesso!");
                }

                case 2 -> {
                    System.out.print("Título para remover: ");
                    String titulo = sc.nextLine();
                    if (tabela.remover(titulo)) System.out.println("Removido!");
                    else System.out.println("Não encontrado!");
                }

                case 3 -> {
                    System.out.print("Título para buscar: ");
                    String titulo = sc.nextLine();
                    Jogo j = tabela.buscar(titulo);
                    System.out.println(j != null ? j : "Não encontrado!");
                }

                case 4 -> tabela.exibirTodos();

                case 5 -> {
                    Jogo[] jogos = tabela.exportar();
                    if (jogos.length == 0) {
                        System.out.println("Nenhum jogo cadastrado!");
                        break;
                    }

                    System.out.print("Ordenar por (titulo/genero/ano): ");
                    String criterio = sc.nextLine().toLowerCase();

                    System.out.println("\nEscolhendo melhor algoritmo...");
                    switch (criterio) {
                        case "titulo" -> {
                            if (jogos.length <= 15) {
                                System.out.println("Poucos jogos → usando InsertionSort (mais leve e estável)");
                                Ordenacao.insertionSort(jogos, "titulo");
                            } else {
                                System.out.println("Muitos jogos → usando QuickSort (mais eficiente)");
                                Ordenacao.quickSort(jogos, 0, jogos.length - 1, "titulo");
                            }
                        }

                        case "genero" -> {
                            System.out.println("Ordenando por gênero → usando InsertionSort (mantém ordem de títulos iguais)");
                            Ordenacao.insertionSort(jogos, "genero");
                        }

                        case "ano" -> {
                            System.out.println("Ordenando por ano → usando QuickSort (mais rápido para números)");
                            Ordenacao.quickSort(jogos, 0, jogos.length - 1, "ano");
                        }

                        default -> {
                            System.out.println("Critério inválido!");
                            break;
                        }
                    }

                    System.out.println("\n--- Jogos ordenados ---");
                    Ordenacao.exibir(jogos);
                }

                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }
}
