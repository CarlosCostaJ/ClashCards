package classes;

import java.util.Objects;

public class Carta {

    public enum Tipo { TROPA, FEITICO, CONSTRUCAO }
    public enum Raridade { COMUM, RARA, EPICA, LENDARIA, CAMPEAO }

    private String nome;
    private int nivel;
    private int custoElixir;
    private Tipo tipo;
    private Raridade raridade;
    private String caminhoImagem; // caminho relativo dentro do projeto (ex: "imagens/gigante.png")
    private int dano;
    private double danoPorSegundo;
    private int pontosVida;
    private String alvos; // ex: "Terra,Ar"
    private double alcance;
    private double velocidade;
    private double velocidadeImpacto;

    public Carta(String nome,
                 int nivel,
                 int custoElixir,
                 Tipo tipo,
                 Raridade raridade,
                 String caminhoImagem,
                 int dano,
                 double danoPorSegundo,
                 int pontosVida,
                 String alvos,
                 double alcance,
                 double velocidade,
                 double velocidadeImpacto) {

        this.nome = nome;
        this.nivel = nivel;
        this.custoElixir = custoElixir;
        this.tipo = tipo;
        this.raridade = raridade;
        this.caminhoImagem = caminhoImagem;
        this.dano = dano;
        this.danoPorSegundo = danoPorSegundo;
        this.pontosVida = pontosVida;
        this.alvos = alvos;
        this.alcance = alcance;
        this.velocidade = velocidade;
        this.velocidadeImpacto = velocidadeImpacto;
    }


    public String getNome() { return nome; }
    public int getNivel() { return nivel; }
    public int getCustoElixir() { return custoElixir; }
    public Tipo getTipo() { return tipo; }
    public Raridade getRaridade() { return raridade; }
    public String getCaminhoImagem() { return caminhoImagem; }
    public int getDano() { return dano; }
    public double getDanoPorSegundo() { return danoPorSegundo; }
    public int getPontosVida() { return pontosVida; }
    public String getAlvos() { return alvos; }
    public double getAlcance() { return alcance; }
    public double getVelocidade() { return velocidade; }
    public double getVelocidadeImpacto() { return velocidadeImpacto; }


    public void setNome(String nome) { this.nome = nome; }
    public void setNivel(int nivel) { this.nivel = nivel; }
    public void setCustoElixir(int custoElixir) { this.custoElixir = custoElixir; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }
    public void setRaridade(Raridade raridade) { this.raridade = raridade; }
    public void setCaminhoImagem(String caminhoImagem) { this.caminhoImagem = caminhoImagem; }
    public void setDano(int dano) { this.dano = dano; }
    public void setDanoPorSegundo(double danoPorSegundo) { this.danoPorSegundo = danoPorSegundo; }
    public void setPontosVida(int pontosVida) { this.pontosVida = pontosVida; }
    public void setAlvos(String alvos) { this.alvos = alvos; }
    public void setAlcance(double alcance) { this.alcance = alcance; }
    public void setVelocidade(double velocidade) { this.velocidade = velocidade; }
    public void setVelocidadeImpacto(double velocidadeImpacto) { this.velocidadeImpacto = velocidadeImpacto; }

    @Override
    public String toString() {
        return nome + " (Elixir: " + custoElixir + ", Niv:" + nivel + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carta)) return false;
        Carta carta = (Carta) o;
        return nome.equalsIgnoreCase(carta.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome.toLowerCase());
    }
}
