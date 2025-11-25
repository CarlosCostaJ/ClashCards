package pc;

import classes.Baralho;
import classes.Carta;
import data.DeckDAO;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;

public class TelaMontarDeck {

    private VBox root;
    private Baralho deck = new Baralho();
    private GridPane gridDeck;
    private Label lblCusto;
    private List<Carta> todasCartas;
    private DeckDAO deckDAO;
    private String pastaProjeto;

    public TelaMontarDeck(String pastaProjeto, List<Carta> todasCartas, DeckDAO deckDAO) {
        this.pastaProjeto = pastaProjeto;
        this.todasCartas = todasCartas;
        this.deckDAO = deckDAO;
        criarLayout();
        atualizarDeck();
    }

    public VBox getLayout() { return root; }

    private void criarLayout() {
        root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        ListView<Carta> lv = new ListView<>();
        lv.getItems().addAll(todasCartas);
        lv.setPrefWidth(250);

        lv.setOnMouseClicked(e -> {
            Carta sel = lv.getSelectionModel().getSelectedItem();
            if (sel != null) {
                boolean ok = deck.adicionarCarta(sel);
                if (!ok) {
                    new Alert(Alert.AlertType.WARNING, "Não é possível adicionar (repetida ou deck cheio)").showAndWait();
                }
                atualizarDeck();
            }
        });

        gridDeck = new GridPane();
        gridDeck.setHgap(8); gridDeck.setVgap(8);

        lblCusto = new Label("Custo médio: 0.0");

        Button btnLimpar = new Button("Limpar");
        Button btnSalvar = new Button("Salvar Deck");

        btnLimpar.setOnAction(e -> { deck.limpar(); atualizarDeck(); });

        btnSalvar.setOnAction(e -> {
            if (!deck.isCompleto()) {
                new Alert(Alert.AlertType.WARNING, "Deck precisa ter exatamente 8 cartas").showAndWait();
                return;
            }
            TextInputDialog td = new TextInputDialog("MeuDeck");
            td.setTitle("Salvar Deck");
            td.setHeaderText("Nome do deck");
            td.showAndWait().ifPresent(nome -> {
                deck.setNome(nome);
                List<Baralho> lista = new ArrayList<>();
                lista.add(deck);
                deckDAO.salvarDecks(lista);
                new Alert(Alert.AlertType.INFORMATION, "Deck salvo").showAndWait();
            });
        });

        HBox center = new HBox(12, lv, gridDeck);
        center.setAlignment(Pos.CENTER);

        HBox botoes = new HBox(8, btnSalvar, btnLimpar);
        botoes.setAlignment(Pos.CENTER);

        root.getChildren().addAll(center, lblCusto, botoes);
    }

    private void atualizarDeck() {
        gridDeck.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            Label slot = new Label();
            slot.setPrefSize(110, 80);
            slot.setStyle("-fx-border-color:black; -fx-background-color:#f3f3f3;");
            slot.setAlignment(Pos.CENTER);
            if (i < deck.getCartas().size()) {
                Carta c = deck.getCartas().get(i);
                slot.setText(c.getNome());
                final int idx = i;
                slot.setOnMouseClicked(ev -> {
                    deck.removerCarta(deck.getCartas().get(idx));
                    atualizarDeck();
                });
            } else {
                slot.setText("Vazio");
            }
            gridDeck.add(slot, i % 4, i / 4);
        }
        lblCusto.setText(String.format("Custo médio: %.1f", deck.getCustoMedioElixir()));
    }
}
