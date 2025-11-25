package pc;

import classes.Carta;
import classes.Baralho;
import data.CartaDAO;
import data.DeckDAO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class App extends Application {

    private final String PASTA = "C:\\Users\\samue\\OneDrive\\Área de Trabalho\\POO";

    @Override
    public void start(Stage stage) {

        File f = new File(PASTA, "imagens");
        if (!f.exists()) f.mkdirs();

        CartaDAO cartaDAO = new CartaDAO(PASTA);
        List<Carta> cartas = cartaDAO.carregarTodas();

        DeckDAO deckDAO = new DeckDAO(PASTA);
        List<Baralho> decks = deckDAO.carregarDecks(cartas);

        TabPane tabs = new TabPane();

        Tab t1 = new Tab("Cartas");
        t1.setClosable(false);
        t1.setContent(new TelaCadastroCarta(PASTA, cartas, cartaDAO).getLayout());

        Tab t2 = new Tab("Coleção");
        t2.setClosable(false);
        t2.setContent(new TelaColecao(PASTA, cartas, cartaDAO).getLayout());

        Tab t3 = new Tab("Decks");
        t3.setClosable(false);
        t3.setContent(new TelaDecks(PASTA, deckDAO, decks).getLayout());

        Tab t4 = new Tab("Montar Deck");
        t4.setClosable(false);
        t4.setContent(new TelaMontarDeck(PASTA, cartas, deckDAO).getLayout());

        tabs.getTabs().addAll(t1,t2,t3,t4);

        stage.setScene(new Scene(tabs, 1100, 700));
        stage.setTitle("ClashCards - Projeto POO");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



