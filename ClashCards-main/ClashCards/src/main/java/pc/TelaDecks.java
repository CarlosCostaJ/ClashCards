package pc;

import classes.Baralho;
import data.DeckDAO;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.util.ArrayList;
import java.util.List;

public class TelaDecks {

    private BorderPane root;
    private DeckDAO deckDAO;
    private List<Baralho> listaDecks;
    private String pastaProjeto;

    public TelaDecks(String pastaProjeto, DeckDAO deckDAO, List<Baralho> listaDecks) {
        this.pastaProjeto = pastaProjeto;
        this.deckDAO = deckDAO;
        this.listaDecks = listaDecks;
        criarLayout();
    }

    public BorderPane getLayout() { return root; }

    private void criarLayout() {
        root = new BorderPane();
        ListView<String> lv = new ListView<>();
        for (Baralho d : listaDecks) lv.getItems().add(d.getNome());

        Button btnNovo = new Button("Novo");
        Button btnExcluir = new Button("Excluir");

        btnNovo.setOnAction(e -> {

            new Alert(Alert.AlertType.INFORMATION, "Abra a aba Montar Deck para criar e salvar decks").showAndWait();
        });

        btnExcluir.setOnAction(e -> {
            String sel = lv.getSelectionModel().getSelectedItem();
            if (sel != null) {
                listaDecks.removeIf(d -> d.getNome().equalsIgnoreCase(sel));
                deckDAO.salvarDecks(listaDecks);
                lv.getItems().remove(sel);
            }
        });

        root.setCenter(lv);
        root.setBottom(new javafx.scene.layout.HBox(8, btnNovo, btnExcluir));
        BorderPane.setMargin(root.getBottom(), new Insets(8));
    }
}
