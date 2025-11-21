package classes;

import java.util.ArrayList;
import java.util.List;

public class Baralho {

    private List<Carta> cartas;
    private double custoMedioElixir;

    public Baralho() {
        cartas = new ArrayList<>();
        custoMedioElixir = 0;
    }

    public void adicionarCarta(Carta carta) {
        if (cartas.size() < 8) {
            cartas.add(carta);
            atualizarCustoMedio();
        } else {
            System.out.println("O baralho já tem 8 cartas.");
        }
    }

    public void removerCarta(String nome) {
        for (int i = 0; i < cartas.size(); i++) {
            if (cartas.get(i).getNome().equalsIgnoreCase(nome)) {
                cartas.remove(i);
                break;
            }
        }
        atualizarCustoMedio();
    }

    private void atualizarCustoMedio() {
        if (cartas.isEmpty()) {
            custoMedioElixir = 0;
            return;
        }

        double soma = 0;
        for (Carta c : cartas) {
            soma += c.getCustoElixir();
        }

        custoMedioElixir = soma / cartas.size();
    }

    public double getCustoMedioElixir() {
        return custoMedioElixir;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void mostrarBaralho() {
        System.out.println("===== BARALHO =====");
        for (Carta c : cartas) {
            System.out.println(c.getNome() + " - Elixir: " + c.getCustoElixir());
        }
        System.out.println("Custo médio: " + custoMedioElixir);
    }
}

