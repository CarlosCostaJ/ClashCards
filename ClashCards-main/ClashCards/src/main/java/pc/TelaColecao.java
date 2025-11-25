package pc;

import classes.Carta;
import data.CartaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class TelaColecao {

    private BorderPane root;
    private CartaDAO dao;
    private List<Carta> lista;

    public TelaColecao(String pastaProjeto, List<Carta> lista, CartaDAO dao) {
        this.dao = dao;
        this.lista = lista;
        criarLayout(pastaProjeto);
    }

    public BorderPane getLayout() { return root; }

    private void criarLayout(String pastaProjeto) {
        root = new BorderPane();
        ObservableList<Carta> dados = FXCollections.observableArrayList(lista);

        TableView<Carta> tabela = new TableView<>(dados);
        TableColumn<Carta, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
        TableColumn<Carta, Integer> colElixir = new TableColumn<>("Elixir");
        colElixir.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getCustoElixir()).asObject());
        TableColumn<Carta, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTipo().name()));
        TableColumn<Carta, String> colRar = new TableColumn<>("Raridade");
        colRar.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getRaridade().name()));

        tabela.getColumns().addAll(colNome, colElixir, colTipo, colRar);

        Button btnDetalhes = new Button("Detalhes");
        Button btnUsar = new Button("Usar");
        Button btnExcluir = new Button("Excluir");
        HBox botoes = new HBox(8, btnDetalhes, btnUsar, btnExcluir);

        btnDetalhes.setOnAction(e -> {
            Carta sel = tabela.getSelectionModel().getSelectedItem();
            if (sel != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Nome: ").append(sel.getNome()).append("\n");
                sb.append("Nível: ").append(sel.getNivel()).append("\n");
                sb.append("Elixir: ").append(sel.getCustoElixir()).append("\n");
                sb.append("Tipo: ").append(sel.getTipo()).append("\n");
                sb.append("Raridade: ").append(sel.getRaridade()).append("\n");
                sb.append("Dano: ").append(sel.getDano()).append("\n");
                sb.append("DPS: ").append(sel.getDanoPorSegundo()).append("\n");
                sb.append("Vida: ").append(sel.getPontosVida()).append("\n");
                sb.append("Alvos: ").append(sel.getAlvos()).append("\n");
                sb.append("Alcance: ").append(sel.getAlcance()).append("\n");
                sb.append("Velocidade: ").append(sel.getVelocidade()).append("\n");
                sb.append("Vel impacto: ").append(sel.getVelocidadeImpacto()).append("\n");
                sb.append("Imagem: ").append(sel.getCaminhoImagem()).append("\n");
                Alert info = new Alert(Alert.AlertType.INFORMATION, sb.toString());
                info.setHeaderText(sel.getNome());
                info.showAndWait();
            }
        });

        btnExcluir.setOnAction(e -> {
            Carta sel = tabela.getSelectionModel().getSelectedItem();
            if (sel != null) {
                Optional<ButtonType> res = new Alert(Alert.AlertType.CONFIRMATION, "Excluir " + sel.getNome() + "?").showAndWait();
                if (res.isPresent() && res.get() == ButtonType.OK) {
                    lista.remove(sel);
                    dao.salvarTodas(lista);
                    tabela.setItems(FXCollections.observableArrayList(lista));
                }
            }
        });

        btnUsar.setOnAction(e -> {
            new Alert(Alert.AlertType.INFORMATION, "Use: selecionar um deck na aba Decks e depois adicionar esta carta (em desenvolvimento)").showAndWait();
        });

        VBox v = new VBox(8, tabela, botoes);
        v.setPadding(new Insets(8));
        root.setCenter(v);
    }
}
