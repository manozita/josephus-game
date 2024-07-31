package listaLigada;


/**
 * Escreva uma descrição da classe IVetDin aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public interface IVetDin
{
    public void adicionar(Object a);
    public Object remover(int i);
    public boolean estaVazia();
    public Object buscar (int i);
    public int getQtd();
}
