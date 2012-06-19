/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistoper.business;

import com.sistoper.domain.Proceso;
import com.sistoper.domain.Programa;
import com.sistoper.utils.EstadoProceso;
import java.util.List;
import java.util.Observer;

/**
 *
 * @author D314721
 */
public interface IDespachador {
    
    public Proceso crearProceso(String nombre, int prioridad, EstadoProceso estado, Integer tiempoEjecucion);
    
    public void encolarProceso (Proceso proceso);
    
    public void asignarProcesoProcesador (Proceso proceso);
    
    public void liberarProcesador();
    
    public void activarProceso (Proceso proceso);
    
    public void suspenderProceso (Proceso proceso);
    
    public void finalizarProceso (Proceso proceso);
    
    public Proceso obtenerProximoProcesoAEjecutar();
    
    public Proceso obtenerProcesoEjecucion();
    
    public List<Proceso> getColaProcesos ();
    
    public List<Proceso> getColaProcesosSuspendidos();

    public List<Proceso> getColaProcesosFinalizados();
    
    public List<Programa> getListadoProgramas ();
    
    public void setQuantum(Integer quantum);
    
    public Integer getQuantum();
    
    public void addObserver(Observer obsrvr);
    
    public Programa crearPrograma(String nombre, List<Proceso> listado);
    
    public Proceso obtenerProceso(Integer id);
}
