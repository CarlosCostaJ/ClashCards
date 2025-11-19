package pc;

import data.CartaDAO;
import classes.Carta;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        CartaDAO dao = new CartaDAO("C:\\Users\\samue\\OneDrive\\Área de Trabalho\\POO");

        List<Carta> cartas = dao.carregarCartas();

        for (Carta c : cartas) {
            System.out.println(c);
        }

        Label label = new Label("Foram carregadas " + cartas.size() + " cartas.");
        Scene scene = new Scene(new StackPane(label), 640, 480);

        stage.setTitle("Projeto Clash Royale");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

