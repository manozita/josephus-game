package listaLigada;
/**
 * Escreva uma descrição da classe IListaDuplamenteLigadaCircular aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public interface IListaDuplamenteLigadaCircular
{
    public boolean estaVazia(); 
    public void inserirInicio(Object novo); 
    public void inserirFim(Object novo);
    public boolean inserirApos(long chave, Object novo);
    public Object removerInicio();
    public Object removerFim();
    public Object remover(long chave);
    public Object buscar(int i);
    public int getQtdNos();
    public String toStringDoFim();
}
