package controller;
import listaLigada.*;
import entities.*;
import javax.swing.JLabel;

/**
 * Classe Armazenador
 * A classe Armazenador contem uma lista duplamente ligada circular com todos os individuos
 * Armazena os mortos em um vetor dinamico
 */
public class Armazenador {
    public IListaDuplamenteLigadaCircular armazenador; // lista duplamente ligada circular com todos os individuos
    public IVetDin mortos; // vetor dinamico com os mortos
    
    /**
     * Método simular
     *
     * @int qtd - parâmetro para quantidade de Pessoas
     * @int m - parâmetro para contador de passos
     */
    public void simular(int qtd, int m){ // inicializa a simulacao
        inicializarArray(); // inicializa o array
        criarPessoas(qtd); // cria todas as novas pessoas, dada a quantidade
        matarPessoas(qtd,m); // mata as pessoas na ordem
        ultimoPessoa(qtd); // retorna ultima pessoa
    }
        
    /**
     * Método criarPessoas
     *
     * @int qtd - parâmetro para quantidade de Pessoas
     */
    private void criarPessoas(int qtd){ // cria novas pessoas e insere no armazenador
        for(int i = 0; i < qtd; i++){
            Pessoa Pessoa = new Pessoa(true, i);
            armazenador.inserirFim(Pessoa); // insere na lista duplamente ligada circular
        }        
    }
    
    /**
     * Método matarPessoas
     *
     * @int qtd - parâmetro para quantidade de Pessoas
     * @int m - parâmetro para contador de passos
     */
    private void matarPessoas(int qtd, int m){ // mata as pessoas: logica do jogo
        int morto = 0; // quantidade de mortos
        Pessoa proxMorto = (Pessoa) armazenador.buscar(0); // proxima pessoa a ser morta
        int count = 0, j = 0, k = 0; // variaveis para monitoramento
        
        for(int i = 0; i < qtd-1; i++) { // percorre as pessoas
            count = 0;
            while(count != m){
                proxMorto = (Pessoa) armazenador.buscar(j);
                if(proxMorto.getStatus()){ // se o status for vivo
                    count++; 
                }
                j++;
                j = j % qtd;
            }
            proxMorto.setStatus(false); // set o status da pessoa como falso (morto)
            k = j;
            if(k == 0) k = qtd; 
            mortos.adicionar(k); // adiciona morto ao vetor dinamico
        }
    }
    
    /**
     * Método ultimoPessoa
     *
     * @int qtd - parâmetro para quantidade de Pessoas
     */
    private void ultimoPessoa(int qtd){
        Pessoa ultimo = (Pessoa) armazenador.buscar(0);
        int count = 0;
        int vivo = 0;
        int sinal = 0;
        while(count < qtd && sinal == 0){
            ultimo = (Pessoa) armazenador.buscar(count);
            count++;
            if(ultimo.getStatus()){
                vivo = count;
                sinal = 1;
            }
        }
        mortos.adicionar(vivo);
    }
    
    /**
     * Método inicializarArray
     *
     */
    public void inicializarArray(){
        armazenador = new ListaDuplamenteLigadaCircular();
        mortos = new VetDin();
    }
}
