/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package despachadortareas;

import com.sistoper.business.BusinessFactory;
import com.sistoper.business.IDespachador;
import com.sistoper.domain.Proceso;
import com.sistoper.utils.EstadoProceso;

/**
 *
 * @author simon
 */
public class SimuladorDespacho extends Thread {

    private boolean prendido;
    private static SimuladorDespacho instance = null;

    private SimuladorDespacho(boolean continuar) {
        super();
        this.prendido = continuar;
    }

    public static SimuladorDespacho getSimuladorDespacho(boolean continuar) {
        if (instance == null) {
            instance = new SimuladorDespacho(continuar);
        }
        return instance;
    }

    public void setPrendido(boolean prendido) {
        this.prendido = prendido;
    }

    @Override
    public void run() {
        IDespachador despachador = BusinessFactory.getDespachador();
        Proceso procesoDespachado = despachador.obtenerProximoProcesoAEjecutar();
        if (procesoDespachado != null) {
            despachador.asignarProcesoProcesador(procesoDespachado);
            despachador.obtenerColaProcesos().remove(procesoDespachado);
        }
        Proceso procesoEjecucion = null;
        int i = 0;
        while (prendido) {
            if (procesoDespachado != null) {
                try {
                    sleep(despachador.getQuantum().longValue());
                    System.out.println("Corrida: " + i + " Proceso: "+ procesoDespachado.getId());
                    i++;
                } catch (Exception e) {
                }
                procesoEjecucion = despachador.obtenerProcesoEjecucion();
                if (procesoDespachado.getId().equals(procesoEjecucion.getId())) {
                    Integer tiempo = procesoEjecucion.getTiempoEjecutado();
                    tiempo = new Integer(tiempo.intValue() + despachador.getQuantum());
                    procesoEjecucion.setTiempoEjecutado(tiempo);
                    if (tiempo.intValue() >= procesoEjecucion.getTiempoEjecucion().intValue()) {
                        procesoEjecucion.setEstado(EstadoProceso.FINALIZADO);
                        despachador.liberarProcesador();
                    } else {
                        despachador.liberarProcesador();
                        procesoEjecucion.setEstado(EstadoProceso.LISTO);
                        despachador.encolarProceso(procesoEjecucion);
                    }
                    procesoDespachado = despachador.obtenerProximoProcesoAEjecutar();
                    if (procesoDespachado != null) {
                        despachador.asignarProcesoProcesador(procesoDespachado);
                        despachador.obtenerColaProcesos().remove(procesoDespachado);
                    }
                } else {
                    procesoDespachado = procesoEjecucion;
                }
            } else {
                procesoDespachado = despachador.obtenerProximoProcesoAEjecutar();
                if (procesoDespachado != null) {
                    despachador.asignarProcesoProcesador(procesoDespachado);
                    despachador.obtenerColaProcesos().remove(procesoDespachado);
                }
            }
        }
    }
}
