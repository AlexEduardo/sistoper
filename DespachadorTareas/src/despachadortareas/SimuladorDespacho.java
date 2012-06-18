/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package despachadortareas;

import com.sistoper.business.BusinessFactory;
import com.sistoper.business.IDespachador;
import com.sistoper.domain.Proceso;
/**
 *
 * @author simon
 */
public class SimuladorDespacho extends Thread {

    private boolean prendido;    

    public SimuladorDespacho(boolean continuar) {
        super();
        this.prendido = continuar;
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
        }
        Proceso procesoEjecucion = null;        
        while (prendido) {
            if (procesoDespachado != null) {
                try {
                    sleep(despachador.getQuantum().longValue());
                    System.out.println("Proceso: "+ procesoDespachado.getId() + " Tiempo: " + procesoDespachado.getTiempoEjecutado());                    
                } catch (Exception e) {
                }
                procesoEjecucion = despachador.obtenerProcesoEjecucion();
                if (procesoDespachado.getId().equals(procesoEjecucion.getId())) {
                    Integer tiempo = procesoEjecucion.getTiempoEjecutado();
                    tiempo = new Integer(tiempo.intValue() + despachador.getQuantum());
                    procesoEjecucion.setTiempoEjecutado(tiempo);
                    if (tiempo.intValue() >= procesoEjecucion.getTiempoEjecucion().intValue()) {
                        despachador.finalizarProceso(procesoEjecucion);
                        despachador.liberarProcesador();
                    } else {                        
                        despachador.activarProceso(procesoEjecucion);
                        despachador.liberarProcesador();
                    }
                    procesoDespachado = despachador.obtenerProximoProcesoAEjecutar();
                    if (procesoDespachado != null) {
                        despachador.asignarProcesoProcesador(procesoDespachado);
                    }
                } else {
                    procesoDespachado = procesoEjecucion;
                }
            } else {
                procesoDespachado = despachador.obtenerProximoProcesoAEjecutar();
                if (procesoDespachado != null) {
                    despachador.asignarProcesoProcesador(procesoDespachado);
                }
            }
        }
        procesoEjecucion = despachador.obtenerProcesoEjecucion();
        if (procesoEjecucion != null) {
            despachador.activarProceso(procesoEjecucion);
            despachador.liberarProcesador();
        }
        interrupt();
    }
}
