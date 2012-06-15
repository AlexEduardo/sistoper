/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistoper.domain;

/**
 *
 * @author D314721
 */
public class Proceso {
    
    private Programa programa = null;
    
    private Cpu cpu = null;
    
    private Integer id;
    
    private String nombre;
    
    private String prioridad;
    
    private String estado;
    
    private Integer tiempoEjecucion;
    
    private Integer tiempoEjecutado;
    
    public Proceso (Programa programa) {
        this.programa = programa;
    }
}
