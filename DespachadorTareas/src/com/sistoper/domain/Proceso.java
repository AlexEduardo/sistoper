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

    public Proceso(Integer id, String nombre, String prioridad, String estado, Integer tiempoEjecucion) {
        this.id = id;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.estado = estado;
        this.tiempoEjecucion = tiempoEjecucion;
    }
    
    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public Integer getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public void setTiempoEjecucion(Integer tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public Integer getTiempoEjecutado() {
        return tiempoEjecutado;
    }

    public void setTiempoEjecutado(Integer tiempoEjecutado) {
        this.tiempoEjecutado = tiempoEjecutado;
    }
    
}
