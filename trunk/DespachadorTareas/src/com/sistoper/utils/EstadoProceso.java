/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistoper.utils;

/**
 *
 * @author simon
 */
public enum EstadoProceso {
    
    LISTO(1),
    
    EJECUCION(2),
    
    BLOQUEADO(3),
    
    FINALIZADO(4);    
    
    private int value;
    
    private EstadoProceso (int value) {
        this.value = value;
    }
    
}
