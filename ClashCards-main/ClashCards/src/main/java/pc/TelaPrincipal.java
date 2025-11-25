package pc;

import classes.Baralho;
import classes.Carta;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaPrincipal {

    private Baralho baralho;

    public TelaPrincipal(Baralho baralho) {
        this.baralho = baralho;
    }

    public void mostrar(Stage stage) {

        BorderPane root = new BorderPane();

        MenuBar menuBar = new MenuBar();

        Menu menuFerramentas = new Menu("Ferramentas");

        MenuItem miOrdenar = new MenuItem("Ordenar Cartas");
        MenuItem miFiltrar = new MenuItem("Filtrar Cartas");
        MenuItem miComparar = new MenuItem("Comparar Cartas");

        miOrdenar.setOnAction(e -> mostrarMsg());
        miFiltrar.setOnAction(e -> mostrarMsg());
        miComparar.setOnAction(e -> mostrarMsg());

        menuFerramentas.getItems().addAll(miOrdenar, miFiltrar, miComparar);
        menuBar.getMenus().add(menuFerramentas);

        root.setTop(menuBar);

        TabPane tabPane = new TabPane();

        Tab tabMontarDeck = new Tab("Montar Deck");
        tabMontarDeck.setClosable(false);

        VBox telaDeck = new VBox(10);
        telaDeck.setPadding(new Insets(20));
        telaDeck.setAlignment(Pos.CENTER);

        Label lblTituloDeck = new Label("Montagem do Deck");
        lblTituloDeck.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label lblCustoMedio = new Label("Custo Médio do Elixir: " +
                String.format("%.1f", baralho.getCustoMedioElixir()));

        Button btnAdicionarCarta = new Button("Adicionar Carta ao Deck");
        btnAdicionarCarta.setOnAction(e -> mostrarMsg());

        telaDeck.getChildren().addAll(lblTituloDeck, lblCustoMedio, btnAdicionarCarta);
        tabMontarDeck.setContent(telaDeck);

        Tab tabColecao = new Tab("Coleção");
        tabColecao.setClosable(false);

        VBox telaColecao = new VBox(10);
        telaColecao.setPadding(new Insets(20));

        Label lblTituloColecao = new Label("Cartas Cadastradas");
        lblTituloColecao.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ListView<Carta> listaCartas = new ListView<>();
        listaCartas.getItems().addAll(baralho.getCartas());

        telaColecao.getChildren().addAll(lblTituloColecao, listaCartas);
        tabColecao.setContent(telaColecao);

        tabPane.getTabs().addAll(tabMontarDeck, tabColecao);

        root.setCenter(tabPane);


        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setTitle("Clash Royale - Projeto POO");
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarMsg() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Função Indisponível");
        alerta.setContentText("Em desenvolvimento");
        alerta.showAndWait();
    }
}
