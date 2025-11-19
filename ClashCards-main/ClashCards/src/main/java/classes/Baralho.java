package classes;

import java.util.ArrayList;
import java.util.List;

public class Baralho {
    private List<Carta> cartas;
    private double custoMedioElixir;

    public Baralho() {
        this.cartas = new ArrayList<>();
        this.custoMedioElixir = 0.0;
    }

    public void adicionarCarta(Carta carta) {
        if (cartas.size() < 8) {
            if (!cartas.contains(carta)) {
                cartas.add(carta);
                atualizarCustoMedio();
            } else {
                System.out.println("Essa carta já está no baralho!");
            }
        } else {
            System.out.println("O baralho já possui 8 cartas!");
        }
    }

    public void removerCarta(String nome) {
        cartas.removeIf(c -> c.getNome().equalsIgnoreCase(nome));
        atualizarCustoMedio();
    }

    private void atualizarCustoMedio() {
        if (cartas.isEmpty()) {
            custoMedioElixir = 0;
            return;
        }

        int soma = 0;
        for (Carta c : cartas) {
            soma += c.getCustoElixir();
        }

        custoMedioElixir = soma / (double) cartas.size();
    }

    public double getCustoMedioElixir() {
        return custoMedioElixir;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    @Override
    public String toString() {
        return "Baralho{" +
                "cartas=" + cartas +
                ", custoMedioElixir=" + custoMedioElixir +
                '}';
    }
}

