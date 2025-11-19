package data;

import classes.Carta;
import classes.Raridade;
import classes.TipoCarta;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartaDAO {
    private final File pasta;
    private static final String ARQUIVO_PRINCIPAL = "cartas.csv";

    public CartaDAO(String caminhoPasta) {
        this.pasta = new File(caminhoPasta);
    }

    public List<Carta> carregarCartas() {
        List<Carta> cartas = new ArrayList<>();

        File principal = new File(this.pasta, ARQUIVO_PRINCIPAL);

        if (principal.exists()) {
            this.lerArquivoCSV(principal, cartas);
        } else {
            File[] arquivos = this.pasta.listFiles((dir, nome) -> nome.endsWith(".csv"));
            if (arquivos != null) {
                for(File arquivo : arquivos) {
                    this.lerArquivoCSV(arquivo, cartas);
                }
            }
        }
        return cartas;
    }

    private void lerArquivoCSV(File arquivo, List<Carta> cartas) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine();

            String linha;
            while((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] att = linha.split(",");
                int len = att.length;

                if (len >= 9) {

                    TipoCarta tipo;
                    Raridade raridade;

                    if (len >= 11) {

                        tipo = TipoCarta.valueOf(att[9].trim().toUpperCase());
                        raridade = Raridade.valueOf(att[10].trim().toUpperCase());
                    } else {

                        tipo = TipoCarta.TROPA;
                        raridade = Raridade.COMUM;
                    }

                    Carta carta = new Carta(att[0].trim(),
                            Integer.parseInt(att[1].trim()),
                            Integer.parseInt(att[2].trim()),
                            Integer.parseInt(att[3].trim()),
                            Integer.parseInt(att[4].trim()),
                            Integer.parseInt(att[5].trim()),
                            Double.parseDouble(att[6].trim()),
                            Double.parseDouble(att[7].trim()),
                            Double.parseDouble(att[8].trim()),
                            tipo,
                            raridade
                    );
                    cartas.add(carta);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("❌ Erro ao ler arquivo " + arquivo.getName() + ": Linha ignorada devido a erro de formato ou dado inválido. Detalhes: " + e.getMessage());
        }
    }

    public void salvarCarta(Carta novaCarta) {
        List<Carta> todasCartas = this.carregarCartas();

        boolean atualizado = false;

        for (int i = 0; i < todasCartas.size(); i++) {
            if (todasCartas.get(i).getNome().equalsIgnoreCase(novaCarta.getNome())) {
                todasCartas.set(i, novaCarta);
                atualizado = true;
                break;
            }
        }

        if (!atualizado) {
            todasCartas.add(novaCarta);
        }

        this.escreverArquivoCSV(todasCartas);
    }

    public boolean excluirCarta(String nomeCarta) {
        List<Carta> todasCartas = this.carregarCartas();
        boolean removido = todasCartas.removeIf(c -> c.getNome().equalsIgnoreCase(nomeCarta));

        if (removido) {
            this.escreverArquivoCSV(todasCartas);
        }

        return removido;
    }

    private void escreverArquivoCSV(List<Carta> cartas) {
        File arquivo = new File(this.pasta, ARQUIVO_PRINCIPAL);

        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, false))) {

            pw.println("NOME,ELIXIR,NIVEL,DANO,VIDA,VELOCIDADE,DPS,ALCANCE,VEL_ATK,TIPO,RARIDADE");

            for (Carta c : cartas) {

                pw.println(String.format(Locale.US,
                        "%s,%d,%d,%d,%d,%d,%.1f,%.1f,%.1f,%s,%s",
                        c.getNome(),
                        c.getCustoElixir(),
                        c.getNivel(),
                        c.getDano(),
                        c.getVida(),
                        c.getVelocidade(),
                        c.getDanoPSegundo(),
                        c.getAlcance(),
                        c.getVelocidadeImpacto(),
                        c.getTipo().name(),
                        c.getRaridade().name()
                ));
            }
        } catch (IOException var9) {
            System.err.println("❌ Erro ao salvar/atualizar o arquivo de cartas: " + var9.getMessage());
        }
    }
}