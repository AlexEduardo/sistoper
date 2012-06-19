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
    private List<Proceso> colaProcesosBloqueados = null;
    private List<Proceso> colaProcesosFinalizados = null;
    private List<Programa> listadoProgramas = null;
    private Cpu cpu = null;
    private Integer quantum = 500;

    private Despachador() {
        cpu = new Cpu(1);
        colaProcesos = new ArrayList<Proceso>();
        colaProcesosBloqueados = new ArrayList<Proceso>();
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
        Integer cantidadProcesos = colaProcesos.size() + colaProcesosBloqueados.size() + colaProcesosFinalizados.size() + 1;
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
        if (this.colaProcesosBloqueados.contains(proceso)) {
            this.colaProcesosBloqueados.remove(proceso);
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

    public void bloquearProceso(Proceso proceso) {
        if (this.colaProcesos.contains(proceso)) {
            this.colaProcesos.remove(proceso);
            this.colaProcesosBloqueados.add(proceso);
            proceso.setEstado(EstadoProceso.BLOQUEADO);
        }
        if (this.cpu.getProceso().equals(proceso)) {
            this.liberarProcesador();
            this.colaProcesosBloqueados.add(proceso);
            proceso.setEstado(EstadoProceso.BLOQUEADO);
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
        if (this.colaProcesosBloqueados.contains(proceso)) {
            this.colaProcesosBloqueados.remove(proceso);
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
                    return p1.getTiempoEjecutado() - p2.getTiempoEjecutado();
                } else {
                    int faltaEjecutar1 = p1.getTiempoEjecucion().intValue() - p1.getTiempoEjecutado().intValue();
                    int faltaEjecutar2 = p2.getTiempoEjecucion().intValue() - p2.getTiempoEjecutado().intValue();
                    if (diferenciaPrioridades > 0) {
                        return faltaEjecutar1 - (faltaEjecutar2 / (2 * diferenciaPrioridades));
                    } else {
                        return faltaEjecutar2 - (faltaEjecutar1 / (2 * Math.abs(diferenciaPrioridades)));
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

    public List<Proceso> getColaProcesosBloqueados() {
        return colaProcesosBloqueados;
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
