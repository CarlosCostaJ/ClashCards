package data;

import classes.Carta;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CartaDAO {

    private final File pasta;
    private final File arquivo;
    private final String ARQUIVO = "cartas.csv";
    private final String SEP = ";";

    public CartaDAO(String caminhoPasta) {
        this.pasta = new File(caminhoPasta);
        if (!pasta.exists()) pasta.mkdirs();
        this.arquivo = new File(pasta, ARQUIVO);
    }

    public List<Carta> carregarTodas() {
        List<Carta> lista = new ArrayList<>();
        if (!arquivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] f = linha.split(SEP, -1);
                if (f.length < 13) continue;

                Carta.Tipo tipo = Carta.Tipo.valueOf(f[3].trim().toUpperCase());
                Carta.Raridade rar = Carta.Raridade.valueOf(f[4].trim().toUpperCase());

                Carta c = new Carta(
                        f[0],
                        Integer.parseInt(f[1]),
                        Integer.parseInt(f[2]),
                        tipo,
                        rar,
                        f[5],
                        Integer.parseInt(f[6]),
                        Double.parseDouble(f[7]),
                        Integer.parseInt(f[8]),
                        f[9],
                        Double.parseDouble(f[10]),
                        Double.parseDouble(f[11]),
                        Double.parseDouble(f[12])
                );
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void salvarTodas(List<Carta> cartas) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo), StandardCharsets.UTF_8))) {
            bw.write(String.join(SEP,
                    "NOME","NIVEL","ELIXIR","TIPO","RARIDADE","IMAGEM","DANO","DPS","VIDA","ALVOS","ALCANCE","VELOCIDADE","VEL_IMPACTO"));
            bw.newLine();
            for (Carta c : cartas) {
                String linha = String.join(SEP,
                        escape(c.getNome()),
                        String.valueOf(c.getNivel()),
                        String.valueOf(c.getCustoElixir()),
                        c.getTipo().name(),
                        c.getRaridade().name(),
                        escape(c.getCaminhoImagem()),
                        String.valueOf(c.getDano()),
                        String.valueOf(c.getDanoPorSegundo()),
                        String.valueOf(c.getPontosVida()),
                        escape(c.getAlvos()),
                        String.valueOf(c.getAlcance()),
                        String.valueOf(c.getVelocidade()),
                        String.valueOf(c.getVelocidadeImpacto())
                );
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\r"," ").replace("\n"," ").replace(";", ",");
    }


    public boolean existePorNome(String nome) {
        return carregarTodas().stream().anyMatch(c -> c.getNome().equalsIgnoreCase(nome));
    }
}
