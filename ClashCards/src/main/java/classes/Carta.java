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
    private TipoCarta tipo;
    private Raridade raridade;

    public Carta(String nome, int custoElixir, int nivel, int dano, int vida, int velocidade,
                 double danoPSegundo, double alcance, double velocidadeImpacto,
                 TipoCarta tipo, Raridade raridade) { // Recebe Enums
        this.nome = nome;
        this.custoElixir = custoElixir;
        this.nivel = nivel;
        this.dano = dano;
        this.vida = vida;
        this.velocidade = velocidade;
        this.danoPSegundo = danoPSegundo;
        this.alcance = alcance;
        this.velocidadeImpacto = velocidadeImpacto;
        this.tipo = tipo;
        this.raridade = raridade;
    }

    public String toString() {
        String var10000 = this.nome;
        return "Carta: [Nome='" + var10000 + "', Elixir=" + this.custoElixir + ", Nível=" + this.nivel + ", Dano=" + this.getDano() + ", Vida=" + this.getVida() + ", Velocidade='" + this.getVelocidade() + "', DPS=" + this.getDanoPSegundo() + ", Alcance='" + this.getAlcance() + "', Vel. Impacto=" + this.getVelocidadeImpacto() + "]";
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCustoElixir() {
        return this.custoElixir;
    }

    public void setCustoElixir(int custoElixir) {
        this.custoElixir = custoElixir;
    }

    public int getNivel() {
        return this.nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getDano() {
        return this.dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getVida() {
        return this.vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVelocidade() {
        return this.velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public double getDanoPSegundo() {
        return this.danoPSegundo;
    }

    public void setDanoPSegundo(double danoPSegundo) {
        this.danoPSegundo = danoPSegundo;
    }

    public double getAlcance() {
        return this.alcance;
    }

    public void setAlcance(double alcance) {
        this.alcance = alcance;
    }

    public double getVelocidadeImpacto() {
        return this.velocidadeImpacto;
    }

    public void setVelocidadeImpacto(double velocidadeImpacto) {
        this.velocidadeImpacto = velocidadeImpacto;
    }

    public TipoCarta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCarta tipo) {
        this.tipo = tipo;
    }

    public Raridade getRaridade() {
        return raridade;
    }

    public void setRaridade(Raridade raridade) {
        this.raridade = raridade;
    }
}
