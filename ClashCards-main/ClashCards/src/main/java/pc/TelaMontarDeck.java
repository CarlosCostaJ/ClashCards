package pc;

import classes.Baralho;
import classes.Carta;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class TelaMontarDeck {

    private Baralho baralho = new Baralho();
    private Label labelCusto;
    private GridPane gridDeck;

    public void mostrar(Stage stage, List<Carta> cartasDisponiveis) {

        ListView<Carta> listView = new ListView<>();
        listView.getItems().addAll(cartasDisponiveis);
        listView.setPrefWidth(250);

        listView.setOnMouseClicked(event -> {
            Carta selecionada = listView.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                adicionarCarta(selecionada);
            }
        });

        gridDeck = new GridPane();
        gridDeck.setHgap(10);
        gridDeck.setVgap(10);
        gridDeck.setAlignment(Pos.CENTER);

        labelCusto = new Label("Custo médio: 0.0");

        Button btnLimpar = new Button("Limpar Deck");
        btnLimpar.setOnAction(e -> {
            baralho.getCartas().clear();
            atualizarDeck();
        });

        HBox areaCentral = new HBox(25, listView, gridDeck);
        areaCentral.setAlignment(Pos.CENTER);

        VBox root = new VBox(20, areaCentral, labelCusto, btnLimpar);
        root.setAlignment(Pos.CENTER);

        Scene cena = new Scene(root, 800, 500);
        stage.setScene(cena);
        stage.show();

        atualizarDeck();
    }


    private void adicionarCarta(Carta carta) {
        if (baralho.getCartas().size() >= 8) {
            mostrarAviso("O deck já tem 8 cartas!");
            return;
        }

        if (baralho.getCartas().contains(carta)) {
            mostrarAviso("Essa carta já foi adicionada!");
            return;
        }

        baralho.adicionarCarta(carta);
        atualizarDeck();
    }

    private void removerCarta(int index) {
        if (index < baralho.getCartas().size()) {
            baralho.getCartas().remove(index);
            atualizarDeck();
        }
    }

    private void atualizarDeck() {
        gridDeck.getChildren().clear();

        for (int i = 0; i < 8; i++) {

            Label slot = new Label();
            slot.setPrefSize(120, 120);
            slot.setStyle("-fx-border-color: black; -fx-background-color: #eeeeee;");
            slot.setAlignment(Pos.CENTER);

            if (i < baralho.getCartas().size()) {
                Carta c = baralho.getCartas().get(i);
                slot.setText(c.getNome());
            } else {
                slot.setText("Vazio");
            }

            final int index = i;
            slot.setOnMouseClicked(e -> removerCarta(index));

            gridDeck.add(slot, i % 4, i / 4);
        }

        double custo = baralho.getCustoMedioElixir();
        labelCusto.setText("Custo médio: " + String.format("%.1f", custo));
    }

    private void mostrarAviso(String texto) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.showAndWait();
    }
}