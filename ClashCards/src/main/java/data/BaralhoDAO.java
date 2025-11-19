package data;

import classes.Baralho;
import classes.Carta;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaralhoDAO {
    private final File pasta;
    private final CartaDAO cartaDAO;
    private final String ARQUIVO_DECKS = "decks.csv";

    public BaralhoDAO(String caminhoPasta, CartaDAO dao) {
        this.pasta = new File(caminhoPasta);
        this.cartaDAO = dao;
        if (!this.pasta.exists()) {
            this.pasta.mkdirs();
        }
    }

    /**
     * Carrega todos os Baralhos do arquivo centralizado 'decks.csv'.
     */
    public List<Baralho> carregarBaralhos() {
        List<Baralho> baralhos = new ArrayList<>();
        File arquivo = new File(this.pasta, ARQUIVO_DECKS);

        if (!arquivo.exists()) {
            return baralhos;
        }

        this.lerArquivoCSV(arquivo, baralhos);

        System.out.println("✅ " + baralhos.size() + " baralhos carregados.");
        return baralhos;
    }

    /**
     * Lógica de leitura de arquivo (mantendo o padrão BufferedReader do CartaDAO).
     */
    private void lerArquivoCSV(File arquivo, List<Baralho> baralhos) {
        List<Carta> todasCartas = cartaDAO.carregarCartas(); // Carrega cartas para resolver as referências

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine(); // Pula o cabeçalho

            String linha;
            while((linha = br.readLine()) != null) {
                String[] nomesCartas = linha.split(",");

                if (nomesCartas.length == 8) {
                    Baralho baralho = this.montarBaralho(nomesCartas, todasCartas);
                    if (baralho != null) {
                        baralhos.add(baralho);
                    }
                }
            }
        } catch (IOException var9) {
            System.err.println("Erro ao ler arquivo de decks: " + var9.getMessage());
        }
    }

    /**
     * Monta o objeto Baralho a partir dos nomes das cartas (IDs) e da coleção principal.
     */
    private Baralho montarBaralho(String[] nomesCartas, List<Carta> todasCartas) {
        Baralho baralho = new Baralho();

        for (String nome : nomesCartas) {
            Carta cartaEncontrada = todasCartas.stream()
                    .filter(c -> c.getNome().equalsIgnoreCase(nome.trim()))
                    .findFirst()
                    .orElse(null);

            if (cartaEncontrada != null) {
                baralho.adicionarCarta(cartaEncontrada);
            } else {
                System.err.println("Carta '" + nome + "' referenciada no deck não foi encontrada na coleção. Deck pode estar incompleto.");
            }
        }
        return baralho;
    }

    /**
     * Salva a lista completa de Baralhos para o arquivo decks.csv.
     */
    public void salvarTodosBaralhos(List<Baralho> baralhos) {
        File arquivoDecks = new File(this.pasta, ARQUIVO_DECKS);

        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivoDecks, false))) {
            pw.println("CARTA_1,CARTA_2,CARTA_3,CARTA_4,CARTA_5,CARTA_6,CARTA_7,CARTA_8");

            for (Baralho baralho : baralhos) {
                pw.println(this.formatarBaralhoParaCSV(baralho));
            }

            System.out.println("✅ " + baralhos.size() + " baralhos salvos com sucesso.");

        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar baralhos: " + e.getMessage());
        }
    }

    /**
     * Converte um objeto Baralho em uma linha formatada para CSV.
     */
    private String formatarBaralhoParaCSV(Baralho baralho) {
        return baralho.getCartas().stream()
                .map(Carta::getNome)
                .collect(Collectors.joining(","));
    }
}