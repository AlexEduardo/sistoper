/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistoper.business;

import com.sistoper.domain.Cpu;
import com.sistoper.domain.Proceso;
import com.sistoper.domain.Programa;
import com.sistoper.utils.EstadoProceso;
import java.util.*;

/**
 *
 * @author D314721
 */
public class Despachador extends Observable implements IDespachador {

    private static Despachador instance = null;
    private List<Proceso> colaProcesos = null;
    private List<Proceso> colaProcesosSuspendidos = null;
    private List<Proceso> colaProcesosFinalizados = null;
    private List<Programa> listadoProgramas = null;
    private Cpu cpu = null;
    private Integer quantum = 500;

    private Despachador() {
        cpu = new Cpu(1);
        colaProcesos = new ArrayList<Proceso>();
        colaProcesosSuspendidos = new ArrayList<Proceso>();
        colaProcesosFinalizados = new ArrayList<Proceso>();
        listadoProgramas = new ArrayList<Programa>();
    }

    public static Despachador getDespachador() {
        if (instance == null) {
            instance = new Despachador();
        }
        return instance;
    }

    public Proceso crearProceso(String nombre, int prioridad, EstadoProceso estado, Integer tiempoEjecucion) {
        Integer cantidadProcesos = colaProcesos.size() + colaProcesosSuspendidos.size() + colaProcesosFinalizados.size() + 1;
        if (this.obtenerProcesoEjecucion() != null) {
            cantidadProcesos++;
        }
        Proceso proceso = new Proceso(cantidadProcesos, nombre, prioridad, estado, tiempoEjecucion);
        this.encolarProceso(proceso);
        return proceso;
    }

    public void encolarProceso(Proceso proceso) {
        this.colaProcesos.add(proceso);
        this.setChanged();
        this.notifyObservers();
    }

    public synchronized void asignarProcesoProcesador(Proceso proceso) {
        this.cpu.setProceso(proceso);
        if (this.colaProcesos.contains(proceso)) {
            this.colaProcesos.remove(proceso);
        }
        proceso.setEstado(EstadoProceso.EJECUCION);
        proceso.setCpu(this.cpu);
        this.setChanged();
        this.notifyObservers();
    }

    public void liberarProcesador() {
        if (this.cpu.getProceso() != null) {
            this.cpu.getProceso().setCpu(null);
        }
        this.cpu.setProceso(null);
        this.setChanged();
        this.notifyObservers();
    }

    public void activarProceso(Proceso proceso) {
        if (this.colaProcesosSuspendidos.contains(proceso)) {
            this.colaProcesosSuspendidos.remove(proceso);
            this.encolarProceso(proceso);
        }
        if (this.cpu.getProceso().equals(proceso)) {
            this.liberarProcesador();
            this.encolarProceso(proceso);
        }
        proceso.setEstado(EstadoProceso.LISTO);
        this.setChanged();
        this.notifyObservers();
    }

    public void suspenderProceso(Proceso proceso) {
        if (this.colaProcesos.contains(proceso)) {
            this.colaProcesos.remove(proceso);
            this.colaProcesosSuspendidos.add(proceso);
            proceso.setEstado(EstadoProceso.SUSPENDIDO);
        }
        if (this.cpu.getProceso().equals(proceso)) {
            this.liberarProcesador();
            this.colaProcesosSuspendidos.add(proceso);
            proceso.setEstado(EstadoProceso.SUSPENDIDO);
        }
        this.setChanged();
        this.notifyObservers();
    }

    public void finalizarProceso(Proceso proceso) {
        if (this.colaProcesos.contains(proceso)) {
            this.colaProcesos.remove(proceso);
            this.colaProcesosFinalizados.add(proceso);
            proceso.setEstado(EstadoProceso.FINALIZADO);
        }
        if (this.colaProcesosSuspendidos.contains(proceso)) {
            this.colaProcesosSuspendidos.remove(proceso);
            this.colaProcesosFinalizados.add(proceso);
            proceso.setEstado(EstadoProceso.FINALIZADO);
        }
        if (this.cpu.getProceso().equals(proceso)) {
            this.liberarProcesador();
            this.colaProcesosFinalizados.add(proceso);
            proceso.setEstado(EstadoProceso.FINALIZADO);
        }
        this.setChanged();
        this.notifyObservers();
    }

    public synchronized Proceso obtenerProximoProcesoAEjecutar() {
        if (!this.colaProcesos.isEmpty()) {
            this.colaProcesos = this.ordenarPorPrioridad(this.colaProcesos);
            this.colaProcesos = this.ordenarPorTiempo(this.colaProcesos);
            this.setChanged();
            this.notifyObservers();
            return this.colaProcesos.iterator().next();
        } else {
            return null;
        }
    }

    public synchronized List<Proceso> ordenarPorPrioridad(List<Proceso> listado) {
        Collections.sort(listado, new Comparator() {

            public int compare(Object arg0, Object arg1) {
                return ((Proceso) arg0).getPrioridad() - ((Proceso) arg1).getPrioridad();
            }
        });
        return listado;
    }

    public synchronized List<Proceso> ordenarPorTiempo(List<Proceso> listado) {
        Collections.sort(listado, new Comparator() {

            public int compare(Object arg0, Object arg1) {
                Proceso p1 = (Proceso) arg0;
                Proceso p2 = (Proceso) arg1;
                int diferenciaPrioridades = p2.getPrioridad() - p1.getPrioridad();
                if (diferenciaPrioridades == 0) {
                    return p2.getTiempoEjecutado() - p1.getTiempoEjecutado();
                } else {
                    int faltaEjecutar1 = p1.getTiempoEjecucion().intValue() - p1.getTiempoEjecutado().intValue();
                    int faltaEjecutar2 = p2.getTiempoEjecucion().intValue() - p2.getTiempoEjecutado().intValue();
                    if (diferenciaPrioridades < 0) {
                        return (faltaEjecutar1 / (2 * diferenciaPrioridades)) - faltaEjecutar2;
                    } else {
                        return (faltaEjecutar2 / (2 * Math.abs(diferenciaPrioridades))) - faltaEjecutar1;
                    }
                }
            }
        });
        return listado;
    }

    public Proceso obtenerProcesoEjecucion() {
        return this.cpu.getProceso();
    }

    public List<Proceso> getColaProcesos() {
        return colaProcesos;
    }

    public List<Proceso> getColaProcesosSuspendidos() {
        return colaProcesosSuspendidos;
    }

    public List<Proceso> getColaProcesosFinalizados() {
        return colaProcesosFinalizados;
    }

    public List<Programa> getListadoProgramas() {
        return this.listadoProgramas;
    }

    public synchronized void setQuantum(Integer quantum) {
        this.setChanged();
        this.notifyObservers();
        this.quantum = quantum;
    }

    public Integer getQuantum() {
        return quantum;
    }
    
    public Programa crearPrograma(String nombre, List<Proceso> listado) {
        Integer proximoId = listadoProgramas.size() + 1;
        Programa programa = new Programa(proximoId, nombre, listado);
        this.listadoProgramas.add(programa);
        this.setChanged();
        this.notifyObservers();
        return programa;
    }
}
