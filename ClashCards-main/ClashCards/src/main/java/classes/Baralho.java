package classes;

import java.util.ArrayList;
import java.util.List;

public class Baralho {

    private String nome;
    private List<Carta> cartas;

    public Baralho() {
        this.nome = "Deck";
        this.cartas = new ArrayList<>();
    }

    public Baralho(String nome) {
        this.nome = nome;
        this.cartas = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Carta> getCartas() { return cartas; }

    public boolean adicionarCarta(Carta c) {
        if (cartas.size() >= 8) return false;
        if (cartas.stream().anyMatch(x -> x.getNome().equalsIgnoreCase(c.getNome()))) return false;
        cartas.add(c);
        return true;
    }

    public boolean removerCarta(Carta c) {
        return cartas.remove(c);
    }

    public void limpar() {
        cartas.clear();
    }

    public boolean isCompleto() {
        return cartas.size() == 8;
    }

    public double getCustoMedioElixir() {
        if (cartas.isEmpty()) return 0;
        double soma = 0;
        for (Carta c : cartas) soma += c.getCustoElixir();
        return soma / cartas.size();
    }
}
