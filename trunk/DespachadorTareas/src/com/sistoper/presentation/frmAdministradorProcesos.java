/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmAdministradorProcesos.java
 *
 * Created on 17-jun-2012, 19:21:08
 */
package com.sistoper.presentation;

import com.sistoper.business.BusinessFactory;
import com.sistoper.business.Despachador;
import com.sistoper.business.IDespachador;
import com.sistoper.domain.Proceso;
import com.sistoper.domain.Programa;
import com.sistoper.utils.EstadoProceso;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mathias
 */
public class frmAdministradorProcesos extends javax.swing.JInternalFrame implements Observer {

    private DefaultTableModel tmProgramas;
    private DefaultTableModel tmProcesos;
    private DefaultTableModel tmMonitoreo;

    /**
     * Creates new form frmAdministradorProcesos
     */
    public frmAdministradorProcesos() {
        initComponents();
        //Inicializo los modelos    
        tmProgramas = new DefaultTableModel();
        tmProcesos = new DefaultTableModel();
        tmMonitoreo = new DefaultTableModel();
        //Asigno los modelos a las tablas
        tblAplicaciones.setModel(tmProgramas);
        tblProcesos.setModel(tmProcesos);
        tblMonitoreo.setModel(tmMonitoreo);
        //Creo las columnas de los modelos
        //Aplicaciones
        tmProgramas.addColumn("Nombre");
        tmProgramas.addColumn("#Procesos");
        //Procesos
        tmProcesos.addColumn("Id");
        tmProcesos.addColumn("Nombre");
        tmProcesos.addColumn("Estado");
        tmProcesos.addColumn("Prioridad");
        tmProcesos.addColumn("% Ejecutado");
        //Monitoreo
        tmMonitoreo.addColumn("Id");
        tmMonitoreo.addColumn("Nombre");
        tmMonitoreo.addColumn("% Ejecutado");
        
        tblProcesos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if( e.getButton() == java.awt.event.MouseEvent.BUTTON3 ){
                    if (tblProcesos.getSelectedRow() != -1){
                        pumProceso.setLocation(e.getXOnScreen(), e.getYOnScreen());
                        pumProceso.setVisible(true);
                    }
                }
            }
        });
        
    }

    public void agregarPrograma(Programa p) {
        String[] aAgregar = new String[2];
        aAgregar[0] = p.getNombre();
        aAgregar[1] = String.valueOf(p.getProcesos().size());
        tmProgramas.addRow(aAgregar);
    }

    public synchronized void agregarProceso(Proceso p) {
        String[] aAgregar = new String[5];
        aAgregar[0] = String.valueOf(p.getId());
        aAgregar[1] = p.getNombre();
        aAgregar[2] = p.getEstado().toString();
        aAgregar[3] = String.valueOf(p.getPrioridad());
        Integer porcEje = (p.getTiempoEjecutado() * 100) / p.getTiempoEjecucion();
        aAgregar[4] = String.valueOf(porcEje);
        tmProcesos.addRow(aAgregar);
    }

    public void agregarMonitoreo(String id, String nom, String ejec) {
        String[] aAgregar = new String[3];
        aAgregar[0] = id;
        aAgregar[1] = nom;
        aAgregar[2] = ejec;
        tmMonitoreo.addRow(aAgregar);
    }

    public void editarProceso(Proceso p) {
        int nroFilas = tmProcesos.getRowCount() - 1;
        boolean encontro = false;
        Integer posicion = 0;
        for (int i = 0; i <= nroFilas; i++) {
            int auxValor = Integer.parseInt(tmProcesos.getValueAt(i, 0).toString());
            if (auxValor == p.getId()) {
                tmProcesos.setValueAt(p.getEstado().toString(), i, 2);
                tmProcesos.setValueAt(String.valueOf(p.getPrioridad()), i, 3);
                Integer porcEje = (p.getTiempoEjecutado() * 100) / p.getTiempoEjecucion();
                tmProcesos.setValueAt(String.valueOf(porcEje), i, 4);
                encontro = true;
                posicion = i;
            }
        }
        //Si no encontro y a su vez el estado es distinto a finalizado lo agrego
        if (!encontro && p.getEstado() != EstadoProceso.FINALIZADO){
            this.agregarProceso(p);
        }else if (encontro && p.getEstado() == EstadoProceso.FINALIZADO){
            //Si esta finalizado elimino de la tabla el proceso
            tmProcesos.removeRow(posicion);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pumProceso = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        tpPrincipal = new javax.swing.JTabbedPane();
        pAplicaciones = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAplicaciones = new javax.swing.JTable();
        pProcesos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProcesos = new javax.swing.JTable();
        btnFinalizar = new javax.swing.JButton();
        btnSuspender = new javax.swing.JButton();
        btnReanudar = new javax.swing.JButton();
        Historial = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMonitoreo = new javax.swing.JTable();

        pumProceso.setName("pumProceso"); // NOI18N

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/sistoper/presentation/Bundle"); // NOI18N
        jMenuItem1.setText(bundle.getString("frmAdministradorProcesos.jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        pumProceso.add(jMenuItem1);

        setName("Form"); // NOI18N

        tpPrincipal.setName("tpPrincipal"); // NOI18N

        pAplicaciones.setName("pAplicaciones"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tblAplicaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tblAplicaciones.setName("tblAplicaciones"); // NOI18N
        tblAplicaciones.setShowHorizontalLines(false);
        tblAplicaciones.setShowVerticalLines(false);
        jScrollPane1.setViewportView(tblAplicaciones);

        org.jdesktop.layout.GroupLayout pAplicacionesLayout = new org.jdesktop.layout.GroupLayout(pAplicaciones);
        pAplicaciones.setLayout(pAplicacionesLayout);
        pAplicacionesLayout.setHorizontalGroup(
            pAplicacionesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pAplicacionesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addContainerGap())
        );
        pAplicacionesLayout.setVerticalGroup(
            pAplicacionesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pAplicacionesLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                .addContainerGap())
        );

        tpPrincipal.addTab("Aplicaciones", pAplicaciones);

        pProcesos.setName("pProcesos"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        tblProcesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProcesos.setName("tblProcesos"); // NOI18N
        tblProcesos.setShowHorizontalLines(false);
        tblProcesos.setShowVerticalLines(false);
        jScrollPane2.setViewportView(tblProcesos);

        btnFinalizar.setText(bundle.getString("frmAdministradorProcesos.btnFinalizar.text")); // NOI18N
        btnFinalizar.setName("btnFinalizar"); // NOI18N
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        btnSuspender.setText(bundle.getString("frmAdministradorProcesos.btnSuspender.text")); // NOI18N
        btnSuspender.setName("btnSuspender"); // NOI18N
        btnSuspender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuspenderActionPerformed(evt);
            }
        });

        btnReanudar.setText(bundle.getString("frmAdministradorProcesos.btnReanudar.text")); // NOI18N
        btnReanudar.setName("btnReanudar"); // NOI18N
        btnReanudar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReanudarActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout pProcesosLayout = new org.jdesktop.layout.GroupLayout(pProcesos);
        pProcesos.setLayout(pProcesosLayout);
        pProcesosLayout.setHorizontalGroup(
            pProcesosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pProcesosLayout.createSequentialGroup()
                .addContainerGap()
                .add(pProcesosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                    .add(pProcesosLayout.createSequentialGroup()
                        .add(btnSuspender)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnReanudar)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 35, Short.MAX_VALUE)
                        .add(btnFinalizar)))
                .addContainerGap())
        );
        pProcesosLayout.setVerticalGroup(
            pProcesosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pProcesosLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 396, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pProcesosLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnSuspender)
                    .add(btnFinalizar)
                    .add(btnReanudar))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tpPrincipal.addTab("Procesos", pProcesos);

        Historial.setName("Historial"); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Historial de uso de CPU"));
        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        tblMonitoreo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Proceso", "Tiempo Restante"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMonitoreo.setName("tblMonitoreo"); // NOI18N
        tblMonitoreo.setShowHorizontalLines(false);
        tblMonitoreo.setShowVerticalLines(false);
        jScrollPane3.setViewportView(tblMonitoreo);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout HistorialLayout = new org.jdesktop.layout.GroupLayout(Historial);
        Historial.setLayout(HistorialLayout);
        HistorialLayout.setHorizontalGroup(
            HistorialLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(HistorialLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        HistorialLayout.setVerticalGroup(
            HistorialLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(HistorialLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tpPrincipal.addTab("Rendimiento", Historial);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(tpPrincipal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 410, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(tpPrincipal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 500, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
// TODO add your handling code here:
    System.out.println("Click");
}//GEN-LAST:event_jMenuItem1ActionPerformed

private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
// TODO add your handling code here:
    this.finalizarProceso();
}//GEN-LAST:event_btnFinalizarActionPerformed

private void btnSuspenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuspenderActionPerformed
// TODO add your handling code here:
    this.suspenderProceso();
}//GEN-LAST:event_btnSuspenderActionPerformed

private void btnReanudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReanudarActionPerformed
// TODO add your handling code here:
    this.reanudarProceso();
}//GEN-LAST:event_btnReanudarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Historial;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnReanudar;
    private javax.swing.JButton btnSuspender;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel pAplicaciones;
    private javax.swing.JPanel pProcesos;
    private javax.swing.JPopupMenu pumProceso;
    private javax.swing.JTable tblAplicaciones;
    private javax.swing.JTable tblMonitoreo;
    private javax.swing.JTable tblProcesos;
    private javax.swing.JTabbedPane tpPrincipal;
    // End of variables declaration//GEN-END:variables

    public void update(Observable o, Object o1) {
        List<Proceso> colaProcesos = ((Despachador) o).getColaProcesos();
        List<Proceso> colaProcesosBloqueados = ((Despachador) o).getColaProcesosSuspendidos();
        List<Proceso> colaProcesosFinalizados = ((Despachador) o).getColaProcesosFinalizados();
        //int numeroProcesos = tmProcesos.getRowCount();
        //for (int i = 0; i < numeroProcesos; i++) {
        //    tmProcesos.removeRow(0);
        //}        
        Proceso procesoEjecucion = ((Despachador) o).obtenerProcesoEjecucion();
        if (procesoEjecucion != null) {
            //this.agregarProceso(procesoEjecucion);
            this.editarProceso(procesoEjecucion);
        }
        for (Proceso proceso : colaProcesos) {
            //this.agregarProceso(proceso);
            this.editarProceso(proceso);
        }
        for (Proceso proceso : colaProcesosBloqueados) {
            //this.agregarProceso(proceso);
            this.editarProceso(proceso);
        }
        //Recorro los Finalizados para ver de eliminarlos
        for (Proceso proceso : colaProcesosFinalizados){
            this.editarProceso(proceso);
        }
        //this.updateUI();
        //Programas
        List<Programa> listaProgramas = ((Despachador) o).getListadoProgramas();
        int numeroProgramas = tmProgramas.getRowCount();
        for (int i = 0; i < numeroProgramas; i++) {
            tmProgramas.removeRow(0);
        }  
        for (Programa programa : listaProgramas){
            if (this.tieneProcesosActivos(programa))
                this.agregarPrograma(programa);
        }
        //Monitoreo
        if (procesoEjecucion != null) {
            Integer porcEje = (procesoEjecucion.getTiempoEjecutado() * 100) / procesoEjecucion.getTiempoEjecucion();
            String ejecutado = String.valueOf(porcEje);
            this.agregarMonitoreo(String.valueOf(procesoEjecucion.getId()),
                    procesoEjecucion.getNombre(), ejecutado);
        }
    }
    
    private void finalizarProceso(){
        if (tblProcesos.getSelectedRow() != -1){
            int filaSel = tblProcesos.getSelectedRow();
            IDespachador despachador = BusinessFactory.getDespachador();
            Integer id = Integer.parseInt(tmProcesos.getValueAt(filaSel, 0).toString());
            Proceso p = despachador.obtenerProceso(id);
            if (p != null) despachador.finalizarProceso(p);            
        }
    }
    
    private void suspenderProceso(){
        if (tblProcesos.getSelectedRow() != -1){
            int filaSel = tblProcesos.getSelectedRow();
            IDespachador despachador = BusinessFactory.getDespachador();
            Integer id = Integer.parseInt(tmProcesos.getValueAt(filaSel, 0).toString());
            Proceso p = despachador.obtenerProceso(id);
            if (p != null && p.getEstado() != EstadoProceso.SUSPENDIDO) despachador.suspenderProceso(p);            
        }
    }
    
    private void reanudarProceso(){
        if (tblProcesos.getSelectedRow() != -1){
            int filaSel = tblProcesos.getSelectedRow();
            IDespachador despachador = BusinessFactory.getDespachador();
            Integer id = Integer.parseInt(tmProcesos.getValueAt(filaSel, 0).toString());
            Proceso p = despachador.obtenerProceso(id);
            if (p != null && p.getEstado() == EstadoProceso.SUSPENDIDO) despachador.activarProceso(p);
        }
    }
    
    private boolean tieneProcesosActivos(Programa p){
        for (Proceso pro : p.getProcesos()){
            if (pro.getEstado() != EstadoProceso.FINALIZADO)
                return true;
        }
        return false;
    }
}
