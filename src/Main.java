import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private TabelaHash tabela = new TabelaHash(10);
    private ObservableList<Jogo> jogosList = FXCollections.observableArrayList();
    private TableView<Jogo> tabelaView = new TableView<>();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Biblioteca de Jogos");

        // Campos de entrada
        TextField tituloField = new TextField();
        tituloField.setPromptText("Título");

        TextField generoField = new TextField();
        generoField.setPromptText("Gênero");

        TextField anoField = new TextField();
        anoField.setPromptText("Ano");

        // Botões
        Button btnInserir = new Button("Inserir");
        Button btnRemover = new Button("Remover");
        Button btnBuscar = new Button("Buscar");
        Button btnOrdenar = new Button("Ordenar");
        Button btnFiltrar = new Button("Filtrar");
        Button btnLimparFiltro = new Button("Limpar Filtro");
        btnLimparFiltro.setOnAction(e -> atualizarLista());



        // Configura tabela
        TableColumn<Jogo, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTitulo()));

        TableColumn<Jogo, String> colGenero = new TableColumn<>("Gênero");
        colGenero.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getGenero()));

        TableColumn<Jogo, String> colAno = new TableColumn<>("Ano");
        colAno.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().getAno())));

        tabelaView.getColumns().addAll(colTitulo, colGenero, colAno);
        tabelaView.setItems(jogosList);
        tabelaView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Layout dos campos
        HBox campos = new HBox(10, tituloField, generoField, anoField, btnInserir, btnRemover, btnBuscar, btnOrdenar, btnFiltrar, btnLimparFiltro);
        campos.setPadding(new Insets(10));

        VBox layout = new VBox(10, campos, tabelaView);
        layout.setPadding(new Insets(10));

        // Eventos dos botões
        btnInserir.setOnAction(e -> {
            try {
                String titulo = tituloField.getText();
                String genero = generoField.getText();
                int ano = Integer.parseInt(anoField.getText());
                tabela.inserir(new Jogo(titulo, genero, ano));
                atualizarLista();
                limparCampos(tituloField, generoField, anoField);
                mostrarAlerta("Sucesso", "Jogo adicionado com sucesso!");
            } catch (Exception ex) {
                mostrarAlerta("Erro", "Preencha todos os campos corretamente!");
            }
        });

        btnRemover.setOnAction(e -> {
            String titulo = tituloField.getText();
            if (tabela.remover(titulo)) {
                atualizarLista();
                mostrarAlerta("Sucesso", "Jogo removido!");
            } else {
                mostrarAlerta("Erro", "Jogo não encontrado!");
            }
        });

        btnBuscar.setOnAction(e -> {
            String titulo = tituloField.getText();
            Jogo j = tabela.buscar(titulo);
            if (j != null) {
                mostrarAlerta("Encontrado", j.toString());
            } else {
                mostrarAlerta("Erro", "Jogo não encontrado!");
            }
        });

        btnOrdenar.setOnAction(e -> {
            // Escolher critério
            ChoiceDialog<String> criterioDialog = new ChoiceDialog<>("titulo", "titulo", "genero", "ano");
            criterioDialog.setTitle("Ordenar Jogos");
            criterioDialog.setHeaderText("Escolha o critério de ordenação:");
            criterioDialog.setContentText("Critério:");

            criterioDialog.showAndWait().ifPresent(criterio -> {
                // Escolher algoritmo
                ChoiceDialog<String> algoritmoDialog = new ChoiceDialog<>("QuickSort", "QuickSort", "BubbleSort", "InsertionSort");
                algoritmoDialog.setTitle("Tipo de Ordenação");
                algoritmoDialog.setHeaderText("Escolha o algoritmo de ordenação:");
                algoritmoDialog.setContentText("Algoritmo:");

                algoritmoDialog.showAndWait().ifPresent(algoritmo -> {
                    Jogo[] jogos = tabela.exportar();
                    if (jogos.length == 0) {
                        mostrarAlerta("Aviso", "Nenhum jogo cadastrado!");
                        return;
                    }

                    long inicio = System.nanoTime(); // ⏱️ começa a medir o tempo

                    switch (algoritmo.toLowerCase()) {
                        case "bubblesort" -> Ordenacao.bubbleSort(jogos, criterio);
                        case "insertionsort" -> Ordenacao.insertionSort(jogos, criterio);
                        case "quicksort" -> Ordenacao.quickSort(jogos, 0, jogos.length - 1, criterio);
                    }

                    long fim = System.nanoTime(); // ⏱️ fim da medição
                    double tempoMs = (fim - inicio) / 1_000_000.0;

                    //Mostrar na interface
                    jogosList.setAll(jogos);
                    mostrarAlerta("Ordenado", "Jogos ordenados por " + criterio + " usando " + algoritmo + "!");

                    //Mostrar no terminal
                    System.out.println("=================================");
                    System.out.println("Algoritmo usado: " + algoritmo);
                    System.out.println("Critério: " + criterio);
                    System.out.printf("Tempo de execução: %.3f ms%n", tempoMs);
                    System.out.println("=================================");
                });
            });
        });
        btnFiltrar.setOnAction(e -> {
            ChoiceDialog<String> criterioDialog = new ChoiceDialog<>("ano", "titulo", "genero", "ano");
            criterioDialog.setTitle("Filtrar Jogos");
            criterioDialog.setHeaderText("Escolha o critério de filtro:");
            criterioDialog.setContentText("Critério:");

            criterioDialog.showAndWait().ifPresent(criterio -> {
                TextInputDialog valorDialog = new TextInputDialog();
                valorDialog.setTitle("Valor do Filtro");
                valorDialog.setHeaderText("Digite o valor para filtrar por " + criterio + ":");
                valorDialog.setContentText("Valor:");

                valorDialog.showAndWait().ifPresent(valor -> {
                    Jogo[] todos = tabela.exportar();
                    ObservableList<Jogo> filtrados = FXCollections.observableArrayList();

                    for (Jogo j : todos) {
                        switch (criterio) {
                            case "titulo" -> {
                                if (j.getTitulo().toLowerCase().contains(valor.toLowerCase()))
                                    filtrados.add(j);
                            }
                            case "genero" -> {
                                if (j.getGenero().equalsIgnoreCase(valor))
                                    filtrados.add(j);
                            }
                            case "ano" -> {
                                try {
                                    int anoFiltro = Integer.parseInt(valor);
                                    if (j.getAno() == anoFiltro)
                                        filtrados.add(j);
                                } catch (NumberFormatException ex) {
                                    mostrarAlerta("Erro", "Digite um ano válido!");
                                    return;
                                }
                            }
                        }
                    }

                    jogosList.setAll(filtrados);

                    mostrarAlerta("Filtro aplicado",
                            "Mostrando apenas jogos com " + criterio + " = " + valor +
                                    " (" + filtrados.size() + " encontrados)");

                    System.out.println("Filtro ativo: " + criterio + " = " + valor);
                });
            });
        });



        stage.setScene(new Scene(layout, 900, 400));
        stage.show();
    }

    private void atualizarLista() {
        jogosList.setAll(tabela.exportar());
    }

    private void limparCampos(TextField... campos) {
        for (TextField f : campos) f.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
