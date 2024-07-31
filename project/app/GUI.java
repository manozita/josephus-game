package app;
import controller.*;
import entities.*;
import gui.*;

/**
 * bliotecas importadas para uso ao longo do programa
 */
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.geom.*;
import java.util.concurrent.TimeUnit;

/**
 * Classe GUI
 * GUI principal do programa, utilizando JFrame
 */
public class GUI implements MouseListener, 
ActionListener, MouseMotionListener{
    ISaida saida = new Saida();                 // para instancias de saida
    int qtd = 50;
    int pas = 10;
    double qtdVel = 1;                        // inicializa qtdVel, qtd e pas com o valor base de entrada
    
    private static int i = 0;
    private static boolean simulando = false;   // variaveis globais que serao utilizadas para monitoramento de pausa do programa

    Simulacao simulacao;                        // cria uma nova instancia de simulacao

    Controller app = new Controller();          // novo controller chamado app

    JLabel []label;                             // lista de label

    Color white = new Color(255,255,255);       // cor branca
    Color red = new Color(255,149,149);         // cor vermelha
    Color green = new Color(144,238,144);       // cor verde

    Container pane;                             // container principal
    JPanel content = new JPanel();              // painel de conteúdo
    JPanel botoes = new JPanel();               // painel de botões
    JPanel caixas = new JPanel();               // painel de caixas de texto
    JFrame frame;                               // frame principal

    JButton bIniciar;                           // botão iniciar
    JButton bPausar;                            // botão pausar
    JButton bReiniciar;                         // botão reiniciar
    JButton bSair;                              // botão sair
    JButton bAplicar;                           // botão aplicar
    JButton bRelatorio;                         // botão relatorio
    JButton bSobre;                             // botão sobre

    JLabel labelQtd, labelPassos, labelVeloc, labelSob; // labels
    JTextField txfQtd, txfPassos, txfVeloc;     // campos de texto
    JComboBox cbVel;                            // combo box para velocidade

    /**
     * GUI Construtor
     *
     * @int qtd - parâmetro para quantidade de individuos
     * @int pas - parâmetro para contador de passos
     */
    public GUI (int qtd, int pas){
        this.qtd = qtd;
        this.pas = pas;
        
        frame = new JFrame("Simulador Josephus");               // define titulo
        frame.setSize(750,750);                                 // define tamanho
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // define acao ao encerrar

        frame.setLocationRelativeTo(null);      // centraliza a janela na tela

        app.simular(qtd,pas);                   // cria uma nova simulacao

        label = new JLabel[qtd];                // inicializa o array de labels

        content.setLayout(new GridLayout(12, 10, 2, 2)); // layout do painel de conteúdo

        botoes = new JPanel();
        botoes.setLayout (new FlowLayout (FlowLayout.CENTER)); // layout do painel de botões
        caixas = new JPanel();
        caixas.setLayout (new FlowLayout(FlowLayout.CENTER, 35, 0)); // layout do painel de caixas de texto
    }

    /**
     * Método mostraGUI, inicia a inteface
     *
     */
    public void mostraGUI() {        
        JFrame.setDefaultLookAndFeelDecorated(true); // estilo Java (default)
        // define a janela e adiciona os componentes.
        adicionaComponentes();

        // mostra a janela.
        frame.setVisible(true);
    }

    /**
     * Método adicionaComponentes insere os componentes no pane
     *
     */
    public void adicionaComponentes(){
        // pega o container da janela principal
        pane = frame.getContentPane();

        // insere painel de ambiente e as celulas(jlabels)
        inserePainelJoses(pane);

        // insere painel de botoes e os botoes 
        inserePainelBotoes(pane);
    }

    /**
     * Método inserePainelJoses
     *
     * @param pane - painel gŕafico
     */
    private void inserePainelJoses(Container pane){
        for (int k = 0; k < label.length; k++) {
            int j = k + 1;            
            label[k] = new JLabel("" + j, 0);   // cria um novo JLabel com o índice do indivíduo
            label[k].setOpaque(true);           // define o label como opaco
            label[k].setBackground(white);      // define o fundo do label como branco

            content.add(label[k]);              // adiciona o label ao painel de conteúdo
        }
        pane.add(content);                      // adiciona o painel de conteúdo ao container principal
    }

    /**
     * Método inserePainelBotoes
     *
     * @param pane - painel gráfico
     */
    private void inserePainelBotoes(Container pane){
        // cria os botoes (Iniciar, Pausar, Reiniciar, Aplicar, Relatorio, Sair e Sobre)
        bIniciar = new JButton ("Iniciar");                 //botao de iniciar o jogo
        bIniciar.setToolTipText("Inicia Simulacao");        // define tooltip para o botão
        bIniciar.setPreferredSize(new Dimension(100,40));   // define o tamanho do botão
        bIniciar.addMouseListener(this);                    // adiciona o listener de mouse

        bPausar = new JButton ("Pausar");                   // botao de pausar o jogo
        bPausar.setToolTipText("Pausa Simulacao");          // define tooltip para o botão
        bPausar.setPreferredSize(new Dimension(100,40));    // define tamanho do botão
        bPausar.setEnabled(false);                          // desabilita o botão inicialmente
        bPausar.removeMouseListener(this);
        // os botoes pausar e relatorio so serao clicaveis apos o inicio da simulacao

        bReiniciar = new JButton ("Reiniciar");             // botao de reiniciar o jogo
        bReiniciar.setToolTipText("Reinicia a Simulacao");    // define tooltip para o botão
        bReiniciar.setPreferredSize(new Dimension(100,40)); // define tamanho do botao
        bReiniciar.addMouseListener(this);

        bAplicar = new JButton ("Aplicar");                 // botao para aplicar alteracoes
        bAplicar.setToolTipText("Aplica novas configuracoes"); // define tooltip para o botão
        bAplicar.setPreferredSize(new Dimension(100,40));
        bAplicar.addMouseListener(this);                    // listener de mouse

        bRelatorio = new JButton ("Relatorio");             // botao para visualizar relatorio
        bRelatorio.setToolTipText("Dados coletados da Simulacao");  // define tooltip para o botão
        bRelatorio.setPreferredSize(new Dimension(100,40));
        bRelatorio.setEnabled(false);                       //desabilita o botao inicialmente
        bRelatorio.removeMouseListener(this);
        
        bSair = new JButton ("Sair");                       // botao para terminar o programa
        bSair.setToolTipText("Termina programa");           // define tooltip para o botão
        bSair.setPreferredSize(new Dimension(100,40));
        bSair.addMouseListener(this);

        bSobre = new JButton("?");                          // botao de informacoes sobre o programa
        bSobre.setToolTipText("Sobre programa");            // define tooltip para o botão
        
        bSobre.setBackground(new Color(163,182,188));
        bSobre.setForeground(Color.WHITE);                  // define cores do botao
        bSobre.addMouseListener(this);
        
        //cria a parte de inserção da qtd de individuos, de velocidade e de passos
        labelQtd = new JLabel("Individuos: ", JLabel.RIGHT);    // label da quantidade de individuos
        labelQtd.setPreferredSize(new Dimension(100,40));       
        labelPassos = new JLabel("Passos: ", JLabel.RIGHT);     // label da quantidade de passos
        labelPassos.setPreferredSize(new Dimension(100,40));
        labelVeloc = new JLabel("Velocidade: ", JLabel.RIGHT);  // label da velocidade
        labelVeloc.setPreferredSize(new Dimension(100,40));

        txfQtd = new JTextField(3);
        txfQtd.setPreferredSize(new Dimension(100,27));         // textfield para a quantidade de individuos
        txfPassos = new JTextField(3);                          // textfield para a quantidade de passos
        txfPassos.setPreferredSize(new Dimension(100,27));

        // string para as velocidades possiveis de execucao do programa
        String vel[] = {"1","1.5","2","2.5","3","3.5","4","4.5",
                "5","5.5","6","6.5","7","7.5","8","8.5","9","9.5","10"};
        cbVel = new JComboBox(vel);                             // cria combo box com as velocidades
        cbVel.addActionListener(this);
        
        //insere na interface os botoes
        botoes.add(bIniciar);
        botoes.add(bPausar);
        botoes.add(bReiniciar);
        botoes.add(bAplicar);
        botoes.add(bRelatorio);
        botoes.add(bSair);
        
        //insere na interface as caixas de texto e o painel de seleção
        caixas.add(labelQtd);
        caixas.add(txfQtd);
        caixas.add(labelPassos);
        caixas.add(txfPassos);
        caixas.add(labelVeloc);
        caixas.add(cbVel);
        caixas.add(bSobre);
        
        //localizacao na interface
        pane.add("South", botoes);
        pane.add("North", caixas);
    }

    /**
     * Método mousePressed
     *
     * le o evento de acordo com o click do mouse
     * metodo usado para leitura dos botoes clicados
     */
    public void mousePressed(MouseEvent e) {
        Component c = e.getComponent();         // componente do evento de click do mouse
        
        if (c instanceof JButton) {             // se o componente for o click em um botao
            JButton jb = (JButton) c;
            switch (jb.getText()) {             // casos para cada possivel botao clicado
                case "Iniciar":                 // botao para iniciar o jogo
                    if (!simulando) {           // se o jogo nao estiver pausado, inicia-se o novo jogo
                        i = 0;
                        simulando = true;
                    }
                    iniciaSimulacao();
                    break;
                case "Pausar": // pausar o jogo ja iniciado
                    finalizaSimulacao();
                    break;
                case "Reiniciar": // reinicia o jogo iniciado
                    simulando = false;
                    reiniciarPainel(pane);
                    inserePainelJoses(pane);
                    inicializaBotoes();
                    break;
                case "Aplicar": // aplicar alteracoes feitas na quantidade de passos e de individuos
                    String ind, passos;
                    ind = txfQtd.getText();        //armazena a qtd de individuo digitada
                    passos = txfPassos.getText();  //armazena a qtd de passos digitada
                    if (Utils.isNumeric(ind) != 1 || Utils.isNumeric(passos) != 1) { // se os numeros nao sao valores inteiros
                        saida.imprimirErro("ERRO DE LEITURA","Insira valores inteiros validos.");
                    }
                    else { // numeros inteiros nao vazios
                        qtd = Integer.parseInt(txfQtd.getText());
                        pas = Integer.parseInt(txfPassos.getText()); // verificacao ja foi feita
                        if (qtd < 2 || qtd > 120 || pas < 2 || pas > 120) { // se os numeros nao pertencem ao intervalo possivel
                            saida.imprimirErro("ERRO DE LEITURA","Apenas numeros de 2 a 120.");
                        }
                        else if (qtd < pas) { // se a quantidade de passos for maior do que a de individuos
                            saida.imprimirErro("ERRO DE LEITURA","A quantidade de individuos nao pode ser menor do que os passos.");
                        }
                        else { // torna-se valido aplicar as alteracoes
                            simulando = false; // reinicia o painel, reinsere individuos e recomeca todo o jogo
                            reiniciarPainel(pane); 
                            inserePainelJoses(pane);
                            app.simular(qtd, pas);
                        }
                    } 
                    break;
                case "?": // sobre o programa
                    saida.imprimirMensagem("PROGRAMA JOSEPHUS\n" + 
                                                   "Implementa o algoritmo de Josephus\n" +
                                                   "em Java, utilizando interface de imagem e\n" + 
                                                   "Lista Duplamente Ligada Circular para armazenamento.\n" + 
                                                   "Para comecar, insira valores para o numero de individuos,\n" +
                                                   "a o pace e a velocidade. Divirta-se!");
                    break;
                case "Relatorio": // mostrar relatorio de simulacoes (somente valido apos inicio)
                    imprimirRelatorio();
                    break;
                case "Sair": // sair do jogo
                    System.exit(0);
                    break;
            }
        }
    }
 
    
    /**
     * Classe que executa a simulação com Threads para interrupções
     * executa a simulacao do programa
     */
    private class Simulacao extends Thread {
        public boolean continuar = true;
        /**
         * Método run, executa a simulação
         */
        public void run() {
            try {
                int morreu = 0; // numero de individuos mortos
                int time; // tempo de intervalo
                
                //loop que verifica tambem se simulacao eh null, para assim conseguir pausar o programa
                while(simulacao != null && i < label.length-1){
                    morreu = (int) app.getVetor().buscar(i);  //pega cada valor armazenado no array
                    label[morreu-1].setBackground(red);       // define o fundo como vermelho

                    time = (int) (qtdVel * 1000);             // define o tempo de intervalo

                    TimeUnit.MILLISECONDS.sleep(time);        // intervalo durante esse tempo
                    i++;
                }

                if(simulacao != null) { // todos os mortos ja foram mortos
                    morreu = (int) app.getVetor().buscar(label.length-1);   //pega o ultimo valor armazenado(o que sobrou)
                    label[morreu-1].setBackground(green);                   // define o fundo como verde (vivo)
                    
                    reiniciaSimulacao();  //funcao que reinicia os botoes
                }

            } catch (InterruptedException e) { // possiveis erros
                JOptionPane.showMessageDialog(null, "ERRO INESPERADO");
                System.exit(0);
            }
        }
    }

    /**
     * Método iniciaSimulacao
     * metodo usado para iniciar a simulacao do jogo
     */
    public void iniciaSimulacao() {
        simulacao = new Simulacao();
        simulacao.start();   //inicia a funcao
        
        // no inicio da simulacao, os botoes iniciar, pausar, reiniciar, relatorio e aplicar nao podem ser clicados
        bIniciar.setEnabled(false);             // desativa botao iniciar
        bIniciar.removeMouseListener(this);

        bPausar.setEnabled(true);               // desativa botao pausar
        bPausar.addMouseListener(this);

        bReiniciar.setEnabled(false);           // desativa botao reiniciar
        bReiniciar.removeMouseListener(this);
        
        bRelatorio.setEnabled(false);           // desativa botao relatorio
        bRelatorio.removeMouseListener(this);
        
        bAplicar.setEnabled(false);             // desativa botao aplicar
        bAplicar.removeMouseListener(this);
    }

    /**
     * Método finalizaSimulacao
     *
     */
    public void finalizaSimulacao() {
        // no fim da simulacao, os botoes iniciar, pausar, reiniciar, aplicar e relatorio ja sao clicaveis
        bIniciar.setEnabled(true);              // ativa botao iniciar
        bIniciar.addMouseListener(this);

        bPausar.setEnabled(false);              // ativa botao pausar
        bPausar.removeMouseListener(this);

        bReiniciar.setEnabled(true);            // ativa botao reiniciar
        bReiniciar.addMouseListener(this);

        bAplicar.setEnabled(true);              // ativa botao aplicar
        bAplicar.addMouseListener(this);
        
        bRelatorio.setEnabled(true);            // ativa botao relatorio
        bRelatorio.addMouseListener(this);
        
        simulacao = null;                       // agora a simulacao nao esta mais sendo feita
    }

    /**
     * Método reiniciaSimulacao
     *
     */
    public void reiniciaSimulacao(){
        // ao reiniciar, os botoes sao de novo clicaveis, mas nao o pausar
        bPausar.setEnabled(false);              // desativa botao pausar
        bPausar.removeMouseListener(this);

        bReiniciar.setEnabled(true);            // ativa botao reiniciar
        bReiniciar.addMouseListener(this);
        
        bRelatorio.setEnabled(true);            // ativa botao relatorio
        bRelatorio.addMouseListener(this);
    }
    
    /**
     * Método inicializaBotoes
     *
     */
    public void inicializaBotoes(){
        // inicializa os botoes para simulacao
        bIniciar.setEnabled(true);          // botao de iniciar
        bIniciar.addMouseListener(this);

        bPausar.setEnabled(false);          // botao de pausar
        bPausar.removeMouseListener(this);

        bAplicar.setEnabled(true);          // botao de aplicar
        bAplicar.addMouseListener(this);
        
        bRelatorio.setEnabled(false);       // botao de relatorio
        bRelatorio.removeMouseListener(this);
    }

    // metodos de acoes que nao tem efeito
    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    // acao para caso a velocidade seja alterada
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cbVel){
            String item = cbVel.getSelectedItem().toString();
            qtdVel = Double.parseDouble(item); // atualiza qtdVel
        }
    }  

    /**
     * Método reiniciarPainel
     *
     * reinicia o painel grafico
     */
    private void reiniciarPainel(Container pane){
        //permite que muda a qtd de individuos na interface (numeros de retangulos)
        content.revalidate();
        content.repaint();
        content.removeAll();

        label = new JLabel[qtd]; // cria um novo array de label com a quantidade atualizada
    }
    
    private void imprimirRelatorio() { // mostra o relatorio
        String txt = "Ordem: [";
        for (int j = 0; j < i; j++) { // imprime cada individuo que ja foi morto
            txt+= " " + app.getVetor().buscar(j);
        }
        txt+= "]\n";
        txt += "Ordem Final: "; // imprime individuos na ordem final, incluindo individuo vivo
        txt = txt + app.getVetor().toString() + "\nSobrou: " + app.getVetor().buscar(qtd-1); 
        saida.imprimirMensagem(txt); // imprime mensagem na saida
    }
}
