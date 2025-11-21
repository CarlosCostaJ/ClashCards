package classes;

public class Carta {
    private String nome;
    private int custoElixir;
    private int nivel;
    private int dano;
    private int vida;
    private int velocidade;
    private double danoPSegundo;
    private double alcance;
    private double velocidadeImpacto;

    public Carta(String nome, int custoElixir, int nivel, int dano, int vida, int velocidade,
                 double danoPSegundo, double alcance, double velocidadeImpacto) {

        this.nome = nome;
        this.custoElixir = custoElixir;
        this.nivel = nivel;
        this.dano = dano;
        this.vida = vida;
        this.velocidade = velocidade;
        this.danoPSegundo = danoPSegundo;
        this.alcance = alcance;
        this.velocidadeImpacto = velocidadeImpacto;
    }

    @Override
    public String toString() {
        return nome + " (Elixir: " + custoElixir + ")";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCustoElixir() {
        return custoElixir;
    }

    public void setCustoElixir(int custoElixir) {
        this.custoElixir = custoElixir;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public double getDanoPSegundo() {
        return danoPSegundo;
    }

    public void setDanoPSegundo(double danoPSegundo) {
        this.danoPSegundo = danoPSegundo;
    }

    public double getAlcance() {
        return alcance;
    }

    public void setAlcance(double alcance) {
        this.alcance = alcance;
    }

    public double getVelocidadeImpacto() {
        return velocidadeImpacto;
    }

    public void setVelocidadeImpacto(double velocidadeImpacto) {
        this.velocidadeImpacto = velocidadeImpacto;
    }
}
