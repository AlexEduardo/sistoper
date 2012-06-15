/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistoper.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author D314721
 */
public class Programa {
    
    private List<Proceso> procesos = null;
    
    private Integer id;
    
    private String nombre;
    
    public Programa () {
        this.procesos = new ArrayList<Proceso>();
    }
    
    public Programa (List<Proceso> procesos) {
        this.procesos = procesos;
    }
}
