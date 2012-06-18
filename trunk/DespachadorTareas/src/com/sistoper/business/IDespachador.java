/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistoper.business;

import com.sistoper.domain.Proceso;
import com.sistoper.domain.Programa;
import com.sistoper.utils.EstadoProceso;
import java.util.List;

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
    
    public void bloquearProceso (Proceso proceso);
    
    public Proceso obtenerProximoProcesoAEjecutar();
    
    public Proceso obtenerProcesoEjecucion();
    
    public List<Proceso> obtenerColaProcesos ();
    
    public List<Programa> obtenerListadoProgramas ();
    
    public void setQuantum(Integer quantum);
    
    public Integer getQuantum();
}
