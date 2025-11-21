package pc;

import data.CartaDAO;
import classes.Carta;
import classes.Baralho;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {

    private List<Carta> cartas;
    private Baralho baralho = new Baralho();

    private Label[] slots = new Label[8];

    @Override
    public void start(Stage stage) {

        CartaDAO dao = new CartaDAO("C:\\Users\\samue\\OneDrive\\Área de Trabalho\\POO");
        List<Carta> cartas = dao.carregarCartas();

        TelaMontarDeck tela = new TelaMontarDeck();
        tela.mostrar(stage, cartas);
    }
}


