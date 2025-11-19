package data;

import classes.Carta;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class CartaDAO {
    private File pasta;

    public CartaDAO(String caminhoPasta) {
        this.pasta = new File(caminhoPasta);
    }

    public List<Carta> carregarCartas() {
        List<Carta> cartas = new ArrayList<>();

        File[] arquivos = pasta.listFiles((dir, nome) -> nome.endsWith(".csv"));
        if (arquivos == null) return cartas;

        for (File arquivo : arquivos) {
            lerArquivoCSV(arquivo, cartas);
        }

        return cartas;
    }

    private void lerArquivoCSV(File arquivo, List<Carta> cartas) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {

            br.readLine();
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] att = linha.split(",");

                Carta carta = new Carta(
                        att[0],
                        Integer.parseInt(att[1]),
                        Integer.parseInt(att[2]),
                        Integer.parseInt(att[3]),
                        Integer.parseInt(att[4]),
                        Integer.parseInt(att[5]),
                        Double.parseDouble(att[6]),
                        Double.parseDouble(att[7]),
                        Double.parseDouble(att[8])
                );

                cartas.add(carta);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: ");
        }
    }

    public void salvarCarta(Carta carta) {
        File arquivo = new File(pasta, "cartas.csv");

        boolean existe = arquivo.exists();

        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, true))) {

            if (!existe) {
                pw.println("NOME,ELIXIR,NIVEL,DANO,VIDA,VELOCIDADE,DPS,ALCANCE,VEL_ATK");
            }

            pw.println(
                    carta.getNome() + "," +
                            carta.getCustoElixir() + "," +
                            carta.getNivel() + "," +
                            carta.getDano() + "," +
                            carta.getVida() + "," +
                            carta.getVelocidade() + "," +
                            carta.getDanoPSegundo() + "," +
                            carta.getAlcance() + "," +
                            carta.getVelocidadeImpacto()
            );

        } catch (IOException e) {
            System.out.println("Erro ao salvar carta.");
        }
    }

}
