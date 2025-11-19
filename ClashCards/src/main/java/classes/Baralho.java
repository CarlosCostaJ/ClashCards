//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package classes;

import java.util.ArrayList;
import java.util.List;

public class Baralho {
    private List<Carta> cartas = new ArrayList();
    private double custoMedioElixir = (double)0.0F;

    public void adicionarCarta(Carta carta) {
        if (this.cartas.size() < 8) {
            if (!this.cartas.contains(carta)) {
                this.cartas.add(carta);
                this.atualizarCustoMedio();
            } else {
                System.out.println("Essa carta já está no baralho!");
            }
        } else {
            System.out.println("O baralho já possui 8 cartas!");
        }

    }

    public void removerCarta(String nome) {
        this.cartas.removeIf((c) -> c.getNome().equalsIgnoreCase(nome));
        this.atualizarCustoMedio();
    }

    private void atualizarCustoMedio() {
        if (this.cartas.isEmpty()) {
            this.custoMedioElixir = (double)0.0F;
        } else {
            int soma = 0;

            for(Carta c : this.cartas) {
                soma += c.getCustoElixir();
            }

            this.custoMedioElixir = (double)soma / (double)this.cartas.size();
        }
    }

    public double getCustoMedioElixir() {
        return this.custoMedioElixir;
    }

    public List<Carta> getCartas() {
        return this.cartas;
    }

    public String toString() {
        return "Baralho{cartas=" + this.cartas + ", custoMedioElixir=" + this.custoMedioElixir + "}";
    }
}
