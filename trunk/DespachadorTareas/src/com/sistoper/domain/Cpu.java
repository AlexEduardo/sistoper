/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistoper.domain;

/**
 *
 * @author D314721
 */
public class Cpu {
    
    private Proceso proceso = null;
    
    private Integer id;
    
    public Cpu (Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }
}
