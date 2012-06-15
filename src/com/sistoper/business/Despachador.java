/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistoper.business;

/**
 *
 * @author D314721
 */
public class Despachador implements IDespachador {
    
    private static Despachador instance = null;    
    
    private Despachador () {
        
    }
    
    public static Despachador getDespachador() {
        if(instance == null) {
            instance = new Despachador();            
        }
        return instance;
    }
    
}
