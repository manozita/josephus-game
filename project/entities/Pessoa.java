package entities;

import javax.swing.JLabel;
import java.awt.Font;

public class Pessoa {
    private boolean status; //true ou false para vivo ou morto.
    
    /**
     * Construtor para objetos da classe Pessoa
     */
    public Pessoa(boolean status, int pos){
        setStatus(status);
    }
    
    // setters e getters
    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus(){
        return this.status;
    }
}
