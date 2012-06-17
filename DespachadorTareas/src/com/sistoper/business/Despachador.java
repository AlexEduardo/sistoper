/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistoper.business;

import com.sistoper.domain.Cpu;
import com.sistoper.domain.Proceso;
import com.sistoper.domain.Programa;
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
    
    private Despachador () {
        
    }
    
    public static Despachador getDespachador() {
        if(instance == null) {
            instance = new Despachador();            
        }
        return instance;
    }
    
    
    public void crearProceso(Integer id, String nombre, String prioridad, String estado, Integer tiempoEjecucion) {
        Proceso proceso = new Proceso(id,nombre,prioridad,estado,tiempoEjecucion);
        this.encolarProceso(proceso);
    }
    
    public void encolarProceso (Proceso proceso) {
        this.colaProcesos.add(proceso);
    }
    
    public void asignarProcesoProcesador () {
        Proceso proceso = this.obtenerProximoProceso();
        this.cpu.setProceso(proceso);
        proceso.setCpu(this.cpu);
    }
    
    public Proceso obtenerProximoProceso() {
        return null;
    }
}
