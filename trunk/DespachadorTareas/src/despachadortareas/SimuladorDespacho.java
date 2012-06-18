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

    private boolean continuar;

    public SimuladorDespacho(boolean continuar) {
        super();
        this.continuar = continuar;
    }

    public void run() {
        IDespachador despachador = BusinessFactory.getDespachador();
        Proceso procesoDespachado = despachador.obtenerProximoProcesoAEjecutar();
        despachador.asignarProcesoProcesador(procesoDespachado);
        despachador.obtenerColaProcesos().remove(procesoDespachado);
        Proceso procesoEjecucion = null;
        while (continuar) {
            try {
                sleep(despachador.getQuantum().longValue());
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
                    despachador.encolarProceso(procesoEjecucion);
                }
                procesoDespachado = despachador.obtenerProximoProcesoAEjecutar();
                despachador.asignarProcesoProcesador(procesoDespachado);
                despachador.obtenerColaProcesos().remove(procesoDespachado);
            } else {
                procesoDespachado = procesoEjecucion;
            }
        }
    }
}
