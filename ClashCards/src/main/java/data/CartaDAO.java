package data;

import classes.Carta;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class CartaDAO {
    File arquivo = new File("C:\\Users\\carlo\\Downloads\\Cartas.txt");

    public void lerCartas() {

        List<Carta> cartas = new ArrayList<Carta>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))){

            String linhaAtual = br.readLine();
            linhaAtual = br.readLine();
            while (linhaAtual!=null) {
                String[] separadorDeAtributos = linhaAtual.split(",");
                String nome = separadorDeAtributos[0];
                int elixir = Integer.parseInt(separadorDeAtributos[1]);
                int nivel = Integer.parseInt(separadorDeAtributos[2]);
                int dano = Integer.parseInt(separadorDeAtributos[3]);
                int vida = Integer.parseInt(separadorDeAtributos[4]);
                int velocidade = Integer.parseInt(separadorDeAtributos[5]);
                double dps = Double.parseDouble(separadorDeAtributos[6]);
                double alcance = Double.parseDouble(separadorDeAtributos[7]);
                double velocidadeATK = Double.parseDouble(separadorDeAtributos[8]);

                Carta carta = new Carta(nome,elixir,nivel,dano,vida,velocidade,dps,alcance,velocidadeATK);
                cartas.add(carta);

                linhaAtual = br.readLine();
            }
            for(Carta c: cartas){
                System.out.println(c.toString());
            }
        } catch (IOException ex) {
            System.out.println("Arquivo não encontrado.");
        }
    }

}
