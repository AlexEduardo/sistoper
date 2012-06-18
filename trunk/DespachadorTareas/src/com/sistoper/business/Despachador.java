/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistoper.business;

import com.sistoper.domain.Cpu;
import com.sistoper.domain.Proceso;
import com.sistoper.domain.Programa;
import com.sistoper.utils.EstadoProceso;
import java.util.List;

/**
 *
 * @author D314721
 */
public class Despachador implements IDespachador {
    
    private static Despachador instance = null;    
    
    private List<Proceso> colaProcesos = null;
    
    private List<Programa> listadoProgramas = null;
    
    private Cpu cpu = null;
    
    private Integer quantum = 1000;
    
    private Despachador () {        
    }
    
    public static Despachador getDespachador() {
        if(instance == null) {
            instance = new Despachador();            
        }
        return instance;
    }
    
    
    public Proceso crearProceso(Integer id, String nombre, String prioridad, EstadoProceso estado, Integer tiempoEjecucion) {
        Proceso proceso = new Proceso(id,nombre,prioridad,estado,tiempoEjecucion);
        return proceso;
    }
    
    public void encolarProceso (Proceso proceso) {
        this.colaProcesos.add(proceso);
    }
    
    public void asignarProcesoProcesador (Proceso proceso) {        
        this.cpu.setProceso(proceso);
        proceso.setCpu(this.cpu);
    }
    
    public void liberarProcesador() {
        this.cpu.getProceso().setCpu(null);
        this.cpu.setProceso(null);        
    }
    
    public Proceso obtenerProximoProcesoAEjecutar() {
        return null;
    }
    
    public Proceso obtenerProcesoEjecucion() {
        return this.cpu.getProceso();
    }
    
    public List<Proceso> obtenerColaProcesos () {
        return this.colaProcesos;
    }
    
    public List<Programa> obtenerListadoProgramas () {
        return this.listadoProgramas;
    }
    
    public void setQuantum(Integer quantum) {
        this.quantum = quantum;
    }

    public Integer getQuantum() {
        return quantum;
    }
}
