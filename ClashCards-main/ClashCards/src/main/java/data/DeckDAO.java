package data;

import classes.Baralho;
import classes.Carta;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DeckDAO {

    private final File pasta;
    private final File arquivo;
    private final String ARQUIVO = "decks.csv";
    private final String SEP = ";";

    public DeckDAO(String caminhoPasta) {
        this.pasta = new File(caminhoPasta);
        if (!pasta.exists()) pasta.mkdirs();
        this.arquivo = new File(pasta, ARQUIVO);
    }

    public List<Baralho> carregarDecks(List<Carta> todasCartas) {
        List<Baralho> decks = new ArrayList<>();
        if (!arquivo.exists()) return decks;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] p = linha.split(SEP, -1);
                if (p.length < 2) continue;
                String nomeDeck = p[0];
                Baralho deck = new Baralho(nomeDeck);
                for (int i = 1; i < p.length; i++) {
                    String nomeCarta = p[i];

                    for (Carta c : todasCartas) {
                        if (c.getNome().equalsIgnoreCase(nomeCarta.trim())) {
                            deck.adicionarCarta(c);
                            break;
                        }
                    }
                }
                decks.add(deck);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decks;
    }

    public void salvarDecks(List<Baralho> decks) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo), StandardCharsets.UTF_8))) {
            for (Baralho deck : decks) {
                StringBuilder sb = new StringBuilder();
                sb.append(deck.getNome());
                for (Carta c : deck.getCartas()) {
                    sb.append(SEP).append(c.getNome());
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
