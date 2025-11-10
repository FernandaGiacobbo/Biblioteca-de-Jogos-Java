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
        HBox campos = new HBox(10, tituloField, generoField, anoField, btnInserir, btnRemover, btnBuscar, btnOrdenar);
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
            ChoiceDialog<String> dialog = new ChoiceDialog<>("titulo", "titulo", "genero", "ano");
            dialog.setTitle("Ordenar Jogos");
            dialog.setHeaderText("Escolha o critério de ordenação:");
            dialog.setContentText("Critério:");

            dialog.showAndWait().ifPresent(criterio -> {
                Jogo[] jogos = tabela.exportar();
                if (jogos.length == 0) {
                    mostrarAlerta("Aviso", "Nenhum jogo cadastrado!");
                    return;
                }

                switch (criterio) {
                    case "titulo" -> Ordenacao.quickSort(jogos, 0, jogos.length - 1, "titulo");
                    case "genero" -> Ordenacao.insertionSort(jogos, "genero");
                    case "ano" -> Ordenacao.quickSort(jogos, 0, jogos.length - 1, "ano");
                }

                jogosList.setAll(jogos);
                mostrarAlerta("Ordenado", "Jogos ordenados por " + criterio + "!");
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
