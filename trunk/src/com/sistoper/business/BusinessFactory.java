/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistoper.business;

/**
 *
 * @author D314721
 */
public class BusinessFactory {
    
    public static IDespachador getDespachador() {
        IDespachador instance = Despachador.getDespachador();
        return instance;
    }
    
}
