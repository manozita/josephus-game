package app;
import gui.*;

/**
 * Classe principal - aplicacao
 * Grupo: Kaua Cordeiro, Luan Capella, Manoela Martedi
 * Data de entrega final: 20/06/2024
 */
public class Aplicacao
{
    public static void main(String[] args){
        //instancia objeto da classe GUI
        GUI aplicacao = new GUI(50,10);
        //metodo que entra no programa Gráfico
        aplicacao.mostraGUI();
    }
}
