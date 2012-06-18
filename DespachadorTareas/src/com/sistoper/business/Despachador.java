/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistoper.business;

import com.sistoper.domain.Cpu;
import com.sistoper.domain.Proceso;
import com.sistoper.domain.Programa;
import com.sistoper.utils.EstadoProceso;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
        cpu = new Cpu(1);
        colaProcesos = new ArrayList<Proceso>();
        listadoProgramas = new ArrayList<Programa>();
    }
    
    public static Despachador getDespachador() {
        if(instance == null) {
            instance = new Despachador();            
        }
        return instance;
    }
    
    
    public Proceso crearProceso(Integer id, String nombre, int prioridad, EstadoProceso estado, Integer tiempoEjecucion) {
        Proceso proceso = new Proceso(id,nombre,prioridad,estado,tiempoEjecucion);
        this.encolarProceso(proceso);
        return proceso;
    }
    
    public void encolarProceso (Proceso proceso) {
        this.colaProcesos.add(proceso);
    } 
    
    public synchronized void asignarProcesoProcesador (Proceso proceso) {        
        this.cpu.setProceso(proceso);
        proceso.setCpu(this.cpu);
    }
    
    public void liberarProcesador() {
        this.cpu.getProceso().setCpu(null);
        this.cpu.setProceso(null);
    }
    
    public synchronized Proceso obtenerProximoProcesoAEjecutar() {
        if (!this.colaProcesos.isEmpty()) {
            this.colaProcesos = this.ordenarPorPrioridad(this.colaProcesos);
            this.colaProcesos = this.ordenarPorTiempo(this.colaProcesos);
            return this.colaProcesos.iterator().next();
        } else {
            return null;
        }        
    }
    
    public synchronized List<Proceso> ordenarPorPrioridad(List<Proceso> listado) {
        Collections.sort(listado, new Comparator() {             
            public int compare(Object arg0, Object arg1) {
                return ((Proceso)arg0).getPrioridad() - ((Proceso)arg1).getPrioridad();
            }            
        });
        return listado;
    }
    
    public synchronized List<Proceso> ordenarPorTiempo(List<Proceso> listado) {
        Collections.sort(listado, new Comparator() {             
            public int compare(Object arg0, Object arg1) {
                Proceso p1 = (Proceso)arg0;
                Proceso p2 = (Proceso)arg1;
                int faltaEjecutar1 = p1.getTiempoEjecucion().intValue() - p1.getTiempoEjecutado().intValue();
                int faltaEjecutar2 = p2.getTiempoEjecucion().intValue() - p2.getTiempoEjecutado().intValue();                        
                int diferenciaPrioridades = p1.getPrioridad() - p2.getPrioridad();                
                return (faltaEjecutar1/(2*diferenciaPrioridades)) - faltaEjecutar2;
            }            
        });
        return listado;
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
    
    public synchronized void setQuantum(Integer quantum) {
        this.quantum = quantum;
    }

    public Integer getQuantum() {
        return quantum;
    }
}
