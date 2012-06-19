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
    
    public Programa (Integer id, String nombre, List<Proceso> procesos) {
        this.id = id;
        this.nombre = nombre;
        this.procesos = procesos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Proceso> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<Proceso> procesos) {
        this.procesos = procesos;
    }
}
