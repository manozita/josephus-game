package controller;
import entities.*;
import listaLigada.*;
/**
 * Escreva uma descrição da classe Controller aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Controller
{
    Armazenador regras = new Armazenador();             // nova instancia de armazenador
    
    /**
     * Método simular
     *
     * @int qtd - parâmetro para quantidade de objetos
     * @int pas - parâmetro para contador de passos
     */    
    public void simular(int qtd, int m){
        // inicializa variáveis de instância
        regras.simular(qtd, m);
    }
    
    /**
     * Método getVetor
     *
     * @retorna um VetDin que contem a ordem de mortos
     */
    public listaLigada.IVetDin getVetor(){
        return regras.mortos;
    } 
}
