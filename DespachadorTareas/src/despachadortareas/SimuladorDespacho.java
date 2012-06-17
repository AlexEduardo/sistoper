/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package despachadortareas;

/**
 *
 * @author simon
 */
public class SimuladorDespacho extends Thread {
        
    public SimuladorDespacho() {
        super();
    }
    
    public void run() {
        for (int i = 0; i < 10 ; i++)  {
            System.out.println(i + " " + getName());
        }
        System.out.println("Termina thread " + getName());
    }    
}
