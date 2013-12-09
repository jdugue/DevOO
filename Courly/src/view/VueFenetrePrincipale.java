/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.ControleurFenetrePrincipale;
import controller.ControleurPlan;

/**
 *
 * @author tanguyhelesbeux
 */
public class VueFenetrePrincipale extends javax.swing.JFrame {
    
    private ControleurFenetrePrincipale controleurFenetrePrincipale;
    private ControleurPlan controleurPlan;

    /**
     * Creates new form VueFenetrePrincipale
     */
    public VueFenetrePrincipale() {
        
        initComponents();
        
        this.controleurFenetrePrincipale = new ControleurFenetrePrincipale(this);
        this.controleurPlan = new ControleurPlan(this.scrollPanePlan, this.controleurFenetrePrincipale);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPanePlan = new javax.swing.JScrollPane();
        scrollPaneInspecteur = new javax.swing.JScrollPane();
        scrollPaneTextArea = new javax.swing.JScrollPane();
        commentArea = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        menuFichier = new javax.swing.JMenu();
        itemChargerPlan = new javax.swing.JMenuItem();
        itemChargerLivraisons = new javax.swing.JMenuItem();
        menuEdition = new javax.swing.JMenu();
        itemRefaire = new javax.swing.JMenuItem();
        itemAnnuler = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1200, 800));
        setMinimumSize(new java.awt.Dimension(1200, 800));
        setPreferredSize(new java.awt.Dimension(1200, 800));
        setSize(new java.awt.Dimension(1200, 800));

        scrollPanePlan.setMaximumSize(new java.awt.Dimension(800, 678));
        scrollPanePlan.setMinimumSize(new java.awt.Dimension(800, 678));
        scrollPanePlan.setPreferredSize(new java.awt.Dimension(800, 678));
        scrollPanePlan.setSize(new java.awt.Dimension(800, 678));

        commentArea.setColumns(20);
        commentArea.setRows(5);
        scrollPaneTextArea.setViewportView(commentArea);

        menuFichier.setText("Fichier");

        itemChargerPlan.setText("Charger plan...");
        menuFichier.add(itemChargerPlan);

        itemChargerLivraisons.setText("Charger livraisons...");
        itemChargerLivraisons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemChargerLivraisonsActionPerformed(evt);
            }
        });
        menuFichier.add(itemChargerLivraisons);

        menuBar.add(menuFichier);

        menuEdition.setText("Edition");

        itemRefaire.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        itemRefaire.setText("Refaire");
        menuEdition.add(itemRefaire);

        itemAnnuler.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        itemAnnuler.setText("Annuler");
        menuEdition.add(itemAnnuler);

        menuBar.add(menuEdition);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPanePlan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPaneTextArea))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneInspecteur, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollPanePlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneTextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
            .addComponent(scrollPaneInspecteur)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemChargerLivraisonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemChargerLivraisonsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemChargerLivraisonsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VueFenetrePrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VueFenetrePrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VueFenetrePrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VueFenetrePrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VueFenetrePrincipale fenetre = new VueFenetrePrincipale();
                fenetre.setVisible(true);
                
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea commentArea;
    private javax.swing.JMenuItem itemAnnuler;
    private javax.swing.JMenuItem itemChargerLivraisons;
    private javax.swing.JMenuItem itemChargerPlan;
    private javax.swing.JMenuItem itemRefaire;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdition;
    private javax.swing.JMenu menuFichier;
    private javax.swing.JScrollPane scrollPaneInspecteur;
    private javax.swing.JScrollPane scrollPanePlan;
    private javax.swing.JScrollPane scrollPaneTextArea;
    // End of variables declaration//GEN-END:variables
}
