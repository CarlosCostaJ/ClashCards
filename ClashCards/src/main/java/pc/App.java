package pc;

import classes.Carta;
import data.CartaDAO;
import classes.Raridade; // NOVO IMPORT
import classes.TipoCarta; // NOVO IMPORT

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {

    private static final String PASTA_DADOS = "C:\\Users\\carlo\\OneDrive\\Documentos\\GitHub\\ClashCards";
    private final CartaDAO dao = new CartaDAO(PASTA_DADOS);

    private ObservableList<Carta> cartasObservable;
    private TableView<Carta> colecaoTable;
    private Carta cartaSendoEditada = null;
    private TextField nomeField, elixirField, nivelField, danoField, vidaField, velocidadeField, dpsField, alcanceField, velAtkField;
    private ComboBox<TipoCarta> tipoComboBox;
    private ComboBox<Raridade> raridadeComboBox;
    private Button salvarButton, excluirButton, novoButton;
    private Label mensagemLabel;

    @Override
    public void start(Stage stage) {

        List<Carta> listaCartas = dao.carregarCartas();
        cartasObservable = FXCollections.observableArrayList(listaCartas);

        TabPane tabPane = new TabPane();

        colecaoTable = criarColecaoView(cartasObservable);

        BorderPane cadastroView = criarCadastroView();

        colecaoTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                carregarCartaParaEdicao(newSelection);
                tabPane.getSelectionModel().select(0);
            }
        });

        Tab cadastroTab = new Tab("Cartas (Cadastro)", cadastroView);
        cadastroTab.setClosable(false);

        Tab colecaoTab = new Tab("Coleção", colecaoTable);
        colecaoTab.setClosable(false);

        Tab decksTab = new Tab("Decks", new Label("Gerenciamento de Decks - Em desenvolvimento"));
        decksTab.setClosable(false);

        tabPane.getTabs().addAll(cadastroTab, colecaoTab, decksTab);

        BorderPane root = new BorderPane(tabPane);
        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Clash Cards - Gerenciador de Decks");
        stage.setScene(scene);
        stage.show();
    }

    private void carregarCartaParaEdicao(Carta carta) {
        this.cartaSendoEditada = carta;

        nomeField.setText(carta.getNome());
        nomeField.setDisable(true);

        elixirField.setText(String.valueOf(carta.getCustoElixir()));
        nivelField.setText(String.valueOf(carta.getNivel()));
        danoField.setText(String.valueOf(carta.getDano()));
        vidaField.setText(String.valueOf(carta.getVida()));
        velocidadeField.setText(String.valueOf(carta.getVelocidade()));
        dpsField.setText(String.valueOf(carta.getDanoPSegundo()));
        alcanceField.setText(String.valueOf(carta.getAlcance()));
        velAtkField.setText(String.valueOf(carta.getVelocidadeImpacto()));

        tipoComboBox.setValue(carta.getTipo());
        raridadeComboBox.setValue(carta.getRaridade());

        salvarButton.setText("Atualizar Carta");
        excluirButton.setDisable(false);
        mensagemLabel.setText("Modifique os dados e clique em Atualizar.");
        mensagemLabel.setStyle("-fx-text-fill: orange;");
    }

    private void limparFormulario() {
        this.cartaSendoEditada = null;

        nomeField.clear(); elixirField.clear(); nivelField.clear(); danoField.clear();
        vidaField.clear(); velocidadeField.clear(); dpsField.clear(); alcanceField.clear(); velAtkField.clear();

        tipoComboBox.setValue(null);
        raridadeComboBox.setValue(null);

        nomeField.setDisable(false);
        salvarButton.setText("Salvar Nova Carta");
        excluirButton.setDisable(true);
        colecaoTable.getSelectionModel().clearSelection();
        mensagemLabel.setText("Insira os dados da nova carta.");
        mensagemLabel.setStyle("-fx-text-fill: black;");
    }

    private BorderPane criarCadastroView() {
        BorderPane painelPrincipal = new BorderPane();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        nomeField = new TextField();
        elixirField = new TextField();
        nivelField = new TextField();
        danoField = new TextField();
        vidaField = new TextField();
        velocidadeField = new TextField();
        dpsField = new TextField();
        alcanceField = new TextField();
        velAtkField = new TextField();
        tipoComboBox = new ComboBox<>(FXCollections.observableArrayList(TipoCarta.values()));
        raridadeComboBox = new ComboBox<>(FXCollections.observableArrayList(Raridade.values()));

        mensagemLabel = new Label("Insira os dados da nova carta.");

        int row = 0;
        grid.addRow(row++, new Label("Nome:"), nomeField);
        grid.addRow(row++, new Label("Custo Elixir (int):"), elixirField);
        grid.addRow(row++, new Label("Nível (int):"), nivelField);
        grid.addRow(row++, new Label("Dano (int):"), danoField);
        grid.addRow(row++, new Label("Vida (int):"), vidaField);
        grid.addRow(row++, new Label("Velocidade (int):"), velocidadeField);
        grid.addRow(row++, new Label("DPS (double):"), dpsField);
        grid.addRow(row++, new Label("Alcance (double):"), alcanceField);
        grid.addRow(row++, new Label("Velocidade ATK (double):"), velAtkField);
        grid.addRow(row++, new Label("Tipo:"), tipoComboBox);
        grid.addRow(row++, new Label("Raridade:"), raridadeComboBox);

        salvarButton = new Button("Salvar Nova Carta");
        excluirButton = new Button("Excluir Carta");
        novoButton = new Button("Limpar / Novo");

        salvarButton.setMinWidth(150);
        excluirButton.setMinWidth(150);
        novoButton.setMinWidth(150);
        excluirButton.setDisable(true);

        HBox buttonBox = new HBox(10, salvarButton, novoButton, excluirButton);
        grid.add(buttonBox, 0, row, 2, 1);

        grid.add(mensagemLabel, 0, row + 1, 2, 1);

        salvarButton.setOnAction(e -> {
            try {
                String nome = nomeField.getText().trim();
                int elixir = Integer.parseInt(elixirField.getText());
                int nivel = Integer.parseInt(nivelField.getText());
                int dano = Integer.parseInt(danoField.getText());
                int vida = Integer.parseInt(vidaField.getText());
                int velocidade = Integer.parseInt(velocidadeField.getText());
                double dps = Double.parseDouble(dpsField.getText());
                double alcance = Double.parseDouble(alcanceField.getText());
                double velAtk = Double.parseDouble(velAtkField.getText());

                TipoCarta tipo = tipoComboBox.getValue();
                Raridade raridade = raridadeComboBox.getValue();

                if (tipo == null || raridade == null) {
                    mensagemLabel.setText("❌ Erro: Selecione o Tipo e a Raridade da carta.");
                    mensagemLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                Carta novaCarta = new Carta(nome, elixir, nivel, dano, vida, velocidade, dps, alcance, velAtk, tipo, raridade);

                dao.salvarCarta(novaCarta);

                if (cartaSendoEditada != null) {
                    cartasObservable.set(cartasObservable.indexOf(cartaSendoEditada), novaCarta);
                    mensagemLabel.setText("✅ Carta '" + nome + "' atualizada com sucesso!");
                } else {
                    cartasObservable.add(novaCarta);
                    mensagemLabel.setText("✅ Carta '" + nome + "' salva com sucesso!");
                    limparFormulario();
                }

                mensagemLabel.setStyle("-fx-text-fill: green;");

            } catch (NumberFormatException ex) {
                mensagemLabel.setText("❌ Erro: Verifique se todos os campos numéricos estão corretos.");
                mensagemLabel.setStyle("-fx-text-fill: red;");
            } catch (Exception ex) {
                mensagemLabel.setText("❌ Erro de Persistência: " + ex.getMessage());
                mensagemLabel.setStyle("-fx-text-fill: red;");
            }
        });

        excluirButton.setOnAction(e -> {
            if (cartaSendoEditada != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja excluir a carta '" + cartaSendoEditada.getNome() + "'?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Confirmar Exclusão");
                alert.setHeaderText(null);

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        dao.excluirCarta(cartaSendoEditada.getNome());
                        cartasObservable.remove(cartaSendoEditada);
                        mensagemLabel.setText("🗑️ Carta '" + cartaSendoEditada.getNome() + "' excluída com sucesso.");
                        mensagemLabel.setStyle("-fx-text-fill: darkred;");
                        limparFormulario();
                    }
                });
            }
        });

        novoButton.setOnAction(e -> limparFormulario());

        limparFormulario();

        painelPrincipal.setCenter(grid);
        return painelPrincipal;
    }

    private TableView<Carta> criarColecaoView(ObservableList<Carta> cartas) {
        TableView<Carta> tableView = new TableView<>();
        tableView.setItems(cartas);

        TableColumn<Carta, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nomeCol.setMinWidth(150);

        TableColumn<Carta, Integer> elixirCol = new TableColumn<>("Elixir");
        elixirCol.setCellValueFactory(new PropertyValueFactory<>("custoElixir"));
        elixirCol.setMaxWidth(60);

        TableColumn<Carta, Integer> nivelCol = new TableColumn<>("Nível");
        nivelCol.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        nivelCol.setMaxWidth(60);

        TableColumn<Carta, Integer> danoCol = new TableColumn<>("Dano");
        danoCol.setCellValueFactory(new PropertyValueFactory<>("dano"));

        TableColumn<Carta, Integer> vidaCol = new TableColumn<>("Vida");
        vidaCol.setCellValueFactory(new PropertyValueFactory<>("vida"));

        TableColumn<Carta, Integer> velocidadeCol = new TableColumn<>("Velocidade");
        velocidadeCol.setCellValueFactory(new PropertyValueFactory<>("velocidade"));

        TableColumn<Carta, Double> dpsCol = new TableColumn<>("DPS");
        dpsCol.setCellValueFactory(new PropertyValueFactory<>("danoPSegundo"));

        TableColumn<Carta, Double> alcanceCol = new TableColumn<>("Alcance");
        alcanceCol.setCellValueFactory(new PropertyValueFactory<>("alcance"));

        TableColumn<Carta, Double> velAtkCol = new TableColumn<>("Vel. ATK");
        velAtkCol.setCellValueFactory(new PropertyValueFactory<>("velocidadeImpacto"));

        // NOVAS COLUNAS
        TableColumn<Carta, TipoCarta> tipoCol = new TableColumn<>("Tipo");
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tipoCol.setMinWidth(80);

        TableColumn<Carta, Raridade> raridadeCol = new TableColumn<>("Raridade");
        raridadeCol.setCellValueFactory(new PropertyValueFactory<>("raridade"));
        raridadeCol.setMinWidth(100);

        tableView.getColumns().addAll(
                nomeCol, elixirCol, nivelCol, danoCol, vidaCol, velocidadeCol, dpsCol,
                alcanceCol, velAtkCol, tipoCol, raridadeCol
        );

        return tableView;
    }


    public static void main(String[] args) {
        launch(args);
    }
}